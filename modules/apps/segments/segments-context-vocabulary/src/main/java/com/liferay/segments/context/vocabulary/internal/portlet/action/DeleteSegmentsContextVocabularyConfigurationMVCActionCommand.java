/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/delete_segments_context_vocabulary_configuration"
	},
	service = MVCActionCommand.class
)
public class DeleteSegmentsContextVocabularyConfigurationMVCActionCommand
	implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String pid = ParamUtil.getString(actionRequest, "pid");

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting configuration for service " + pid);
		}

		try {
			Configuration configuration = _getConfiguration(pid);

			if (configuration == null) {
				return false;
			}

			configuration.delete();
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}

		return true;
	}

	private Configuration _getConfiguration(String pid) throws IOException {
		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteSegmentsContextVocabularyConfigurationMVCActionCommand.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}