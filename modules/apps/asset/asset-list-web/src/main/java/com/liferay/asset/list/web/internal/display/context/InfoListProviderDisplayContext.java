/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class InfoListProviderDisplayContext {

	public InfoListProviderDisplayContext(
		InfoItemServiceTracker infoItemServiceTracker,
		InfoListProviderTracker infoListProviderTracker,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_infoListProviderTracker = infoListProviderTracker;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(_renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<InfoListProvider<?>> getSearchContainer() {
		SearchContainer<InfoListProvider<?>> searchContainer =
			new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				LanguageUtil.get(
					_httpServletRequest, "there-are-no-collection-providers"));

		List<InfoListProvider<?>> infoListProviders =
			_infoListProviderTracker.getInfoListProviders();

		searchContainer.setResults(
			ListUtil.subList(
				infoListProviders, searchContainer.getStart(),
				searchContainer.getEnd()));
		searchContainer.setTotal(infoListProviders.size());

		return searchContainer;
	}

	public String getSubtitle(InfoListProvider<?> infoListProvider) {
		String className = _getClassName(infoListProvider);

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				_themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	public String getTitle(InfoListProvider<?> infoListProvider) {
		return infoListProvider.getLabel(_themeDisplay.getLocale());
	}

	private String _getClassName(InfoListProvider<?> infoListProvider) {
		Class<?> clazz = infoListProvider.getClass();

		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			Class<?> typeClazz =
				(Class<?>)parameterizedType.getActualTypeArguments()[0];

			return typeClazz.getName();
		}

		return StringPool.BLANK;
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
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InfoListProviderTracker _infoListProviderTracker;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}