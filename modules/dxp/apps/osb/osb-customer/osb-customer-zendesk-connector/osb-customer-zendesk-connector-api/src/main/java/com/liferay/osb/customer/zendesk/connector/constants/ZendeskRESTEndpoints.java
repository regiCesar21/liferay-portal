/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.zendesk.connector.constants;

/**
 * @author Amos Fong
 */
public interface ZendeskRESTEndpoints {

	public static final String IDENTITIES = "/identities.json";

	public static final String ORGANIZATION_MEMBERSHIPS_CREATE_MANY =
		"organization_memberships/create_many.json";

	public static final String ORGANIZATION_MEMBERSHIPS_DESTROY_MANY =
		"organization_memberships/destroy_many.json";

	public static final String ORGANIZATION_SUBSCRIPTIONS =
		"organization_subscriptions.json";

	public static final String ORGANIZATIONS = "organizations.json";

	public static final String ORGANIZATIONS_CREATE_OR_UPDATE =
		"organizations/create_or_update.json";

	public static final String RELATED = "/related.json";

	public static final String URL_API_V2 = "/api/v2/";

	public static final String USERS_CREATE_OR_UPDATE =
		"users/create_or_update.json";

}