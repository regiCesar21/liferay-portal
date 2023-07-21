/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import Soy from 'metal-soy';
import React from 'react';

import {PageProvider} from '../../hooks/usePage.es';
import {useFieldTypesResource} from '../../hooks/useResource.es';
import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import {Field} from './Field.es';
import templates from './ReactFieldAdapter.soy';

/**
 * This creates a compatibility layer for the Field component on React, allowing
 * it to be called by Metal+Soy file.
 *
 * This component creates a mask for the Field component, some DDM fields access
 * the `usePage` and we need to bring the `usePage` here since this component
 * will not have a React context above it.
 */
export const ReactFieldAdapter = ({fieldType, instance, ...field}) => {
	const {resource: fieldTypes} = useFieldTypesResource();

	if (!fieldType || fieldType === '') {
		return null;
	}

	const emit = (name, event) => {
		instance.emit(name, {
			...event,
			fieldInstance: {
				...event.fieldInstance,
				element: instance.element,
			},
		});
	};

	return (
		<PageProvider value={{...field, fieldTypes}}>
			<ClayIconSpriteContext.Provider value={field.spritemap}>
				<Field
					field={{
						...field,
						type: fieldType,
					}}
					onBlur={(event) => emit('fieldBlurred', event)}
					onChange={(event) => emit('fieldEdited', event)}
					onFocus={(event) => emit('fieldFocused', event)}
				/>
			</ClayIconSpriteContext.Provider>
		</PageProvider>
	);
};

const ReactComponentAdapter = getConnectedReactComponentAdapter(
	ReactFieldAdapter
);

Soy.register(ReactComponentAdapter, templates);

export default ReactComponentAdapter;
