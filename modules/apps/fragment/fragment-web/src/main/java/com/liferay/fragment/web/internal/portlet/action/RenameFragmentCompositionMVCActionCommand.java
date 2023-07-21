/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.web.internal.handler.FragmentEntryExceptionRequestHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/rename_fragment_composition"
	},
	service = MVCActionCommand.class
)
public class RenameFragmentCompositionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCompositionId = ParamUtil.getLong(
			actionRequest, "fragmentCompositionId");

		String name = ParamUtil.getString(actionRequest, "name");

		try {
			FragmentComposition fragmentComposition =
				_fragmentCompositionService.updateFragmentComposition(
					fragmentCompositionId, name);

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL",
				getRedirectURL(actionResponse, fragmentComposition));

			if (SessionErrors.contains(actionRequest, "fragmentNameInvalid")) {
				addSuccessMessage(actionRequest, actionResponse);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, "fragmentNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_fragmentEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	protected String getRedirectURL(
		ActionResponse actionResponse,
		FragmentComposition fragmentComposition) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entries");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentComposition.getFragmentCollectionId()));

		return portletURL.toString();
	}

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryExceptionRequestHandler
		_fragmentEntryExceptionRequestHandler;

	@Reference
	private Portal _portal;

}