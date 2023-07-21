/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

const KEY_ENTER = 13;

export default function ({namespace}) {
	const layoutsTree = document.getElementById(`${namespace}layoutsTree`);

	const eventDelegates = [];

	const dropdownAction = (event) => {
		if (event.keyCode === KEY_ENTER) {
			event.stopImmediatePropagation();

			const trigger = event.delegateTarget;
			const menu = trigger.parentNode.querySelector(
				'.pages-tree-dropdown .dropdown-menu'
			);

			Liferay.DropdownProvider.show({menu, trigger});
		}
	};

	const dropdownActionKeyupHandler = delegate(
		layoutsTree,
		'keyup',
		'.pages-tree-dropdown button.dropdown-toggle',
		dropdownAction
	);

	eventDelegates.push(dropdownActionKeyupHandler);

	const linkAction = (event) => {
		if (event.keyCode === KEY_ENTER) {
			event.stopImmediatePropagation();
			window.location.href = event.delegateTarget.href;
		}
	};

	const linkActionKeyupHandler = delegate(
		layoutsTree,
		'keyup',
		'a.layout-tree[data-url], .pages-tree-dropdown a.dropdown-item',
		linkAction
	);

	eventDelegates.push(linkActionKeyupHandler);

	const viewCollectionItemsActionOptionQuery = (event) => {
		Liferay.Util.openModal({
			id: `${namespace}viewCollectionItemsDialog`,
			title: Liferay.Language.get('collection-items'),
			url: event.delegateTarget.dataset.viewCollectionItemsUrl,
		});
	};

	const viewCollectionItemsActionOptionQueryClickHandler = delegate(
		layoutsTree,
		'click',
		'.view-collection-items-action-option.collection',
		viewCollectionItemsActionOptionQuery
	);

	eventDelegates.push(viewCollectionItemsActionOptionQueryClickHandler);

	return {
		dispose() {
			Liferay.destroyComponent(`${namespace}pagesTree`);

			eventDelegates.forEach((eventDelegate) => eventDelegate.dispose());
		},
	};
}
