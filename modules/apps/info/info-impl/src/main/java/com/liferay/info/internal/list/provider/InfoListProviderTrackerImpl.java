/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.list.provider;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProviderTracker.class)
public class InfoListProviderTrackerImpl implements InfoListProviderTracker {

	@Override
	public InfoListProvider<?> getInfoListProvider(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoListProvider.class, key);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders() {
		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class);
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(Class<?> itemClass) {
		return getInfoListProviders(itemClass.getName());
	}

	@Override
	public List<InfoListProvider<?>> getInfoListProviders(
		String itemClassName) {

		return (List<InfoListProvider<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListProvider.class, itemClassName);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}