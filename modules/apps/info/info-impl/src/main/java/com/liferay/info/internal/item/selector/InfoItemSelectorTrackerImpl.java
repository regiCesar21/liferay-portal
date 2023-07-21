/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.item.selector;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.selector.InfoItemSelector;
import com.liferay.info.item.selector.InfoItemSelectorTracker;
import com.liferay.info.list.provider.InfoListProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoItemSelectorTracker.class)
public class InfoItemSelectorTrackerImpl implements InfoItemSelectorTracker {

	@Override
	public InfoItemSelector<?> getInfoItemSelector(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoItemSelector.class, key);
	}

	@Override
	public List<InfoItemSelector<?>> getInfoItemSelectors() {
		return (List<InfoItemSelector<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemSelector.class);
	}

	@Override
	public List<InfoItemSelector<?>> getInfoItemSelectors(
		String itemClassName) {

		return (List<InfoItemSelector<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemSelector.class, itemClassName);
	}

	@Override
	public Set<String> getInfoItemSelectorsClassNames() {
		return new HashSet(
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoListProvider.class));
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}