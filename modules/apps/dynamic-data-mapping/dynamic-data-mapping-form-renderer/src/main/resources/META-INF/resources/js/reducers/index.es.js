/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import defaultReducer from './defaultReducer.es';
import fieldReducer from './fieldReducer.es';
import pageReducer from './pageReducer.es';

const reducers = [defaultReducer, fieldReducer, pageReducer];

let hasSentEvent = false;

export const createReducer = (onEvent) => {
	return (state, action) =>
		reducers.reduce((prevState, reducer, index) => {
			const nextProperties = reducer(prevState, action);

			if (prevState === nextProperties) {

				// We only propagate the event when no event has been handled
				// before and we are at the last interaction of the reducers,
				// this ensures that we do not send unnecessary events.

				if (index === reducers.length - 1 && !hasSentEvent) {
					hasSentEvent = true;
					onEvent(action.type, action.payload);
				}

				return nextProperties;
			}

			// The reducer propagates the events so that they can be
			// handled by other components in the upper layer that are
			// not the responsibility of the form renderer, some
			// operations are transformed and treated here, we issue
			// the final result to avoid double operations that are
			// costly.

			hasSentEvent = false;
			onEvent(action.type, {
				transformation: nextProperties,
				...(typeof action.payload === 'object'
					? action.payload
					: {value: action.payload}),
			});

			return {
				...prevState,
				...nextProperties,
			};
		}, state);
};
