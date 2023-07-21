/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import KeywordsDetail from './detail/KeywordsDetail';
import ReferralDetail from './detail/ReferralDetail';
import SocialDetail from './detail/SocialDetail';

const TRAFFIC_CHANNELS = {
	DIRECT: 'direct',
	ORGANIC: 'organic',
	PAID: 'paid',
	REFERRAL: 'referral',
	SOCIAL: 'social',
};

export default function Detail({
	currentPage,
	languageTag,
	onCurrentPageChange,
	onTrafficSourceNameChange,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	return (
		<>
			<div className="c-pt-3 c-px-3 d-flex">
				<ClayButton
					displayType="unstyled"
					onClick={() => {
						onCurrentPageChange({view: 'main'});
						onTrafficSourceNameChange('');
					}}
					small={true}
				>
					<ClayIcon symbol="angle-left-small" />
				</ClayButton>

				<div className="align-self-center flex-grow-1 mx-2">
					<strong>{currentPage.data.title}</strong>
				</div>
			</div>

			{(currentPage.view === TRAFFIC_CHANNELS.ORGANIC ||
				currentPage.view === TRAFFIC_CHANNELS.PAID) &&
				currentPage.data.countryKeywords.length > 0 && (
					<KeywordsDetail
						currentPage={currentPage}
						languageTag={languageTag}
						trafficShareDataProvider={trafficShareDataProvider}
						trafficVolumeDataProvider={trafficVolumeDataProvider}
					/>
				)}

			{currentPage.view === TRAFFIC_CHANNELS.REFERRAL && (
				<ReferralDetail
					currentPage={currentPage}
					languageTag={languageTag}
					timeSpanOptions={timeSpanOptions}
					trafficShareDataProvider={trafficShareDataProvider}
					trafficVolumeDataProvider={trafficVolumeDataProvider}
				/>
			)}

			{currentPage.view === TRAFFIC_CHANNELS.SOCIAL && (
				<SocialDetail
					currentPage={currentPage}
					languageTag={languageTag}
					timeSpanOptions={timeSpanOptions}
					trafficShareDataProvider={trafficShareDataProvider}
					trafficVolumeDataProvider={trafficVolumeDataProvider}
				/>
			)}
		</>
	);
}

Detail.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	onCurrentPageChange: PropTypes.func.isRequired,
	onTrafficSourceNameChange: PropTypes.func.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
