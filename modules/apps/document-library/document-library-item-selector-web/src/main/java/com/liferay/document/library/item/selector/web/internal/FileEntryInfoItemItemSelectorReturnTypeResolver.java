/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ItemSelectorReturnTypeResolver.class
)
public class FileEntryInfoItemItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoItemItemSelectorReturnType, FileEntry> {

	@Override
	public Class<InfoItemItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoItemItemSelectorReturnType.class;
	}

	@Override
	public Class<FileEntry> getModelClass() {
		return FileEntry.class;
	}

	@Override
	public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay) {
		JSONObject fileEntryJSONObject = JSONUtil.put(
			"className", FileEntry.class.getName()
		).put(
			"classNameId", _portal.getClassNameId(FileEntry.class.getName())
		).put(
			"classPK", fileEntry.getFileEntryId()
		).put(
			"title", fileEntry.getTitle()
		);

		return fileEntryJSONObject.toString();
	}

	@Reference
	private Portal _portal;

}