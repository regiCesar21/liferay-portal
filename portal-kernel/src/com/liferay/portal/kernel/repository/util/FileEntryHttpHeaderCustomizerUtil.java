/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.util;

import com.liferay.portal.kernel.repository.http.header.customizer.FileEntryHttpHeaderCustomizer;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Adolfo Pérez
 */
public class FileEntryHttpHeaderCustomizerUtil {

	public static String getHttpHeaderValue(
		FileEntry fileEntry, String httpHeaderName, String currentValue) {

		FileEntryHttpHeaderCustomizer fileEntryHttpHeaderCustomizer =
			_fileEntryHttpHeaderCustomizerUtil.
				_getFileEntryHttpHeaderCustomizer(httpHeaderName);

		if (fileEntryHttpHeaderCustomizer == null) {
			return currentValue;
		}

		return fileEntryHttpHeaderCustomizer.getHttpHeaderValue(
			fileEntry, currentValue);
	}

	private FileEntryHttpHeaderCustomizerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			FileEntryHttpHeaderCustomizer.class,
			new FileEntryHttpHeaderCustomizerTrackerCustomizer());

		_serviceTracker.open();
	}

	private FileEntryHttpHeaderCustomizer _getFileEntryHttpHeaderCustomizer(
		String httpHeaderName) {

		return _fileEntryHttpHeaderCustomizers.get(httpHeaderName);
	}

	private static final FileEntryHttpHeaderCustomizerUtil
		_fileEntryHttpHeaderCustomizerUtil =
			new FileEntryHttpHeaderCustomizerUtil();

	private final ConcurrentMap<String, FileEntryHttpHeaderCustomizer>
		_fileEntryHttpHeaderCustomizers = new ConcurrentHashMap<>();
	private final ServiceTracker
		<FileEntryHttpHeaderCustomizer, FileEntryHttpHeaderCustomizer>
			_serviceTracker;

	private class FileEntryHttpHeaderCustomizerTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FileEntryHttpHeaderCustomizer, FileEntryHttpHeaderCustomizer> {

		@Override
		public FileEntryHttpHeaderCustomizer addingService(
			ServiceReference<FileEntryHttpHeaderCustomizer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			FileEntryHttpHeaderCustomizer fileEntryHttpHeaderCustomizer =
				registry.getService(serviceReference);

			List<String> httpHeaderNames = StringPlus.asList(
				serviceReference.getProperty("http.header.name"));

			for (String httpHeaderName : httpHeaderNames) {
				_fileEntryHttpHeaderCustomizers.put(
					httpHeaderName, fileEntryHttpHeaderCustomizer);
			}

			return fileEntryHttpHeaderCustomizer;
		}

		@Override
		public void modifiedService(
			ServiceReference<FileEntryHttpHeaderCustomizer> serviceReference,
			FileEntryHttpHeaderCustomizer service) {
		}

		@Override
		public void removedService(
			ServiceReference<FileEntryHttpHeaderCustomizer> serviceReference,
			FileEntryHttpHeaderCustomizer service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> httpHeaderNames = StringPlus.asList(
				serviceReference.getProperty("http.header.name"));

			for (String httpHeaderName : httpHeaderNames) {
				_fileEntryHttpHeaderCustomizers.remove(httpHeaderName);
			}
		}

	}

}