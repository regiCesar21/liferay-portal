/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.servlet.taglib.util;

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class LayoutClassedModelUsagesTaglibUtil {

	public static void recordLayoutClassedModelUsage(
		String className, long classPK) {

		try {
			Map<String, LayoutClassedModelUsageRecorder>
				layoutClassedModelUsageRecorders =
					ServletContextUtil.getLayoutClassedModelUsageRecorders();

			LayoutClassedModelUsageRecorder layoutClassedModelUsageRecorder =
				layoutClassedModelUsageRecorders.get(className);

			if (layoutClassedModelUsageRecorder != null) {
				layoutClassedModelUsageRecorder.record(
					PortalUtil.getClassNameId(className), classPK);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to check layout classed model usages for ",
						"class name ", className, " and class PK ", classPK),
					portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutClassedModelUsagesTaglibUtil.class);

}