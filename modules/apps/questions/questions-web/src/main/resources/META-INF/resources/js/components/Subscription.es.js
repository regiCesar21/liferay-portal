/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useState} from 'react';

import {subscribeQuery, unsubscribeQuery} from '../utils/client.es';

export default ({question: {id: messageBoardThreadId, subscribed}}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(subscribed);
	}, [subscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
	};

	const [subscribe] = useMutation(subscribeQuery, {onCompleted});
	const [unsubscribe] = useMutation(unsubscribeQuery, {onCompleted});

	const changeSubscription = () => {
		if (subscription) {
			unsubscribe({variables: {messageBoardThreadId}});
		}
		else {
			subscribe({variables: {messageBoardThreadId}});
		}
	};

	return (
		<ClayTooltipProvider>
			<ClayButton
				data-tooltip-align="top"
				displayType={subscription ? 'primary' : 'secondary'}
				monospaced
				onClick={changeSubscription}
				title={
					subscription
						? Liferay.Language.get('unsubscribe')
						: Liferay.Language.get('subscribe')
				}
			>
				<ClayIcon symbol="bell-on" />
			</ClayButton>
		</ClayTooltipProvider>
	);
};
