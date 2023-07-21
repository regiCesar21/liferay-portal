/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.data.integration.web.internal.portlet.action;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=/commerce_data_integration/edit_commerce_data_integration_process_log"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDataIntegrationProcessLogActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceDataIntegrationProcessLog(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCDataIntegrationProcessLogIds = null;

		long cDataIntegrationProcessLogId = ParamUtil.getLong(
			actionRequest, "cDataIntegrationProcessLogId");

		if (cDataIntegrationProcessLogId > 0) {
			deleteCDataIntegrationProcessLogIds = new long[] {
				cDataIntegrationProcessLogId
			};
		}
		else {
			deleteCDataIntegrationProcessLogIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCDataIntegrationProcessLogIds"),
				0L);
		}

		for (long deleteCDataIntegrationProcessId :
				deleteCDataIntegrationProcessLogIds) {

			_commerceDataIntegrationProcessLogService.
				deleteCommerceDataIntegrationProcessLog(
					deleteCDataIntegrationProcessId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Constants.DELETE.equals(cmd)) {
				deleteCommerceDataIntegrationProcessLog(actionRequest);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference
	private CommerceDataIntegrationProcessLogService
		_commerceDataIntegrationProcessLogService;

}