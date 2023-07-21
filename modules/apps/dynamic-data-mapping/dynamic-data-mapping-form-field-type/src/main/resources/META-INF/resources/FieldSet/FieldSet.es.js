/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './FieldSet.scss';

import {Layout, getRepeatedIndex} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import Panel from './Panel.es';

const getRowsArray = (rows) => {
	if (typeof rows === 'string') {
		try {
			return JSON.parse(rows);
		}
		catch (e) {
			return [];
		}
	}

	return rows;
};

const getRows = (rows, nestedFields) => {
	const normalizedRows = getRowsArray(rows);

	return normalizedRows.map((row) => ({
		...row,
		columns: row.columns.map((column) => {
			return {
				...column,
				fields: nestedFields.filter((nestedField) =>
					column.fields.includes(nestedField.fieldName)
				),
			};
		}),
	}));
};

const FieldSet = ({
	collapsible,
	label,
	name,
	nestedFields = [],
	readOnly,
	repeatable,
	rows,
	showLabel,
	...otherProps
}) => {
	const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);
	const renderLayout = ({ddmStructureId, type}) => (
		<Layout
			editable={type === 'fieldset' && !ddmStructureId}
			rows={getRows(rows, nestedFields)}
		/>
	);

	return (
		<FieldBase
			{...otherProps}
			name={name}
			readOnly={readOnly}
			repeatable={collapsible ? false : repeatable}
			required={false}
			showLabel={false}
			style={null}
		>
			<div className="ddm-field-types-fieldset__nested">
				{showLabel && !collapsible && (
					<>
						<label className="text-uppercase">{label}</label>
						<div className="ddm-field-types-fieldset__nested-separator">
							<div className="mt-1 separator" />
						</div>
					</>
				)}

				{collapsible ? (
					<Panel
						name={name}
						readOnly={readOnly}
						repeatable={repeatable}
						showLabel={showLabel}
						showRepeatableRemoveButton={
							repeatable && repeatedIndex > 0
						}
						title={label}
					>
						{renderLayout(otherProps)}
					</Panel>
				) : (
					renderLayout(otherProps)
				)}
			</div>
		</FieldBase>
	);
};

export default FieldSet;
