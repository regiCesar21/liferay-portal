/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-web';

/**
 * @param {HTMLElement} element HTMLElement where the editor
 *  should be applied to.
 * @param {function} changeCallback Function that should be called whenever the
 *  editor produces a change. It must receive a string with
 *  the new editable value.
 * @param {function} destroyCallback Function that should be called if
 *  the editor is destroyed for any internal reason. This function does NOT need
 *  to be called if the editor is destroyed with destroyEditor function.
 */
function createEditor(element, changeCallback, destroyCallback) {
	let editor;

	openModal({
		bodyHTML: '<div class="editor-container" />',
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				label: Liferay.Language.get('save'),
				onClick: () => {
					const annotations = editor._editor
						.getSession()
						.getAnnotations();

					const errorAnnotations = annotations.filter(
						(annotation) => annotation.type === 'error'
					);

					if (errorAnnotations.length) {
						const errorMessage = errorAnnotations
							.map((annotation) => annotation.text)
							.join('\n');

						openToast({
							message: errorMessage,
							type: 'danger',
						});
					}
					else {
						changeCallback(editor.get('value'));

						Liferay.fire('closeModal');
					}
				},
			},
		],
		onClose: () => destroyCallback(),
		onOpen: () => {
			Liferay.Util.getTop()
				.AUI()
				.use('liferay-fullscreen-source-editor', (A) => {
					const editorContainer = document.querySelector(
						'.liferay-modal .editor-container'
					);

					if (editorContainer) {
						editor = new A.LiferayFullScreenSourceEditor({
							boundingBox: editorContainer,
							previewCssClass: 'alloy-editor',
							value: element.innerHTML,
						}).render();
					}
				});
		},
		size: 'full-screen',
		title: Liferay.Language.get('edit-content'),
	});
}

/**
 */
function destroyEditor() {}

/**
 * @param {HTMLElement} element HTMLElement that should be mutated with the
 *  given value.
 * @param {string} value Element content
 */
function render(element, value) {
	if (value) {
		element.innerHTML = value;
	}
}

export default {
	createEditor,
	destroyEditor,
	render,
};
