/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.document.library.web.internal.configuration.FFDocumentLibraryDDMEditorConfigurationUtil;
import com.liferay.document.library.web.internal.security.permission.resource.DLPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class DLViewFileEntryTypesDisplayContext {

	public DLViewFileEntryTypesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		this.renderResponse = renderResponse;
	}

	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public CreationMenu getCreationMenu() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (DLPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_DOCUMENT_TYPE)) {

			return CreationMenuBuilder.addPrimaryDropdownItem(
				dropdownItem -> {
					PortletURL creationURL = renderResponse.createRenderURL();

					String mvcRenderCommandName =
						"/document_library/edit_file_entry_type";

					if (useDataEngineEditor()) {
						mvcRenderCommandName =
							"/document_library" +
								"/edit_file_entry_type_data_definition";
					}

					creationURL.setParameter(
						"mvcRenderCommandName", mvcRenderCommandName);

					creationURL.setParameter(
						"redirect",
						PortalUtil.getCurrentURL(_httpServletRequest));

					dropdownItem.setHref(creationURL.toString());

					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "new"));
				}
			).build();
		}

		return null;
	}

	public String getSearchActionURL() {
		return String.valueOf(getPortletURL());
	}

	public SearchContainer<DLFileEntryType> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<DLFileEntryType> searchContainer = new SearchContainer(
			_renderRequest, new DisplayTerms(_httpServletRequest),
			new DisplayTerms(_httpServletRequest),
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			getPortletURL(), null,
			LanguageUtil.get(_httpServletRequest, "there-are-no-results"));

		DisplayTerms searchTerms = searchContainer.getSearchTerms();

		boolean includeBasicFileEntryType = ParamUtil.getBoolean(
			_renderRequest, "includeBasicFileEntryType");

		int total = DLFileEntryTypeServiceUtil.searchCount(
			themeDisplay.getCompanyId(),
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId()),
			searchTerms.getKeywords(), includeBasicFileEntryType);

		searchContainer.setTotal(total);

		searchContainer.setResults(
			DLFileEntryTypeServiceUtil.search(
				themeDisplay.getCompanyId(),
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					themeDisplay.getScopeGroupId()),
				searchTerms.getKeywords(), includeBasicFileEntryType,
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator()));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<DLFileEntryType> searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public boolean useDataEngineEditor() {
		return FFDocumentLibraryDDMEditorConfigurationUtil.
			useDataEngineEditor();
	}

	protected PortletURL getPortletURL() {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("navigation", "file_entry_types");

		return portletURL;
	}

	protected final RenderResponse renderResponse;

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private SearchContainer<DLFileEntryType> _searchContainer;

}