/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {AddItemDropDown} from './AddItemDropdown';

export const EmptyState = () => (
	<div className="p-3 taglib-empty-result-message text-center">
		<div className="taglib-empty-state" />

		<h1 className="taglib-empty-result-message-title">
			{Liferay.Language.get('no-element-yet')}
		</h1>

		<p className="taglib-empty-result-message-description">
			{Liferay.Language.get(
				'fortunately-it-is-very-easy-to-add-new-ones'
			)}
		</p>

		<div className="taglib-empty-result-message-actionDropdownItems">
			<AddItemDropDown
				trigger={
					<ClayButton small>{Liferay.Language.get('new')}</ClayButton>
				}
			/>
		</div>
	</div>
);
