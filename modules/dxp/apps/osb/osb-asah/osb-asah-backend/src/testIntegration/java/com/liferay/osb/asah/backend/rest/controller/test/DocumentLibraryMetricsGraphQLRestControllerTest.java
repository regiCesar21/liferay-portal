/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.liferay.osb.asah.test.util.annotation.BQSQLResource;

import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Rachael Koestartyo
 */
@BQSQLResource(
	resourcePath = "document_library_metrics_graphql_rest_controller_test.sql"
)
public class DocumentLibraryMetricsGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "document_library_metrics_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "document_library_metrics_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "document_library_metrics_query.graphql";
	}

	@Override
	protected void assertJSONObject(
		JSONObject expectedJSONObject, JSONObject jsonObject) {

		JSONAssert.assertEquals(expectedJSONObject, jsonObject, true);
	}

}