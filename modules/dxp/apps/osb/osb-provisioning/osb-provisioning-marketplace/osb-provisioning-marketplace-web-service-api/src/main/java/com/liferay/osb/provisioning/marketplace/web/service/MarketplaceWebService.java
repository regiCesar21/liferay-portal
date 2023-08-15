/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.marketplace.web.service;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;

/**
 * @author Amos Fong
 */
public interface MarketplaceWebService {

	public void syncAccount(Account account) throws Exception;

}