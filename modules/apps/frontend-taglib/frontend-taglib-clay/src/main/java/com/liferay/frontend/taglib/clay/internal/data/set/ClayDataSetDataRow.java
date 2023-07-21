/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.data.set;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ClayDataSetDataRow {

	public ClayDataSetDataRow(Object item) {
		_item = item;

		_actionDropdownItems = new ArrayList<>();
	}

	public void addActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems.addAll(actionDropdownItems);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return _actionDropdownItems;
	}

	@JsonUnwrapped
	public Object getItem() {
		return _item;
	}

	private final List<DropdownItem> _actionDropdownItems;
	private final Object _item;

}