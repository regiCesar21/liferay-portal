/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {AppContext} from '../../AppContext';
import DataSetDisplayContext from '../../DataSetDisplayContext';
import persistActiveView from '../../thunks/persistActiveView';
import ViewsContext from '../../views/ViewsContext';

function ActiveViewSelector({views}) {
	const {appURL, portletId} = useContext(AppContext);
	const [active, setActive] = useState(false);
	const [{activeView}, dispatch] = useContext(ViewsContext);
	const {id} = useContext(DataSetDisplayContext);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					displayType="secondary"
					symbol={activeView.thumbnail}
				/>
			}
		>
			<ClayDropDown.ItemList>
				{views.map(({label, name, thumbnail}) => (
					<ClayDropDown.Item
						key={name}
						onClick={(event) => {
							event.preventDefault();
							setActive(false);
							dispatch(
								persistActiveView({
									activeViewName: name,
									appURL,
									id,
									portletId,
								})
							);
						}}
					>
						<ClayIcon className="mr-3" symbol={thumbnail} />
						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

ActiveViewSelector.propTypes = {
	views: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			thumbnail: PropTypes.string.isRequired,
		})
	),
};

export default ActiveViewSelector;
