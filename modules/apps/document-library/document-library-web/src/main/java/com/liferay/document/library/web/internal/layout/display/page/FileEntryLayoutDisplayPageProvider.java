/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.layout.display.page;

import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutDisplayPageProvider.class)
public class FileEntryLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<FileEntry> {

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<FileEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			LocalRepository localRepository =
				_repositoryProvider.fetchFileEntryLocalRepository(
					infoItemReference.getClassPK());

			if (localRepository == null) {
				return null;
			}

			FileEntry fileEntry = localRepository.getFileEntry(
				infoItemReference.getClassPK());

			if (fileEntry.isInTrash()) {
				return null;
			}

			return new FileEntryLayoutDisplayPageObjectProvider(fileEntry);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<FileEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		return getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				FileEntry.class.getName(), Long.valueOf(urlTitle)));
	}

	@Override
	public String getURLSeparator() {
		return "/d/";
	}

	@Reference
	private RepositoryProvider _repositoryProvider;

}