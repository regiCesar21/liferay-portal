/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../../config/index';
import InfoItemService from '../../services/InfoItemService';

export default function (
	editableValues,
	editableId,
	processorType,
	languageId,
	getFieldValue = InfoItemService.getInfoItemFieldValue
) {
	const editableValue = editableValues[processorType][editableId];

	if (!editableValue) {
		return Promise.resolve(null);
	}

	let valuePromise;

	if (editableIsMappedToInfoItem(editableValue)) {
		valuePromise = getFieldValue({
			classNameId: editableValue.classNameId,
			classPK: editableValue.classPK,
			collectionFieldId: editableValue.collectionFieldId,
			fieldId: editableValue.fieldId,
			languageId,
		}).catch(() => {
			return selectEditableValueContent(editableValue, languageId);
		});
	}
	else {
		valuePromise = Promise.resolve(
			selectEditableValueContent(editableValue, languageId)
		);
	}

	let configPromise;

	if (editableIsMappedToInfoItem(editableValue.config)) {
		configPromise = getFieldValue({
			classNameId: editableValue.config.classNameId,
			classPK: editableValue.config.classPK,
			collectionFieldId: editableValue.config.collectionFieldId,
			fieldId: editableValue.config.fieldId,
			languageId,
		})
			.then((href) => {
				return {...editableValue.config, href};
			})
			.catch(() => {
				return {...editableValue.config};
			});
	}
	else {
		configPromise = Promise.resolve(editableValue.config);
	}

	return Promise.all([valuePromise, configPromise]);
}

function selectEditableValueContent(editableValue, languageId) {
	let content = editableValue;

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (content == null || content.defaultValue) {
		content = editableValue.defaultValue;
	}

	return content;
}

function editableIsMappedToInfoItem(editableValue) {
	return (
		editableValue &&
		((editableValue.classNameId &&
			editableValue.classPK &&
			editableValue.fieldId) ||
			editableValue.collectionFieldId)
	);
}
