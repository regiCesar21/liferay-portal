/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.liferay.osb.asah.test.util.annotation.BQSQLResource;

import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcos Martins
 */
@BQSQLResource(resourcePath = "asset_pages_graphql_rest_controller_test.sql")
public class AssetPagesGraphQLRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "asset_pages_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "expected_asset_pages.json";
	}

	@Override
	public String getQueryPath() {
		return "asset_pages_query.graphql";
	}

	@Override
	protected void assertJSONObject(
		JSONObject expectedJSONObject, JSONObject jsonObject) {

		JSONAssert.assertEquals(expectedJSONObject, jsonObject, true);
	}

}