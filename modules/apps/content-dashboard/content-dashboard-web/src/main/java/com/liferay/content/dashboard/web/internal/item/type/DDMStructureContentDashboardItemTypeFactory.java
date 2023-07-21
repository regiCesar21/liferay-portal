/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemTypeFactory.class)
public class DDMStructureContentDashboardItemTypeFactory
	implements ContentDashboardItemTypeFactory<DDMStructure> {

	@Override
	public ContentDashboardItemType<DDMStructure> create(long classPK)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			classPK);

		return new DDMStructureContentDashboardItemType(
			ddmStructure,
			_groupLocalService.fetchGroup(ddmStructure.getGroupId()));
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}