/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {
	subscribeSectionQuery,
	unsubscribeSectionQuery,
} from '../utils/client.es';

export default ({
	onSubscription,
	section: {id: messageBoardSectionId, parentSection, subscribed},
}) => {
	const [subscription, setSubscription] = useState(false);

	useEffect(() => {
		setSubscription(
			subscribed || (parentSection && parentSection.subscribed)
		);
	}, [messageBoardSectionId, parentSection, subscribed]);

	const onCompleted = () => {
		setSubscription(!subscription);
		if (onSubscription) {
			onSubscription(!subscription);
		}
	};

	const update = (proxy) => {
		proxy.evict(`MessageBoardSection:${messageBoardSectionId}`);
		proxy.gc();
	};

	const [subscribeSection] = useMutation(subscribeSectionQuery, {
		onCompleted,
		update,
	});

	const [unsubscribeSection] = useMutation(unsubscribeSectionQuery, {
		onCompleted,
		update,
	});

	const changeSubscription = () => {
		if (subscription) {
			unsubscribeSection({variables: {messageBoardSectionId}});
		}
		else {
			subscribeSection({variables: {messageBoardSectionId}});
		}
	};

	const btnTitle = subscription
		? Liferay.Language.get('subscribed')
		: Liferay.Language.get('subscribe');

	return (
		<ClayButton
			displayType={subscription ? 'primary' : 'secondary'}
			onClick={changeSubscription}
			title={btnTitle}
		>
			<ClayIcon symbol="bell-on" />

			<span className="c-ml-2 d-none d-sm-inline-block">{btnTitle}</span>
		</ClayButton>
	);
};
