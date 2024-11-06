/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.release.tool.web.internal.search;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryPropertyLocalService;
import com.liferay.osb.customer.jira.rest.connector.configuration.JIRARESTConnectorConfigurationValues;
import com.liferay.osb.customer.jira.rest.connector.service.JIRAIssueRESTService;
import com.liferay.osb.customer.release.tool.configuration.ReleaseToolConfigurationValues;
import com.liferay.osb.customer.release.tool.web.internal.constants.ProductConstants;
import com.liferay.osb.customer.release.tool.web.internal.constants.ReleaseAssetCategoryProperty;
import com.liferay.osb.customer.release.tool.web.internal.util.ReleasesAssetCategoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = JiraIssueSearcher.class)
public class JiraIssueSearcher extends BaseSearcher {

	protected String buildJQL(
			long assetCategoryId, String jiraFixPackCustomField,
			double productVersion, double fromFixPackVersion,
			double toFixPackVersion, String keywords, String[] components,
			String orderByType, PortletPreferences preferences)
		throws PortalException {

		int pos = jiraFixPackCustomField.indexOf(StringPool.UNDERLINE);

		String jiraFixPackJQLField =
			"cf[" + jiraFixPackCustomField.substring(pos + 1) + "]";

		String[] jiraProjects = null;

		String productName = preferences.getValue("productName", null);

		if (productName.equals(ProductConstants.COMMERCE)) {
			jiraProjects = new String[] {"COMMERCE"};
		}
		else {
			jiraProjects =
				ReleaseToolConfigurationValues.FIX_PACK_JIRA_PROJECTS;
		}

		StringBundler sb = new StringBundler(26);

		sb.append("project in (\"");
		sb.append(StringUtil.merge(jiraProjects, "\",\""));
		sb.append("\") AND ");

		if (productVersion == 7.4) {
			String jiraFixPacksCustomField =
				_releasesAssetCategoryUtil.getPropertyValue(
					assetCategoryId,
					ReleaseAssetCategoryProperty.JIRA_FIX_PACK_VERSIONS);

			String jiraFixPacksJQLField =
				"cf[" + jiraFixPacksCustomField.substring(pos + 1) + "]";

			List<String> jiraIssueVersionTags = _getJIRAIssueVersionTags(
				assetCategoryId, fromFixPackVersion, toFixPackVersion);

			sb.append(jiraFixPacksJQLField);
			sb.append(" in (\"");
			sb.append(StringUtil.merge(jiraIssueVersionTags, "\",\""));
			sb.append("\")");

			List<String> jiraIssueVersionExcludeTags =
				_getJIRAIssueVersionExcludeTags(
					assetCategoryId, fromFixPackVersion);

			if (!jiraIssueVersionExcludeTags.isEmpty()) {
				sb.append(" AND ");
				sb.append(jiraFixPacksJQLField);
				sb.append(" not in (\"");
				sb.append(
					StringUtil.merge(jiraIssueVersionExcludeTags, "\",\""));
				sb.append("\")");
			}
		}
		else {
			sb.append(jiraFixPackJQLField);
			sb.append(">=");
			sb.append(fromFixPackVersion);
			sb.append(" AND ");
			sb.append(jiraFixPackJQLField);
			sb.append("<=");
			sb.append(toFixPackVersion);
		}

		if (Validator.isNotNull(keywords)) {
			sb.append(" AND (description ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(" OR summary ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(")");
		}

		if (ArrayUtil.isNotEmpty(components)) {
			sb.append(" AND ");

			sb.append("component in (\"");
			sb.append(StringUtil.merge(components, "\", \""));
			sb.append("\")");
		}

		sb.append(" AND level is empty");

		if (!orderByType.equals("asc")) {
			orderByType = "desc";
		}

		sb.append(" order by ");
		sb.append(jiraFixPackJQLField);
		sb.append(" ");
		sb.append(orderByType);

		return sb.toString();
	}

	protected JSONObject doSearch(
			PortletRequest portletRequest, MimeResponse mimeResponse)
		throws Exception {

		PortletPreferences preferences = portletRequest.getPreferences();

		String product = ParamUtil.getString(portletRequest, "product");
		double productVersion = ParamUtil.getDouble(
			portletRequest, "productVersion");
		double fromFixPackVersion = ParamUtil.getDouble(
			portletRequest, "fromFixPackVersion");
		double toFixPackVersion = ParamUtil.getDouble(
			portletRequest, "toFixPackVersion");
		String keywords = ParamUtil.getString(portletRequest, "keywords");
		String[] components = ParamUtil.getStringValues(
			portletRequest, "components");
		int startAt = ParamUtil.getInteger(portletRequest, "startAt");
		int maxResults = ParamUtil.getInteger(
			portletRequest, "maxResults",
			ReleaseToolConfigurationValues.FIX_PACK_JIRA_MAX_RESULTS);
		String orderByType = ParamUtil.getString(portletRequest, "orderByType");

		AssetCategory productAssetCategory =
			_releasesAssetCategoryUtil.getProductAssetCategory(
				product, productVersion);

		String jiraFixPackCustomField =
			_releasesAssetCategoryUtil.getPropertyValue(
				productAssetCategory.getCategoryId(),
				ReleaseAssetCategoryProperty.JIRA_FIX_PACK_VERSION);

		String jql = buildJQL(
			productAssetCategory.getCategoryId(), jiraFixPackCustomField,
			productVersion, fromFixPackVersion, toFixPackVersion, keywords,
			components, orderByType, preferences);

		JSONObject jiraResultsJSONObject = _jiraIssueRESTService.getJIRAIssues(
			jql, "renderedFields", _ISSUE_FIELDS + "," + jiraFixPackCustomField,
			startAt, maxResults);

		return processResults(
			productAssetCategory, jiraFixPackCustomField,
			jiraResultsJSONObject);
	}

	protected String getJiraIssueURL(String issueKey) {
		StringBundler sb = new StringBundler(4);

		sb.append(Http.HTTPS_WITH_SLASH);
		sb.append(JIRARESTConnectorConfigurationValues.JIRA_DOMAIN_NAME);
		sb.append("/browse/");
		sb.append(issueKey);

		return sb.toString();
	}

	protected JSONObject processJiraIssue(
		AssetCategory productAssetCategory, String jiraFixPackCustomField,
		JSONObject jiraIssueJSONObject) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		JSONObject fieldsJSONObject = jiraIssueJSONObject.getJSONObject(
			"fields");

		JSONArray componentsJSONArray = processJiraIssueComponents(
			fieldsJSONObject.getJSONArray("components"));

		jsonObject.put("components", componentsJSONArray);

		JSONObject renderedFieldsJSONObject = jiraIssueJSONObject.getJSONObject(
			"renderedFields");

		jsonObject.put(
			"description", renderedFieldsJSONObject.getString("description"));

		jsonObject.put("key", jiraIssueJSONObject.getString("key"));

		double fixPackVersion = GetterUtil.getDouble(
			fieldsJSONObject.getString(jiraFixPackCustomField));

		AssetCategory assetCategory =
			_releasesAssetCategoryUtil.getFixPackAssetCategory(
				productAssetCategory.getCategoryId(), fixPackVersion);

		if (assetCategory != null) {
			jsonObject.put("release", assetCategory.getName());
		}
		else {
			jsonObject.put("release", StringPool.BLANK);
		}

		jsonObject.put("summary", fieldsJSONObject.getString("summary"));
		jsonObject.put(
			"url", getJiraIssueURL(jiraIssueJSONObject.getString("key")));

		return jsonObject;
	}

	protected JSONArray processJiraIssueComponents(JSONArray jsonArray) {
		JSONArray componentsJSONArray = jsonFactory.createJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			componentsJSONArray.put(jsonObject.getString("name"));
		}

		return componentsJSONArray;
	}

	protected JSONObject processResults(
		AssetCategory productAssetCategory, String jiraFixPackCustomField,
		JSONObject jiraResultsJSONObject) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		JSONArray jiraIssuesJSONArray = jiraResultsJSONObject.getJSONArray(
			"issues");

		for (int i = 0; i < jiraIssuesJSONArray.length(); i++) {
			JSONObject jiraIssueJSONObject = jiraIssuesJSONArray.getJSONObject(
				i);

			jsonArray.put(
				processJiraIssue(
					productAssetCategory, jiraFixPackCustomField,
					jiraIssueJSONObject));
		}

		jsonObject.put("results", jsonArray);

		jsonObject.put("total", jiraResultsJSONObject.getInt("total"));

		return jsonObject;
	}

	private List<String> _getJIRAIssueVersionExcludeTags(
			long assetCategoryId, double fromFixPackVersion)
		throws PortalException {

		List<String> jiraIssueVersionTags = new ArrayList<>();

		if (!_isQuarterlyRelease(fromFixPackVersion)) {
			return jiraIssueVersionTags;
		}

		Map<String, String> fromVersionMap = _getVersionMap(
			String.valueOf(fromFixPackVersion));

		List<AssetCategory> childAssetCategories =
			_assetCategoryLocalService.getChildCategories(assetCategoryId);

		for (AssetCategory childAssetCategory : childAssetCategories) {
			String version = _releasesAssetCategoryUtil.getPropertyValue(
				childAssetCategory.getCategoryId(),
				ReleaseAssetCategoryProperty.VERSION);

			if (!_isQuarterlyRelease(Double.valueOf(version))) {
				continue;
			}

			Map<String, String> versionMap = _getVersionMap(version);

			if (StringUtil.equals(
					fromVersionMap.get("year"), versionMap.get("year")) &&
				StringUtil.equals(
					fromVersionMap.get("quarter"), versionMap.get("quarter")) &&
				(GetterUtil.getInteger(versionMap.get("version")) <
					GetterUtil.getInteger(fromVersionMap.get("version")))) {

				jiraIssueVersionTags.add(_getVersionTag(versionMap));
			}
		}

		return jiraIssueVersionTags;
	}

	private List<String> _getJIRAIssueVersionTags(
			long assetCategoryId, double fromFixPackVersion,
			double toFixPackVersion)
		throws PortalException {

		List<String> jiraIssueVersionTags = new ArrayList<>();

		List<AssetCategory> childAssetCategories =
			_assetCategoryLocalService.getChildCategories(assetCategoryId);

		for (AssetCategory childAssetCategory : childAssetCategories) {
			String version = _releasesAssetCategoryUtil.getPropertyValue(
				childAssetCategory.getCategoryId(),
				ReleaseAssetCategoryProperty.VERSION);

			if ((Double.valueOf(version) >= fromFixPackVersion) &&
				(Double.valueOf(version) <= toFixPackVersion)) {

				jiraIssueVersionTags.add(_getVersionTag(version));
			}
		}

		return jiraIssueVersionTags;
	}

	private Map<String, String> _getVersionMap(String version) {
		if (version.length() < 8) {
			for (int i = 0; i <= (8 - version.length()); i++) {
				version += "0";
			}
		}

		Map<String, String> versionMap = new HashMap<>();

		int pos = version.indexOf(StringPool.PERIOD);

		versionMap.put("quarter", version.substring(pos + 1, pos + 2));
		versionMap.put(
			"version",
			String.valueOf(GetterUtil.getInteger(version.substring(pos + 2))));
		versionMap.put("year", version.substring(0, pos));

		return versionMap;
	}

	private String _getVersionTag(Map<String, String> versionMap) {
		return StringBundler.concat(
			versionMap.get("year"), ".Q", versionMap.get("quarter"),
			StringPool.PERIOD, versionMap.get("version"));
	}

	private String _getVersionTag(String version) {
		if (!_isQuarterlyRelease(Double.valueOf(version))) {
			if (version.equals("0.0")) {
				return "7.4.13 DXP GA1";
			}

			return "7.4.13 DXP U" +
				version.substring(0, version.indexOf(StringPool.PERIOD));
		}

		Map<String, String> versionMap = _getVersionMap(version);

		return _getVersionTag(versionMap);
	}

	private boolean _isQuarterlyRelease(double version) {
		if (version > 2023.0) {
			return true;
		}

		return false;
	}

	private static final String _ISSUE_FIELDS =
		"components,description,key,summary";

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@Reference
	private JIRAIssueRESTService _jiraIssueRESTService;

	@Reference
	private ReleasesAssetCategoryUtil _releasesAssetCategoryUtil;

}