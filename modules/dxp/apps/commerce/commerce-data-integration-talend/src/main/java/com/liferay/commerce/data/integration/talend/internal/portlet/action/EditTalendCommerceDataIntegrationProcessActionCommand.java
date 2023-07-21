/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.data.integration.talend.internal.portlet.action;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.talend.TalendProcessTypeHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=/commerce_data_integration/edit_talend_commerce_data_integration_process"
	},
	service = MVCActionCommand.class
)
public class EditTalendCommerceDataIntegrationProcessActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			long commerceDataIntegrationProcessId = ParamUtil.getLong(
				uploadPortletRequest, "commerceDataIntegrationProcessId");

			_talendProcessTypeHelper.addFileEntry(
				_portal.getCompanyId(actionRequest),
				_portal.getUserId(actionRequest),
				commerceDataIntegrationProcessId,
				uploadPortletRequest.getFileName("srcArchive"),
				uploadPortletRequest.getSize("srcArchive"),
				uploadPortletRequest.getContentType("srcArchive"),
				uploadPortletRequest.getFileAsStream("srcArchive"));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			SessionErrors.add(actionRequest, exception.getClass());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditTalendCommerceDataIntegrationProcessActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private TalendProcessTypeHelper _talendProcessTypeHelper;

}