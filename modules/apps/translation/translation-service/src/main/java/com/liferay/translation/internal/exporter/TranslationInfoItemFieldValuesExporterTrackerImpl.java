/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.exporter;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;

import java.util.Collection;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = TranslationInfoItemFieldValuesExporterTracker.class)
public class TranslationInfoItemFieldValuesExporterTrackerImpl
	implements TranslationInfoItemFieldValuesExporterTracker {

	@Override
	public Collection<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValueExporters() {

		return _serviceTrackerMap.values();
	}

	@Override
	public Optional<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValuesExporterOptional(String mimeType) {

		return Optional.ofNullable(_serviceTrackerMap.getService(mimeType));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, TranslationInfoItemFieldValuesExporter.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(translationInfoItemFieldValuesExporter, emitter) ->
					emitter.emit(
						translationInfoItemFieldValuesExporter.getMimeType())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private volatile ServiceTrackerMap
		<String, TranslationInfoItemFieldValuesExporter> _serviceTrackerMap;

}