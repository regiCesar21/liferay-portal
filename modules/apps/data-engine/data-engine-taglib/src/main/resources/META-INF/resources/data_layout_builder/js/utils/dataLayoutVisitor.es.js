/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function findField(dataLayoutPages, fieldName) {
	return (dataLayoutPages || []).find(({dataLayoutRows}) => {
		return (dataLayoutRows || []).find(({dataLayoutColumns}) => {
			return (dataLayoutColumns || []).find(({fieldNames}) => {
				return (fieldNames || []).find((name) => name === fieldName);
			});
		});
	});
}

export function containsField(dataLayoutPages, fieldName) {
	return !!findField(dataLayoutPages, fieldName);
}

export function mapDataLayoutColumns(dataLayoutPages, fn = () => {}) {
	return (dataLayoutPages || []).map(
		({dataLayoutRows, ...dataLayoutPage}, pageIndex) => {
			return {
				...dataLayoutPage,
				dataLayoutRows: (dataLayoutRows || []).map(
					({dataLayoutColumns, ...dataLayoutRow}, rowIndex) => {
						return {
							...dataLayoutRow,
							dataLayoutColumns: dataLayoutColumns.map(
								(dataLayoutColumn, columnIndex) =>
									fn(
										dataLayoutColumn,
										columnIndex,
										rowIndex,
										pageIndex
									)
							),
						};
					}
				),
			};
		}
	);
}

export function deleteField(dataLayoutPages, fieldName) {
	return mapDataLayoutColumns(
		dataLayoutPages,
		({fieldNames, ...dataLayoutColumn}) => {
			return {
				...dataLayoutColumn,
				fieldNames: (fieldNames || []).filter(
					(name) => name !== fieldName
				),
			};
		}
	);
}

export function getFieldNameFromIndexes(
	{dataLayoutPages},
	{columnIndex, fieldIndex = 0, pageIndex, rowIndex}
) {
	return dataLayoutPages[pageIndex].dataLayoutRows[rowIndex]
		.dataLayoutColumns[columnIndex].fieldNames[fieldIndex];
}

export function getIndexesFromFieldName({dataLayoutPages}, fieldName) {
	let indexes = {};

	dataLayoutPages.some(({dataLayoutRows}, pageIndex) => {
		return dataLayoutRows.some(({dataLayoutColumns}, rowIndex) => {
			return dataLayoutColumns.some(({fieldNames = []}, columnIndex) => {
				return fieldNames.some((name) => {
					if (name === fieldName) {
						indexes = {
							columnIndex,
							pageIndex,
							rowIndex,
						};

						return true;
					}

					return false;
				});
			});
		});
	});

	return indexes;
}

export function normalizeRule(dataRule) {
	if (Object.prototype.hasOwnProperty.call(dataRule, 'logical-operator')) {
		dataRule['logicalOperator'] = dataRule['logical-operator'];
		delete dataRule['logical-operator'];
	}

	dataRule = {
		...dataRule,
		name: {
			en_US: dataRule.name,
		},
	};

	return dataRule;
}

export function isDataLayoutEmpty(dataLayoutPages) {
	return dataLayoutPages.every(({dataLayoutRows}) => {
		return dataLayoutRows.every(({dataLayoutColumns}) => {
			return dataLayoutColumns.every(({fieldNames = []}) => {
				return !fieldNames.length;
			});
		});
	});
}

export function normalizeDataLayoutRows(dataLayoutPages) {
	return dataLayoutPages[0].dataLayoutRows.map(({dataLayoutColumns}) => {
		return {
			columns: dataLayoutColumns.map(
				({columnSize: size, fieldNames: fields}) => ({
					fields,
					size,
				})
			),
		};
	});
}
