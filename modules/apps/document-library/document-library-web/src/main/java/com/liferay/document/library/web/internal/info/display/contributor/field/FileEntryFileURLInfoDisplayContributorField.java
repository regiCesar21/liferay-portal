/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.display.contributor.field;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
	service = InfoDisplayContributorField.class
)
public class FileEntryFileURLInfoDisplayContributorField
	implements InfoDisplayContributorField<FileEntry> {

	@Override
	public String getKey() {
		return "fileURL";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()), "file-url");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.IMAGE;
	}

	@Override
	public WebImage getValue(FileEntry fileEntry, Locale locale) {
		try {
			String mimeType = fileEntry.getMimeType();

			if (!mimeType.startsWith("image")) {
				return null;
			}

			WebImage webImage = new WebImage(
				_dlURLHelper.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK));

			webImage.setAlt(fileEntry.getTitle());

			return webImage;
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Reference
	private DLURLHelper _dlURLHelper;

}