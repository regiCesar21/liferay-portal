/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classnames from 'classnames';
import {navigate} from 'frontend-js-web';
import React, {createRef, useEffect, useState} from 'react';

import {
	CHECKOUT_DEFAULT_LAYOUT_ENDPOINT,
	COMMERCE_CHECKOUT_PORTLET_ID,
	TRIAL_DEFAULT_LAYOUT_ENDPOINT,
	TRIAL_SKU,
	addToOrder
} from '../../helper/index';
import SubscriptionEntry from '../subscription_entry/index';

const PRODUCT_HIGHLIGHT = 'highlightProduct';

function CardEntryRenderer({
	checkoutURL,
	commerceAccountId,
	commerceChannelGroupId,
	commerceChannelId,
	commerceCurrencyCode,
	detailURL,
	isFeatured,
	namespace,
	productId,
	sku,
	skuId,
	...entry
}) {
	const [isHighlighted, setIsHighlighted] = useState(isFeatured),
		cardEntryRef = createRef();

	useEffect(() => {
		const onProductHighlight = ({id}) =>
			!id
				? setIsHighlighted(isFeatured)
				: setIsHighlighted(id === productId);

		Liferay.on(`${namespace}_${PRODUCT_HIGHLIGHT}`, onProductHighlight);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [namespace]);

	useEffect(() => {
		const cardEntryElement = cardEntryRef.current,
			onHover = () =>
				Liferay.fire(`${namespace}_${PRODUCT_HIGHLIGHT}`, {
					id: productId,
				}),
			onOut = () =>
				Liferay.fire(`${namespace}_${PRODUCT_HIGHLIGHT}`, {id: ''});

		cardEntryElement.addEventListener('mouseover', onHover);
		cardEntryElement.addEventListener('mouseout', onOut);
	}, [cardEntryRef, namespace, productId]);

	return (
		<div
			className={classnames(
				'card',
				'osb-commerce-product-card',
				'd-flex flex-column justify-content-between text-center',
				isHighlighted && 'is-highlighted'
			)}
			ref={cardEntryRef}
		>
			<SubscriptionEntry {...entry} />

			<div className={'actions'}>
				<div>
					<ClayButton
						displayType={
							isFeatured || isHighlighted
								? 'primary'
								: 'secondary'
						}
						onClick={() => addToOrder({
							commerceAccountId,
							commerceChannelGroupId,
							commerceChannelId,
							commerceCurrencyCode,
							skuId,
						}).then(
							({orderUUID}) => {
								let to = Liferay.ThemeDisplay.getCanonicalURL();

								if (TRIAL_SKU === sku) {
									to += TRIAL_DEFAULT_LAYOUT_ENDPOINT;
								}
								else {
									to = new URL(`${
										to.replace('web', 'group')
									}${CHECKOUT_DEFAULT_LAYOUT_ENDPOINT}`);

									to.searchParams.set(
										`_${
											COMMERCE_CHECKOUT_PORTLET_ID
										}_commerceOrderUuid`,
										orderUUID
									);

									to = to.toString();
								}

								window.location.href = to;
							}
						)}
					>
						{Liferay.Language.get(
							TRIAL_SKU === sku ? 'start-trial' : 'subscribe'
						)}
					</ClayButton>
				</div>

				<div>
					<ClayButton
						displayType={'link'}
						onClick={() => {
							navigate(detailURL);
						}}
						type={'button'}
					>
						{Liferay.Language.get('learn-more')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
}

export default CardEntryRenderer;
