/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.configuration.OperationModeResolver;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelper;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperImpl;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchConnectionLogTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_elasticsearchConfigurationWrapper = Mockito.mock(
			ElasticsearchConfigurationWrapper.class);

		Mockito.when(
			_elasticsearchConfigurationWrapper.restClientLoggerLevel()
		).thenReturn(
			RESTClientLoggerLevel.DEBUG
		);

		_searchLogHelper = Mockito.spy(new SearchLogHelperImpl());

		SearchLogHelperUtil.setSearchLogHelper(_searchLogHelper);
	}

	@Test
	public void testLogLevel() throws Exception {
		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager() {
				{
					elasticsearchConfigurationWrapper =
						_elasticsearchConfigurationWrapper;
					operationModeResolver = Mockito.mock(
						OperationModeResolver.class);
				}
			};

		elasticsearchConnectionManager.applyConfigurations();

		Mockito.verify(
			_searchLogHelper
		).setRESTClientLoggerLevel(
			RESTClientLoggerLevel.DEBUG
		);
	}

	private ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private SearchLogHelper _searchLogHelper;

}