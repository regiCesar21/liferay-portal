/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.release.tool.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.osb.customer.downloads.display.constants.DownloadsDDMStructureConstants;
import com.liferay.osb.customer.downloads.display.constants.DownloadsDisplayPortletKeys;
import com.liferay.osb.customer.release.tool.constants.ArtifactVersionConstants;
import com.liferay.osb.customer.release.tool.model.JIRAComponent;
import com.liferay.osb.customer.release.tool.service.JIRAComponentLocalServiceUtil;
import com.liferay.osb.customer.release.tool.util.comparator.AssetCategoryPropertyComparator;
import com.liferay.osb.customer.release.tool.web.internal.constants.DDMStructureConstants;
import com.liferay.osb.customer.release.tool.web.internal.constants.FixPackField;
import com.liferay.osb.customer.release.tool.web.internal.constants.ProductConstants;
import com.liferay.osb.customer.release.tool.web.internal.constants.ReleaseAssetCategoryProperty;
import com.liferay.osb.customer.release.tool.web.internal.util.ReleasesAssetCategoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.MimeResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Amos Fong
 */
public class ReleaseToolDisplayContext {

	public ReleaseToolDisplayContext(
			RenderRequest renderRequest, MimeResponse mimeResponse)
		throws Exception {

		_renderRequest = renderRequest;

		_portletPreferences = _renderRequest.getPreferences();

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_releasesAssetCategoryUtil =
			(ReleasesAssetCategoryUtil)renderRequest.getAttribute(
				ReleasesAssetCategoryUtil.class.getName());

		_initHighlightsFilters();
	}

	public JSONArray getArtifactVersionFiltersJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_isCommerce()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("label", "Commerce Artifacts");
			jsonObject.put(
				"value",
				String.valueOf(ArtifactVersionConstants.OWNER_COMMERCE));

			jsonArray.put(jsonObject);

			return jsonArray;
		}

		for (int owner : ArtifactVersionConstants.OWNERS_DXP) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (owner == ArtifactVersionConstants.OWNER_LIFERAY) {
				jsonObject.put("label", "Liferay Artifacts");
			}
			else if (owner == ArtifactVersionConstants.OWNER_THIRD_PARTY) {
				jsonObject.put("label", "Third Party Libraries");
			}

			jsonObject.put("value", String.valueOf(owner));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public String getFixPackDownloadURL(
			String product, double productVersion, double fixPackVersion)
		throws PortalException {

		if (Validator.isNull(product) && (productVersion <= 0)) {
			return StringPool.BLANK;
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(1);
		searchContext.setStart(0);

		BooleanQuery fullQuery = new BooleanQueryImpl();

		fullQuery.addRequiredTerm(
			FixPackField.FIX_PACK_VERSION, fixPackVersion);
		fullQuery.addRequiredTerm(FixPackField.PRODUCT, product);
		fullQuery.addRequiredTerm(FixPackField.PRODUCT_VERSION, productVersion);

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.addRequiredTerm(
			Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		booleanFilter.addRequiredTerm(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		booleanFilter.addRequiredTerm(
			"ddmStructureKey", DownloadsDDMStructureConstants.KEY_DOWNLOAD);
		booleanFilter.addRequiredTerm("head", true);

		fullQuery.setPreBooleanFilter(booleanFilter);

		Hits hits = IndexSearcherHelperUtil.search(searchContext, fullQuery);

		Document[] documents = hits.getDocs();

		if (ArrayUtil.isEmpty(documents)) {
			return StringPool.BLANK;
		}

		Document document = documents[0];

		long plid = PortalUtil.getPlidFromPortletId(
			_themeDisplay.getScopeGroupId(),
			DownloadsDisplayPortletKeys.DOWNLOADS_DISPLAY);

		PortletURL renderURL = PortletURLFactoryUtil.create(
			_renderRequest, DownloadsDisplayPortletKeys.DOWNLOADS_DISPLAY, plid,
			PortletRequest.RENDER_PHASE);

		renderURL.setParameter("mvcRenderCommandName", "/view");

		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		renderURL.setParameter(
			"journalArticleResourcePrimKey", String.valueOf(classPK));

		return renderURL.toString();
	}

	public JSONArray getFixPackFiltersJSONArray() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> assetCategories =
			_releasesAssetCategoryUtil.getDisplayProductAssetCategories();

		assetCategories = ListUtil.sort(
			assetCategories,
			new AssetCategoryPropertyComparator(
				ReleaseAssetCategoryProperty.VERSION));

		for (AssetCategory assetCategory : assetCategories) {
			if (_isCommerce() &&
				(getAssetCategoryVersion(assetCategory) <= 7.2)) {

				continue;
			}

			jsonArray.put(getRootAssetCategoryJSONObject(assetCategory));
		}

		return jsonArray;
	}

	public JSONArray getHightlightsFiltersJSONArray() {
		return _highlightsFiltersJSONArray;
	}

	public JSONArray getJIRAComponentFiltersJSONArray() throws PortalException {
		String jiraProject = null;

		if (_isCommerce()) {
			jiraProject = "COMMERCE";
		}
		else {
			jiraProject = "LPD";
		}

		List<JIRAComponent> jiraComponents =
			JIRAComponentLocalServiceUtil.getJIRAComponents(jiraProject, true);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JIRAComponent jiraComponent : jiraComponents) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("name", jiraComponent.getName());
			jsonObject.put("value", jiraComponent.getJiraComponentId());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected JSONObject getAssetCategoryJSONObject(
		AssetCategory assetCategory) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"name", assetCategory.getTitle(_themeDisplay.getLocale()));

		List<AssetCategoryProperty> assetCategoryProperties =
			AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
				assetCategory.getCategoryId());

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			jsonObject.put(
				assetCategoryProperty.getKey(),
				assetCategoryProperty.getValue());
		}

		return jsonObject;
	}

	protected double getAssetCategoryVersion(AssetCategory assetCategory) {
		JSONObject jsonObject = getAssetCategoryJSONObject(assetCategory);

		return GetterUtil.getDouble(jsonObject.get("version"));
	}

	protected JSONObject getRootAssetCategoryJSONObject(
		AssetCategory assetCategory) {

		JSONObject jsonObject = getAssetCategoryJSONObject(assetCategory);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> childAssetCategories =
			AssetCategoryLocalServiceUtil.getChildCategories(
				assetCategory.getCategoryId());

		childAssetCategories = ListUtil.sort(
			childAssetCategories,
			new AssetCategoryPropertyComparator(
				ReleaseAssetCategoryProperty.VERSION));

		for (AssetCategory childAssetCategory : childAssetCategories) {
			JSONObject childJSONObject = getAssetCategoryJSONObject(
				childAssetCategory);

			jsonArray.put(childJSONObject);
		}

		jsonObject.put("fixPacks", jsonArray);

		return jsonObject;
	}

	private void _initHighlightsFilters() throws PortalException {
		_highlightsFiltersJSONArray = JSONFactoryUtil.createJSONArray();

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			JournalArticle.class);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			_themeDisplay.getCompanyGroupId(), classNameId,
			DDMStructureConstants.KEY_FIX_PACK);

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			DDMStructureConstants.FIELD_HIGHLIGHTS);

		List<DDMFormField> childDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		for (DDMFormField childDDMFormField : childDDMFormFields) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			LocalizedValue localizedValue = childDDMFormField.getLabel();

			jsonObject.put(
				"label", localizedValue.getString(_themeDisplay.getLocale()));

			jsonObject.put("value", childDDMFormField.getName());

			_highlightsFiltersJSONArray.put(jsonObject);
		}
	}

	private boolean _isCommerce() {
		String productName = GetterUtil.getString(
			_portletPreferences.getValue("productName", ProductConstants.DXP));

		if (productName.equals(ProductConstants.COMMERCE)) {
			return true;
		}

		return false;
	}

	private JSONArray _highlightsFiltersJSONArray;
	private final PortletPreferences _portletPreferences;
	private final ReleasesAssetCategoryUtil _releasesAssetCategoryUtil;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}