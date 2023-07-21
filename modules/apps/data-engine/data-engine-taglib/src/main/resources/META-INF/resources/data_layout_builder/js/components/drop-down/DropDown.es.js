/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayPopover from '@clayui/popover';
import React, {useEffect, useState} from 'react';

const {Item, ItemList} = ClayDropDown;

export default ({actions, disabled}) => {
	const [active, setActive] = useState(false);
	const [showPopover, setShowPopover] = useState(false);

	const DropdownButton = (
		<ClayButtonWithIcon
			className="page-link"
			disabled={disabled}
			displayType="unstyled"
			symbol="ellipsis-v"
		/>
	);

	if (actions.length === 0) {
		return DropdownButton;
	}

	const onSelectItem = (event, action) => {
		event.preventDefault();

		if (typeof action.action === 'function') {
			action.action();
		}

		setActive(false);
	};

	useEffect(() => {
		if (showPopover && !active) {
			setShowPopover(false);
		}
	}, [active, showPopover]);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.RightCenter}
			className="dropdown-action"
			onActiveChange={(item) => setActive(item)}
			trigger={DropdownButton}
		>
			<ItemList>
				{actions.map((action, index) => {
					const ItemWrapper = () => (
						<Item
							className={action.className}
							key={index}
							onClick={(event) => onSelectItem(event, action)}
						>
							{action.name}
						</Item>
					);

					if (action.popover) {
						const {alignPosition, body, header} = action.popover;

						return (
							<ClayPopover
								alignPosition={alignPosition}
								disableScroll
								header={header}
								show={showPopover}
								style={{zIndex: 1420}}
								trigger={
									<div
										onMouseOut={() => setShowPopover(false)}
										onMouseOver={() => setShowPopover(true)}
									>
										<ItemWrapper />
									</div>
								}
							>
								{body}
							</ClayPopover>
						);
					}

					return <ItemWrapper key={index} />;
				})}
			</ItemList>
		</ClayDropDown>
	);
};
