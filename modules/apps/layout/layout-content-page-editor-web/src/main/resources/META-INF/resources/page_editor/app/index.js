/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import App from './components/App';
import {initializeConfig} from './config/index';

let removeChild;

/**
 * LPS-120418 remove child function that doesn't throw a NotFoundError exception.
 * This is done by default in all browser except ie11
 *
 * When mounting the dropzones this error is thrown making the fragment fail,
 * swallowing seems harmless and makes the dropzones work in ie11
 */
function safeRemoveChild() {
	try {
		return removeChild.apply(this, arguments);
	}
	catch (error) {
		if (!!error && !!error.message && error.message === 'NotFoundError') {
			if (process.env.NODE_ENV === 'development') {
				console.warn('IE NotFoundError handled');
			}
		}
		else {
			throw error;
		}
	}
}

/**
 * Default application export.
 *
 * We should define contexts here instead of Container component, as Container
 * is re-rendered when hooks change.
 */
export default function (data) {
	initializeConfig(data.config);

	if (Liferay?.Browser?.isIe()) {
		removeChild = window.HTMLElement.prototype.removeChild;

		window.HTMLElement.prototype.removeChild = safeRemoveChild;
	}

	useEffect(() => {
		return () => {
			if (removeChild) {
				window.HTMLElement.prototype.removeChild = removeChild;
				removeChild = undefined;
			}
		};
	}, []);

	return (
		<DndProvider backend={HTML5Backend}>
			<App state={data.state} />
		</DndProvider>
	);
}
