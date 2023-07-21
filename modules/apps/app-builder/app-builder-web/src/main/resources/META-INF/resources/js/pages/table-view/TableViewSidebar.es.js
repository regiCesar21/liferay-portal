/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {
	DataDefinitionUtils,
	DragTypes,
	FieldType,
	FieldTypeList,
	Sidebar,
} from 'data-engine-taglib';
import React, {useContext, useState} from 'react';

import Button from '../../components/button/Button.es';
import EditTableViewContext, {
	UPDATE_FOCUSED_COLUMN,
} from './EditTableViewContext.es';
import TableViewFiltersList from './TableViewFilters.es';
import {getFieldLabel, getFieldTypeLabel} from './utils.es';

const BtnAction = ({angle = 'left', className, onClick}) => (
	<Button
		className={className}
		displayType="secondary"
		onClick={onClick}
		symbol={`angle-${angle}`}
	/>
);

const FiltersSidebarHeader = () => {
	const [
		{dataDefinition, editingLanguageId, fieldTypes, focusedColumn},
		dispatch,
	] = useContext(EditTableViewContext);

	const onClickBack = () => {
		dispatch({payload: {fieldName: null}, type: UPDATE_FOCUSED_COLUMN});
	};

	const {fieldType} = DataDefinitionUtils.getDataDefinitionField(
		dataDefinition,
		focusedColumn
	);

	return (
		<Sidebar.Header className="d-flex table-view-filters-sidebar-header">
			<ClayLayout.ContentRow verticalAlign="center">
				<ClayLayout.ContentCol>
					<BtnAction className="mr-2" onClick={onClickBack} />
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<FieldType
						description={getFieldTypeLabel(fieldTypes, fieldType)}
						dragAlignment="none"
						draggable={false}
						icon={fieldType}
						label={getFieldLabel(
							dataDefinition,
							editingLanguageId,
							focusedColumn
						)}
						name={focusedColumn}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</Sidebar.Header>
	);
};

const FieldsTabContent = ({keywords, onAddFieldName}) => {
	const [
		{
			dataDefinition: {dataDefinitionFields = [], defaultLanguageId},
			dataListView: {fieldNames},
			editingLanguageId,
			fieldTypes,
		},
	] = useContext(EditTableViewContext);

	const fieldTypesItems = [];

	const fieldTypeModel = ({fieldType, label, name}) => ({
		description: getFieldTypeLabel(fieldTypes, fieldType),
		disabled: fieldNames.some((fieldName) => fieldName === name),
		icon: fieldType,
		label: label[editingLanguageId] || label[defaultLanguageId],
		name,
	});

	dataDefinitionFields.forEach(
		({nestedDataDefinitionFields, ...dataDefinitionField}) => {
			if (nestedDataDefinitionFields.length) {
				fieldTypesItems.push(
					...nestedDataDefinitionFields.map((nestedField) =>
						fieldTypeModel(nestedField)
					)
				);
			}
			else {
				fieldTypesItems.push(fieldTypeModel(dataDefinitionField));
			}
		}
	);

	fieldTypesItems.sort((a, b) => a.label.localeCompare(b.label));

	return (
		<FieldTypeList
			dragType={DragTypes.DRAG_FIELD_TYPE}
			emptyState={{
				description: Liferay.Language.get(
					'columns-are-needed-to-create-table-views-for-this-object'
				),
				title: Liferay.Language.get('there-are-no-columns-yet'),
			}}
			fieldTypes={fieldTypesItems}
			keywords={keywords}
			onDoubleClick={({name}) => onAddFieldName(name, fieldNames.length)}
		/>
	);
};

export default ({className, onAddFieldName, onToggle}) => {
	const [{focusedColumn}] = useContext(EditTableViewContext);
	const [keywords, setKeywords] = useState('');

	const displayFieldFilters = !!focusedColumn;

	return (
		<div className={className}>
			<Sidebar className="default">
				{displayFieldFilters ? (
					<>
						<FiltersSidebarHeader />
						<Sidebar.Body>
							<TableViewFiltersList />
						</Sidebar.Body>
					</>
				) : (
					<>
						<Sidebar.Header>
							<ClayForm
								onSubmit={(event) => event.preventDefault()}
							>
								<Sidebar.SearchInput
									onSearch={(keywords) =>
										setKeywords(keywords)
									}
								>
									<ClayLayout.ContentCol>
										<BtnAction
											angle="right"
											className="close-sidebar-btn ml-2"
											onClick={onToggle}
										/>
									</ClayLayout.ContentCol>
								</Sidebar.SearchInput>
							</ClayForm>
						</Sidebar.Header>

						<Sidebar.Body>
							{!displayFieldFilters && (
								<Sidebar.Tabs
									tabs={[
										{
											label: Liferay.Language.get(
												'columns'
											),
											render: () => (
												<FieldsTabContent
													keywords={keywords}
													onAddFieldName={
														onAddFieldName
													}
												/>
											),
										},
										{
											label: Liferay.Language.get(
												'filters'
											),
											render: () => (
												<TableViewFiltersList />
											),
										},
									]}
								/>
							)}
						</Sidebar.Body>
					</>
				)}
			</Sidebar>

			<Sidebar className="secondary">
				<BtnAction
					className="m-3 open-sidebar-btn"
					onClick={onToggle}
				/>
			</Sidebar>
		</div>
	);
};
