/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceTestUtil {

	public static DDMFormInstance addDDMFormInstance(
		DDMForm ddmForm, Group group, DDMFormValues settingsDDMFormValues,
		long userId) {

		try {
			DDMStructureTestHelper ddmStructureTestHelper =
				new DDMStructureTestHelper(
					PortalUtil.getClassNameId(DDMFormInstance.class), group);

			DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
				ddmForm, StorageType.JSON.toString());

			return DDMFormInstanceLocalServiceUtil.addFormInstance(
				userId, group.getGroupId(), ddmStructure.getStructureId(),
				HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build(),
				HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build(),
				settingsDDMFormValues,
				ServiceContextTestUtil.getServiceContext());
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	public static DDMFormInstance addDDMFormInstance(
			DDMForm ddmForm, Group group, long userId)
		throws Exception {

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		return addDDMFormInstance(
			ddmForm, group, settingsDDMFormValues, userId);
	}

	public static DDMFormInstance addDDMFormInstance(Group group, long userId)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("text");

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		return addDDMFormInstance(
			ddmForm, group, settingsDDMFormValues, userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceTestUtil.class);

}