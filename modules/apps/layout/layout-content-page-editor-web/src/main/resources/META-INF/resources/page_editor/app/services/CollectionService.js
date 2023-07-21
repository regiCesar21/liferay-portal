/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Get an asset's value
	 * @param {object} options
	 * @param {string} options.listItemStyle
	 * @param {string} options.listStyle
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionField({
		collection,
		languageId,
		listItemStyle,
		listStyle,
		onNetworkStatus,
		size,
		templateKey,
	}) {
		return serviceFetch(
			config.getCollectionFieldURL,
			{
				body: {
					languageId,
					layoutObjectReference: JSON.stringify(collection),
					listItemStyle,
					listStyle,
					size,
					templateKey,
				},
			},
			onNetworkStatus
		);
	},

	/**
	 * Get available collection mapping fields
	 * @param {object} options
	 * @param {string} options.fieldType Type of field to which we are mapping
	 * @param {string} options.itemSubtype Collection itemSubtype
	 * @param {string} options.itemType Collection itemType
	 * @param {function} options.onNetworkStatus
	 */
	getCollectionMappingFields({
		fieldType,
		itemSubtype,
		itemType,
		onNetworkStatus,
	}) {
		return serviceFetch(
			config.getCollectionMappingFieldsURL,
			{
				body: {
					fieldType,
					itemSubtype,
					itemType,
				},
			},
			onNetworkStatus
		);
	},
};
