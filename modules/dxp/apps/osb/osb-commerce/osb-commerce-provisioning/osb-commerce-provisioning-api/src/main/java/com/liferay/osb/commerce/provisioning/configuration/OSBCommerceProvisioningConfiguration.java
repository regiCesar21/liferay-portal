/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 */
@ExtendedObjectClassDefinition(category = "osb-commerce")
@Meta.OCD(
	id = "com.liferay.osb.commerce.provisioning.configuration.OSBCommerceProvisioningConfiguration",
	localization = "content/Language",
	name = "osb-commerce-provisioning-configuration-name"
)
public interface OSBCommerceProvisioningConfiguration {

	@Meta.AD(deflt = "DEVELOPMENT", name = "environment", required = false)
	public ApplicationProfile applicationProfile();

	@Meta.AD(
		deflt = "http://localhost:9999", name = "dxp-cloud-api-url",
		required = false
	)
	public String dxpCloudAPIURL();

	@Meta.AD(deflt = "test", name = "dxp-cloud-api-password", required = false)
	public String dxpCloudAPIPassword();

	@Meta.AD(
		deflt = "test@liferay.com", name = "dxp-cloud-api-username",
		required = false
	)
	public String dxpCloudAPIUsername();

	@Meta.AD(
		deflt = "8080", name = "osb-commerce-portal-instance-port",
		required = false
	)
	public int osbCommercePortalInstancePort();

	@Meta.AD(
		deflt = "http", name = "osb-commerce-portal-instance-protocol",
		required = false
	)
	public String osbCommercePortalInstanceProtocol();

}