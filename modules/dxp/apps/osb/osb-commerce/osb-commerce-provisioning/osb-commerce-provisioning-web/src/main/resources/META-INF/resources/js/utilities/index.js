/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {String} from './constants';
import * as MOCKS from './dataMocks/index';

export function getDataMock(componentName) {
	if (componentName in MOCKS) {
		return MOCKS[componentName];
	}

	throw new Error(`No data mock was found for: "${componentName}"`);
}

export function renderOption({label, parent, value}) {
	const optionNode = document.createElement('option');

	if (parent) {
		optionNode.value = value || '';
		optionNode.innerText = label || value || '';

		parent.appendChild(optionNode);
	}
}

export function renderRegionOptions(optionKeys, options = [], selectNode) {
	if (selectNode) {
		selectNode.innerHTML = String.BLANK;

		const baseOptionNode = document.createElement('option');

		if (options.length > 0) {
			const {labelKey, valueKey} = optionKeys;

			selectNode.disabled = false;

			options.forEach((option) => {
				const optionNode = baseOptionNode.cloneNode(false);

				optionNode.innerText = option[labelKey] || option[valueKey];
				optionNode.value = option[valueKey] || 0;

				selectNode.appendChild(optionNode);
			});
		}
		else {
			selectNode.disabled = true;
			selectNode.value = String.BLANK;
		}
	}
}

export function findCommerceRegions(commerceCountryId = 0, active = true) {
	return new Promise((responseCB) => {
		Liferay.Service(
			'/commerce.commerceregion/get-commerce-regions',
			{
				active,
				commerceCountryId,
			},
			responseCB
		);
	});
}
