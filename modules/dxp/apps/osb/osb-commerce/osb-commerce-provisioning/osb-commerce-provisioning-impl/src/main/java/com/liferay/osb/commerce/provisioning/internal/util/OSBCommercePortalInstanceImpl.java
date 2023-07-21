/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.internal.util;

import com.liferay.osb.commerce.provisioning.configuration.OSBCommerceProvisioningConfiguration;
import com.liferay.osb.commerce.provisioning.util.OSBCommercePortalInstance;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(
	configurationPid = "com.liferay.osb.commerce.provisioning.configuration.OSBCommerceProvisioningConfiguration",
	immediate = true, service = OSBCommercePortalInstance.class
)
public class OSBCommercePortalInstanceImpl
	implements OSBCommercePortalInstance {

	@Override
	public String getPortalInstanceURL(String portalInstanceVirtualHostname) {
		StringBundler sb = new StringBundler(6);

		sb.append(
			_osbCommerceProvisioningConfiguration.
				osbCommercePortalInstanceProtocol());
		sb.append(StringPool.COLON);
		sb.append(StringPool.DOUBLE_SLASH);
		sb.append(portalInstanceVirtualHostname);
		sb.append(StringPool.COLON);
		sb.append(
			_osbCommerceProvisioningConfiguration.
				osbCommercePortalInstancePort());

		return sb.toString();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_osbCommerceProvisioningConfiguration =
			ConfigurableUtil.createConfigurable(
				OSBCommerceProvisioningConfiguration.class, properties);
	}

	private OSBCommerceProvisioningConfiguration
		_osbCommerceProvisioningConfiguration;

}