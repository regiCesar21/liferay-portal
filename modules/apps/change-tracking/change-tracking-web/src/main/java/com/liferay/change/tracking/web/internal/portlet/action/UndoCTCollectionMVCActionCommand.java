/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/undo_ct_collection"
	},
	service = MVCActionCommand.class
)
public class UndoCTCollectionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		if (Validator.isNull(name)) {
			CTCollection ctCollection =
				_ctCollectionLocalService.getCTCollection(ctCollectionId);

			name = StringBundler.concat(
				_language.get(themeDisplay.getLocale(), "revert"), " \"",
				ctCollection.getName(), "\"");
		}

		CTCollection ctCollection = _ctCollectionService.undoCTCollection(
			ctCollectionId, themeDisplay.getUserId(), name, description);

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			actionRequest, CTPortletKeys.PUBLICATIONS,
			PortletRequest.RENDER_PHASE);

		String publishTime = ParamUtil.get(actionRequest, "publishTime", "now");

		if (publishTime.equals("now")) {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_conflicts");
		}
		else {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_changes");
		}

		redirectURL.setParameter(
			"ctCollectionId", String.valueOf(ctCollection.getCtCollectionId()));

		sendRedirect(actionRequest, actionResponse, redirectURL.toString());
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

}