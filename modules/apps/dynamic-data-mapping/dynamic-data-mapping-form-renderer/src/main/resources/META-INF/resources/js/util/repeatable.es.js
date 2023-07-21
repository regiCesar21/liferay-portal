/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

const NESTED_FIELD_NAME_REGEX = /(_\w+_)ddm\$\$(.+)\$(\w+)\$(\d+)#(.+)\$(\w+)\$(\d+)\$\$(\w+)/;

export const generateInstanceId = () => Math.random().toString(36).substr(2, 8);

export const parseName = (name) => {
	let parsed = {};
	const result = FIELD_NAME_REGEX.exec(name);

	if (result) {
		parsed = {
			editingLanguageId: result[5],
			fieldName: result[2],
			instanceId: result[3],
			portletNamespace: result[1],
			repeatedIndex: Number(result[4]),
		};
	}

	return parsed;
};

export const generateName = (name, props = {}) => {
	const parsedName = parseName(name);
	const {
		editingLanguageId = parsedName.editingLanguageId,
		fieldName = parsedName.fieldName,
		instanceId = parsedName.instanceId,
		portletNamespace = parsedName.portletNamespace,
		repeatedIndex = parsedName.repeatedIndex,
	} = props;

	return `${portletNamespace}ddm$$${fieldName}$${instanceId}$${repeatedIndex}$$${editingLanguageId}`;
};

export const parseNestedFieldName = (name) => {
	let parsed = {};
	const result = NESTED_FIELD_NAME_REGEX.exec(name);

	if (result) {
		parsed = {
			fieldName: result[5],
			instanceId: result[6],
			locale: result[8],
			parentFieldName: result[2],
			parentInstanceId: result[3],
			parentRepeatedIndex: Number(result[4]),
			portletNamespace: result[1],
			repeatedIndex: Number(result[7]),
		};
	}

	return parsed;
};

export const generateNestedFieldName = (name, parentFieldName) => {
	const parsedParentFieldName = parseName(parentFieldName);
	let parsedName = parseNestedFieldName(name);

	if (!parsedName.fieldName) {
		parsedName = parseName(name);
	}

	const {fieldName, instanceId, portletNamespace, repeatedIndex} = parsedName;

	return [
		portletNamespace,
		'ddm$$',
		parsedParentFieldName.fieldName,
		'$',
		parsedParentFieldName.instanceId,
		'$',
		parsedParentFieldName.repeatedIndex,
		'#',
		fieldName,
		'$',
		instanceId,
		'$',
		repeatedIndex,
		'$$',
		parsedName.locale || parsedName.editingLanguageId,
	].join('');
};

export const getRepeatedIndex = (name) => {
	let parsedName;

	if (NESTED_FIELD_NAME_REGEX.test(name)) {
		parsedName = parseNestedFieldName(name);
	}
	else {
		parsedName = parseName(name);
	}

	return parsedName.repeatedIndex;
};
