/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class DeletedLayoutStructureItem {

	public static DeletedLayoutStructureItem of(JSONObject jsonObject) {
		if (jsonObject == null) {
			return new DeletedLayoutStructureItem(
				StringPool.BLANK, Collections.emptyList());
		}

		return new DeletedLayoutStructureItem(
			jsonObject.getString("itemId"),
			JSONUtil.toStringList(jsonObject.getJSONArray("portletIds")),
			jsonObject.getInt("position"));
	}

	public DeletedLayoutStructureItem(String itemId, List<String> portletIds) {
		this(itemId, portletIds, 0);
	}

	public DeletedLayoutStructureItem(
		String itemId, List<String> portletIds, int position) {

		_itemId = itemId;
		_portletIds = portletIds;
		_position = position;
	}

	public boolean contains(String portletId) {
		if (_portletIds.contains(portletId)) {
			return true;
		}

		return false;
	}

	public String getItemId() {
		return _itemId;
	}

	public List<String> getPortletIds() {
		return _portletIds;
	}

	public int getPosition() {
		return _position;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"itemId", _itemId
		).put(
			"portletIds", _portletIds
		).put(
			"position", _position
		);
	}

	private final String _itemId;
	private final List<String> _portletIds;
	private final int _position;

}