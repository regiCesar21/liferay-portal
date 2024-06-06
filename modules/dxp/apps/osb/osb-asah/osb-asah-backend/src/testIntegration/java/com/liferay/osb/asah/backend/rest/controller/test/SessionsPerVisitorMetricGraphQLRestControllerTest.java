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
	resourcePath = "sessions_per_visitor_metric_graphql_rest_controller_test.sql"
)
public class SessionsPerVisitorMetricGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "sessions_per_visitor_metric_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "sessions_per_visitor_metric_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "sessions_per_visitor_metric_query.graphql";
	}

}