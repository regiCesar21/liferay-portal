/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {UPDATE_FIELDSETS} from '../../../actions.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import {addItem} from '../../../utils/client.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({availableLanguageIds, childrenContext}) => {
	const [{fieldSets}, dispatch] = useContext(AppContext);
	const {state: childrenState} = childrenContext;
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const {contentType, fieldSetContentType} = dataLayoutBuilder.props;

	return (name) => {
		const {
			dataDefinition: {dataDefinitionFields},
			dataLayout: {dataLayoutPages},
		} = childrenState;

		const fieldSet = {
			availableLanguageIds,
			dataDefinitionFields,
			defaultDataLayout: {
				dataLayoutPages,
				name,
			},
			name,
		};

		return addItem(
			`/o/data-engine/v2.0/data-definitions/by-content-type/${
				fieldSetContentType || contentType
			}`,
			fieldSet
		)
			.then((dataDefinitionFieldSet) => {
				dispatch({
					payload: {
						fieldSets: [...fieldSets, dataDefinitionFieldSet],
					},
					type: UPDATE_FIELDSETS,
				});

				successToast(Liferay.Language.get('fieldset-saved'));

				return Promise.resolve();
			})
			.catch(({message}) => errorToast(message));
	};
};
