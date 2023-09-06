/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.rest.internal.resource.v1_0;

import com.liferay.osb.provisioning.rest.resource.v1_0.ContactResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;


/**
 * @author Will Newbury
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/contact.properties",
	scope = ServiceScope.PROTOTYPE, service = ContactResource.class
)
public class ContactResourceImpl extends BaseContactResourceImpl {
}