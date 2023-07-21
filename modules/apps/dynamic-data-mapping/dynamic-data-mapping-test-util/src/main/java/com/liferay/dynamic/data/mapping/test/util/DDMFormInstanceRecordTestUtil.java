/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Gabriel Ibson
 */
public class DDMFormInstanceRecordTestUtil {

	public static DDMFormInstanceRecord addDDMFormInstanceRecord(
			Group group, long userId)
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("text");

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, group, settingsDDMFormValues, userId);

		return DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			userId, group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			settingsDDMFormValues, ServiceContextTestUtil.getServiceContext());
	}

	public static DDMFormInstanceRecord
			addDDMFormInstanceRecordWithStatusByUserId(
				Group group, long statusByUserId, long userId)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord = addDDMFormInstanceRecord(
			group, userId);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getFormInstanceRecordVersion();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return DDMFormInstanceRecordLocalServiceUtil.updateStatus(
			statusByUserId,
			ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

}