/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.configuration;

import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;

import java.util.Map;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchConfigurationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testConfigurationsFromBuildTestXmlAntFile() throws Exception {
		Map<String, Object> configurationProperties =
			loadConfigurationProperties(
				"ElasticsearchConfigurationTest-build-test-xml.cfg");

		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchConfigurationTest.class.getSimpleName()
			).elasticsearchConfigurationProperties(
				configurationProperties
			).build();

		try {
			elasticsearchConnectionFixture.createNode();
		}
		finally {
			elasticsearchConnectionFixture.destroyNode();
		}
	}

	protected Map<String, Object> loadConfigurationProperties(String fileName)
		throws Exception {

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		properties.load(clazz.getResourceAsStream(fileName));

		return PropertiesUtil.toMap(properties);
	}

}