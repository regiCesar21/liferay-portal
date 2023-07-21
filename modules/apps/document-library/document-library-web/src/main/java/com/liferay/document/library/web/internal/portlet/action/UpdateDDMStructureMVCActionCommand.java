/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.web.internal.configuration.FFDocumentLibraryDDMEditorConfigurationUtil;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/update_ddm_structure"
	},
	service = MVCActionCommand.class
)
public class UpdateDDMStructureMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		long parentDDMStructureId = ParamUtil.getLong(
			actionRequest, "parentDDMStructureId",
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		DDMForm ddmForm = _ddm.getDDMForm(actionRequest);

		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

		if (FFDocumentLibraryDDMEditorConfigurationUtil.useDataEngineEditor()) {
			DDMStructure ddmStructure = _ddmStructureService.getStructure(
				ddmStructureId);

			ddmForm = ddmStructure.getDDMForm();
			ddmFormLayout = ddmStructure.getDDMFormLayout();
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), actionRequest);

		_ddmStructureService.updateStructure(
			ddmStructureId, parentDDMStructureId, nameMap, descriptionMap,
			ddmForm, ddmFormLayout, serviceContext);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureService _ddmStructureService;

}