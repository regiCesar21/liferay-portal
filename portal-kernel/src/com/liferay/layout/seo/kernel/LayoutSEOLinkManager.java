/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ListMergeable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Cristina González
 */
public interface LayoutSEOLinkManager {

	public default LayoutSEOLink getCanonicalLayoutSEOLink(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public default String getFullPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, String companyName,
			Locale locale)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public List<LayoutSEOLink> getLocalizedLayoutSEOLinks(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException;

	public default String getPageTitle(
			Layout layout, String portletId, String tilesTitle,
			ListMergeable<String> titleListMergeable,
			ListMergeable<String> subtitleListMergeable, Locale locale)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public default String getPageTitleSuffix(Layout layout, String companyName)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             OpenGraphConfiguration#isOpenGraphEnabled(Group)}
	 */
	@Deprecated
	public default boolean isOpenGraphEnabled(Layout layout)
		throws PortalException {

		return false;
	}

}