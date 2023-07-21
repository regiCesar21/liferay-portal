/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.notifications;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
	service = UserNotificationHandler.class
)
public class MFASystemConfigurationUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public MFASystemConfigurationUserNotificationHandler() {
		setPortletId(ConfigurationAdminPortletKeys.INSTANCE_SETTINGS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		boolean mfaDisableGlobally = jsonObject.getBoolean(
			"mfaDisableGlobally");

		String body = null;

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			serviceContext.getLocale(), getClass());

		if (mfaDisableGlobally) {
			body = _language.get(
				resourceBundle,
				"multi-factor-authentication-has-been-disabled-by-the-system-" +
					"administrator-and-is-unavailable-to-all-instances");
		}
		else {
			body = _language.get(
				resourceBundle,
				"multi-factor-authentication-has-been-enabled-by-the-system-" +
					"administrator");
		}

		String title = _language.get(
			resourceBundle, "multi-factor-authentication");

		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {body, title});
	}

	@Reference
	private Language _language;

}