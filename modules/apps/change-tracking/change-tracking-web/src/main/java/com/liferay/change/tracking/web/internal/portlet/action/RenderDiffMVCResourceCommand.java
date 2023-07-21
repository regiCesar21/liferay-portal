/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/render_diff"
	},
	service = MVCResourceCommand.class
)
public class RenderDiffMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long ctEntryId = ParamUtil.getLong(resourceRequest, "ctEntryId");

		CTEntry ctEntry = _ctEntryLocalService.getCTEntry(ctEntryId);

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ctEntry.getCtCollectionId());

		CTEntryDiffDisplay ctEntryDiffDisplay = new CTEntryDiffDisplay(
			ctCollection, _ctDisplayRendererRegistry, ctEntry,
			_ctEntryLocalService,
			_portal.getHttpServletRequest(resourceRequest),
			_portal.getHttpServletResponse(resourceResponse), _language);

		resourceRequest.setAttribute(
			CTWebKeys.CT_ENTRY_DIFF_DISPLAY, ctEntryDiffDisplay);

		include(
			resourceRequest, resourceResponse, "/publications/render_diff.jsp");
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}