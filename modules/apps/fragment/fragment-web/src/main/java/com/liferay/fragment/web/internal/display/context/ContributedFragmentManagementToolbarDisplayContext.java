/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContributedFragmentManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public ContributedFragmentManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			fragmentDisplayContext.
				getContributedFragmentEntriesSearchContainer());

		_fragmentDisplayContext = fragmentDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.add(
			() -> FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES),
			dropdownItem -> {
				dropdownItem.putData(
					"action", "copyToSelectedContributedFragmentEntries");
				dropdownItem.setIcon("paste");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "make-a-copy"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		return clearResultsURL.toString();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"copyContributedFragmentEntryURL",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletURL copyContributedFragmentEntryURL =
					liferayPortletResponse.createActionURL();

				copyContributedFragmentEntryURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/fragment/copy_contributed_fragment_entry");
				copyContributedFragmentEntryURL.setParameter(
					"redirect", themeDisplay.getURLCurrent());

				return copyContributedFragmentEntryURL.toString();
			}
		).put(
			"selectFragmentCollectionURL",
			() -> {
				PortletURL selectFragmentCollectionURL =
					liferayPortletResponse.createActionURL();

				selectFragmentCollectionURL.setParameter(
					"mvcRenderCommandName",
					"/fragment/select_fragment_collection");
				selectFragmentCollectionURL.setWindowState(
					LiferayWindowState.POP_UP);

				return selectFragmentCollectionURL.toString();
			}
		).build();
	}

	@Override
	public String getComponentId() {
		return "contributedFragmentEntriesManagementToolbar" +
			_fragmentDisplayContext.getFragmentCollectionKey();
	}

	@Override
	public String getDefaultEventHandler() {
		return "FRAGMENT_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	private final FragmentDisplayContext _fragmentDisplayContext;

}