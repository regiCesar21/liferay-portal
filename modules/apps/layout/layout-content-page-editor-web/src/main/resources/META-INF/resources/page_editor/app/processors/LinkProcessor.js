/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getAlloyEditorProcessor from './getAlloyEditorProcessor';
import {getLinkableEditableEditorWrapper} from './getLinkableEditableEditorWrapper';

export default getAlloyEditorProcessor(
	'text',
	getLinkableEditableEditorWrapper,
	(element, value, config) => {
		const anchor =
			element instanceof HTMLAnchorElement
				? element
				: element.querySelector('a');

		if (anchor) {
			if (config.href) {
				anchor.href = config.href;
			}

			anchor.innerHTML = value;
			anchor.target = config.target || '';
		}
	}
);
