/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AnalyticsReportsInfoItemTracker.class)
public class AnalyticsReportsInfoItemTrackerImpl
	implements AnalyticsReportsInfoItemTracker {

	@Override
	public AnalyticsReportsInfoItem<?> getAnalyticsReportsInfoItem(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _analyticsReportsInfoItems.get(key);
	}

	@Override
	public List<AnalyticsReportsInfoItem<?>> getAnalyticsReportsInfoItems() {
		return new ArrayList<>(_analyticsReportsInfoItems.values());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setAnalyticsReportsInfoItem(
		AnalyticsReportsInfoItem<?> analyticsReportsInfo) {

		_analyticsReportsInfoItems.put(
			GenericUtil.getGenericClassName(analyticsReportsInfo),
			analyticsReportsInfo);
	}

	protected void unsetAnalyticsReportsInfoItem(
		AnalyticsReportsInfoItem<?> analyticsReportsInfo) {

		_analyticsReportsInfoItems.remove(
			GenericUtil.getGenericClassName(analyticsReportsInfo));
	}

	private final Map<String, AnalyticsReportsInfoItem<?>>
		_analyticsReportsInfoItems = new ConcurrentHashMap<>();

}