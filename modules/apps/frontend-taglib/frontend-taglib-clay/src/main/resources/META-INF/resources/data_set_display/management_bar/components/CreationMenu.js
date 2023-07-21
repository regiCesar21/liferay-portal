/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import {triggerAction} from '../../utilities/actionItems/index';

function CreationMenu({primaryItems}) {
	const [active, setActive] = useState(false);
	const dataSetContext = useContext(DataSetDisplayContext);

	return (
		primaryItems?.length > 0 && (
			<ul className="navbar-nav">
				<li className="nav-item">
					{primaryItems.length > 1 ? (
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={<ClayButtonWithIcon symbol="plus" />}
						>
							<ClayDropDown.ItemList>
								{primaryItems.map((item, i) => (
									<ClayDropDown.Item
										key={i}
										onClick={(event) => {
											event.preventDefault();
											setActive(false);
											triggerAction(item, dataSetContext);
										}}
									>
										{item.label}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					) : (
						<ClayButtonWithIcon
							onClick={() =>
								triggerAction(primaryItems[0], dataSetContext)
							}
							symbol="plus"
						/>
					)}
				</li>
			</ul>
		)
	);
}

CreationMenu.propTypes = {
	primaryItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'event', 'link']),
		})
	).isRequired,
	secondaryItems: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			target: PropTypes.oneOf(['modal', 'sidePanel', 'event', 'link']),
		})
	),
};

export default CreationMenu;
