/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.info.display.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class BlogsInfoDisplayObjectProvider
	implements InfoDisplayObjectProvider<BlogsEntry> {

	public BlogsInfoDisplayObjectProvider(
			BlogsEntry blogsEntry, AssetHelper assetHelper)
		throws PortalException {

		_blogsEntry = blogsEntry;
		_assetHelper = assetHelper;

		_assetEntry = _getAssetEntry(blogsEntry);
	}

	@Override
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _blogsEntry.getEntryId();
	}

	@Override
	public long getClassTypeId() {
		return _assetEntry.getClassTypeId();
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetEntry.getDescription(locale);
	}

	@Override
	public BlogsEntry getDisplayObject() {
		return _blogsEntry;
	}

	@Override
	public long getGroupId() {
		return _blogsEntry.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return _assetHelper.getAssetKeywords(
			_assetEntry.getClassName(), _assetEntry.getClassPK(), locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetEntry.getTitle(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		AssetRenderer<?> assetRenderer = _assetEntry.getAssetRenderer();

		return assetRenderer.getUrlTitle(locale);
	}

	private AssetEntry _getAssetEntry(BlogsEntry blogsEntry)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					PortalUtil.getClassNameId(BlogsEntry.class));

		return assetRendererFactory.getAssetEntry(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());
	}

	private final AssetEntry _assetEntry;
	private final AssetHelper _assetHelper;
	private final BlogsEntry _blogsEntry;

}