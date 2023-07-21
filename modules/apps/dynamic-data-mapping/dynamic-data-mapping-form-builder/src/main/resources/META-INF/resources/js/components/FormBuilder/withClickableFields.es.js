/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';

import formBuilderProps from './props.es';

const withClickableFields = (ChildComponent) => {
	class ClickableFields extends Component {
		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate(
					'click',
					'.ddm-field-container',
					this._handleFieldClicked.bind(this)
				)
			);
		}

		disposed() {
			this._eventHandler.removeAllListeners();
		}

		render() {
			return <ChildComponent {...this.props} />;
		}

		_handleFieldClicked(event) {
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const {fieldName} = delegateTarget.dataset;
			let {activePage} = this.context.store.state;

			event.stopPropagation();
			if (
				!event.delegateTarget.children[1].hasAttribute('aria-grabbed')
			) {
				activePage = parseInt(
					dom.closest(event.delegateTarget, '[data-ddm-page]').dataset
						.ddmPage,
					10
				);
			}

			dispatch('fieldClicked', {activePage, fieldName});
		}
	}

	ClickableFields.PROPS = {
		...formBuilderProps,
	};

	return ClickableFields;
};

export default withClickableFields;
