/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dom from 'metal-dom';

const CssClass = {
	ACTIVE: 'active',
	SHOW: 'show',
};

const Selector = {
	TRIGGER: '[data-toggle="liferay-tab"]',
};

class TabsProvider {
	EVENT_HIDDEN = 'liferay.tabs.hidden';
	EVENT_HIDE = 'liferay.tabs.hide';
	EVENT_SHOW = 'liferay.tabs.show';
	EVENT_SHOWN = 'liferay.tabs.shown';

	constructor() {
		if (Liferay.TabsProvider) {
			return Liferay.TabsProvider;
		}

		this._setTransitionEndEvent();

		dom.delegate(
			document.body,
			'click',
			Selector.TRIGGER,
			this._onTriggerClick
		);

		Liferay.TabsProvider = this;
	}

	hide = ({panel, trigger}) => {
		if (panel && !trigger) {
			trigger = this._getTrigger(panel);
		}

		if (!panel) {
			panel = this._getPanel(trigger);
		}

		if (this._transitioning || !panel.classList.contains(CssClass.SHOW)) {
			return;
		}

		Liferay.fire(this.EVENT_HIDE, {panel, trigger});

		trigger.classList.remove(CssClass.ACTIVE);
		trigger.setAttribute('aria-selected', false);

		panel.classList.remove(CssClass.SHOW);

		this._transitioning = true;

		dom.once(panel, this._transitionEndEvent, () => {
			panel.classList.remove(CssClass.ACTIVE);

			this._transitioning = false;

			Liferay.fire(this.EVENT_HIDDEN, {panel, trigger});
		});
	};

	show = ({panel, trigger}) => {
		if (panel && !trigger) {
			trigger = this._getTrigger(panel);
		}

		if (!panel) {
			panel = this._getPanel(trigger);
		}

		if (this._transitioning || panel.classList.contains(CssClass.SHOW)) {
			return;
		}

		const panels = Array.from(panel.parentElement.children);

		const activePanels = panels.filter((item) => {
			return item.classList.contains(CssClass.SHOW);
		});

		if (activePanels.length) {
			const activePanel = activePanels[0];

			Liferay.on(this.EVENT_HIDDEN, (event) => {
				if (event.panel === activePanel) {
					this.show({panel, trigger});
				}
			});

			this.hide({panel: activePanel});
		}
		else {
			Liferay.fire(this.EVENT_SHOW, {panel, trigger});

			trigger.classList.add(CssClass.ACTIVE);
			trigger.setAttribute('aria-selected', true);

			panel.classList.add(CssClass.ACTIVE);
			panel.classList.add(CssClass.SHOW);

			Liferay.fire(this.EVENT_SHOWN, {panel, trigger});
		}
	};

	_getPanel(trigger) {
		return document.querySelector(trigger.getAttribute('href'));
	}

	_getTrigger(panel) {
		return document.querySelector(`[href="#${panel.getAttribute('id')}"]`);
	}

	_onTriggerClick = (event) => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const panel = this._getPanel(trigger);

		if (panel && !panel.classList.contains(CssClass.SHOW)) {
			this.show({panel, trigger});
		}
	};

	_setTransitionEndEvent() {
		const sampleElement = document.body;

		const transitionEndEvents = {
			MozTransition: 'transitionend',
			OTransition: 'oTransitionEnd otransitionend',
			WebkitTransition: 'webkitTransitionEnd',
			transition: 'transitionend',
		};

		let eventName = false;

		Object.keys(transitionEndEvents).some((name) => {
			if (sampleElement.style[name] !== undefined) {
				eventName = transitionEndEvents[name];

				return true;
			}
		});

		this._transitionEndEvent = eventName;
	}
}

export default TabsProvider;
