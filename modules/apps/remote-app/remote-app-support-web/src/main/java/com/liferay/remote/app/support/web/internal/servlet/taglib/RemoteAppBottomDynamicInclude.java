/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.support.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.remote.app.support.web.internal.configuration.RemoteAppConfiguration;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Greg Hurrell
 */
@Component(
	configurationPid = "com.liferay.remote.app.support.web.internal.configuration.RemoteAppConfiguration",
	service = DynamicInclude.class
)
public class RemoteAppBottomDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {
		if (_remoteAppConfiguration.enablePostMessage()) {
			ScriptData scriptData = new ScriptData();

			String initModuleName = _npmResolver.resolveModuleName(
				"remote-app-support-web/index");

			scriptData.append(
				null, "RemoteAppSupport.default()",
				initModuleName + " as RemoteAppSupport",
				ScriptData.ModulesType.ES6);

			scriptData.writeTo(httpServletResponse.getWriter());
		}
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_remoteAppConfiguration = ConfigurableUtil.createConfigurable(
			RemoteAppConfiguration.class, properties);
	}

	@Reference
	private NPMResolver _npmResolver;

	private volatile RemoteAppConfiguration _remoteAppConfiguration;

}