/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getLocalizedText} from '../utils/utils';
import Icon from './Icon';

function NoMembers(props) {
	return (
		<div className={'no-members'}>
			<p>
				<Icon spritemap={props.spritemap} symbol={'close'} />
			</p>
			<p>{getLocalizedText('no-members-found')}</p>
		</div>
	);
}

export default NoMembers;
