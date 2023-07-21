/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.portlet.action.test.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.util.HttpImpl;

import java.io.IOException;

import java.util.Map;

/**
 * @author Cristina González
 */
public class MockHttpUtil {

	public static Http geHttp(
		Map<String, UnsafeSupplier<String, Exception>> mockRequest) {

		return new HttpImpl() {

			@Override
			public String URLtoString(Options options) throws IOException {
				try {
					String location = options.getLocation();

					String endpoint = location.substring(
						location.lastIndexOf("/api"),
						_getLastPosition(location));

					if (mockRequest.containsKey(endpoint)) {
						Response httpResponse = new Response();

						httpResponse.setResponseCode(200);

						options.setResponse(httpResponse);

						UnsafeSupplier<String, Exception> unsafeSupplier =
							mockRequest.get(endpoint);

						return unsafeSupplier.get();
					}

					Response httpResponse = new Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					return "error";
				}
				catch (Throwable throwable) {
					Response httpResponse = new Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					throw new RuntimeException(throwable);
				}
			}

		};
	}

	private static int _getLastPosition(String location) {
		if (location.contains("?")) {
			return location.indexOf("?");
		}

		return location.length();
	}

}