/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.servlet;

import com.liferay.osb.customer.zendesk.connector.service.ZendeskBaseWebService;
import com.liferay.osb.customer.zendesk.constants.ZendeskHeatScoreConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Jenny Chen
 */
@Component(
	immediate = true,
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_NAME + "=com.liferay.osb.customer.zendesk.servlet.ZendeskHeatScoreServlet",
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN + "=/zendesk-heat-score-servlet/*"
	},
	service = Servlet.class
)
public class ZendeskHeatScoreServlet extends ZendeskBaseServlet {

	public void postHeatScore(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		JSONObject jsonObject = getRequestJSONObject(request);

		JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

		BigDecimal accountRiskScore =
			ZendeskHeatScoreConstants.getAccountRiskScore(
				fieldsJSONObject.getString("accountRisk"));

		BigDecimal ageScore = ZendeskHeatScoreConstants.getAgeScore(
			ChronoUnit.DAYS.between(
				OffsetDateTime.parse(
					fieldsJSONObject.getString("createdAt")
				).toInstant(),
				Instant.now()));

		BigDecimal causedByRegressionScore =
			ZendeskHeatScoreConstants.getCausedByRegressionScore(
				fieldsJSONObject.getString("causedByRegression"));

		BigDecimal environmentScore =
			ZendeskHeatScoreConstants.getEnvironmentScore(
				fieldsJSONObject.getString("environment"));

		BigDecimal heatTagScore = ZendeskHeatScoreConstants.getHeatTagScore(
			fieldsJSONObject.getString("heatTag"));

		BigDecimal priorityScore = ZendeskHeatScoreConstants.getPriorityScore(
			fieldsJSONObject.getString("priority"));

		BigDecimal productScore = ZendeskHeatScoreConstants.getProductScore(
			fieldsJSONObject.getString("product"));

		BigDecimal ticketTagScore = ZendeskHeatScoreConstants.getTicketTagScore(
			StringUtil.split(
				fieldsJSONObject.getString("ticketTags"), StringPool.SPACE));

		BigDecimal totalHeatScore = BigDecimal.ZERO;

		totalHeatScore = totalHeatScore.add(accountRiskScore);
		totalHeatScore = totalHeatScore.add(causedByRegressionScore);
		totalHeatScore = totalHeatScore.add(environmentScore);
		totalHeatScore = totalHeatScore.add(heatTagScore);
		totalHeatScore = totalHeatScore.add(productScore);
		totalHeatScore = totalHeatScore.add(ticketTagScore);
		totalHeatScore = totalHeatScore.multiply(ageScore);
		totalHeatScore = totalHeatScore.multiply(priorityScore);

		int roundedTotalHeatScore = (int)Math.ceil(
			totalHeatScore.doubleValue());

		int heatScore = fieldsJSONObject.getInt("heatScore", 0);

		if (roundedTotalHeatScore != heatScore) {
			updateZendeskTicketHeatScore(
				fieldsJSONObject.getLong("ticketId"),
				fieldsJSONObject.getLong("heatScoreFieldId"),
				roundedTotalHeatScore);
		}

		boolean automation = fieldsJSONObject.getBoolean("automation");

		if (automation) {
			removeZendeskAutoHeatScoreTag(fieldsJSONObject.getLong("ticketId"));
		}
	}

	protected JSONObject removeZendeskAutoHeatScoreTag(long zendeskTicketId)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("safe_update", true);

		JSONArray tagsJSONArray = JSONFactoryUtil.createJSONArray();

		tagsJSONArray.put("auto_heat_score");

		jsonObject.put("tags", tagsJSONArray);

		SimpleDateFormat updateStampFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		jsonObject.put("updated_stamp", updateStampFormat.format(new Date()));

		return _zendeskBaseWebService.delete(
			"/api/v2/tickets/" + zendeskTicketId + "/tags.json",
			jsonObject.toString());
	}

	protected JSONObject updateZendeskTicketHeatScore(
			long zendeskTicketId, long heatScoreFieldId, int totalHeatScore)
		throws PortalException {

		JSONObject ticketJSONObject = JSONFactoryUtil.createJSONObject();

		JSONArray customFieldsJSONArray = JSONFactoryUtil.createJSONArray();

		JSONObject customFieldJSONObject = JSONFactoryUtil.createJSONObject();

		customFieldJSONObject.put("id", heatScoreFieldId);
		customFieldJSONObject.put("value", totalHeatScore);

		customFieldsJSONArray.put(customFieldJSONObject);

		ticketJSONObject.put("custom_fields", customFieldsJSONArray);

		ticketJSONObject.put("safe_update", true);

		SimpleDateFormat updateStampFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		ticketJSONObject.put(
			"updated_stamp", updateStampFormat.format(new Date()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("ticket", ticketJSONObject);

		return _zendeskBaseWebService.put(
			"/api/v2/tickets/" + zendeskTicketId + ".json",
			jsonObject.toString());
	}

	@Reference
	private ZendeskBaseWebService _zendeskBaseWebService;

}