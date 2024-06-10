/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.liferay.osb.asah.test.util.annotation.BQSQLResource;

/**
 * @author Sillas Cavalcanti
 */
@BQSQLResource(
	resourcePath = "bounce_rate_metric_graphql_rest_controller_test.sql"
)
public class BounceRateMetricGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "bounce_rate_metric_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "bounce_rate_metric_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "bounce_rate_metric_query.graphql";
	}

}