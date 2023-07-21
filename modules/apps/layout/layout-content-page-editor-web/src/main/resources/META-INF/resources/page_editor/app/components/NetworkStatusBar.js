/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEventListener} from 'frontend-js-react-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';

const LoadingText = ({children}) => (
	<>
		<span className="m-0 navbar-text page-editor__status-bar text-info">
			{children}
		</span>
		<ClayLoadingIndicator className={'mr-3 my-0'} small />
	</>
);

const SuccessText = ({children}) => (
	<>
		<span className="m-0 navbar-text page-editor__status-bar text-success">
			{children}
		</span>
		<ClayIcon className={'mr-3 text-success'} symbol={'check-circle'} />
	</>
);

const getContent = (isOnline, status) => {
	if (!isOnline) {
		return (
			<LoadingText>
				{Liferay.Language.get('trying-to-reconnect')}
			</LoadingText>
		);
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.draftSaved) {
		return <SuccessText>{Liferay.Language.get('saved')}</SuccessText>;
	}

	if (status === SERVICE_NETWORK_STATUS_TYPES.savingDraft) {
		return <LoadingText>{Liferay.Language.get('saving')}</LoadingText>;
	}

	return null;
};

const NetworkStatusBar = ({error, status}) => {
	const [isOnline, setIsOnline] = useState(true);

	useEffect(() => {
		if (status === SERVICE_NETWORK_STATUS_TYPES.error) {
			openToast({
				message: error,
				type: 'danger',
			});
		}
	}, [error, status]);

	useEventListener('online', () => setIsOnline(true), true, window);

	useEventListener('offline', () => setIsOnline(false), true, window);

	const content = getContent(isOnline, status);

	return (
		<li className="d-flex flex-direction-row nav-item text-truncate">
			{content}
		</li>
	);
};

export default NetworkStatusBar;
