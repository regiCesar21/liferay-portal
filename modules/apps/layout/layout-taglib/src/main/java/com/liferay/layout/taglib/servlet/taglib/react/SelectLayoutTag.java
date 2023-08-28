/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.servlet.taglib.react;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.util.LayoutUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

/**
 * @author Eudaldo Alonso
 * @author Marko Cikos
 */
public class SelectLayoutTag extends IncludeTag {

	public String getComponentId() {
		return _componentId;
	}

	public boolean getFollowURLOnTitleClick() {
		return _followURLOnTitleClick;
	}

	public String getItemSelectorSaveEvent() {
		return _itemSelectorSaveEvent;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	public String getViewType() {
		return _viewType;
	}

	public boolean isCheckDisplayPage() {
		return _checkDisplayPage;
	}

	public boolean isEnableCurrentPage() {
		return _enableCurrentPage;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isShowHiddenLayouts() {
		return _showHiddenLayouts;
	}

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		_checkDisplayPage = checkDisplayPage;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		_enableCurrentPage = enableCurrentPage;
	}

	public void setFollowURLOnTitleClick(boolean followURLOnTitleClick) {
		_followURLOnTitleClick = followURLOnTitleClick;
	}

	public void setItemSelectorSaveEvent(String itemSelectorSaveEvent) {
		_itemSelectorSaveEvent = itemSelectorSaveEvent;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	public void setPathThemeImages(String pathThemeImages) {
		_pathThemeImages = pathThemeImages;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setShowHiddenLayouts(boolean showHiddenLayouts) {
		_showHiddenLayouts = showHiddenLayouts;
	}

	public void setViewType(String viewType) {
		_viewType = viewType;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkDisplayPage = false;
		_componentId = null;
		_enableCurrentPage = false;
		_followURLOnTitleClick = false;
		_itemSelectorSaveEvent = null;
		_multiSelection = false;
		_namespace = null;
		_pathThemeImages = null;
		_privateLayout = false;
		_showHiddenLayouts = false;
		_viewType = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.setAttribute(
				"liferay-layout:select-layout:data", _getData());
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private Map<String, Object> _getData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"checkDisplayPage", _checkDisplayPage
		).put(
			"config",
			HashMapBuilder.<String, Object>put(
				"loadMoreItemsURL",
				() -> {
					HttpServletRequest httpServletRequest = getRequest();

					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return themeDisplay.getPathMain() + "/portal/get_layouts";
				}
			).put(
				"maxPageSize",
				GetterUtil.getInteger(
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)
			).build()
		).put(
			"followURLOnTitleClick", _followURLOnTitleClick
		).put(
			"groupId",
			() -> {
				HttpServletRequest httpServletRequest = getRequest();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getScopeGroupId();
			}
		).put(
			"itemSelectorSaveEvent", _itemSelectorSaveEvent
		).put(
			"multiSelection", _multiSelection
		).put(
			"namespace", _namespace
		).put(
			"nodes", _getLayoutsJSONArray()
		).put(
			"pathThemeImages", _pathThemeImages
		).put(
			"privateLayout", _privateLayout
		).put(
			"viewType", _viewType
		).build();
	}

	private JSONArray _getLayoutsJSONArray()
		throws Exception {

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if ((_privateLayout && !group.hasPrivateLayouts()) ||
			(!_privateLayout && !group.hasPublicLayouts())) {

			return JSONFactoryUtil.createJSONArray();
		}

		String layoutUuid = ParamUtil.getString(request, "layoutUuid");

		return JSONUtil.put(
			JSONUtil.put(
				"children",
				LayoutUtil.getLayoutsJSONArray(
					_checkDisplayPage, _enableCurrentPage,
					themeDisplay.getScopeGroupId(), getRequest(),
					_privateLayout, 0, layoutUuid, _showHiddenLayouts)
			).put(
				"disabled", true
			).put(
				"expanded", true
			).put(
				"hasChildren", true
			).put(
				"icon", "home"
			).put(
				"id", "0"
			).put(
				"name", themeDisplay.getScopeGroupName()
			));
	}

	private static final String _PAGE = "/select_layout/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SelectLayoutTag.class);

	private boolean _checkDisplayPage;
	private String _componentId;
	private boolean _enableCurrentPage;
	private boolean _followURLOnTitleClick;
	private String _itemSelectorSaveEvent;
	private boolean _multiSelection;
	private String _namespace;
	private String _pathThemeImages;
	private boolean _privateLayout;
	private boolean _showHiddenLayouts;
	private String _viewType;

}