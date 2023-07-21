/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openImageSelector} from '../../core/openImageSelector';
import {config} from '../config/index';

/**
 * @param {HTMLElement} element HTMLElement where the editor
 *  should be applied to.
 * @param {function} changeCallback Function that should be called whenever the
 *  editor produces a change. It must receive two parameters, the editable value
 *  and the editable config.
 * @param {function} destroyCallback Function that should be called if
 *  the editor is destroyed for any internal reason. This function does NOT need
 *  to be called if the editor is destroyed with destroyEditor function.
 */
function createEditor(element, changeCallback, destroyCallback) {
	openImageSelector((image) => {
		const url = image && image.url ? image.url : '';

		changeCallback(
			config.adaptiveMediaEnabled
				? {
						fileEntryId: image ? image.fileEntryId : undefined,
						url,
				  }
				: url,
			{imageTitle: image && image.title ? image.title : ''}
		);
	}, destroyCallback);
}

/**
 */
function destroyEditor() {}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Image url
 * @param {object} config Editable value's config object
 * @param {string} [config.href] Image anchor url
 * @param {string} [config.target] Image anchor target
 */
function render(element, value, config = {}) {
	let image = null;

	if (element instanceof HTMLImageElement) {
		image = element;
	}
	else {
		image = element.querySelector('img');
	}

	if (image) {
		image.alt = value.alt || config.alt || image.alt;

		if (config.href) {
			if (image.parentElement instanceof HTMLAnchorElement) {
				image.parentElement.href = config.href;
				image.parentElement.target = config.target || '';
			}
			else {
				const link = document.createElement('a');

				link.href = config.href;
				link.target = config.target || '';

				image.parentElement.replaceChild(link, image);
				link.appendChild(image);
			}
		}

		const imageValue =
			value && typeof value !== 'string' ? value.url : value;

		if (imageValue) {
			image.src = imageValue;
		}
	}
}

export default {
	createEditor,
	destroyEditor,
	render,
};
