/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import React from 'react';

export default function PersonalMenuEntry({
	size = 'lg',
	portraitURL,
	items = [],
}) {
	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'dropdown-menu-personal-menu'}}
			trigger={
				<ClayButton
					aria-label={Liferay.Language.get('personal-menu')}
					displayType="unstyled"
				>
					<span className={`sticker sticker-${size}`}>
						<ClaySticker
							displayType="light"
							shape="circle"
							size={size}
						>
							{portraitURL ? (
								<img
									className="sticker-img"
									src={portraitURL}
								/>
							) : (
								<ClayIcon symbol="user" />
							)}
						</ClaySticker>
					</span>
				</ClayButton>
			}
		/>
	);
}
