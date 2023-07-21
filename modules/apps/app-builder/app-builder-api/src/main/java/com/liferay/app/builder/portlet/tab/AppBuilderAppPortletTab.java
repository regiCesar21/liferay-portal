/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.portlet.tab;

import com.liferay.app.builder.model.AppBuilderApp;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface AppBuilderAppPortletTab {

	public AppBuilderAppPortletTabContext getAppBuilderAppPortletTabContext(
		AppBuilderApp appBuilderApp, long dataRecordId);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getAppBuilderAppPortletTabContext(AppBuilderApp, long)}
	 */
	@Deprecated
	public default List<Long> getDataLayoutIds(
		AppBuilderApp appBuilderApp, long dataRecordId) {

		AppBuilderAppPortletTabContext appBuilderAppPortletTabContext =
			getAppBuilderAppPortletTabContext(appBuilderApp, dataRecordId);

		return appBuilderAppPortletTabContext.getDataLayoutIds();
	}

	public String getEditEntryPoint();

	public String getListEntryPoint();

	public String getViewEntryPoint();

}