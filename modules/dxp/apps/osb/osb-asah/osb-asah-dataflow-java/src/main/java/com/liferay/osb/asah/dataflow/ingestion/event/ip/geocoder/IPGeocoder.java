/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.event.ip.geocoder;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.InetAddress;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.zip.GZIPInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Inácio Nery
 */
public class IPGeocoder {

	public static IPInfo getIPInfo(String ipAddress) {
		if (StringUtils.isBlank(ipAddress)) {
			return null;
		}

		try {
			InetAddress inetAddress = InetAddress.getByName(ipAddress);

			CityResponse cityResponse = _ipGeocoder._databaseReader.city(
				inetAddress);

			if (cityResponse != null) {
				return new IPInfo(cityResponse);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get IP info for " + ipAddress);
			}

			if (inetAddress.isLoopbackAddress() ||
				inetAddress.isSiteLocalAddress()) {

				return IPInfo.LOCAL_NETWORK;
			}
		}
		catch (GeoIp2Exception | IOException exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception.getMessage(), exception);
			}
		}

		return null;
	}

	private IPGeocoder() {
		try {
			DatabaseReader.Builder builder = new DatabaseReader.Builder(
				_getIPGeocoderFile());

			_databaseReader = builder.build();
		}
		catch (IOException ioException) {
			_log.error("Unable to load DB-IP data", ioException);

			throw new IllegalStateException(ioException);
		}

		if (_log.isInfoEnabled()) {
			_log.info("IPGeocoder loaded successfully");
		}
	}

	private InputStream _getDatasetInputStream() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"META-INF/dbip.location.mmdb.gz");
	}

	private File _getIPGeocoderFile() throws IOException {
		Path geoip = Files.createTempFile("IPGeocoder", ".mmdb");

		File file = geoip.toFile();

		synchronized (this) {
			_write(file, new GZIPInputStream(_getDatasetInputStream()));
		}

		if (_log.isDebugEnabled()) {
			_log.debug("IPGeocoder database file successfully created");
		}

		return file;
	}

	private void _write(File file, InputStream inputStream) throws IOException {
		File parentFile = file.getParentFile();

		if (parentFile == null) {
			return;
		}

		try {
			if (!parentFile.exists() && !parentFile.mkdirs()) {
				throw new IOException("Unable to create path");
			}
		}
		catch (SecurityException securityException) {
			throw new IOException("Unable to create path", securityException);
		}

		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);
			BufferedOutputStream bufferedOutputStream =
				new BufferedOutputStream(new FileOutputStream(file))) {

			int i = 0;

			while ((i = bufferedInputStream.read()) != -1) {
				bufferedOutputStream.write(i);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(IPGeocoder.class);

	private static final IPGeocoder _ipGeocoder = new IPGeocoder();

	private volatile DatabaseReader _databaseReader;

}