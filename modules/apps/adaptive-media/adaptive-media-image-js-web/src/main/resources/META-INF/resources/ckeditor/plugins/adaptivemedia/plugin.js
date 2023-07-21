/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	var Lang = AUI().Lang;

	var STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType';

	var STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType';

	var TPL_PICTURE_TAG =
		'<picture {fileEntryAttributeName}="{fileEntryId}">{sources}<img src="{defaultSrc}"></picture>';

	var TPL_SOURCE_TAG = '<source srcset="{srcset}" media="{media}">';

	CKEDITOR.plugins.add('adaptivemedia', {
		_bindEvent(editor) {
			var instance = this;

			editor.on('beforeCommandExec', (event) => {
				if (event.data.name === 'imageselector') {
					event.removeListener();

					event.cancel();

					var onSelectedImageChangeFn = instance._onSelectedImageChange.bind(
						instance,
						editor
					);

					editor.execCommand(
						'imageselector',
						onSelectedImageChangeFn
					);

					instance._bindEvent(editor);
				}
			});
		},

		_getImgElement(imageSrc, selectedItem, fileEntryAttributeName) {
			var imgEl = CKEDITOR.dom.element.createFromHtml('<img>');

			if (
				selectedItem.returnType ===
				STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE
			) {
				var itemValue = JSON.parse(selectedItem.value);

				imgEl.setAttribute('src', itemValue.url);
				imgEl.setAttribute(
					fileEntryAttributeName,
					itemValue.fileEntryId
				);
			}
			else {
				imgEl.setAttribute('src', imageSrc);
			}

			return imgEl;
		},

		_getPictureElement(selectedItem, fileEntryAttributeName) {
			var pictureEl;

			try {
				var itemValue = JSON.parse(selectedItem.value);

				var sources = '';

				itemValue.sources.forEach((source) => {
					var propertyNames = Object.getOwnPropertyNames(
						source.attributes
					);

					var mediaText = propertyNames.reduce(
						(previous, current) => {
							var value =
								'(' +
								current +
								':' +
								source.attributes[current] +
								')';

							return previous
								? previous + ' and ' + value
								: value;
						},
						''
					);

					sources += Lang.sub(TPL_SOURCE_TAG, {
						media: mediaText,
						srcset: source.src,
					});
				});

				var pictureHtml = Lang.sub(TPL_PICTURE_TAG, {
					defaultSrc: itemValue.defaultSource,
					fileEntryAttributeName,
					fileEntryId: itemValue.fileEntryId,
					sources,
				});

				pictureEl = CKEDITOR.dom.element.createFromHtml(pictureHtml);
			}
			catch (e) {}

			return pictureEl;
		},

		_isEmptySelection(editor) {
			var selection = editor.getSelection();

			var ranges = selection.getRanges();

			return (
				selection.getType() === CKEDITOR.SELECTION_NONE ||
				(ranges.length === 1 && ranges[0].collapsed)
			);
		},

		_onSelectedImageChange(editor, imageSrc, selectedItem) {
			var instance = this;

			var element;

			var fileEntryAttributeName =
				editor.config.adaptiveMediaFileEntryAttributeName;

			if (
				selectedItem.returnType === STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE
			) {
				element = instance._getPictureElement(
					selectedItem,
					fileEntryAttributeName
				);
			}
			else {
				element = instance._getImgElement(
					imageSrc,
					selectedItem,
					fileEntryAttributeName
				);
			}

			var elementOuterHtml = element.getOuterHtml();

			if (instance._isEmptySelection(editor)) {
				elementOuterHtml += '<br />';
			}

			editor.insertHtml(elementOuterHtml);

			editor.focus();
		},

		init(editor) {
			var instance = this;

			instance._bindEvent(editor);
		},
	});
})();
