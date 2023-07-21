/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.asset.entry.query.processor;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Sarai Díaz
 */
@ProviderType
public interface AssetListAssetEntryQueryProcessor {

	public void processAssetEntryQuery(
		long companyId, String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #processAssetEntryQuery(long, String, UnicodeProperties,
	 *             AssetEntryQuery)}
	 */
	@Deprecated
	public void processAssetEntryQuery(
		String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery);

}