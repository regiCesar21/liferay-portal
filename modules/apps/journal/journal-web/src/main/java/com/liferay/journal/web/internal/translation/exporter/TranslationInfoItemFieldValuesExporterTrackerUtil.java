/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.translation.exporter;

import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporterTracker;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class TranslationInfoItemFieldValuesExporterTrackerUtil {

	public static Collection<TranslationInfoItemFieldValuesExporter>
		getTranslationInfoItemFieldValuesExporters() {

		return _translationInfoItemFieldValuesExporterTracker.
			getTranslationInfoItemFieldValueExporters();
	}

	@Reference(unbind = "-")
	protected void setTranslationInfoItemFieldValuesExporterTracker(
		TranslationInfoItemFieldValuesExporterTracker
			translationInfoItemFieldValuesExporterTracker) {

		_translationInfoItemFieldValuesExporterTracker =
			translationInfoItemFieldValuesExporterTracker;
	}

	private static TranslationInfoItemFieldValuesExporterTracker
		_translationInfoItemFieldValuesExporterTracker;

}