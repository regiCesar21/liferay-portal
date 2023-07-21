/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

import {sub} from '../../../../utils/lang.es';
import {COLORS} from '../../constants.es';

const POPOVER_MAX_WIDTH = 362;
const POPOVER_IMAGE_WIDTH = 336;

export default () => {
	const [isVisible, setVisible] = useState(false);

	return (
		<ClayPopover
			alignPosition="right"
			disableScroll
			header={Liferay.Language.get('add-apps-as-widgets')}
			show={isVisible}
			style={{maxWidth: POPOVER_MAX_WIDTH}}
			trigger={
				<ClayIcon
					className="ml-2"
					color={COLORS.secondary}
					onMouseOut={() => setVisible(false)}
					onMouseOver={() => setVisible(true)}
					symbol="question-circle-full"
				/>
			}
		>
			<p>
				{sub(
					Liferay.Language.get(
						'when-editing-a-site-page-in-the-right-sidebar-go-to-x-and-then-open-the-x-tab-you-will-find-your-deployed-apps-under-the-x-section'
					),
					[
						<b key={0}>
							{Liferay.Language.get('fragments-and-widgets')}
						</b>,
						<b key={1}>{Liferay.Language.get('widget')}</b>,
						<b key={2}>{Liferay.Language.get('app-builder')}</b>,
					],
					false
				)}
			</p>

			<img
				alt={Liferay.Language.get('fragments-and-widgets')}
				src={`${themeDisplay.getPathThemeImages()}/app_builder/fragment_and_widgets.png`}
				width={POPOVER_IMAGE_WIDTH}
			/>
		</ClayPopover>
	);
};
