/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.liferay.osb.asah.test.util.annotation.BQSQLResource;

/**
 * @author Rachael Koestartyo
 */
@BQSQLResource(
	resourcePath = "asset_metric_bag_top_pages_graphql_rest_controller_test.sql"
)
public class AssetMetricBagTopPageVisitorsGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "asset_metric_bag_top_page_visitors_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "asset_metric_bag_top_page_visitors_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "asset_metric_bag_top_pages_query.graphql";
	}

}