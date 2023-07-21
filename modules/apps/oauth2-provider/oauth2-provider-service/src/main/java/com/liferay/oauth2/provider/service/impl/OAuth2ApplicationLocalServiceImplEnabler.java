/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.impl;

import com.liferay.osgi.util.ComponentUtil;
import com.liferay.portal.change.tracking.store.CTStoreFactory;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = {})
public class OAuth2ApplicationLocalServiceImplEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		ComponentUtil.enableComponents(
			StoreFactory.class, "(dl.store.impl.enabled=true)",
			componentContext, OAuth2ApplicationLocalServiceImpl.class);
	}

	@Reference
	private CTStoreFactory _ctStoreFactory;

}