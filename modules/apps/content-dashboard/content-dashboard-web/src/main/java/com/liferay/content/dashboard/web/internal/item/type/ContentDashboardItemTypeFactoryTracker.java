/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemTypeFactoryTracker.class)
public class ContentDashboardItemTypeFactoryTracker {

	public Collection<String> getClassNames() {
		return Collections.unmodifiableCollection(_serviceTrackerMap.keySet());
	}

	public Optional<ContentDashboardItemTypeFactory>
		getContentDashboardItemTypeFactoryOptional(String className) {

		return Optional.ofNullable(_serviceTrackerMap.getService(className));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ContentDashboardItemTypeFactory.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(contentDashboardItem, emitter) -> emitter.emit(
					GenericUtil.getGenericClassName(contentDashboardItem))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap<String, ContentDashboardItemTypeFactory>
		_serviceTrackerMap;

}