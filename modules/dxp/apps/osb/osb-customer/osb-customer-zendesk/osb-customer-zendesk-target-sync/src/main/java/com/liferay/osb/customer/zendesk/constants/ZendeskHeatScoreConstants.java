/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.constants;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.math.BigDecimal;

/**
 * @author Jenny Chen
 */
public interface ZendeskHeatScoreConstants {

	public static final String ACCOUNT_RISK_BUSINESS_CRITICAL =
		"account_risk_business_critical";

	public static final String ACCOUNT_RISK_CUSTOMER_EXEC_TEAM_INVOLVEMENT =
		"account_risk_customer_exec_team_involvement";

	public static final String ACCOUNT_RISK_RENEWAL_RISK =
		"account_risk_renewal_risk";

	public static final String CAUSED_BY_REGRESSION_DEPLOYMENT =
		"caused_by_regression_deployment";

	public static final String CAUSED_BY_REGRESSION_PRODUCT =
		"caused_by_regression_product";

	public static final String ENVIRONMENT_PRODUCTION = "production";

	public static final String HEAT_TAG_CUSTOMER_UPGRADE = "customer_upgrade";

	public static final String HEAT_TAG_ESCALATION_OVERRIDE =
		"escalation_override";

	public static final String HEAT_TAG_EVENT_7_DAYS = "event_7_days";

	public static final String HEAT_TAG_EVENT_30_DAYS = "event_30_days";

	public static final String HEAT_TAG_EVENT_MISSED = "event_missed";

	public static final String HEAT_TAG_GO_LIVE_7_DAYS = "go_live_7_days";

	public static final String HEAT_TAG_GO_LIVE_30_DAYS = "go_live_30_days";

	public static final String HEAT_TAG_GO_LIVE_MISSED = "go_live_missed";

	public static final String HEAT_TAG_SECURITY_EXPERIENCING_ATTACK =
		"security_experiencing_attack";

	public static final String HEAT_TAG_SECURITY_REPORTED_VULNERABILITIES =
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

	public static final String TICKET_TAG_ESCALATION_FORM = "escalation_form";

	public static BigDecimal getAccountRiskScore(String accountRisk) {
		if (accountRisk.equals(ACCOUNT_RISK_BUSINESS_CRITICAL) ||
			accountRisk.equals(ACCOUNT_RISK_CUSTOMER_EXEC_TEAM_INVOLVEMENT) ||
			accountRisk.equals(ACCOUNT_RISK_RENEWAL_RISK)) {

			return BigDecimal.valueOf(21);
		}

		return BigDecimal.ZERO;
	}

	public static BigDecimal getAgeScore(long days) {
		if (days == 0) {
			return BigDecimal.ONE;
		}

		BigDecimal ageScore = BigDecimal.valueOf(0.9);
		BigDecimal increment = BigDecimal.valueOf(0.1);

		for (int i = 1; i <= days; i++) {
			ageScore = ageScore.add(increment);
		}

		return ageScore;
	}

	public static BigDecimal getCausedByRegressionScore(
		String causedByRegression) {

		if (causedByRegression.equals(CAUSED_BY_REGRESSION_DEPLOYMENT) ||
			causedByRegression.equals(CAUSED_BY_REGRESSION_PRODUCT)) {

			return BigDecimal.valueOf(21);
		}

		return BigDecimal.ZERO;
	}

	public static BigDecimal getEnvironmentScore(String environment) {
		if (environment.equals(ENVIRONMENT_PRODUCTION)) {
			return BigDecimal.valueOf(8);
		}

		return BigDecimal.valueOf(1);
	}

	public static BigDecimal getHeatTagScore(String heatTag) {
		if (heatTag.equals(HEAT_TAG_CUSTOMER_UPGRADE)) {
			return BigDecimal.valueOf(5);
		}
		else if (heatTag.equals(HEAT_TAG_ESCALATION_OVERRIDE)) {
			return BigDecimal.valueOf(1000);
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_7_DAYS)) {
			return BigDecimal.valueOf(5);
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_30_DAYS)) {
			return BigDecimal.valueOf(3);
		}
		else if (heatTag.equals(HEAT_TAG_EVENT_MISSED)) {
			return BigDecimal.valueOf(8);
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_7_DAYS)) {
			return BigDecimal.valueOf(13);
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_30_DAYS)) {
			return BigDecimal.valueOf(8);
		}
		else if (heatTag.equals(HEAT_TAG_GO_LIVE_MISSED)) {
			return BigDecimal.valueOf(21);
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_EXPERIENCING_ATTACK)) {
			return BigDecimal.valueOf(21);
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_REPORTED_VULNERABILITIES)) {
			return BigDecimal.valueOf(13);
		}
		else if (heatTag.equals(HEAT_TAG_SECURITY_SCAN_RESULT_CONCERNS)) {
			return BigDecimal.valueOf(5);
		}

		return BigDecimal.ZERO;
	}

	public static BigDecimal getPriorityScore(String priority) {
		if (priority.equals(PRIORITY_HIGH)) {
			return BigDecimal.valueOf(8);
		}
		else if (priority.equals(PRIORITY_URGENT)) {
			return BigDecimal.valueOf(13);
		}

		return BigDecimal.ONE;
	}

	public static BigDecimal getProductScore(String product) {
		if (ArrayUtil.contains(PRODUCTS_LXC, product)) {
			return BigDecimal.valueOf(5);
		}

		return BigDecimal.ZERO;
	}

	public static BigDecimal getTicketTagScore(String[] ticketTags) {
		if (ArrayUtil.contains(ticketTags, TICKET_TAG_ESCALATION_FORM)) {
			return BigDecimal.valueOf(5);
		}

		return BigDecimal.ZERO;
	}

}