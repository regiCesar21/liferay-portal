/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DDMFormUploadFileEntryHandler.class)
public class DDMFormUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		File file = null;

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			long formInstanceId = ParamUtil.getLong(
				uploadPortletRequest, "formInstanceId");
			long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
			long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

			file = FileUtil.createTempFile(inputStream);

			String fileName = uploadPortletRequest.getFileName("file");

			_ddmFormUploadValidator.validateFileSize(file, fileName);

			_ddmFormUploadValidator.validateFileExtension(fileName);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)uploadPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return addFileEntry(
				formInstanceId, groupId, folderId, file, fileName,
				MimeTypesUtil.getContentType(file, fileName), themeDisplay);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	protected FileEntry addFileEntry(
			long formInstanceId, long groupId, long folderId, File file,
			String fileName, String mimeType, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!DDMFormInstancePermission.contains(
				themeDisplay.getPermissionChecker(), formInstanceId,
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD)) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getPermissionChecker(),
				DDMFormInstance.class.getName(), formInstanceId,
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
		}

		long userId = _getDDMFormDefaultUserId(themeDisplay.getCompanyId());

		String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
			groupId, folderId, fileName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, DDMFormInstance.class.getName(), 0,
			DDMFormConstants.SERVICE_NAME, folderId, file, uniqueFileName,
			mimeType, true);
	}

	private long _getDDMFormDefaultUserId(long companyId)
		throws PortalException {

		User user = _userLocalService.getUserByScreenName(
			companyId, DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME);

		return user.getUserId();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMFormUploadValidator _ddmFormUploadValidator;

	@Reference
	private UserLocalService _userLocalService;

}