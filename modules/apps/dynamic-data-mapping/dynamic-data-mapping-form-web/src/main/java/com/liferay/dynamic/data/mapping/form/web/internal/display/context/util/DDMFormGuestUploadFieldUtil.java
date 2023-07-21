/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(immediate = true, service = {})
public class DDMFormGuestUploadFieldUtil {

	public static boolean isMaximumSubmissionLimitReached(
			DDMFormInstance ddmFormInstance,
			HttpServletRequest httpServletRequest,
			int guestUploadMaximumSubmissions)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isSignedIn() ||
			!hasGuestUploadField(ddmFormInstance)) {

			return false;
		}

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				ddmFormInstance.getFormInstanceId(),
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (int i = 0; i < ddmFormInstanceRecords.size(); i++) {
			DDMFormInstanceRecord ddmFormInstanceRecord =
				ddmFormInstanceRecords.get(i);

			if (Objects.equals(
					ddmFormInstanceRecord.getIpAddress(),
					httpServletRequest.getRemoteAddr()) &&
				((i + 1) == guestUploadMaximumSubmissions)) {

				return true;
			}
		}

		return false;
	}

	protected static boolean hasGuestUploadField(
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Collection<DDMFormField> ddmFormFields = ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		Optional<DDMFormField> ddmFormFieldOptional =
			ddmFormFieldsStream.filter(
				ddmFormField ->
					Objects.equals(
						ddmFormField.getType(), "document_library") &&
					GetterUtil.getBoolean(
						ddmFormField.getProperty("allowGuestUsers"))
			).findFirst();

		return ddmFormFieldOptional.isPresent();
	}

	@Reference(unbind = "-")
	private void _setDDMFormInstanceRecordLocalService(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	private static DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}