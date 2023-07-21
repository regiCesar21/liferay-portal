/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.importer;

import java.io.File;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface FragmentsImporter {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #importFragmentEntries}
	 */
	@Deprecated
	public List<String> importFile(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception;

	public List<FragmentsImporterResultEntry> importFragmentEntries(
			long userId, long groupId, long fragmentCollectionId, File file,
			boolean overwrite)
		throws Exception;

}