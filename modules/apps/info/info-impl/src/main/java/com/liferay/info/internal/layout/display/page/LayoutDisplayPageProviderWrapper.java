/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.layout.display.page;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class LayoutDisplayPageProviderWrapper
	implements LayoutDisplayPageProvider<Object> {

	public LayoutDisplayPageProviderWrapper(
		InfoDisplayContributor<Object> infoDisplayContributor) {

		_infoDisplayContributor = infoDisplayContributor;
	}

	@Override
	public String getClassName() {
		return _infoDisplayContributor.getClassName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<Object>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		try {
			InfoDisplayObjectProvider<Object> infoDisplayObjectProvider =
				_infoDisplayContributor.getInfoDisplayObjectProvider(
					infoItemReference.getClassPK());

			if (infoDisplayObjectProvider == null) {
				return null;
			}

			return _getLayoutDisplayPageObjectProvider(
				infoDisplayObjectProvider);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get info display object provider for class PK " +
					infoItemReference.getClassPK(),
				portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<Object>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			InfoDisplayObjectProvider<Object> infoDisplayObjectProvider =
				_infoDisplayContributor.getInfoDisplayObjectProvider(
					groupId, urlTitle);

			if (infoDisplayObjectProvider == null) {
				return null;
			}

			return _getLayoutDisplayPageObjectProvider(
				infoDisplayObjectProvider);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get info display object provider for URL title " +
					urlTitle,
				portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return _infoDisplayContributor.getInfoURLSeparator();
	}

	private LayoutDisplayPageObjectProvider<Object>
		_getLayoutDisplayPageObjectProvider(
			InfoDisplayObjectProvider<Object> infoDisplayObjectProvider) {

		return new LayoutDisplayPageObjectProvider() {

			@Override
			public long getClassNameId() {
				return infoDisplayObjectProvider.getClassNameId();
			}

			@Override
			public long getClassPK() {
				return infoDisplayObjectProvider.getClassPK();
			}

			@Override
			public long getClassTypeId() {
				return infoDisplayObjectProvider.getClassTypeId();
			}

			@Override
			public String getDescription(Locale locale) {
				return infoDisplayObjectProvider.getDescription(locale);
			}

			@Override
			public Object getDisplayObject() {
				return infoDisplayObjectProvider.getDisplayObject();
			}

			@Override
			public long getGroupId() {
				return infoDisplayObjectProvider.getGroupId();
			}

			@Override
			public String getKeywords(Locale locale) {
				return infoDisplayObjectProvider.getKeywords(locale);
			}

			@Override
			public String getTitle(Locale locale) {
				return infoDisplayObjectProvider.getTitle(locale);
			}

			@Override
			public String getURLTitle(Locale locale) {
				return infoDisplayObjectProvider.getURLTitle(locale);
			}

		};
	}

	private final InfoDisplayContributor<Object> _infoDisplayContributor;

}