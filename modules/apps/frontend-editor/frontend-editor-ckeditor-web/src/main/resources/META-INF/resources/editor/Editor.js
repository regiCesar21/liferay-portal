/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CKEditor from 'ckeditor4-react';
import React, {useEffect} from 'react';

const BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';
const CONTEXT_URL = Liferay.ThemeDisplay.getPathContext();
const CURRENT_PATH = CONTEXT_URL ? CONTEXT_URL + BASEPATH : BASEPATH;

const Editor = React.forwardRef((props, ref) => {
	useEffect(() => {
		Liferay.once('beforeScreenFlip', () => {
			if (
				window.CKEDITOR &&
				Object.keys(window.CKEDITOR.instances).length === 0
			) {
				delete window.CKEDITOR;
			}
		});
	}, []);

	return <CKEditor ref={ref} {...props} />;
});

CKEditor.editorUrl = `${CURRENT_PATH}ckeditor.js`;
window.CKEDITOR_BASEPATH = CURRENT_PATH;

export {Editor};
