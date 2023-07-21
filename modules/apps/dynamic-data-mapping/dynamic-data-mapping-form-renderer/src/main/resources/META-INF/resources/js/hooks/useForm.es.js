/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useThunk} from 'frontend-js-react-web';
import React, {useContext, useEffect, useReducer} from 'react';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {createReducer} from '../reducers/index.es';

const FormContext = React.createContext(() => {});

FormContext.displayName = 'FormContext';

export const FormNoopProvider = ({children, onEvent}) => {
	const [, dispatch] = useThunk([
		{},
		({payload, type}) => onEvent(type, payload),
	]);

	return (
		<FormContext.Provider value={dispatch}>{children}</FormContext.Provider>
	);
};

export const FormProvider = ({children, onEvent, value}) => {
	const [state, dispatch] = useThunk(
		useReducer(createReducer(onEvent), value)
	);

	useEffect(() => dispatch({payload: value, type: EVENT_TYPES.ALL}), [
		dispatch,
		value,
	]);

	return (
		<FormContext.Provider value={dispatch}>
			{children(state)}
		</FormContext.Provider>
	);
};

export const useForm = () => {
	return useContext(FormContext);
};
