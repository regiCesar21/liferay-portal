/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayTable from '@clayui/table';
import React from 'react';

import DropDown from './DropDown.es';
import EditableCell from './EditableCell.es';

const {Body, Cell, Head, Row} = ClayTable;

const CellWrapper = ({children, colSpan = 1, fieldAlign, index}) => (
	<Cell
		align={index === 0 ? 'left' : fieldAlign}
		className={index > 0 && 'table-cell-expand-smaller'}
		colSpan={colSpan}
		expanded={index === 0}
		headingTitle={index === 0}
		key={index}
	>
		{children}
	</Cell>
);

const Table = ({
	actions,
	align = 'left',
	checkable,
	columns,
	editMode,
	forwardRef,
	items,
	noActionsMessage,
}) => {
	return (
		<div ref={forwardRef}>
			<ClayTable className="thead-valign-top" hover={false}>
				<Head>
					<Row>
						{checkable && <Cell headingCell></Cell>}
						{columns.map((column, index) => (
							<Cell
								align={index === 0 ? 'left' : align}
								className={
									index > 0 && 'table-cell-expand-smaller'
								}
								expanded={index === 0}
								headingCell
								key={index}
							>
								{column.value}
							</Cell>
						))}
						{actions && <Cell headingCell></Cell>}
					</Row>
				</Head>
				<Body>
					{items.map((item, index) => {
						const isEditMode = editMode && editMode.id === item.id;

						return (
							<Row key={index}>
								{checkable && (
									<Cell>
										<ClayCheckbox
											checked={false}
											disabled={false}
											indeterminate={false}
											onChange={() => {}}
										/>
									</Cell>
								)}
								{isEditMode ? (
									<EditableCell
										Cell={CellWrapper}
										columns={columns}
										editMode={editMode}
										item={item}
									/>
								) : (
									columns.map((column, index) => {
										return (
											<CellWrapper
												fieldAlign={align}
												index={index}
												key={index}
											>
												{item[column.key]}
											</CellWrapper>
										);
									})
								)}
								{actions && !isEditMode && (
									<Cell>
										<DropDown
											actions={actions}
											item={item}
											noActionsMessage={noActionsMessage}
										/>
									</Cell>
								)}
							</Row>
						);
					})}
				</Body>
			</ClayTable>
		</div>
	);
};

export default React.forwardRef((props, ref) => (
	<Table {...props} forwardRef={ref} />
));
