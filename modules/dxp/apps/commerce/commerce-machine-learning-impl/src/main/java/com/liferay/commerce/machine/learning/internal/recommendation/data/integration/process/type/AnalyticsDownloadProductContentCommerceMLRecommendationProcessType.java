/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type;

import com.liferay.commerce.data.integration.process.type.ProcessType;
import com.liferay.commerce.machine.learning.internal.data.integration.BaseProcessType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Athanasius (7.3.x)
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.data.integration.process.type.key=" + AnalyticsDownloadProductContentCommerceMLRecommendationProcessType.KEY,
		"commerce.data.integration.process.type.order=100"
	},
	service = ProcessType.class
)
@Deprecated
public class AnalyticsDownloadProductContentCommerceMLRecommendationProcessType
	extends BaseProcessType {

	public static final String KEY =
		"analytics-download-product-content-commerce-ml-recommendation";

	@Override
	public String getKey() {
		return KEY;
	}

}