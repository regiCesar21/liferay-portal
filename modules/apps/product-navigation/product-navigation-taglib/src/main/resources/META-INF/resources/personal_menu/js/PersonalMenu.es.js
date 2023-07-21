/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

function PersonalMenu({
	color,
	isImpersonated,
	itemsURL,
	label,
	size,
	userPortraitURL,
}) {
	const [items, setItems] = useState([]);
	const preloadPromise = useRef();

	function preloadItems() {
		if (!preloadPromise.current) {
			preloadPromise.current = fetch(itemsURL)
				.then((response) => response.json())
				.then((items) => setItems(items));
		}
	}

	return (
		<ClayDropDownWithItems
			items={items}
			menuElementAttrs={{className: 'dropdown-menu-personal-menu'}}
			trigger={
				label ? (
					<div
						dangerouslySetInnerHTML={{__html: label}}
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					/>
				) : (
					<ClayButton
						aria-label={Liferay.Language.get('personal-menu')}
						displayType="unstyled"
						onFocus={preloadItems}
						onMouseOver={preloadItems}
					>
						<span className={`sticker sticker-${size}`}>
							<ClaySticker
								className={`user-icon-color-${color}`}
								shape="circle"
								size={size}
							>
								{userPortraitURL ? (
									<img
										className="sticker-img"
										src={userPortraitURL}
									/>
								) : (
									<ClayIcon symbol="user" />
								)}
							</ClaySticker>

							{isImpersonated && (
								<ClaySticker
									className="sticker-user-icon"
									id="impersonate-user-sticker"
									outside
									position="bottom-right"
									shape="circle"
									size={size ? 'sm' : ''}
								>
									<span id="impersonate-user-icon">
										<ClayIcon symbol="user" />
									</span>
								</ClaySticker>
							)}
						</span>
					</ClayButton>
				)
			}
		/>
	);
}

PersonalMenu.propTypes = {
	itemsURL: PropTypes.string,
};

export default PersonalMenu;
