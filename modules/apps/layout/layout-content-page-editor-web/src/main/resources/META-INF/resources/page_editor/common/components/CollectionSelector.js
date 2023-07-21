/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../app/config/index';
import ItemSelector from './ItemSelector';

export default function CollectionSelector({
	collectionTitle,
	itemSelectorURL,
	label,
	onCollectionSelect,
}) {
	const eventName = `${config.portletNamespace}selectInfoList`;

	return (
		<ItemSelector
			eventName={eventName}
			itemSelectorURL={itemSelectorURL || config.infoListSelectorURL}
			label={label}
			onItemSelect={onCollectionSelect}
			quickMappedInfoItems={config.selectedMappingTypes?.linkedCollection}
			selectedItemTitle={collectionTitle}
			showMappedItems={!!config.selectedMappingTypes?.linkedCollection}
		/>
	);
}

CollectionSelector.propTypes = {
	collectionTitle: PropTypes.string,
	label: PropTypes.string,
	onCollectionSelect: PropTypes.func.isRequired,
};
