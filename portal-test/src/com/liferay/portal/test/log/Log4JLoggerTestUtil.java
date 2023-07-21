/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Shuyang Zhou
 */
public class Log4JLoggerTestUtil {

	public static CaptureAppender configureLog4JLogger(
		String name, Level level) {

		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		Logger logger = null;

		try {
			logger = ReflectionTestUtil.getFieldValue(log, "_logger");
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Log " + name + " is not a Log4j logger");
		}

		CaptureAppender captureAppender = new CaptureAppender(logger);

		logger.addAppender(captureAppender);

		logger.setLevel(level);

		return captureAppender;
	}

	public static Level setLoggerLevel(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		Logger logger = null;

		try {
			logger = ReflectionTestUtil.getFieldValue(log, "_logger");
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Log " + name + " is not a Log4j logger");
		}

		Level oldLevel = logger.getLevel();

		logger.setLevel(level);

		return oldLevel;
	}

}