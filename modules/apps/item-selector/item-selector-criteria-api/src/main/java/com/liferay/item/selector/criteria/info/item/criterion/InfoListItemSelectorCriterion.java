/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.criteria.info.item.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class InfoListItemSelectorCriterion extends BaseItemSelectorCriterion {

	public String getItemSubtype() {
		return _itemSubtype;
	}

	public String getItemType() {
		if (ListUtil.isEmpty(_itemTypes)) {
			return null;
		}

		return _itemTypes.get(0);
	}

	public List<String> getItemTypes() {
		return _itemTypes;
	}

	public void setItemSubtype(String itemSubtype) {
		_itemSubtype = itemSubtype;
	}

	public void setItemType(String itemType) {
		_itemTypes.add(itemType);
	}

	public void setItemTypes(List<String> itemTypes) {
		_itemTypes = itemTypes;
	}

	private String _itemSubtype;
	private List<String> _itemTypes = new ArrayList<>();

}