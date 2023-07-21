/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CollectionProvidersVerticalCard extends BaseVerticalCard {

	public CollectionProvidersVerticalCard(
		long groupId, InfoListProvider<?> infoListProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(null, renderRequest, null);

		_groupId = groupId;
		_infoListProvider = infoListProvider;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		try {
			PortletURL selectLayoutMasterLayoutURL =
				_renderResponse.createRenderURL();

			selectLayoutMasterLayoutURL.setParameter(
				"mvcPath", "/select_layout_master_layout.jsp");

			String redirect = ParamUtil.getString(
				_httpServletRequest, "redirect");

			selectLayoutMasterLayoutURL.setParameter("redirect", redirect);

			selectLayoutMasterLayoutURL.setParameter(
				"backURL", themeDisplay.getURLCurrent());
			selectLayoutMasterLayoutURL.setParameter(
				"groupId", String.valueOf(_groupId));

			long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

			selectLayoutMasterLayoutURL.setParameter(
				"selPlid", String.valueOf(selPlid));

			boolean privateLayout = ParamUtil.getBoolean(
				_httpServletRequest, "privateLayout");

			selectLayoutMasterLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));

			selectLayoutMasterLayoutURL.setParameter(
				"collectionPK", String.valueOf(_infoListProvider.getKey()));
			selectLayoutMasterLayoutURL.setParameter(
				"collectionType",
				InfoListProviderItemSelectorReturnType.class.getName());

			data.put(
				"select-layout-master-layout-url",
				selectLayoutMasterLayoutURL.toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		return "select-collection-action-option card-interactive " +
			"card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "list";
	}

	@Override
	public String getImageSrc() {
		return StringPool.BLANK;
	}

	@Override
	public String getSubtitle() {
		String className = _getClassName(_infoListProvider);

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	@Override
	public String getTitle() {
		return _infoListProvider.getLabel(themeDisplay.getLocale());
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

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionProvidersVerticalCard.class);

	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoListProvider<?> _infoListProvider;
	private final RenderResponse _renderResponse;

}