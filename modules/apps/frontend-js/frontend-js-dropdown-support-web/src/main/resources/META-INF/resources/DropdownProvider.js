/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import domAlign from 'dom-align';
import dom from 'metal-dom';

const CssClass = {
	SHOW: 'show',
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-dropdown"]',
};

const KEYCODES = {
	ENTER: 13,
	SPACE: 32,
};

class DropdownProvider {
	EVENT_HIDDEN = 'liferay.dropdown.hidden';
	EVENT_HIDE = 'liferay.dropdown.hide';
	EVENT_SHOW = 'liferay.dropdown.show';
	EVENT_SHOWN = 'liferay.dropdown.shown';

	constructor() {
		if (Liferay.DropdownProvider) {
			return Liferay.DropdownProvider;
		}

		dom.delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		dom.delegate(
			document.body,
			'keydown',
			Selector.TRIGGER,
			this._onKeyDown
		);

		Liferay.DropdownProvider = this;
	}

	hide = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (!menu.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_HIDE, {menu, trigger});

		trigger.parentElement.classList.remove(CssClass.SHOW);
		trigger.setAttribute('aria-expanded', false);

		menu.classList.remove(CssClass.SHOW);

		Liferay.fire(this.EVENT_HIDDEN, {menu, trigger});
	};

	show = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (menu.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_SHOW, {menu, trigger});

		trigger.parentElement.classList.add(CssClass.SHOW);
		trigger.setAttribute('aria-expanded', true);

		const clickOutsideHandler = (event) => {
			if (
				!menu.contains(event.target) &&
				!trigger.contains(event.target)
			) {
				this.hide({menu, trigger});

				document.removeEventListener('mousedown', clickOutsideHandler);
				document.removeEventListener('touchstart', clickOutsideHandler);
			}
		};

		document.addEventListener('mousedown', clickOutsideHandler);
		document.addEventListener('touchstart', clickOutsideHandler);

		menu.classList.add(CssClass.SHOW);

		domAlign(menu, trigger, {
			overflow: {
				adjustX: true,
				adjustY: true,
			},
			points: ['tl', 'bl'],
		});

		Liferay.fire(this.EVENT_SHOWN, {menu, trigger});
	};

	_getMenu(trigger) {
		return trigger.parentElement.querySelector('.dropdown-menu');
	}

	_getTrigger(menu) {
		return menu.parentElement.querySelector('.dropdown-toggle');
	}

	_onKeyDown = (event) => {
		if (
			event.keyCode === KEYCODES.ENTER ||
			event.keyCode === KEYCODES.SPACE
		) {
			this._onTriggerClick(event);
		}
	};

	_onTriggerClick = (event) => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const menu = this._getMenu(trigger);

		if (menu) {
			if (menu.classList.contains(CssClass.SHOW)) {
				this.hide({menu, trigger});
			}
			else {
				this.show({menu, trigger});
			}
		}
	};
}

export default DropdownProvider;
