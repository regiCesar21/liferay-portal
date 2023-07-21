/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import FormViewContext from './FormViewContext.es';
import {deleteDefinitionField} from './actions.es';

export default ({dataLayoutBuilder}) => {
	const [, dispatch] = useContext(FormViewContext);

	return (event) => {
		const {pages} = dataLayoutBuilder.getStore();
		const visitor = new PagesVisitor(pages);

		if (visitor.containsField(event.fieldName, true)) {
			dataLayoutBuilder.dispatch('fieldDeleted', event);
		}
		else {
			dispatch(deleteDefinitionField(event.fieldName));
		}
	};
};
