/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';

class EntriesDefaultEventHandler extends DefaultEventHandler {
	moveEntry(itemData) {
		const instance = this;

		openSelectionModal({
			onSelect: (event) => {
				const selectContainerForm = document.getElementById(
					`${instance.namespace}selectContainerForm`
				);

				if (selectContainerForm) {
					const className = selectContainerForm.querySelector(
						`#${instance.namespace}className`
					);

					if (className) {
						className.setAttribute('value', event.classname);
					}

					const classPK = selectContainerForm.querySelector(
						`#${instance.namespace}classPK`
					);

					if (classPK) {
						classPK.setAttribute('value', event.classpk);
					}

					const containerModelId = selectContainerForm.querySelector(
						`#${instance.namespace}containerModelId`
					);

					if (containerModelId) {
						containerModelId.setAttribute(
							'value',
							event.containermodelid
						);
					}

					const redirect = selectContainerForm.querySelector(
						`#${instance.namespace}redirect`
					);

					if (redirect) {
						redirect.setAttribute('value', event.redirect);
					}

					submitForm(selectContainerForm);
				}
			},
			selectEventName: this.ns('selectContainer'),
			title: Liferay.Language.get('warning'),
			url: itemData.moveEntryURL,
		});
	}

	restoreEntry(itemData) {
		submitForm(document.hrefFm, itemData.restoreEntryURL);
	}

	deleteEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteEntryURL);
		}
	}
}

export default EntriesDefaultEventHandler;
