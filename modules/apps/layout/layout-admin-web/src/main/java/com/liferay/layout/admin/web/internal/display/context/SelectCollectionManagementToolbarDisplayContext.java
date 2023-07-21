/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectCollectionManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SelectCollectionManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SelectLayoutCollectionDisplayContext
			selectLayoutCollectionDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			selectLayoutCollectionDisplayContext.
				getCollectionsSearchContainer());

		_selectLayoutCollectionDisplayContext =
			selectLayoutCollectionDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL =
			_selectLayoutCollectionDisplayContext.getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "selectCollectionManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_MANUAL_LABEL,
				"manual-collection", AssetListEntryTypeConstants.TYPE_MANUAL)
		).addPrimaryDropdownItem(
			_getAddAssetListEntryDropdownItemUnsafeConsumer(
				AssetListEntryTypeConstants.TYPE_DYNAMIC_LABEL,
				"dynamic-collection", AssetListEntryTypeConstants.TYPE_DYNAMIC)
		).build();
	}

	@Override
	public String getDefaultEventHandler() {
		return "selectCollectionManagementToolbarDefaultEventHandler";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL =
			_selectLayoutCollectionDisplayContext.getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "entries";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isShowCreationMenu() {
		return true;
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"title", "create-date"};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddAssetListEntryDropdownItemUnsafeConsumer(
			String label, String title, int type) {

		return dropdownItem -> {
			dropdownItem.putData("action", "addAssetListEntry");

			PortletURL addAssetListEntryURL =
				PortalUtil.getControlPanelPortletURL(
					liferayPortletRequest, AssetListPortletKeys.ASSET_LIST,
					PortletRequest.ACTION_PHASE);

			addAssetListEntryURL.setParameter(
				ActionRequest.ACTION_NAME, "/asset_list/add_asset_list_entry");
			addAssetListEntryURL.setParameter(
				"backURL", _themeDisplay.getURLCurrent());
			addAssetListEntryURL.setParameter("type", String.valueOf(type));

			dropdownItem.putData(
				"addAssetListEntryURL", addAssetListEntryURL.toString());

			dropdownItem.putData(
				"namespace",
				PortalUtil.getPortletNamespace(
					AssetListPortletKeys.ASSET_LIST));
			dropdownItem.putData(
				"title",
				LanguageUtil.format(
					httpServletRequest, "add-x-collection", label, true));
			dropdownItem.setHref("#");
			dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, title));
		};
	}

	private final SelectLayoutCollectionDisplayContext
		_selectLayoutCollectionDisplayContext;
	private final ThemeDisplay _themeDisplay;

}