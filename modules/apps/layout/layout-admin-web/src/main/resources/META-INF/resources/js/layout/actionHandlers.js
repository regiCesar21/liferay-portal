/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

const actionHandlers = {
	copyLayout: ({actionURL, namespace}) => {
		openModal({
			id: `${namespace}addLayoutDialog`,
			title: Liferay.Language.get('copy-page'),
			url: actionURL,
		});
	},

	delete: ({actionURL, hasChildren, hasScopeGroup}) => {
		let deleteMessage = Liferay.Language.get(
			'are-you-sure-you-want-to-delete-this-page'
		);

		if (hasChildren && hasScopeGroup) {
			deleteMessage = Liferay.Language.get(
				'this-page-is-being-used-as-a-scope-for-content-and-also-has-child-pages'
			);
		}
		else if (hasChildren) {
			deleteMessage = Liferay.Util.sub(
				Liferay.Language.get(
					'this-page-has-child-pages-that-will-also-be-removed'
				),
				hasChildren
			);
		}
		else if (hasScopeGroup) {
			deleteMessage = Liferay.Language.get(
				'this-page-is-being-used-as-a-scope-for-content'
			);
		}

		if (confirm(deleteMessage)) {
			Liferay.Util.navigate(actionURL);
		}
	},

	discardDraft: ({actionURL}) => {
		const discardDraftMessage = Liferay.Language.get(
			'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
		);

		if (confirm(discardDraftMessage)) {
			Liferay.Util.navigate(actionURL);
		}
	},

	permissions: ({actionURL}) => {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: actionURL,
		});
	},

	viewCollectionItems: ({actionURL, namespace}) => {
		openModal({
			id: `${namespace}viewCollectionItemsDialog`,
			title: Liferay.Language.get('collection-items'),
			url: actionURL,
		});
	},
};

export default actionHandlers;
