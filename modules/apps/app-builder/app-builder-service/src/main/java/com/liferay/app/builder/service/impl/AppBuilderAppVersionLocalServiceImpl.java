/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.service.impl;

import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.base.AppBuilderAppVersionLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.app.builder.model.AppBuilderAppVersion",
	service = AopService.class
)
public class AppBuilderAppVersionLocalServiceImpl
	extends AppBuilderAppVersionLocalServiceBaseImpl {

	@Override
	public AppBuilderAppVersion addAppBuilderAppVersion(
			long groupId, long companyId, long userId, long appBuilderAppId,
			long ddlRecordSetId, long ddmStructureId, long ddmStructureLayoutId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AppBuilderAppVersion appBuilderApp =
			appBuilderAppVersionPersistence.create(
				counterLocalService.increment());

		appBuilderApp.setGroupId(groupId);
		appBuilderApp.setCompanyId(companyId);
		appBuilderApp.setUserId(user.getUserId());
		appBuilderApp.setUserName(user.getFullName());
		appBuilderApp.setCreateDate(new Date());
		appBuilderApp.setModifiedDate(new Date());
		appBuilderApp.setAppBuilderAppId(appBuilderAppId);
		appBuilderApp.setDdlRecordSetId(ddlRecordSetId);
		appBuilderApp.setDdmStructureId(ddmStructureId);
		appBuilderApp.setDdmStructureLayoutId(ddmStructureLayoutId);
		appBuilderApp.setVersion(_getNextVersion(appBuilderAppId));

		return appBuilderAppVersionPersistence.update(appBuilderApp);
	}

	@Override
	public void deleteAppBuilderAppVersions(long appBuilderAppId) {
		appBuilderAppVersionPersistence.removeByAppBuilderAppId(
			appBuilderAppId);
	}

	@Override
	public AppBuilderAppVersion fetchLatestAppBuilderAppVersion(
		long appBuilderAppId) {

		return appBuilderAppVersionPersistence.fetchByAppBuilderAppId_First(
			appBuilderAppId, null);
	}

	@Override
	public AppBuilderAppVersion getAppBuilderAppVersion(
			long appBuilderAppId, String version)
		throws PortalException {

		return appBuilderAppVersionPersistence.findByA_V(
			appBuilderAppId, version);
	}

	@Override
	public AppBuilderAppVersion getLatestAppBuilderAppVersion(
			long appBuilderAppId)
		throws PortalException {

		return appBuilderAppVersionPersistence.findByAppBuilderAppId_First(
			appBuilderAppId, null);
	}

	private String _getNextVersion(long appBuilderAppId)
		throws PortalException {

		AppBuilderAppVersion latestAppBuilderAppVersion =
			fetchLatestAppBuilderAppVersion(appBuilderAppId);

		if (latestAppBuilderAppVersion == null) {
			return _VERSION_DEFAULT;
		}

		int[] versionParts = StringUtil.split(
			latestAppBuilderAppVersion.getVersion(), StringPool.PERIOD, 0);

		return StringBundler.concat(
			++versionParts[0], StringPool.PERIOD, versionParts[1]);
	}

	private static final String _VERSION_DEFAULT = "1.0";

}