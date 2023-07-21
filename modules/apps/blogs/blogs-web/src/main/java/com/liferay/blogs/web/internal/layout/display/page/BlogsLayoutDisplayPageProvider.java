/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = LayoutDisplayPageProvider.class)
public class BlogsLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			BlogsEntry blogsEntry = _blogsEntryService.getEntry(
				infoItemReference.getClassPK());

			if (blogsEntry.isDraft() || blogsEntry.isInTrash()) {
				return null;
			}

			return new BlogsLayoutDisplayPageObjectProvider(
				blogsEntry, _assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<BlogsEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			BlogsEntry blogsEntry = _blogsEntryService.getEntry(
				groupId, urlTitle);

			if (blogsEntry.isInTrash()) {
				return null;
			}

			return new BlogsLayoutDisplayPageObjectProvider(
				blogsEntry, _assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return "/b/";
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private BlogsEntryService _blogsEntryService;

}