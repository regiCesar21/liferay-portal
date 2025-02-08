/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.util;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ByteArrayResource;

/**
 * @author Leslie Wong
 */
public class SQLUtil {

	public static ByteArrayResource toByteArrayResource(String query) {
		return new ByteArrayResource(query.getBytes(StandardCharsets.UTF_8));
	}

}