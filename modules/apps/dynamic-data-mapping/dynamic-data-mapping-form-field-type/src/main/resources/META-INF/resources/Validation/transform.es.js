/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import VALIDATIONS from '../util/validations.es';

const getValidationFromExpression = (validations, validation) => {
	return function transformValidationFromExpression(expression) {
		let mutValidation;

		if (!expression && validation) {
			expression = validation.expression;
		}

		if (expression) {
			mutValidation = validations.find(
				(validation) => validation.name === expression.name
			);
		}

		return mutValidation;
	};
};

const transformValidations = (validations, initialDataType) => {
	const dataType = initialDataType == 'string' ? initialDataType : 'numeric';

	return VALIDATIONS[dataType].map((validation) => {
		return {
			...validation,
			checked: false,
			value: validation.name,
		};
	});
};

const getValidation = (
	defaultLanguageId,
	editingLanguageId,
	validations,
	transformValidationFromExpression
) => {
	return function transformValue(value) {
		const {errorMessage = {}, expression = {}, parameter = {}} = value;
		let parameterMessage = '';
		let selectedValidation = transformValidationFromExpression(expression);
		const enableValidation = !!expression.value;

		if (selectedValidation) {
			parameterMessage = selectedValidation.parameterMessage;
		}
		else {
			selectedValidation = validations[0];
		}

		return {
			enableValidation,
			errorMessage:
				errorMessage[editingLanguageId] ||
				errorMessage[defaultLanguageId],
			expression,
			parameter:
				parameter[editingLanguageId] || parameter[defaultLanguageId],
			parameterMessage,
			selectedValidation,
		};
	};
};

export const getSelectedValidation = (validations) => {
	return function transformSelectedValidation(value) {
		if (Array.isArray(value)) {
			value = value[0];
		}

		let selectedValidation = validations.find(({name}) => name === value);

		if (!selectedValidation) {
			selectedValidation = validations[0];
		}

		return selectedValidation;
	};
};

export const transformData = ({
	defaultLanguageId,
	editingLanguageId,
	initialDataType,
	initialValidations,
	validation,
	value,
}) => {
	const dataType =
		validation && validation.dataType
			? validation.dataType
			: initialDataType;
	const validations = transformValidations(initialValidations, dataType);
	const parsedValidation = getValidation(
		defaultLanguageId,
		editingLanguageId,
		validations,
		getValidationFromExpression(validations, validation)
	)(value);
	const localizationMode = editingLanguageId !== defaultLanguageId;

	return {
		...parsedValidation,
		dataType,
		localizationMode,
		validations,
	};
};
