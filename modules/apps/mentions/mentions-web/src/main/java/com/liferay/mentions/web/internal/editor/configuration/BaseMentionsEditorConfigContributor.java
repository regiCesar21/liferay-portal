/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.web.internal.editor.configuration;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.matcher.MentionsMatcherUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public class BaseMentionsEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		JSONObject autoCompleteConfigJSONObject = JSONUtil.put(
			"requestTemplate", "query={query}");

		JSONObject triggerJSONObject = JSONUtil.put(
			"regExp",
			"(?:\\strigger|^trigger)(" +
				MentionsMatcherUtil.getScreenNameRegularExpression() + ")"
		).put(
			"resultFilters", "function(query, results) {return results;}"
		).put(
			"resultTextLocator", "screenName"
		);

		LiferayPortletURL portletURL = (LiferayPortletURL)getPortletURL(
			themeDisplay, requestBackedPortletURLFactory);

		portletURL.setAnchor(false);

		String source =
			portletURL.toString() + "&" +
				PortalUtil.getPortletNamespace(MentionsPortletKeys.MENTIONS);

		triggerJSONObject.put(
			"source", source
		).put(
			"term", "@"
		).put(
			"tplReplace", "{mention}"
		);

		String tplResults = StringBundler.concat(
			"<div class=\"p-1 autofit-row autofit-row-center\">",
			"<div class=\"autofit-col inline-item-before\">{portraitHTML}",
			"</div><div class=\"autofit-col autofit-col-expand\">",
			"<strong class=\"text-truncate\">{fullName}</strong>",
			"<div class=\"autofit-col-expand\">",
			"<small class=\"text-truncate\">@{screenName}</small></div></div>",
			"</div>");

		triggerJSONObject.put("tplResults", tplResults);

		autoCompleteConfigJSONObject.put(
			"trigger", JSONUtil.put(triggerJSONObject));

		jsonObject.put("autocomplete", autoCompleteConfigJSONObject);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",autocomplete";
		}
		else {
			extraPlugins =
				"autocomplete,ae_placeholder,ae_selectionregion,ae_uicore";
		}

		jsonObject.put("extraPlugins", extraPlugins);
	}

	protected PortletURL getPortletURL(
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		PortletURL portletURL =
			requestBackedPortletURLFactory.createResourceURL(
				MentionsPortletKeys.MENTIONS);

		String discussionPortletId = themeDisplay.getPpid();

		if (Validator.isBlank(discussionPortletId)) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			discussionPortletId = portletDisplay.getId();
		}

		portletURL.setParameter("discussionPortletId", discussionPortletId);

		return portletURL;
	}

}