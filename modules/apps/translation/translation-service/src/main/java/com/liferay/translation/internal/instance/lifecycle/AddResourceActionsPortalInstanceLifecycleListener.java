/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddResourceActionsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		String xml = StringUtil.read(
			AddResourceActionsPortalInstanceLifecycleListener.class.
				getClassLoader(),
			"/com/liferay/translation/internal/instance/lifecycle" +
				"/dependencies/resource-actions.xml.tpl");

		String[] languageIds = ArrayUtil.sortedUnique(PropsValues.LOCALES);

		for (int i = 0; i < languageIds.length; i++) {
			_resourceActions.populateModelResources(
				SAXReaderUtil.read(
					StringUtil.replace(
						StringUtil.replace(
							xml, "[$LANGUAGE_ID$]", languageIds[i]),
						"[$WEIGHT$]", String.valueOf(i))));
		}
	}

	@Reference
	private ResourceActions _resourceActions;

}