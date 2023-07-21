/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, useThunk} from 'frontend-js-react-web';
import React, {useReducer} from 'react';

import {AppContext} from './AppContext';
import DataSetDisplay from './DataSetDisplay';
import ViewsContext, {viewsReducer} from './views/ViewsContext';

const App = ({apiURL, appURL, portletId, ...props}) => {
	const {
		activeViewSettings: {name: activeViewName, visibleFieldNames = {}},
		views,
	} = props;
	const activeView = activeViewName
		? views.find(({name}) => name === activeViewName)
		: views[0];
	const [state, dispatch] = useThunk(
		useReducer(viewsReducer, {
			activeView,
			views,
			visibleFieldNames,
		})
	);

	return (
		<AppContext.Provider value={{apiURL, appURL, portletId}}>
			<ViewsContext.Provider value={[state, dispatch]}>
				<DataSetDisplay {...props} />
			</ViewsContext.Provider>
		</AppContext.Provider>
	);
};

export default (...data) => render(App, ...data);
