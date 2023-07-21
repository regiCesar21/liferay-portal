/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useCallback, useContext, useReducer} from 'react';

const INITIAL_STATE = {
	historicalWarning: false,
	publishedToday: false,
	warning: false,
};
const ADD_HISTORICAL_WARNING = 'add-historical-warning';
const ADD_WARNING = 'add-warning';

const noop = () => {};

export const StoreContext = createContext([INITIAL_STATE, noop]);

function reducer(state = INITIAL_STATE, action) {
	if (action.type === ADD_HISTORICAL_WARNING) {
		return state.historicalWarning
			? state
			: {...state, historicalWarning: true};
	}
	else if (action.type === ADD_WARNING) {
		return state.warning ? state : {...state, warning: true};
	}

	return state;
}

export function StoreContextProvider({children, value}) {
	const stateAndDispatch = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreContext.Provider value={stateAndDispatch}>
			{children}
		</StoreContext.Provider>
	);
}

export function useHistoricalWarning() {
	const [state, dispatch] = useContext(StoreContext);

	const addHistoricalWarning = useCallback(() => {
		dispatch({
			type: ADD_HISTORICAL_WARNING,
		});
	}, [dispatch]);

	const hasHistoricalWarning = state.historicalWarning;

	return [hasHistoricalWarning, addHistoricalWarning];
}

export function useWarning() {
	const [state, dispatch] = useContext(StoreContext);

	const addWarning = useCallback(() => {
		dispatch({
			type: ADD_WARNING,
		});
	}, [dispatch]);

	const hasWarning = state.warning;

	return [hasWarning, addWarning];
}
