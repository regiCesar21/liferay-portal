/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {
	DELETE_DATA_DEFINITION_FIELD,
	UPDATE_FIELDSETS,
} from '../../../actions.es';
import {deleteItem} from '../../../utils/client.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({dataLayoutBuilder}) => {
	const [{dataDefinition, fieldSets}, dispatch] = useContext(AppContext);

	return (fieldSet) => {
		const endpoint = '/o/data-engine/v2.0/data-definitions/';

		const onError = () =>
			errorToast(Liferay.Language.get('the-item-could-not-be-deleted'));

		const onSuccess = () => {
			dispatch({
				payload: {
					fieldSets: fieldSets.filter(({id}) => id !== fieldSet.id),
				},
				type: UPDATE_FIELDSETS,
			});

			successToast(
				Liferay.Language.get('the-item-was-deleted-successfully')
			);

			return Promise.resolve();
		};

		const deleteField = () => {
			const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
				({customProperties: {ddmStructureId}}) =>
					ddmStructureId == fieldSet.id
			);

			if (dataDefinitionField) {
				const {pages} = dataLayoutBuilder.getStore();
				const visitor = new PagesVisitor(pages);
				const fieldName = dataDefinitionField.name;
				const event = {
					activePage: 0,
					fieldName,
				};
				if (visitor.containsField(fieldName, true)) {
					dataLayoutBuilder.dispatch('fieldDeleted', event);
				}
				else {
					dispatch({
						payload: {fieldName},
						type: DELETE_DATA_DEFINITION_FIELD,
					});
				}
			}

			return Promise.resolve();
		};

		return deleteItem(`${endpoint}${fieldSet.id}`)
			.then(deleteField)
			.then(onSuccess)
			.catch(onError);
	};
};
