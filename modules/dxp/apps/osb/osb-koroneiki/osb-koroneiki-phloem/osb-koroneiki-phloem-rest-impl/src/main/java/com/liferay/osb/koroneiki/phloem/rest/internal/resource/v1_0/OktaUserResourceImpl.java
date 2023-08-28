/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.OktaUser;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.OktaUserResource;
import com.liferay.osb.koroneiki.root.identity.management.provider.ContactIdentityProvider;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/okta-user.properties",
	scope = ServiceScope.PROTOTYPE, service = OktaUserResource.class
)
public class OktaUserResourceImpl extends BaseOktaUserResourceImpl {

	public static final String RESOURCE_NAME_OKTA =
		"com.liferay.osb.koroneiki.phloem.okta";

	@Override
	public void postOktaUser(
			String agentName, String agentUID, OktaUser oktaUser)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.hasPermission(
				0, RESOURCE_NAME_OKTA, RESOURCE_NAME_OKTA, "ADD_USER")) {

			throw new PrincipalException();
		}

		_contactIdentityProvider.createContact(
			oktaUser.getEmailAddress(), oktaUser.getFirstName(),
			StringPool.BLANK, oktaUser.getLastName(), oktaUser.getUuid());
	}

	@Reference(target = "(provider=okta)")
	private ContactIdentityProvider _contactIdentityProvider;

}