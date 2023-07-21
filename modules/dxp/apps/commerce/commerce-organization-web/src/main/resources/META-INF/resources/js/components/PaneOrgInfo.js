/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLocalizedText} from '../utils/utils';

export default function PaneOrgInfo(props) {
	const {childrenNo, colorIdentifier, orgName} = props;

	return (
		<div className="pane-org-info">
			<div
				className={'org-color-identifier'}
				style={{backgroundColor: colorIdentifier}}
			></div>
			<div className="org-data">
				<p>{orgName}</p>
				<p>
					{childrenNo
						? `${childrenNo} ${getLocalizedText(
								'suborganizations'
						  )}`
						: `${getLocalizedText('suborganization')}`}
				</p>
			</div>
			<div className="org-actions" role="button" tabIndex="1">
				<p style={{display: 'none'}}>&sdot;&sdot;&sdot;</p>
			</div>
		</div>
	);
}
