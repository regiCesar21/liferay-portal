/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {Align} from 'metal-position';
import React, {useLayoutEffect, useRef, useState} from 'react';

import LayoutSelector from './LayoutSelector';

export default function PreviewInfoBar() {
	const [isShowPopover, setIsShowPopover] = useState(false);
	const popoverRef = useRef(null);
	const helpIconRef = useRef(null);

	useLayoutEffect(() => {
		if (isShowPopover) {
			Align.align(
				popoverRef.current,
				helpIconRef.current,
				Align.BottomRight,
				false
			);
		}
	}, [isShowPopover]);

	return (
		<div className="style-book-editor__page-preview-info-bar">
			<div className="align-items-center d-flex justify-content-center">
				<span className="style-book-editor__page-preview-text">
					{Liferay.Language.get('page-preview')}
				</span>
				<LayoutSelector />
			</div>
			<span className="d-none d-xl-block">
				{Liferay.Language.get(
					'edit-the-style-book-using-the-sidebar-form.-you-can-preview-the-changes-instantly'
				)}
			</span>

			<span className="d-block d-xl-none">
				<ClayIcon
					onMouseEnter={() => setIsShowPopover(true)}
					onMouseLeave={() => setIsShowPopover(false)}
					ref={helpIconRef}
					symbol={'question-circle'}
				/>
				{isShowPopover && (
					<ClayPopover
						alignPosition={'bottom-right'}
						ref={popoverRef}
					>
						{Liferay.Language.get(
							'edit-the-style-book-using-the-sidebar-form.-you-can-preview-the-changes-instantly'
						)}
					</ClayPopover>
				)}
			</span>
		</div>
	);
}
