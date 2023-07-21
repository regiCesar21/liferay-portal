/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {createPortletURL, navigate, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

const Component = ({
	checkoutURL,
	iconClass,
	iconName,
	items,
	namespace,
	selectURL,
	title,
}) => {
	const onSelectPublication = function () {
		openSelectionModal({
			onSelect: (event) => {
				const portletURL = createPortletURL(checkoutURL, {
					ctCollectionId: event.ctcollectionid,
				});

				navigate(portletURL.toString());
			},
			selectEventName: namespace + 'selectPublication',
			title: Liferay.Language.get('select-a-publication'),
			url: selectURL,
		});
	};

	const onDestroyPortlet = function () {
		Liferay.detach('destroyPortlet', onDestroyPortlet);
		Liferay.detach(namespace + 'openDialog', onSelectPublication);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);

	Liferay.on(namespace + 'openDialog', onSelectPublication);

	return (
		<ClayDropDownWithItems
			alignmentPosition={Align.BottomCenter}
			items={items}
			trigger={
				<button className="change-tracking-indicator-button">
					<ClayIcon className={iconClass} symbol={iconName} />

					<span className="change-tracking-indicator-title">
						{title}
					</span>

					<ClayIcon symbol="caret-bottom" />
				</button>
			}
		/>
	);
};

Component.propTypes = {
	checkoutURL: PropTypes.string,
	iconClass: PropTypes.string,
	iconName: PropTypes.string,
	namespace: PropTypes.string,
	selectURL: PropTypes.string,
	title: PropTypes.string,
};

export default function (props) {
	return <Component {...props} />;
}
