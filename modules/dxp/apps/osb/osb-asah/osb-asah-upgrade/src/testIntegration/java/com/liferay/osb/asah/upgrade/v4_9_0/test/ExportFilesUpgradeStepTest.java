/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_9_0.test;

import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;
import com.liferay.osb.asah.test.util.util.RandomTestUtil;
import com.liferay.osb.asah.upgrade.OSBAsahUpgradeSpringTestContext;
import com.liferay.osb.asah.upgrade.v4_9_0.ExportFilesUpgradeStep;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcellus Tavares
 */
public class ExportFilesUpgradeStepTest
	implements OSBAsahTestExecutionListenersContext,
			   OSBAsahUpgradeSpringTestContext {

	@BeforeEach
	public void setUp() throws Exception {
		_tmpPath = Paths.get(
			System.getProperty("java.io.tmpdir"),
			RandomTestUtil.randomString());

		if (!Files.exists(_tmpPath)) {
			Files.createDirectory(_tmpPath);
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		Stream<Path> stream = Files.list(_tmpPath);

		stream.forEach(
			filePath -> {
				try {
					Files.delete(filePath);
				}
				catch (Exception exception) {
					_log.error("Unable to delete file " + filePath);
				}
			});

		Files.delete(_tmpPath);
	}

	@Test
	public void testListExportFilesModifiedLast30Days() throws Exception {
		Path newFilePath1 = Files.write(
			Paths.get(_tmpPath.toString(), "new_1.zip"), new byte[0]);

		Path newFilePath2 = Files.write(
			Paths.get(_tmpPath.toString(), "new_2.zip"), new byte[0]);

		Files.write(Paths.get(_tmpPath.toString(), "new_3.json"), new byte[0]);

		Path oldFilePath = Files.write(
			Paths.get(_tmpPath.toString(), "old.zip"), new byte[0]);

		LocalDateTime localDateTime = LocalDateTime.now();

		localDateTime = localDateTime.minusDays(35);

		Files.setAttribute(
			oldFilePath, "lastModifiedTime",
			FileTime.from(localDateTime.toInstant(ZoneOffset.UTC)));

		List<Path> paths =
			_exportFilesUpgradeStep.listZipFilePathsModifiedLast7Days(
				SetUtil.of("new_1.zip", "new_2.zip", "old.zip"), _tmpPath);

		Assertions.assertEquals(2, paths.size());
		Assertions.assertTrue(paths.contains(newFilePath1));
		Assertions.assertTrue(paths.contains(newFilePath2));
	}

	private static final Log _log = LogFactory.getLog(
		ExportFilesUpgradeStepTest.class);

	@Autowired
	private ExportFilesUpgradeStep _exportFilesUpgradeStep;

	private Path _tmpPath;

}