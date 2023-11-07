/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.constants;

import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Jenny Chen
 */
public interface ZendeskHeatScoreConstants {

	public static final String ENVIRONMENT_PRODUCTION = "production";

	public static final String HEAT_TAG_ACCOUNT_RISK_BUSINESS_CRITICAL =
		"account_risk_business_critical";

	public static final String
		HEAT_TAG_ACCOUNT_RISK_CUSTOMER_EXEC_TEAM_INVOLVEMENT =
			"account_risk_customer_exec_team_involvement";

	public static final String HEAT_TAG_ACCOUNT_RISK_RENEWAL_RISK =
		"account_risk_renewal_risk";

	public static final String HEAT_TAG_CUSTOMER_UPGRADE = "customer_upgrade";

	public static final String HEAT_TAG_EVENT_7_DAYS = "event_7_days";

	public static final String HEAT_TAG_EVENT_30_DAYS = "event_30_days";

	public static final String HEAT_TAG_EVENT_MISSED = "event_missed";

	public static final String HEAT_TAG_GO_LIVE_7_DAYS = "go_live_7_days";

	public static final String HEAT_TAG_GO_LIVE_30_DAYS = "go_live_30_days";

	public static final String HEAT_TAG_GO_LIVE_MISSED = "go_live_missed";

	public static final String HEAT_TAG_SECURITY_EXPERIENCING_ATTACK =
		"security_experiencing_attack";

	public static final String HEAT_TAG_SECURITY_RERPORTED_VULNERABILITIES =
		"security_reported_vulnerabilities";

	public static final String HEAT_TAG_SECURITY_SCAN_RESULT_CONCERNS =
		"security_scan_result_concerns";

	public static final String PRIORITY_HIGH = "High";

	public static final String PRIORITY_URGENT = "Urgent";

	public static final String PRODUCT_LXC_BUSINESS = "prd_lxc_business";

	public static final String PRODUCT_LXC_CSP = "prd_lxc_csp";

	public static final String PRODUCT_LXC_ENGAGE = "prd_lxc_engage";

	public static final String PRODUCT_LXC_ENTERPRISE = "prd_lxc_enterprise";

	public static final String PRODUCT_LXC_PRO = "prd_lxc_pro";

	public static final String PRODUCT_LXC_SM = "prd_lxc_sm";

	public static final String PRODUCT_LXC_SUPPORT = "prd_lxc_support";

	public static final String PRODUCT_LXC_TRANSACT = "prd_lxc_transact";

	public static final String[] PRODUCTS_LXC = {
		PRODUCT_LXC_BUSINESS, PRODUCT_LXC_CSP, PRODUCT_LXC_ENGAGE,
		PRODUCT_LXC_ENTERPRISE, PRODUCT_LXC_PRO, PRODUCT_LXC_SM,
		PRODUCT_LXC_SUPPORT, PRODUCT_LXC_TRANSACT
	};

	public static double getAgeScore(long days) {
		return days * 0.25;
	}

	public static int getEnvironmentScore(String environment) {
		if (environment.equals(ENVIRONMENT_PRODUCTION)) {
			return 8;
		}

		return 0;
	}

	public static int getHeatTagScore(String heatTag) {
		if (heatTag.equals(HEAT_TAG_ACCOUNT_RISK_BUSINESS_CRITICAL)) {
			return 21;
		}
		else if (heatTag.equals(
					HEAT_TAG_ACCOUNT_RISK_CUSTOMER_EXEC_TEAM_INVOLVEMENT)) {

			return 8;
		}
		else if (heatTag.equals(HEAT_TAG_ACCOUNT_RISK_RENEWAL_RISK)) {
			return 13;
		}
		else if (heatTag.equals(HEAT_TAG_CUSTOMER_UPGRADE)) {
			return 5;
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_7_DAYS)) {
			return 5;
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_30_DAYS)) {
			return 3;
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_MISSED)) {
			return 8;
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_7_DAYS)) {
			return 13;
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_30_DAYS)) {
			return 8;
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_MISSED)) {
			return 21;
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_EXPERIENCING_ATTACK)) {
			return 21;
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_RERPORTED_VULNERABILITIES)) {
			return 13;
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_SCAN_RESULT_CONCERNS)) {
			return 5;
		}

		return 0;
	}

	public static int getPriorityScore(String priority) {
		if (priority.equals(PRIORITY_HIGH)) {
			return 8;
		}
		else if (priority.equals(PRIORITY_URGENT)) {
			return 13;
		}

		return 1;
	}

	public static int getProductScore(String product) {
		if (ArrayUtil.contains(PRODUCTS_LXC, product)) {
			return 5;
		}

		return 0;
	}

}