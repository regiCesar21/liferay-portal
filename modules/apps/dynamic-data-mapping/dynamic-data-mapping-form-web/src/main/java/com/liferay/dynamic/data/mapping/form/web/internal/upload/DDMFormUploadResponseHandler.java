/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadResponseHandler;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DDMFormUploadResponseHandler.class)
public class DDMFormUploadResponseHandler implements UploadResponseHandler {

	@Override
	public JSONObject onFailure(
			PortletRequest portletRequest, PortalException portalException)
		throws PortalException {

		JSONObject jsonObject = _defaultUploadResponseHandler.onFailure(
			portletRequest, portalException);

		String errorMessage = StringPool.BLANK;

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (portalException instanceof AntivirusScannerException) {
			errorMessage = themeDisplay.translate(
				"a-virus-was-detected-in-the-file");
		}
		else if (portalException instanceof FileExtensionException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-extension-x",
				StringUtil.merge(
					_ddmFormUploadValidator.getGuestUploadFileExtensions(),
					StringPool.COMMA_AND_SPACE));
		}
		else if (portalException instanceof FileNameException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-name");
		}
		else if (portalException instanceof FileSizeException) {
			errorMessage = themeDisplay.translate(
				"please-enter-a-file-with-a-valid-file-size-no-larger-than-x",
				LanguageUtil.formatStorageSize(
					_ddmFormUploadValidator.getGuestUploadMaximumFileSize(),
					themeDisplay.getLocale()));
		}
		else if (portalException instanceof InvalidFileException) {
			errorMessage = themeDisplay.translate("please-enter-a-valid-file");
		}
		else {
			errorMessage = themeDisplay.translate(
				"an-unexpected-error-occurred-while-uploading-your-file");
		}

		return jsonObject.put("error", JSONUtil.put("message", errorMessage));
	}

	@Override
	public JSONObject onSuccess(
			UploadPortletRequest uploadPortletRequest, FileEntry fileEntry)
		throws PortalException {

		return _defaultUploadResponseHandler.onSuccess(
			uploadPortletRequest, fileEntry);
	}

	@Reference
	private DDMFormUploadValidator _ddmFormUploadValidator;

	@Reference(target = "(upload.response.handler.system.default=true)")
	private UploadResponseHandler _defaultUploadResponseHandler;

}