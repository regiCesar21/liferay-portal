/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DLSelectDDMStructureManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public DLSelectDDMStructureManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DLSelectDDMStructureDisplayContext
				dlSelectDDMStructureDisplayContext)
		throws Exception {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			dlSelectDDMStructureDisplayContext.getDDMStructureSearch());

		_dlSelectDDMStructureDisplayContext =
			dlSelectDDMStructureDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/document_library/ddm/select_ddm_structure.jsp");
		portletURL.setParameter(
			"ddmStructureId",
			String.valueOf(
				_dlSelectDDMStructureDisplayContext.getDDMStructureId()));
		portletURL.setParameter(
			"eventName", _dlSelectDDMStructureDisplayContext.getEventName());

		return portletURL.toString();
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "id"};
	}

	private final DLSelectDDMStructureDisplayContext
		_dlSelectDDMStructureDisplayContext;

}