/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import Collapse from '../../../common/components/Collapse';
import TabItem from './TabItem';

export default function TabCollection({collection, isSearchResult, open}) {
	return (
		<Collapse
			key={collection.collectionId}
			label={collection.label}
			open={isSearchResult || open}
		>
			{collection.collections &&
				collection.collections.map((collection, index) => (
					<TabCollection
						collection={collection}
						isSearchResult={isSearchResult}
						key={index}
					/>
				))}

			<ul className="list-unstyled">
				{collection.children.map((item) => (
					<React.Fragment key={item.itemId}>
						<TabItem item={item} key={item.itemId} />
						{item.portletItems?.length && (
							<TabPortletItems item={item} />
						)}
					</React.Fragment>
				))}
			</ul>
		</Collapse>
	);
}

TabCollection.proptypes = {
	collection: PropTypes.shape({
		children: PropTypes.array,
		collectionId: PropTypes.string,
		label: PropTypes.string,
	}).isRequired,
	isSearchResult: PropTypes.bool,
	open: PropTypes.bool,
};

const TabPortletItems = ({item}) => (
	<ul className="list-unstyled">
		{item.portletItems.map((portlet, index) => (
			<TabItem item={portlet} key={index} />
		))}
	</ul>
);

TabPortletItems.proptypes = {
	data: PropTypes.shape({
		instanceable: PropTypes.bool,
		portletId: PropTypes.string,
		portletItemId: PropTypes.string,
		used: PropTypes.bool,
	}).isRequired,
	disabled: PropTypes.bool,
	icon: PropTypes.string,
	itemId: PropTypes.string,
	label: PropTypes.string,
	portletItems: PropTypes.array,
	preview: PropTypes.string,
	type: PropTypes.string,
};
