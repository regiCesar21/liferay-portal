/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {compose} from 'dynamic-data-mapping-form-renderer';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import LayoutProvider from '../LayoutProvider/LayoutProvider.es';
import {FormBuilderBase} from './FormBuilder.es';
import withActionableFields from './withActionableFields.es';
import withClickableFields from './withClickableFields.es';
import withEditablePageHeader from './withEditablePageHeader.es';
import withMoveableFields from './withMoveableFields.es';
import withMultiplePages from './withMultiplePages.es';
import withResizeableColumns from './withResizeableColumns.es';

/**
 * LayoutProvider listens to your children's events to
 * control the `pages` and make manipulations.
 * @extends Component
 */

class FormBuilderWithLayoutProvider extends Component {
	render() {
		const {formBuilderProps, layoutProviderProps} = this.props;

		const LProvider = LayoutProvider;

		const composeList = [
			withActionableFields,
			withClickableFields,
			withMoveableFields,
			withResizeableColumns,
		];

		if (layoutProviderProps.allowMultiplePages) {
			composeList.push(withMultiplePages);
			composeList.push(withEditablePageHeader);
		}

		const FBuilder = compose(...composeList)(FormBuilderBase);

		return (
			<LProvider {...layoutProviderProps}>
				<FBuilder {...formBuilderProps} />
			</LProvider>
		);
	}
}

FormBuilderWithLayoutProvider.PROPS = {
	formBuilderProps: Config.object(),
	layoutProviderProps: Config.object(),
};

export default FormBuilderWithLayoutProvider;
