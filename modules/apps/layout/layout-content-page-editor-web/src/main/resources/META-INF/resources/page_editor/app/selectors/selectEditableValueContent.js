/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import selectEditableValue from './selectEditableValue';

export default function selectEditableValueContent(
	state,
	fragmentEntryLinkId,
	editableId,
	processorType
) {
	const {languageId} = state;

	const data = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processorType
	);

	let content = data;

	if (content[languageId]) {
		content = content[languageId];
	}
	else if (content[config.defaultLanguageId]) {
		content = content[config.defaultLanguageId];
	}

	if (content == null || content.defaultValue) {
		content = data.defaultValue;
	}

	return content;
}
