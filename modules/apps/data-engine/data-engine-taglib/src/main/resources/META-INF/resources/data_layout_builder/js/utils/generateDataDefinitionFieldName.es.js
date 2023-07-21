/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';

const findFieldByName = (dataDefinitionFields, fieldName) => {
	let result;

	const traverse = (dataDefinitionFields) =>
		dataDefinitionFields.forEach((dataDefinition) => {
			const {name, nestedDataDefinitionFields = []} = dataDefinition;

			if (name === fieldName) {
				result = dataDefinition;

				return;
			}

			traverse(nestedDataDefinitionFields);
		});

	traverse(dataDefinitionFields);

	return result;
};

export default (
	dataDefinitionFields,
	desiredName,
	currentName = null,
	blacklist = []
) => {
	let counter = 0;
	let name = normalizeFieldName(desiredName);

	let existingField;

	while (
		((existingField = findFieldByName(dataDefinitionFields, name)) &&
			existingField &&
			existingField.name !== currentName) ||
		blacklist.includes(name)
	) {
		name = normalizeFieldName(`${desiredName}${++counter}`);
	}

	return name;
};
