/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.test.util;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Map;

/**
 * @author Alicia Garcia
 */
public class DepotStagingTestUtil {

	public static void disableStaging(DepotEntry depotEntry)
		throws PortalException {

		disableStaging(depotEntry.getGroup());
	}

	public static void disableStaging(Group group) throws PortalException {
		StagingLocalServiceUtil.disableStaging(
			group,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	public static DepotEntry enableLocalStaging(DepotEntry depotEntry)
		throws Exception {

		Group stagingGroup = enableLocalStaging(depotEntry.getGroup());

		return DepotEntryLocalServiceUtil.fetchGroupDepotEntry(
			stagingGroup.getGroupId());
	}

	public static Group enableLocalStaging(Group group) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		_addStagingAttributes(serviceContext);

		serviceContext.setAttribute("staging", Boolean.TRUE);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		return group.getStagingGroup();
	}

	public static void publishLayouts(Group stagingGroup, Group liveGroup)
		throws PortalException {

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), false, parameters);
	}

	private static void _addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(value));
	}

	private static void _addStagingAttributes(ServiceContext serviceContext) {
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.DATA_STRATEGY_MIRROR, true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, true);
		_addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, true);
	}

}