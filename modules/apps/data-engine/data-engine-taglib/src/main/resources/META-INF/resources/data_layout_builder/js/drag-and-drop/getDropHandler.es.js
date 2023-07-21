/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	dropCustomObjectField,
	dropFieldSet,
	dropLayoutBuilderField,
} from '../actions.es';
import {
	DRAG_DATA_DEFINITION_FIELD,
	DRAG_FIELDSET,
	DRAG_FIELD_TYPE,
} from './dragTypes.es';

export const getDropHandler = ({dataDefinition, dataLayoutBuilder}) => {
	return ({item, monitor, sourceItem}) => {
		const {data, type} = item;
		const {fieldName, origin, parentField, ...indexes} = sourceItem;

		if (monitor.didDrop()) {
			return;
		}

		switch (type) {
			case DRAG_FIELD_TYPE: {
				if (
					parentField &&
					parentField.nestedFields &&
					parentField.type !== 'fieldset'
				) {
					throw new Error(
						Liferay.Language.get(
							'you-cannot-drop-new-fields-to-a-deprecated-field-group'
						)
					);
				}

				const payload = dropLayoutBuilderField({
					dataLayoutBuilder,
					fieldName,
					fieldTypeName: data.name,
					indexes,
					parentFieldName: parentField?.fieldName,
				});

				dataLayoutBuilder.dispatch(
					origin === 'empty' ? 'fieldAdded' : 'sectionAdded',
					payload
				);
				break;
			}
			case DRAG_DATA_DEFINITION_FIELD: {
				const payload = dropCustomObjectField({
					dataDefinition,
					dataDefinitionFieldName: data.name,
					dataLayoutBuilder,
					fieldName,
					indexes,
					parentFieldName: parentField?.fieldName,
				});

				dataLayoutBuilder.dispatch(
					origin === 'empty' ? 'fieldAdded' : 'sectionAdded',
					payload
				);
				break;
			}
			case DRAG_FIELDSET:
				dataLayoutBuilder.dispatch(
					'fieldSetAdded',
					dropFieldSet({
						dataLayoutBuilder,
						fieldName,
						fieldSet: data.fieldSet,
						indexes,
						parentFieldName: parentField?.fieldName,
						properties: data.properties,
						useFieldName: data.useFieldName,
					})
				);
				break;
			default:
				break;
		}
	};
};
