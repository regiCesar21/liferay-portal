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
	resourcePath = "site_activity_bounce_rate_graphql_rest_controller_test.sql"
)
public class SiteActivityBounceRateGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "site_activity_bounce_rate_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "site_activity_bounce_rate_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "site_activity_bounce_rate_query.graphql";
	}

}