/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.importer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;

import java.io.File;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public interface LayoutPageTemplatesImporter {

	public void importFile(
			long userId, long groupId, File file, boolean overwrite)
		throws Exception;

	public List<LayoutPageTemplatesImporterResultEntry> importFile(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			File file, boolean overwrite)
		throws Exception;

	public List<FragmentEntryLink> importPageElement(
			Layout layout, LayoutStructure layoutStructure, String parentItemId,
			String pageElementJSON, int position)
		throws Exception;

}