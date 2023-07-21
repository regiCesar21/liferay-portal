/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.context.ViewEntryDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_entry"
	},
	service = MVCRenderCommand.class
)
public class ViewEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ctEntryId = ParamUtil.getLong(renderRequest, "ctEntryId");
		long modelClassNameId = ParamUtil.getLong(
			renderRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(renderRequest, "modelClassPK");

		renderRequest.setAttribute(
			CTWebKeys.VIEW_ENTRY_DISPLAY_CONTEXT,
			_createViewEntryDisplayContext(
				ctEntryId, modelClassNameId, modelClassPK));

		return "/publications/view_entry.jsp";
	}

	private <T extends BaseModel<T>> ViewEntryDisplayContext<T>
		_createViewEntryDisplayContext(
			long ctEntryId, long modelClassNameId, long modelClassPK) {

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(ctEntryId);

		T baseModel = null;

		if (ctEntry == null) {
			baseModel = _ctDisplayRendererRegistry.fetchCTModel(
				modelClassNameId, modelClassPK);
		}
		else {
			modelClassNameId = ctEntry.getModelClassNameId();

			baseModel = _ctDisplayRendererRegistry.fetchCTModel(
				ctEntry.getCtCollectionId(),
				_ctDisplayRendererRegistry.getCTSQLMode(
					ctEntry.getCtCollectionId(), ctEntry),
				modelClassNameId, ctEntry.getModelClassPK());
		}

		return new ViewEntryDisplayContext<>(
			baseModel, _ctCollectionLocalService, _ctDisplayRendererRegistry,
			ctEntry, _language, modelClassNameId);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Language _language;

}