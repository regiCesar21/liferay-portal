/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import {Editor} from './Editor';

const ClassicEditor = ({
	ariaRequired,
	contents = '',
	editorConfig,
	initialToolbarSet = 'simple',
	name,
	onChange,
	onChangeMethodName,
	title,
	...otherProps
}) => {
	const editorRef = useRef();

	const [toolbarSet, setToolbarSet] = useState(initialToolbarSet);

	const getConfig = () => {
		return {
			toolbar: toolbarSet,
			...editorConfig,
		};
	};

	const getHTML = useCallback(() => {
		let data = contents;

		const editor = editorRef.current.editor;

		if (editor && editor.instanceReady) {
			data = editor.getData();

			if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) === '<br />') {
				data = '';
			}

			data = data.replace(/(\u200B){7}/, '');
		}

		return data;
	}, [contents]);

	const onChangeCallback = () => {
		if (!onChangeMethodName && !onChange) {
			return;
		}

		const editor = editorRef.current.editor;

		if (editor.checkDirty()) {
			if (onChangeMethodName) {
				window[onChangeMethodName](getHTML());
			}
			else {
				onChange(getHTML());
			}

			editor.resetDirty();
		}
	};

	useEffect(() => {
		setToolbarSet(initialToolbarSet);
	}, [initialToolbarSet]);

	useEffect(() => {
		window[name] = {
			getHTML,
			getText() {
				return contents;
			},
		};
	}, [contents, getHTML, name]);

	const onResize = debounce(() => {
		setToolbarSet(initialToolbarSet);
	}, 200);

	useEventListener('resize', onResize, true, window);

	return (
		<div id={`${name}Container`} role="textbox">
			{title && (
				<label className="control-label" htmlFor={name}>
					{title}
				</label>
			)}
			<Editor
				className="lfr-editable"
				config={getConfig()}
				data={contents}
				name={name}
				onBeforeLoad={(CKEDITOR) => {
					CKEDITOR.disableAutoInline = true;
					CKEDITOR.dtd.$removeEmpty.i = 0;
					CKEDITOR.dtd.$removeEmpty.span = 0;

					CKEDITOR.getNextZIndex = function () {
						return CKEDITOR.dialog._.currentZIndex
							? CKEDITOR.dialog._.currentZIndex + 10
							: Liferay.zIndex.WINDOW + 10;
					};
				}}
				onChange={onChangeCallback}
				onDrop={(event) => {
					const data = event.data.dataTransfer.getData('text/html');
					const editor = event.editor;

					if (data) {
						const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
							data
						);

						const name = fragment.children[0].name;

						if (name) {
							return editor.pasteFilter.check(name);
						}
					}
				}}
				onInstanceReady={({editor}) => {
					editor.setData(contents);

					const iframe = document.querySelector(
						'iframe.cke_wysiwyg_frame'
					);

					iframe.onload = function () {
						const iframeDocument = iframe.contentDocument;
						const iframeBody = iframeDocument.querySelector(
							'body.html-editor'
						);

						if (iframeBody) {
							iframeBody.setAttribute(
								'aria-required',
								ariaRequired
							);
						}
					};
				}}
				ref={editorRef}
				{...otherProps}
			/>
		</div>
	);
};

ClassicEditor.propTypes = {
	contents: PropTypes.string,
	editorConfig: PropTypes.object,
	initialToolbarSet: PropTypes.string,
	name: PropTypes.string,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
	title: PropTypes.string,
};

export {ClassicEditor};
export default ClassicEditor;
