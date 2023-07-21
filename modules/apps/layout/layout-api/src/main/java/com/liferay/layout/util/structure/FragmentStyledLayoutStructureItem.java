/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class FragmentStyledLayoutStructureItem
	extends StyledLayoutStructureItem {

	public FragmentStyledLayoutStructureItem(String parentItemId) {
		super(parentItemId);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentStyledLayoutStructureItem)) {
			return false;
		}

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)object;

		if (!Objects.equals(
				_fragmentEntryLinkId,
				fragmentStyledLayoutStructureItem._fragmentEntryLinkId)) {

			return false;
		}

		return super.equals(object);
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	@Override
	public JSONObject getItemConfigJSONObject() {
		JSONObject jsonObject = super.getItemConfigJSONObject();

		JSONObject stylesJSONObject = jsonObject.getJSONObject("styles");

		if (stylesJSONObject == null) {
			stylesJSONObject = JSONFactoryUtil.createJSONObject();
		}

		return jsonObject.put(
			"fragmentEntryLinkId", String.valueOf(_fragmentEntryLinkId)
		).put(
			"indexed", _indexed
		).put(
			"styles", stylesJSONObject
		);
	}

	@Override
	public String getItemType() {
		return LayoutDataItemTypeConstants.TYPE_FRAGMENT;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getItemId());
	}

	public boolean isIndexed() {
		return _indexed;
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;
	}

	public void setIndexed(boolean indexed) {
		_indexed = indexed;
	}

	@Override
	public void updateItemConfig(JSONObject itemConfigJSONObject) {
		super.updateItemConfig(itemConfigJSONObject);

		if (itemConfigJSONObject.has("indexed")) {
			setIndexed(itemConfigJSONObject.getBoolean("indexed"));
		}

		if (itemConfigJSONObject.has("fragmentEntryLinkId")) {
			setFragmentEntryLinkId(
				itemConfigJSONObject.getLong("fragmentEntryLinkId"));
		}
	}

	private long _fragmentEntryLinkId;
	private boolean _indexed = true;

}