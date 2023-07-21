/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Pavel Savinov
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.layout.util.LayoutClassedModelUsageRecorder}
 */
@Deprecated
public interface AssetEntryUsageRecorder {

	public void record(AssetEntry assetEntry) throws PortalException;

}