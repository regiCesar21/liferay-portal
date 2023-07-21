/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

export default function DropdownMenu({
	actionsDropdown = false,
	additionalProps: _additionalProps,
	componentId: _componentId,
	cssClass,
	icon,
	items,
	label,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	...otherProps
}) {
	return (
		<>
			<ClayDropDownWithItems
				className={classNames({
					'dropdown-action': actionsDropdown,
				})}
				items={items.map(({data, ...rest}) => {
					const dataAttributes = data
						? Object.entries(data).reduce((acc, [key, value]) => {
								acc[`data-${key}`] = value;

								return acc;
						  }, {})
						: {};

					return {
						...dataAttributes,
						...rest,
					};
				})}
				trigger={
					<ClayButton
						className={classNames(cssClass, {
							'component-action': actionsDropdown,
						})}
						{...otherProps}
					>
						{icon && (
							<span
								className={classNames('inline-item', {
									'inline-item-before': label,
								})}
							>
								<ClayIcon symbol={icon} />
							</span>
						)}

						{label}
					</ClayButton>
				}
			/>

			<div className="quick-action-menu">
				{items.map(({data, href, icon, quickAction, ...rest}) =>
					data?.action && quickAction ? (
						<ClayButtonWithIcon
							className="component-action quick-action-item"
							displayType="unstyled"
							small={true}
							symbol={icon}
							{...rest}
						/>
					) : (
						href &&
						icon &&
						quickAction && (
							<ClayLink
								className="component-action quick-action-item"
								href={href}
								{...rest}
							>
								<ClayIcon symbol={icon} />
							</ClayLink>
						)
					)
				)}
			</div>
		</>
	);
}
