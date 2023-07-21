/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-jsx';
import {Config} from 'metal-state';

import formBuilderProps from './props.es';

const withMultiplePages = (ChildComponent) => {
	class MultiplePages extends Component {
		getPages() {
			let {pages} = this.props;
			const {
				defaultLanguageId,
				paginationMode,
				successPageSettings,
			} = this.props;

			if (successPageSettings.enabled) {
				pages = [
					...pages,
					{
						contentRenderer: 'success',
						defaultLanguageId,
						paginationItemRenderer: `${paginationMode}_success`,
						rows: [],
						successPageSettings,
					},
				];
			}

			return pages.map((page) => {
				return {
					...page,
					enabled: true,
				};
			});
		}

		getPaginationPosition() {
			const {pages, paginationMode} = this.props;
			const position = paginationMode === 'wizard' ? 'top' : 'bottom';

			return pages.length > 1 ? position : 'top';
		}

		render() {
			return (
				<div
					class={`container ddm-paginated-builder ${this.getPaginationPosition()}`}
				>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</div>
			);
		}
	}

	MultiplePages.PROPS = {

		/**
		 * @instance
		 * @memberof LayoutProvider
		 * @type {boolean}
		 */

		allowSuccessPage: Config.bool().value(true),

		...formBuilderProps,
	};

	MultiplePages.STATE = {

		/**
		 * @default false
		 * @instance
		 * @memberof FormRenderer
		 * @type {boolean}
		 */

		dropdownExpanded: Config.bool().value(false).internal(),
	};

	return MultiplePages;
};

export default withMultiplePages;
