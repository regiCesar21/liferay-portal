/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import ActionsDropdownRenderer from '../../data_renderers/ActionsDropdownRenderer';
import CheckboxRenderer from '../../data_renderers/CheckboxRenderer';
import CommentRenderer from '../../data_renderers/CommentRenderer';
import RadioRenderer from '../../data_renderers/RadioRenderer';
import {
	getDataRendererById,
	getDataRendererByURL,
} from '../../data_renderers/index';
import {getValueFromItem} from '../../utilities/index';
import ViewsContext from '../ViewsContext';
import TableHeadRow from './TableHeadRow';

function CustomTableCell({
	actions,
	comment,
	itemData,
	itemId,
	options,
	value,
	view,
}) {
	const [currentView, updateCurrentView] = useState({
		...view,
		Component: view.contentRendererModuleURL
			? null
			: getDataRendererById(view.contentRenderer),
	});
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (loading) {
			return;
		}
		if (currentView.contentRendererModuleURL) {
			setLoading(true);
			getDataRendererByURL(currentView.contentRendererModuleURL).then(
				(Component) => {
					updateCurrentView({
						...currentView,
						Component,
					});
					setLoading(false);
				}
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentView]);

	return (
		<ClayTable.Cell>
			{currentView.Component && !loading ? (
				<currentView.Component
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					value={value}
				/>
			) : (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			)}
			{comment && <CommentRenderer>{comment}</CommentRenderer>}
		</ClayTable.Cell>
	);
}

function getItemFields(item, fields, itemId, itemsActions) {
	return fields.map((field, i) => {
		const {actionDropdownItems, comments} = item;
		const rawValue = getValueFromItem(item, field.fieldName);
		const formattedValue = field.mapData
			? field.mapData(rawValue)
			: rawValue;
		const comment = comments ? comments[field.fieldName] : null;

		return (
			<CustomTableCell
				actions={itemsActions || actionDropdownItems}
				comment={comment}
				itemData={item}
				itemId={itemId}
				key={field.fieldName || i}
				options={field}
				value={formattedValue}
				view={{
					contentRenderer: field.contentRenderer,
					contentRendererModuleURL: field.contentRendererModuleURL,
				}}
			/>
		);
	});
}

export const getVisibleFields = (fields, visibleFieldNames) => {
	const visibleFields = fields.filter(
		({fieldName}) => visibleFieldNames[fieldName]
	);

	return visibleFields.length ? visibleFields : fields;
};

function Table({items, itemsActions, schema, style}) {
	const {
		highlightedItemsValue,
		nestedItemsKey,
		nestedItemsReferenceKey,
		selectItems,
		selectable,
		selectedItemsKey,
		selectedItemsValue,
		selectionType,
		sorting,
		updateSorting,
	} = useContext(DataSetDisplayContext);
	const [{visibleFieldNames}] = useContext(ViewsContext);

	const visibleFields = getVisibleFields(schema.fields, visibleFieldNames);

	const showActionItems = Boolean(
		itemsActions?.length ||
			items.find((item) => item.actions || item.actionDropdownItems)
	);

	const SelectionComponent =
		selectionType === 'multiple' ? CheckboxRenderer : RadioRenderer;

	return (
		<div className={`table-style-${style}`}>
			<ClayTable borderless hover={false} responsive>
				<TableHeadRow
					items={items}
					schema={schema}
					selectItems={selectItems}
					selectable={selectable}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					selectionType={selectionType}
					sorting={sorting}
					updateSorting={updateSorting}
					visibleFields={visibleFields}
				/>
				<ClayTable.Body>
					{items.map((item, i) => {
						const itemId = item[selectedItemsKey] || i;
						const nestedItems =
							nestedItemsReferenceKey &&
							item[nestedItemsReferenceKey];

						return (
							<React.Fragment key={itemId}>
								<ClayTable.Row
									className={classNames(
										highlightedItemsValue.includes(
											itemId
										) && 'active'
									)}
								>
									{selectable && (
										<ClayTable.Cell
											className="data-set-item-selector-wrapper"
											rowSpan={
												1 + (nestedItems?.length || 0)
											}
										>
											<SelectionComponent
												checked={
													!!selectedItemsValue.find(
														(element) =>
															String(element) ===
															String(itemId)
													)
												}
												onChange={() =>
													selectItems(itemId)
												}
												value={itemId}
											/>
										</ClayTable.Cell>
									)}
									{getItemFields(
										item,
										visibleFields,
										itemId,
										itemsActions
									)}
									<ClayTable.Cell className="data-set-item-actions-wrapper">
										{(showActionItems || item.actions) && (
											<ActionsDropdownRenderer
												actions={
													itemsActions ||
													item.actions ||
													item.actionDropdownItems
												}
												itemData={item}
												itemId={itemId}
											/>
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
								{nestedItems?.length
									? nestedItems.map((nestedItem, i) => (
											<ClayTable.Row
												className={classNames(
													'data-set-sub-item',
													highlightedItemsValue.includes(
														nestedItem[
															nestedItemsKey
														]
													) && 'active',
													i ===
														nestedItems.length -
															1 && 'last-of-group'
												)}
												key={nestedItem[nestedItemsKey]}
											>
												{getItemFields(
													nestedItem,
													schema.fields,
													nestedItem[nestedItemsKey],
													itemsActions
												)}
												{showActionItems ? (
													<ClayTable.Cell />
												) : null}
											</ClayTable.Row>
									  ))
									: null}
							</React.Fragment>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		</div>
	);
}

Table.propTypes = {
	items: PropTypes.arrayOf(PropTypes.object),
	itemsActions: PropTypes.array,
	schema: PropTypes.shape({
		fields: PropTypes.arrayOf(
			PropTypes.shape({
				fieldName: PropTypes.oneOfType([
					PropTypes.string,
					PropTypes.array,
				]),
				mapData: PropTypes.func,
			})
		).isRequired,
	}).isRequired,
	style: PropTypes.string.isRequired,
};

Table.defaultProps = {
	items: [],
};

export default Table;
