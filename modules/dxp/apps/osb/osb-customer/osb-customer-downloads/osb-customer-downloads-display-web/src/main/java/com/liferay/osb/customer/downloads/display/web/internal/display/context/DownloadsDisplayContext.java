/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.downloads.display.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.osb.customer.downloads.display.constants.DownloadsDDMStructureConstants;
import com.liferay.osb.customer.downloads.display.constants.DownloadsDisplayWebKeys;
import com.liferay.osb.customer.downloads.display.web.internal.util.DownloadsAssetCategoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceURL;

/**
 * @author Amos Fong
 */
public class DownloadsDisplayContext {

	public DownloadsDisplayContext(
			RenderRequest renderRequest, MimeResponse mimeResponse)
		throws Exception {

		_renderRequest = renderRequest;
		_mimeResponse = mimeResponse;

		_ddmIndexer = (DDMIndexer)_renderRequest.getAttribute(
			DDMIndexer.class.getName());
		_downloadsAssetCategoryUtil =
			(DownloadsAssetCategoryUtil)_renderRequest.getAttribute(
				DownloadsAssetCategoryUtil.class.getName());
		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences = _renderRequest.getPreferences();

		String ddmStructureKey = portletPreferences.getValue(
			"ddmStructureKey", null);

		if (Validator.isNotNull(ddmStructureKey)) {
			long classNameId = ClassNameLocalServiceUtil.getClassNameId(
				JournalArticle.class);

			_ddmStructure = DDMStructureLocalServiceUtil.getStructure(
				_themeDisplay.getCompanyGroupId(), classNameId,
				ddmStructureKey);
		}
		else {
			_ddmStructure = null;
		}
	}

	public String getAcceptAgreementURL(String agreement, String version) {
		PortletURL acceptAgreementURL = _mimeResponse.createActionURL();

		acceptAgreementURL.setParameter(
			ActionRequest.ACTION_NAME, "acceptAgreement");
		acceptAgreementURL.setParameter("agreement", agreement);
		acceptAgreementURL.setParameter("version", version);

		return acceptAgreementURL.toString();
	}

	public String getDDMStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	public JSONArray getProductsJSONArray() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> assetCategories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				_downloadsAssetCategoryUtil.getProductAssetVocabularyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (AssetCategory assetCategory : assetCategories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"fileTypes",
				getFileTypesJSONArray(assetCategory.getCategoryId()));
			jsonObject.put("name", assetCategory.getName());
			jsonObject.put("value", assetCategory.getCategoryId());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		AssetCategory fileTypeAssetCategory =
			(AssetCategory)_renderRequest.getAttribute(
				DownloadsDisplayWebKeys.ASSET_CATEGORY_FILE_TYPE);
		AssetCategory productAssetCategory =
			(AssetCategory)_renderRequest.getAttribute(
				DownloadsDisplayWebKeys.ASSET_CATEGORY_PRODUCT);

		PortletURL iteratorURL = _mimeResponse.createRenderURL();

		if (fileTypeAssetCategory != null) {
			iteratorURL.setParameter(
				"fileTypeAssetCategoryId",
				String.valueOf(fileTypeAssetCategory.getCategoryId()));
		}

		if (productAssetCategory != null) {
			iteratorURL.setParameter(
				"productAssetCategoryId",
				String.valueOf(productAssetCategory.getCategoryId()));
		}

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, iteratorURL, null, null);

		String ddmStructureKey = _ddmStructure.getStructureKey();

		if (ddmStructureKey.equals(
				DownloadsDDMStructureConstants.KEY_DOWNLOAD) &&
			((fileTypeAssetCategory == null) ||
			 (productAssetCategory == null))) {

			return searchContainer;
		}

		searchContainer.setSearch(true);

		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = buildSearchContext(
			fileTypeAssetCategory, productAssetCategory,
			searchContainer.getStart(), searchContainer.getEnd());

		Hits hits = indexer.search(searchContext);

		searchContainer.setTotal(hits.getLength());

		List results = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			JournalArticle journalArticle =
				JournalArticleLocalServiceUtil.fetchLatestArticle(
					classPK, WorkflowConstants.STATUS_APPROVED);

			results.add(journalArticle);
		}

		searchContainer.setResults(results);

		return searchContainer;
	}

	public String getVerifyAgreementURL(String agreement, String version) {
		ResourceURL verifyAgreementURL = _mimeResponse.createResourceURL();

		verifyAgreementURL.setParameter("agreement", agreement);
		verifyAgreementURL.setParameter("version", version);
		verifyAgreementURL.setResourceID("/verify_agreement");

		return verifyAgreementURL.toString();
	}

	protected SearchContext buildSearchContext(
		AssetCategory fileTypeAssetCategory, AssetCategory productAssetCategory,
		int start, int end) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);

		long[] assetCategoryIds = new long[0];

		if (fileTypeAssetCategory != null) {
			assetCategoryIds = ArrayUtil.append(
				assetCategoryIds, fileTypeAssetCategory.getCategoryId());
		}

		if (productAssetCategory != null) {
			assetCategoryIds = ArrayUtil.append(
				assetCategoryIds, productAssetCategory.getCategoryId());
		}

		if (ArrayUtil.isNotEmpty(assetCategoryIds)) {
			searchContext.setAttribute(
				Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		}

		searchContext.setAttribute(
			Field.ENTRY_CLASS_NAME, JournalArticle.class.getName());
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		searchContext.setAttribute(
			"ddmStructureId", _ddmStructure.getStructureId());
		searchContext.setAttribute(
			"ddmStructureKey", _ddmStructure.getStructureKey());
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute("latest", Boolean.TRUE);

		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {_themeDisplay.getScopeGroupId()});
		searchContext.setLocale(_themeDisplay.getLocale());

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		searchContext.setSorts(getSorts());
		searchContext.setStart(start);

		return searchContext;
	}

	protected JSONArray getFileTypesJSONArray(long assetCategoryId)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<AssetCategory> assetCategories =
			AssetCategoryServiceUtil.getChildCategories(assetCategoryId);

		for (AssetCategory assetCategory : assetCategories) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("name", assetCategory.getName());
			jsonObject.put("value", assetCategory.getCategoryId());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	protected Sort[] getSorts() {
		Sort[] sorts = new Sort[2];

		String downloadNumberSortFieldName = _ddmIndexer.encodeName(
			_ddmStructure.getStructureId(), "downloadNumber",
			_themeDisplay.getLocale());

		downloadNumberSortFieldName += "_Number";

		sorts[0] = new Sort(downloadNumberSortFieldName, Sort.LONG_TYPE, true);

		String releaseDateSortFieldName = _ddmIndexer.encodeName(
			_ddmStructure.getStructureId(), "releaseDate",
			_themeDisplay.getLocale());

		releaseDateSortFieldName += "_String_sortable";

		sorts[1] = new Sort(releaseDateSortFieldName, Sort.STRING_TYPE, true);

		return sorts;
	}

	private final DDMIndexer _ddmIndexer;
	private final DDMStructure _ddmStructure;
	private final DownloadsAssetCategoryUtil _downloadsAssetCategoryUtil;
	private final MimeResponse _mimeResponse;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}