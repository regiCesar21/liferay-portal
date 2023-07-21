/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.action;

import com.liferay.asset.kernel.action.AssetEntryAction;
import com.liferay.osgi.service.tracker.collections.ServiceTrackerMapBuilder;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetEntryActionRegistry.class)
public class AssetEntryActionRegistry {

	public List<AssetEntryAction<?>> getAssetEntryActions(String className) {
		List<AssetEntryAction<?>> assetEntryActions =
			_assetEntryActionsMap.getService(className);

		if (assetEntryActions != null) {
			return assetEntryActions;
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_assetEntryActionsMap =
			ServiceTrackerMapBuilder.SelectorFactory.newSelector(
				bundleContext,
				(Class<AssetEntryAction<?>>)(Class<?>)AssetEntryAction.class
			).map(
				"model.class.name"
			).collectMultiValue(
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator<>(
						"asset.entry.action.order"))
			).build();
	}

	private ServiceTrackerMap<String, List<AssetEntryAction<?>>>
		_assetEntryActionsMap;

}