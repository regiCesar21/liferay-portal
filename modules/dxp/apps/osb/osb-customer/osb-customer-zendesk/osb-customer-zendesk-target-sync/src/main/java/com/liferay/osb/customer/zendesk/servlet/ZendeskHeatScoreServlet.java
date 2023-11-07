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

		String createdAt = fieldsJSONObject.getString("createdAt");

		OffsetDateTime offsetDateTime = OffsetDateTime.parse(createdAt);

		Instant created = offsetDateTime.toInstant();

		long days = ChronoUnit.DAYS.between(created, Instant.now());

		double ageScore = ZendeskHeatScoreConstants.getAgeScore(days);

		String environment = fieldsJSONObject.getString("environment");

		int environmentScore = ZendeskHeatScoreConstants.getEnvironmentScore(
			environment);

		String heatTag = fieldsJSONObject.getString("heatTag");

		int heatTagScore = ZendeskHeatScoreConstants.getHeatTagScore(heatTag);

		String priority = fieldsJSONObject.getString("priority");

		int priorityScore = ZendeskHeatScoreConstants.getPriorityScore(
			priority);

		String product = fieldsJSONObject.getString("product");

		int productScore = ZendeskHeatScoreConstants.getProductScore(product);

		long zendeskTicketId = fieldsJSONObject.getLong("ticketId");
		long heatScoreFieldId = fieldsJSONObject.getLong("heatScoreFieldId");
		int heatScore = fieldsJSONObject.getInt("heatScore", 0);

		int totalHeatScore = (int)Math.ceil(
			(ageScore + environmentScore + heatTagScore + productScore) *
				priorityScore);

		if (totalHeatScore != heatScore) {
			updateZendeskTicketHeatScore(
				zendeskTicketId, heatScoreFieldId, totalHeatScore);
		}
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