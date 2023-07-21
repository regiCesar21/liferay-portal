/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PortletBase, openSelectionModal} from 'frontend-js-web';
import * as dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import {Config} from 'metal-state';

class PersonAccountEntryEventHandler extends PortletBase {

	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.eventHandler_.add(
			dom.on(
				this.selectUserButton,
				'click',
				this._handleSelectUserButtonClicked.bind(this)
			)
		);

		this.eventHandler_.add(
			dom.delegate(
				this.container,
				'click',
				this.removeUserLinkSelector,
				this._handleRemoveUserButtonClicked.bind(this)
			)
		);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	_handleOnSelect(selectedItemData) {
		this._setSearchContainerUser(selectedItemData);
	}

	_handleRemoveUserButtonClicked() {
		this.searchContainer.deleteRow(1, this.searchContainer.getData());

		this.userIdInput.value = null;
	}

	_handleSelectUserButtonClicked() {
		this._selectAccountUser();
	}

	_selectAccountUser() {
		openSelectionModal({
			id: this.ns(this.selectUserEventName),
			onSelect: this._handleOnSelect.bind(this),
			selectEventName: this.ns(this.selectUserEventName),
			selectedData: [this.userIdInput.value],
			title: Liferay.Language.get('assign-user'),
			url: this.selectUserURL,
		});
	}

	_setSearchContainerUser({
		emailaddress: emailAddress,
		entityid: userId,
		entityname: userName,
		jobtitle: jobTitle,
	}) {
		this.userIdInput.value = userId;

		this.searchContainer.deleteRow(1, this.searchContainer.getData());
		this.searchContainer.addRow(
			[userName, emailAddress, jobTitle, this.removeUserIconMarkup],
			userId
		);
		this.searchContainer.updateDataStore([userId]);
	}

	_setSearchContainer(searchContainerId) {
		return Liferay.SearchContainer.get(this.ns(searchContainerId));
	}

	_setElement(selector) {
		return this.one(selector);
	}
}

PersonAccountEntryEventHandler.STATE = {
	container: Config.string().setter('_setElement'),
	removeUserIconMarkup: Config.string(),
	removeUserLinkSelector: Config.string(),
	searchContainer: Config.string().setter('_setSearchContainer'),
	selectUserButton: Config.string().setter('_setElement'),
	selectUserEventName: Config.string(),
	selectUserURL: Config.string(),
	userIdInput: Config.string().setter('_setElement'),
};

export default PersonAccountEntryEventHandler;
