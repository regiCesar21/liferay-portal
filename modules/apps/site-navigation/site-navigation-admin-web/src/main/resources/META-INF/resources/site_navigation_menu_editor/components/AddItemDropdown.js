/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';

export const AddItemDropDown = ({trigger}) => {
	const [active, setActive] = useState(false);
	const {addSiteNavigationMenuItemOptions, portletNamespace} = useConstants();

	return (
		<>
			<ClayDropDown
				active={active}
				className="mr-3"
				onActiveChange={setActive}
				trigger={trigger}
			>
				<ClayDropDown.ItemList>
					{addSiteNavigationMenuItemOptions.map(({data, label}) => (
						<ClayDropDown.Item
							key={label}
							onClick={() => {
								Liferay.Util.openWindow({
									dialog: {
										destroyOnHide: true,
									},
									id: `${portletNamespace}addMenuItem`,
									title: label,
									uri: data.href,
								});
							}}
						>
							{label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
};

AddItemDropDown.propTypes = {
	trigger: PropTypes.element,
};
