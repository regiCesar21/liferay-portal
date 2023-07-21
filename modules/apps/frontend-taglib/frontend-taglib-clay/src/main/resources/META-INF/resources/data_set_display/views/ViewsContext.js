/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ACTION_UPDATE_ACTIVE_VIEW} from '../actions/updateActiveView';
import {ACTION_UPDATE_VIEW_COMPONENT} from '../actions/updateViewComponent';
import {ACTION_UPDATE_VISIBLE_FIELD_NAMES} from '../actions/updateVisibleFieldNames';

export const viewsReducer = (state, {type, value}) => {
	const {activeView, views} = state;

	if (type === ACTION_UPDATE_ACTIVE_VIEW) {
		return {
			...state,
			activeView: views.find(({name}) => name === value),
		};
	}
	else if (type === ACTION_UPDATE_VIEW_COMPONENT) {
		const {component, name} = value;

		return {
			...state,
			activeView:
				name === activeView?.name
					? {
							...activeView,
							component,
					  }
					: activeView,
			views: views.map((view) =>
				view.name === name
					? {
							...view,
							component,
					  }
					: view
			),
		};
	}
	else if (type === ACTION_UPDATE_VISIBLE_FIELD_NAMES) {
		return {
			...state,
			visibleFieldNames: value,
		};
	}

	return state;
};

export default React.createContext({
	activeView: null,
	views: [],
	visibleFieldNames: {},
});
