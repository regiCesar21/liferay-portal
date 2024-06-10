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
	resourcePath = "session_duration_metric_graphql_rest_controller_test.sql"
)
public class SessionDurationMetricGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "session_duration_metric_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "session_duration_metric_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "session_duration_metric_query.graphql";
	}

}