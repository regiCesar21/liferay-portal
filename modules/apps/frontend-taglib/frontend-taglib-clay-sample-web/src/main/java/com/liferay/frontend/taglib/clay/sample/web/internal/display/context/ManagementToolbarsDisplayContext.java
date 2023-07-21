/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarsDisplayContext {

	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#edit");
				dropdownItem.setLabel("Edit");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#download");
				dropdownItem.setIcon("download");
				dropdownItem.setLabel("Download");
				dropdownItem.setQuickAction(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#delete");
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel("Delete");
				dropdownItem.setQuickAction(true);
			}
		).build();

		return _actionDropdownItems;
	}

	public CreationMenu getCreationMenu() {
		if (_creationMenu != null) {
			return _creationMenu;
		}

		_creationMenu = CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Sample 1");
			}
		).addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Sample 2");
			}
		).addFavoriteDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("#3");
				dropdownItem.setLabel("Favorite 1");
			}
		).addFavoriteDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("#4");
				dropdownItem.setLabel("Other item");
			}
		).build();

		return _creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		if (_filterDropdownItems != null) {
			return _filterDropdownItems;
		}

		_filterDropdownItems = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#1");
							dropdownItem.setLabel("Filter 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#2");
							dropdownItem.setLabel("Filter 2");
						}
					).build());

				dropdownGroupItem.setLabel("Filter By");
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#3");
							dropdownItem.setLabel("Order 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#4");
							dropdownItem.setLabel("Order 2");
						}
					).build());

				dropdownGroupItem.setLabel("Order By");
			}
		).build();

		return _filterDropdownItems;
	}

	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			labelItem -> labelItem.setLabel("Filter 1")
		).add(
			labelItem -> labelItem.setLabel("Filter 2")
		).build();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		if (_viewTypeItems != null) {
			return _viewTypeItems;
		}

		_viewTypeItems = new ViewTypeItemList() {
			{
				addCardViewTypeItem(
					viewTypeItem -> {
						viewTypeItem.setActive(true);
						viewTypeItem.setLabel("Card");
					});

				addListViewTypeItem(
					viewTypeItem -> viewTypeItem.setLabel("List"));

				addTableViewTypeItem(
					viewTypeItem -> viewTypeItem.setLabel("Table"));
			}
		};

		return _viewTypeItems;
	}

	private List<DropdownItem> _actionDropdownItems;
	private CreationMenu _creationMenu;
	private List<DropdownItem> _filterDropdownItems;
	private List<ViewTypeItem> _viewTypeItems;

}