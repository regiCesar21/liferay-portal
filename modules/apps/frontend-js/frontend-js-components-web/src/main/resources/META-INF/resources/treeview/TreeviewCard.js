/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import TreeviewContext from './TreeviewContext';

export default function TreeviewCard({node}) {
	const {state} = useContext(TreeviewContext);
	const {filterQuery, focusedNodeId} = state;

	const path =
		node.nodePath && filterQuery ? (
			<div className="lfr-card-subtitle-text text-default text-truncate treeview-node-name">
				{node.nodePath}
			</div>
		) : null;

	return (
		<div
			className={classNames({
				'card-type-directory': true,
				disabled: node.disabled,
				focused: node.id === focusedNodeId,
				'form-check': true,
				'form-check-card': true,
				'form-check-middle-left': true,
				selected: node.selected,
			})}
		>
			<div className="card card-horizontal">
				<div className="card-body">
					<ClayCard.Row className="autofit-row-center">
						<div className="autofit-col">
							<ClaySticker displayType="secondary" inline>
								<ClayIcon symbol={node.icon} />
							</ClaySticker>
						</div>

						<div className="autofit-col autofit-col-expand autofit-col-gutters">
							<ClayCard.Description displayType="title">
								{node.name}
							</ClayCard.Description>
						</div>
					</ClayCard.Row>

					{path}
				</div>
			</div>
		</div>
	);
}

TreeviewCard.propTypes = {
	node: PropTypes.shape({
		icon: PropTypes.string,
		name: PropTypes.string.isRequired,
		nodePath: PropTypes.string,
	}).isRequired,
};
