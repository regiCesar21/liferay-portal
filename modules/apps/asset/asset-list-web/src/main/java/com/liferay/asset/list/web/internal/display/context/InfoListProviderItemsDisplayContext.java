/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class InfoListProviderItemsDisplayContext {

	public InfoListProviderItemsDisplayContext(
		InfoItemServiceTracker infoItemServiceTracker,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public InfoItemFieldValuesProvider<Object>
		getInfoItemFieldValuesProvider() {

		if (_infoItemFieldValuesProvider != null) {
			return _infoItemFieldValuesProvider;
		}

		_infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				GenericUtil.getGenericClassName(_getInfoListProvider()));

		return _infoItemFieldValuesProvider;
	}

	public String getInfoListItemsType(Object result) {
		String className = StringPool.BLANK;

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			className = PortalUtil.getClassName(assetEntry.getClassNameId());
		}
		else {
			className = GenericUtil.getGenericClassName(_getInfoListProvider());
		}

		return ResourceActionsUtil.getModelResource(
			_themeDisplay.getLocale(), className);
	}

	public String getInfoListProviderClassName() {
		if (Validator.isNotNull(_infoListProviderClassName)) {
			return _infoListProviderClassName;
		}

		_infoListProviderClassName = GenericUtil.getGenericClassName(
			_getInfoListProvider());

		return _infoListProviderClassName;
	}

	public SearchContainer<Object> getSearchContainer() {
		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_renderRequest, _getPortletURL(), null,
			LanguageUtil.get(
				_httpServletRequest,
				"there-are-no-items-in-the-collection-provider"));

		InfoListProvider<?> infoListProvider = _getInfoListProvider();

		DefaultInfoListProviderContext defaultInfoListProviderContext =
			new DefaultInfoListProviderContext(
				_themeDisplay.getScopeGroup(), _themeDisplay.getUser());

		List<Object> infoList = (List<Object>)infoListProvider.getInfoList(
			defaultInfoListProviderContext);

		searchContainer.setResults(
			ListUtil.subList(
				infoList, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoList.size());

		return searchContainer;
	}

	public boolean isShowActions() {
		if (_showActions != null) {
			return _showActions;
		}

		_showActions = ParamUtil.get(_renderRequest, "showActions", false);

		return _showActions;
	}

	private InfoListProvider<?> _getInfoListProvider() {
		if (_infoListProvider != null) {
			return _infoListProvider;
		}

		_infoListProvider = _infoItemServiceTracker.getInfoItemService(
			InfoListProvider.class, _getInfoListProviderKey());

		return _infoListProvider;
	}

	private String _getInfoListProviderKey() {
		if (_infoListProviderKey != null) {
			return _infoListProviderKey;
		}

		String infoListProviderKey = ParamUtil.getString(
			_renderRequest, "infoListProviderKey");

		if (Validator.isNull(infoListProviderKey)) {
			infoListProviderKey = ParamUtil.getString(
				_renderRequest, "collectionPK");
		}

		_infoListProviderKey = infoListProviderKey;

		return _infoListProviderKey;
	}

	private PortletURL _getPortletURL() {
		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		try {
			return PortletURLUtil.clone(currentURLObj, _renderResponse);
		}
		catch (PortletException portletException) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			portletURL.setParameters(currentURLObj.getParameterMap());

			return portletURL;
		}
	}

	private final HttpServletRequest _httpServletRequest;
	private InfoItemFieldValuesProvider<Object> _infoItemFieldValuesProvider;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private InfoListProvider<?> _infoListProvider;
	private String _infoListProviderClassName;
	private String _infoListProviderKey;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showActions;
	private final ThemeDisplay _themeDisplay;

}