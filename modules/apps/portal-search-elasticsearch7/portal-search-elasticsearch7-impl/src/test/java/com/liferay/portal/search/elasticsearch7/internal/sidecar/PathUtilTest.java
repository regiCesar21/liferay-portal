/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class PathUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws IOException {
		_toPath = Files.createTempDirectory("temp_dir");
	}

	@After
	public void tearDown() {
		PathUtil.deleteDir(_toPath);
	}

	@Test
	public void testCopyDirectory() throws Exception {
		Path fromPath = getResourcePath("root");

		Path excludedPaths = getResourcePath("root/excluded");

		PathUtil.copyDirectory(fromPath, _toPath, excludedPaths);

		assertExists(_toPath, "directory1/file1.txt");
		assertExists(_toPath, "directory2/file2.txt");
		assertDoesNotExist(_toPath, "excluded/excluded.txt");
	}

	protected void assertDoesNotExist(Path path, String name) {
		Path fullPath = path.resolve(name);

		Assert.assertFalse(Files.exists(fullPath));
	}

	protected void assertExists(Path path, String name) {
		Path fullPath = path.resolve(name);

		Assert.assertTrue(Files.exists(fullPath));
	}

	protected Path getResourcePath(String name) throws URISyntaxException {
		Class<? extends PathUtilTest> clazz = getClass();

		URL url = clazz.getResource(name);

		return Paths.get(url.toURI());
	}

	private Path _toPath;

}