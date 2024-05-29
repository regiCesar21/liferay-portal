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
	resourcePath = "blogs_visited_more_than_24_hours_graphql_rest_controller_test.sql"
)
public class BlogsVisitedMoreThan24HoursGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "blogs_visited_more_than_24_hours_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "blogs_visited_more_than_24_hours_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "blogs_visited_more_than_24_hours_query.graphql";
	}

}