/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.test.util.AssertUtils;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchIndexInformationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());

		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchIndexInformationTest.class.getSimpleName()
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchConnectionFixture.destroyNode();
	}

	@Before
	public void setUp() throws Exception {
		_companyIndexFactoryFixture = createCompanyIndexFactoryFixture(
			_elasticsearchConnectionFixture);

		_elasticsearchIndexInformation = createElasticsearchIndexInformation(
			_elasticsearchConnectionFixture);
	}

	@Test
	public void testGetCompanyIndexName() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		long companyId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			getIndexNameBuilder(companyId),
			_elasticsearchIndexInformation.getCompanyIndexName(companyId));
	}

	@Test
	public void testGetFieldMappings() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		String fieldMappings = _elasticsearchIndexInformation.getFieldMappings(
			_companyIndexFactoryFixture.getIndexName());

		AssertUtils.assertEquals(
			"", loadJSONObject(testName.getMethodName()),
			_jsonFactory.createJSONObject(fieldMappings));
	}

	@Test
	public void testGetIndexNames() throws Exception {
		_companyIndexFactoryFixture.createIndices();

		AssertUtils.assertEquals(
			"", Arrays.asList(_companyIndexFactoryFixture.getIndexName()),
			Arrays.asList(_elasticsearchIndexInformation.getIndexNames()));
	}

	@Rule
	public TestName testName = new TestName();

	protected static ElasticsearchIndexInformation
		createElasticsearchIndexInformation(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchIndexInformation() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setIndexNameBuilder(
					ElasticsearchIndexInformationTest::getIndexNameBuilder);
			}
		};
	}

	protected static String getIndexNameBuilder(long companyId) {
		return "test-" + companyId;
	}

	protected CompanyIndexFactoryFixture createCompanyIndexFactoryFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CompanyIndexFactoryFixture(
			elasticsearchClientResolver, testName.getMethodName());
	}

	protected JSONObject loadJSONObject(String suffix) throws Exception {
		String json = ResourceUtil.getResourceAsString(
			getClass(),
			"ElasticsearchIndexInformationTest-" + suffix + ".json");

		return _jsonFactory.createJSONObject(json);
	}

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;

	private CompanyIndexFactoryFixture _companyIndexFactoryFixture;
	private ElasticsearchIndexInformation _elasticsearchIndexInformation;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}