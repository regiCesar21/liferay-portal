/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Align} from 'metal-position';
import PropTypes from 'prop-types';
import React from 'react';

import Keywords from '../Keywords';
import TotalCount from '../TotalCount';

export default function KeywordsDetail({
	currentPage,
	languageTag,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	return (
		<div className="c-p-3 traffic-source-detail">
			<TotalCount
				className="mb-2"
				dataProvider={trafficVolumeDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-volume'))}
				languageTag={languageTag}
				popoverAlign={Align.Bottom}
				popoverHeader={Liferay.Language.get('traffic-volume')}
				popoverMessage={Liferay.Language.get(
					'traffic-volume-is-the-number-of-page-views-coming-from-one-channel'
				)}
				popoverPosition="bottom"
			/>

			<TotalCount
				className="mb-4"
				dataProvider={trafficShareDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-share'))}
				percentage={true}
				popoverHeader={Liferay.Language.get('traffic-share')}
				popoverMessage={Liferay.Language.get(
					'traffic-share-is-the-percentage-of-traffic-sent-to-your-page-by-one-channel'
				)}
			/>

			<Keywords currentPage={currentPage} languageTag={languageTag} />
		</div>
	);
}

KeywordsDetail.proptypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
