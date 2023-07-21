/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormNoop,
	PagesVisitor,
	compose,
	getConnectedReactComponentAdapter,
} from 'dynamic-data-mapping-form-renderer';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {pageStructure} from '../../util/config.es';
import withActionableFields from './withActionableFields.es';
import withClickableFields from './withClickableFields.es';
import withEditablePageHeader from './withEditablePageHeader.es';
import withMoveableFields from './withMoveableFields.es';
import withMultiplePages from './withMultiplePages.es';
import withResizeableColumns from './withResizeableColumns.es';

const FormNoopAdapter = getConnectedReactComponentAdapter(FormNoop);

/**
 * Builder.
 * @extends Component
 */

class FormBuilderBase extends Component {
	attached() {
		const formBasicInfo = document.querySelector('.ddm-form-basic-info');
		const translationManager = document.querySelector(
			'.ddm-translation-manager'
		);

		if (formBasicInfo && translationManager) {
			formBasicInfo.classList.remove('hide');
			translationManager.classList.remove('hide');
		}
	}

	preparePagesForRender(pages) {
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(
			(field) => {
				if (
					field.type === 'select' &&
					!field.dataSourceType.includes('manual')
				) {
					field = {
						...field,
						options: [
							{
								label: Liferay.Language.get(
									'dynamically-loaded-data'
								),
								value: 'dynamic',
							},
						],
						value: 'dynamic',
					};
				}

				return {
					...field,
					readOnly: true,
				};
			},
			true,
			true
		);
	}

	render() {
		const {props} = this;
		const {
			activePage,
			allowNestedFields,
			dnd,
			editingLanguageId,
			pages,
			paginationMode,
			portletNamespace,
			spritemap,
			successPageSettings,
			view,
		} = props;

		return (
			<div class="ddm-form-builder-wrapper">
				<div class="container ddm-form-builder">
					<FormNoopAdapter
						activePage={activePage}
						allowNestedFields={allowNestedFields}
						dnd={dnd}
						editable={true}
						editingLanguageId={editingLanguageId}
						pages={this.preparePagesForRender(pages)}
						paginationMode={paginationMode}
						portletNamespace={portletNamespace}
						ref="FormRenderer"
						spritemap={spritemap}
						successPageSettings={successPageSettings}
						view={view}
					/>
				</div>
			</div>
		);
	}
}

FormBuilderBase.PROPS = {

	/**
	 * @default
	 * @instance
	 * @memberof FormBuilder
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	defaultLanguageId: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	editingLanguageId: Config.string(),

	/**
	 * @default []
	 * @instance
	 * @memberof FormBuilder
	 * @type {?array<object>}
	 */

	pages: Config.arrayOf(pageStructure).value([]),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	paginationMode: Config.string().required(),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * @instance
	 * @memberof FormBuilder
	 * @type {object}
	 */

	successPageSettings: Config.shapeOf({
		body: Config.object(),
		enabled: Config.bool(),
		title: Config.object(),
	}).value({}),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?string}
	 */

	view: Config.string(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormBuilder
	 * @type {?bool}
	 */

	viewMode: Config.bool(),
};

export default compose(
	withActionableFields,
	withClickableFields,
	withEditablePageHeader,
	withMoveableFields,
	withMultiplePages,
	withResizeableColumns
)(FormBuilderBase);

export {FormBuilderBase};
