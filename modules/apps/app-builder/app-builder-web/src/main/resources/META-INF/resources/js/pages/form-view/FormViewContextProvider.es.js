/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useState} from 'react';

import DataLayoutBuilderInstanceProvider from './DataLayoutBuilderInstanceProvider.es';
import FormViewContext from './FormViewContext.es';

export default ({children, dataLayoutBuilder}) => {
	const [state, setState] = useState(dataLayoutBuilder.getState());

	useEffect(() => {
		const callback = () => {
			setState(dataLayoutBuilder.getState());
		};

		dataLayoutBuilder.on('contextUpdated', callback);

		return () =>
			dataLayoutBuilder.removeEventListener('contextUpdated', callback);
	}, [dataLayoutBuilder]);

	const dispatch = useCallback(
		(action) => {
			dataLayoutBuilder.dispatchAction(action);
		},
		[dataLayoutBuilder]
	);

	return (
		<FormViewContext.Provider value={[state, dispatch]}>
			<DataLayoutBuilderInstanceProvider
				dataLayoutBuilder={dataLayoutBuilder}
			>
				{children}
			</DataLayoutBuilderInstanceProvider>
		</FormViewContext.Provider>
	);
};
