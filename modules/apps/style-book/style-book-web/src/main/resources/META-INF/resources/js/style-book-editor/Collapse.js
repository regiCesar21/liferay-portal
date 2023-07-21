/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const Collapse = ({children, label}) => {
	const [isOpen, setIsOpen] = useState(true);
	const collapseIcon = isOpen ? 'angle-down-small' : 'angle-right-small';
	const collapseIconClassName = isOpen ? 'open' : 'closed';

	const handleClick = () => {
		setIsOpen((isOpen) => !isOpen);
	};

	return (
		<div
			className={classNames(
				'style-book-editor__sidebar-collapse',
				'panel-group panel-group-flush'
			)}
		>
			<button
				aria-expanded={isOpen}
				className={classNames(
					'btn',
					'btn-unstyled',
					'collapse-icon',
					'sheet-subtitle',
					{
						collapsed: !isOpen,
					}
				)}
				onClick={handleClick}
			>
				<span className="c-inner" tabIndex="-1">
					{label}
					<span className={`collapse-icon-${collapseIconClassName}`}>
						<ClayIcon key={collapseIcon} symbol={collapseIcon} />
					</span>
				</span>
			</button>
			{isOpen && children}
		</div>
	);
};

Collapse.propTypes = {
	children: PropTypes.node.isRequired,
	label: PropTypes.string.isRequired,
	open: PropTypes.bool,
};

export default Collapse;
