/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider} from '@clayui/modal';
import React from 'react';

import {AppContextProvider} from '../../AppContext.es';
import EditFormView from './EditFormView.es';

const EditFormViewApp = ({basePortletURL, ...otherProps}) => {
	return (
		<AppContextProvider basePortletURL={basePortletURL}>
			<ClayModalProvider>
				<EditFormView {...otherProps} />
			</ClayModalProvider>
		</AppContextProvider>
	);
};

export default EditFormViewApp;
