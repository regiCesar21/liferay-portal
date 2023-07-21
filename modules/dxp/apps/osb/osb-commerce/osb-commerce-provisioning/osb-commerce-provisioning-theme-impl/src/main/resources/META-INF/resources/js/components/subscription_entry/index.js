/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

function SubscriptionEntry({
	description,
	features,
	name,
	productImageURL: imageURL,
	spritemap,
}) {
	return (
		<div className={'subscription-entry'}>
			<div className={'header'}>
				<div className={'image'}>
					<img alt={name} src={imageURL} />
				</div>

				<div className={'name'}>
					<h1>{name}</h1>
				</div>

				<div className={'description'}>
					<p className={'text-truncate'}>{description}</p>
				</div>
			</div>

			{features.length && (
				<div className={'features'}>
					<ul>
						{features.map((feature, index) => {
							return (
								<li key={index}>
									<div className={'list-icon'}>
										<ClayIcon
											spritemap={spritemap}
											symbol={'check'}
										/>
									</div>
									<div className={'feature'}>
										<span className={'text-truncate'}>
											{feature}
										</span>
									</div>
								</li>
							);
						})}
					</ul>
				</div>
			)}
		</div>
	);
}

SubscriptionEntry.defaultProps = {
	description: 'This is the description of the product',
	features: [
		'This is an amazing feature',
		'This is an amazing feature with a super long text',
		'This is yet another cool feature',
		'And much more. No, really. I mean it.',
	],
};

SubscriptionEntry.propTypes = {
	description: PropTypes.string,
	features: PropTypes.arrayOf(PropTypes.string),
	name: PropTypes.string,
	productImageURL: PropTypes.string,
	skuId: PropTypes.string,
	spritemap: PropTypes.string,
};

export default SubscriptionEntry;
