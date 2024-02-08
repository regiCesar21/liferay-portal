/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

/**
 * @author Stian Sigvartsen
 */
public class SetupAdminAutoLoginStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			AutoLogin.class, new SetupAdminAutoLogin(),
			HashMapBuilder.<String, Object>put(
				"component.name", SetupAdminAutoLogin.class.getName()
			).build());
	}

}