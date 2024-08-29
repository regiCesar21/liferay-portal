/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.annotation.SQLResource;

/**
 * @author Leslie Wong
 */
@BQSQLResource(
	resourcePath = "audience_report_form_data_fetcher_rest_controller_test_bq.sql"
)
@SQLResource(
	resourcePath = "audience_report_form_data_fetcher_rest_controller_test.sql"
)
public class AudienceReportFormDataFetcherRestControllerTest
	extends BaseGraphQLRestControllerTestCase {

	@Override
	public String getBodyPath() {
		return "audience_report_form_body.json";
	}

	@Override
	public String getExpectedResultPath() {
		return "audience_report_form_expected_result.json";
	}

	@Override
	public String getQueryPath() {
		return "audience_report_form_query.graphql";
	}

}