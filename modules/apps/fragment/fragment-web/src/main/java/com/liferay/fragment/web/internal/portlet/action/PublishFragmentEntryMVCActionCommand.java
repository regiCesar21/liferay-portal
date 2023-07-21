/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.web.internal.handler.FragmentEntryExceptionRequestHandler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/publish_fragment_entry"
	},
	service = AopService.class
)
public class PublishFragmentEntryMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		try {
			FragmentEntry draftFragmentEntry = null;

			FragmentEntry fragmentEntry =
				_fragmentEntryService.fetchFragmentEntry(fragmentEntryId);

			if (fragmentEntry.isDraft()) {
				draftFragmentEntry = fragmentEntry;
			}
			else {
				draftFragmentEntry = _fragmentEntryService.fetchDraft(
					fragmentEntryId);
			}

			FragmentEntry publishedFragmentEntry;

			if (draftFragmentEntry == null) {
				publishedFragmentEntry = fragmentEntry;
			}
			else {
				draftFragmentEntry.setStatus(WorkflowConstants.STATUS_APPROVED);

				publishedFragmentEntry = _fragmentEntryService.publishDraft(
					draftFragmentEntry);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"redirectURL",
					getRedirectURL(actionResponse, publishedFragmentEntry)));
		}
		catch (PortalException portalException) {
			_fragmentEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	protected String getRedirectURL(
		ActionResponse actionResponse, FragmentEntry fragmentEntry) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entries");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentEntry.getFragmentCollectionId()));

		return portletURL.toString();
	}

	@Reference
	private FragmentEntryExceptionRequestHandler
		_fragmentEntryExceptionRequestHandler;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

}