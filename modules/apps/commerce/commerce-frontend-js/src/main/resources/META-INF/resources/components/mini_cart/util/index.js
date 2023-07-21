/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	DISCOUNT_LEVEL_PREFIX,
	ORDER_DETAILS_ENDPOINT,
	ORDER_UUID_PARAMETER,
} from './constants';

export function isNonnull(...values) {
	return !!values.find((value) => parseFloat(value) > 0);
}

export function collectDiscountLevels(price) {
	return Object.keys(price).reduce((levels, key) => {
		if (key.startsWith(DISCOUNT_LEVEL_PREFIX)) {
			levels.push(price[key].toFixed(2));
		}

		return levels;
	}, []);
}

export function parseOptions(stringifiedJSON) {
	let options;

	try {
		options = JSON.parse(stringifiedJSON);
	}
	catch (ignore) {
		options = '';
	}

	return Array.isArray(options)
		? options.map(({value}) => `${value}`).join(', ')
		: options;
}

function generatedOrderDetailURL() {
	const baseURL = new URL(
		`${Liferay.ThemeDisplay.getCanonicalURL()}${ORDER_DETAILS_ENDPOINT}`
	);

	baseURL.searchParams.append('p_p_id', DEFAULT_ORDER_DETAILS_PORTLET_ID);
	baseURL.searchParams.append('p_p_lifecycle', '0');
	baseURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_mvcRenderCommandName`,
		'/commerce_open_order_content/edit_commerce_order'
	);
	baseURL.searchParams.append(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_commerceOrderUuid`,
		'0'
	);

	return baseURL;
}

export function regenerateOrderDetailURL(orderDetailURL, orderUUID) {
	const originalURL = orderDetailURL
		? new URL(orderDetailURL)
		: generatedOrderDetailURL();

	originalURL.searchParams.set(
		`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_${ORDER_UUID_PARAMETER}`,
		orderUUID
	);

	return originalURL.toString();
}

export function summaryDataMapper(summary) {
	return Object.keys(summary).reduce((values, key) => {
		const summaryItem = {value: summary[key]};

		switch (key) {
			case 'itemsQuantity':
				values.push({
					label: Liferay.Language.get('quantity'),
					...summaryItem,
				});
				break;
			case 'subtotalFormatted':
				values.push({
					label: Liferay.Language.get('subtotal'),
					...summaryItem,
				});
				break;
			case 'totalDiscountValueFormatted':
				values.push({
					label: Liferay.Language.get('order-discount'),
					...summaryItem,
				});
				break;
			case 'totalFormatted':
				values.push({
					label: Liferay.Language.get('total'),
					style: 'big',
					...summaryItem,
				});
				break;
			default:
				break;
		}

		return values;
	}, []);
}

export function hasErrors(cartItems) {
	return !!cartItems.find(({errorMessages}) => !!errorMessages);
}
