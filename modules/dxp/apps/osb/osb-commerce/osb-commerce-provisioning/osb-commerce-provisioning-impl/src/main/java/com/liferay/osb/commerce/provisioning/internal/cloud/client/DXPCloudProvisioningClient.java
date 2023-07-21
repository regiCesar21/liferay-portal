/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.internal.cloud.client;

import com.liferay.osb.commerce.provisioning.internal.cloud.client.dto.PortalInstance;

import java.util.List;

/**
 * @author Ivica Cardic
 */
public interface DXPCloudProvisioningClient extends Client {

	public void deletePortalInstance(String portalInstanceId);

	public PortalInstance getPortalInstance(String portalInstanceId);

	public List<PortalInstance> getPortalInstances();

	public PortalInstance postPortalInstance(PortalInstance portalInstance);

	public PortalInstance updatePortalInstance(
		String domain, String portalInstanceId);

}