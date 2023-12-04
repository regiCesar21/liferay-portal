/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const adminBaseURL = '/o/headless-admin-workflow/v1.0';

const metricsBaseURL = '/o/portal-workflow-metrics/v1.0';

const headers = {
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
};

export {adminBaseURL, headers, metricsBaseURL};
