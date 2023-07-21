/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {GUEST_ID} from '../../helper/index';
import CardEntryRenderer from './CardEntryRenderer';

function CardListRenderer({
	commerceAccountId = GUEST_ID,
	commerceChannelGroupId,
	commerceChannelId,
	commerceCurrencyCode,
	cpEntries,
	checkoutURL,
	portletNamespace,
}) {
	return (
		<div className={'align-items-center d-flex'}>
			{cpEntries.map((entry, index) => (
				<div
					className={
						'col-md-4 col-sm-12 osb-commerce-product-card-container'
					}
					key={index}
				>
					<CardEntryRenderer
						checkoutURL={checkoutURL}
						commerceAccountId={commerceAccountId}
						commerceChannelId={commerceChannelId}
						commerceChannelGroupId={commerceChannelGroupId}
						commerceCurrencyCode={commerceCurrencyCode}
						isFeatured={index === 1}
						isTrial={index === 0}
						namespace={portletNamespace}
						{...entry}
					/>
				</div>
			))}
		</div>
	);
}

export default CardListRenderer;
