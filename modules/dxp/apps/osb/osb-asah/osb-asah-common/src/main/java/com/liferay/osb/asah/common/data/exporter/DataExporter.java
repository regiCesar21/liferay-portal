/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.data.exporter;

import java.io.File;

/**
 * @author Marcellus Tavares
 */
public interface DataExporter {

	public File export() throws Exception;

}