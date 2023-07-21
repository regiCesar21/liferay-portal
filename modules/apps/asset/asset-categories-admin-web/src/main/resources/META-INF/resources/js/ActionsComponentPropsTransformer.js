/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

const ACTIONS = {
	deleteVocabularies({
		deleteVocabulariesURL,
		portletNamespace,
		viewVocabulariesURL,
	}) {
		const vocabulariesForm = document.createElement('form');

		vocabulariesForm.setAttribute('method', 'post');

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('delete'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems) {
					if (
						confirm(
							Liferay.Language.get(
								'are-you-sure-you-want-to-delete-the-selected-entries'
							)
						)
					) {
						selectedItems.forEach((item) => {
							vocabulariesForm.appendChild(item.cloneNode(true));
						});

						submitForm(vocabulariesForm, deleteVocabulariesURL);
					}
				}
			},
			selectEventName: `${portletNamespace}selectVocabularies`,
			title: Liferay.Language.get('delete-vocabulary'),
			url: viewVocabulariesURL,
		});
	},
};

export default function propsTransformer({
	additionalProps: {deleteVocabulariesURL, viewVocabulariesURL},
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action]({
							deleteVocabulariesURL,
							portletNamespace,
							viewVocabulariesURL,
						});
					}
				},
			};
		}),
	};
}
