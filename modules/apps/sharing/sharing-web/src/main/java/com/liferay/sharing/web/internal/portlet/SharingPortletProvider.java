/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.portlet;

import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.EditPortletProvider;
import com.liferay.sharing.constants.SharingPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.sharing.model.SharingEntry",
	service = EditPortletProvider.class
)
public class SharingPortletProvider
	extends BasePortletProvider implements EditPortletProvider {

	@Override
	public String getPortletName() {
		return SharingPortletKeys.SHARING;
	}

}