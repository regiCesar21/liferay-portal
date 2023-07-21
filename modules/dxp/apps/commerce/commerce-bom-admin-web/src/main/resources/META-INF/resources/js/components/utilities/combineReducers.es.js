/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function combineReducers(reducers) {
	const reducerKeys = Object.keys(reducers);

	return function combineReducers(state = {}, action) {
		const nextState = {};

		let hasChanged = false;

		reducerKeys.forEach((key) => {
			const reducer = reducers[key],
				previousStateForKey = state[key],
				nextStateForKey = reducer(previousStateForKey, action);

			if (typeof nextStateForKey === 'undefined') {
				throw new Error(`The reducer for "${key}" is undefined`);
			}

			nextState[key] = nextStateForKey;

			if (nextStateForKey !== previousStateForKey) {
				hasChanged = true;
			}
		});

		return hasChanged ? nextState : state;
	};
}
