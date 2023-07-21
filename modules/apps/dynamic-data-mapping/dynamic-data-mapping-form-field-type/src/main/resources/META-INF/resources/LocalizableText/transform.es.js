/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const convertValueToJSON = (value) => {
	if (value && typeof value === 'string') {
		try {
			return JSON.parse(value);
		}
		catch (e) {
			console.warn('Unable to parse JSON', value);
		}
	}

	return value;
};

export const getEditingValue = ({defaultLocale, editingLocale, value}) => {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		return (
			valueJSON[editingLocale.localeId] ||
			valueJSON[defaultLocale.localeId] ||
			''
		);
	}

	return editingLocale;
};

export const getInitialInternalValue = ({editingLocale, value}) => {
	const valueJSON = convertValueToJSON(value);

	return valueJSON[editingLocale.localeId] || '';
};

const convertValueToString = (value) => {
	if (value && typeof value === 'object') {
		return JSON.stringify(value);
	}

	return value;
};

const isTranslated = ({localeId, value}) => {
	const valueJSON = convertValueToJSON(value);

	if (valueJSON) {
		return !!valueJSON[localeId];
	}

	return false;
};

const isDefaultLocale = ({defaultLocale, localeId}) => {
	return defaultLocale.localeId === localeId;
};

export const normalizeLocaleId = (localeId) => {
	if (!localeId || localeId === '') {
		throw new Error(`localeId ${localeId} is invalid`);
	}

	return localeId.replace('_', '-').toLowerCase();
};

export const transformAvailableLocalesAndValue = ({
	availableLocales,
	defaultLocale,
	value,
}) => ({
	availableLocales: availableLocales.map((availableLocale) => ({
		...availableLocale,
		icon: normalizeLocaleId(availableLocale.localeId),
		isDefault: isDefaultLocale({
			defaultLocale,
			localeId: availableLocale.localeId,
		}),
		isTranslated: isTranslated({
			localeId: availableLocale.localeId,
			value,
		}),
	})),
	value: convertValueToString(value),
});
