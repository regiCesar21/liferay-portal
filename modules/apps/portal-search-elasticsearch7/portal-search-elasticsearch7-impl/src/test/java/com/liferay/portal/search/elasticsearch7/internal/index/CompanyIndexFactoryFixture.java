/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionNotInitializedException;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.HashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class CompanyIndexFactoryFixture {

	public CompanyIndexFactoryFixture(
		ElasticsearchClientResolver elasticsearchClientResolver,
		String indexName) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_indexName = indexName;

		_elasticsearchConnectionManager = Mockito.mock(
			ElasticsearchConnectionManager.class);

		Mockito.when(
			_elasticsearchConnectionManager.getRestHighLevelClient()
		).thenThrow(
			ElasticsearchConnectionNotInitializedException.class
		);
	}

	public void createIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.createIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public void deleteIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.deleteIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public CompanyIndexFactory getCompanyIndexFactory() {
		return new CompanyIndexFactory() {
			{
				setElasticsearchConfigurationWrapper(
					createElasticsearchConfigurationWrapper());
				setElasticsearchConnectionManager(
					_elasticsearchConnectionManager);
				setIndexNameBuilder(new TestIndexNameBuilder());
				setJsonFactory(new JSONFactoryImpl());
			}
		};
	}

	public String getIndexName() {
		IndexName indexName = new IndexName(_indexName);

		return indexName.getName();
	}

	protected ElasticsearchConfigurationWrapper
		createElasticsearchConfigurationWrapper() {

		return new ElasticsearchConfigurationWrapper() {
			{
				activate(new HashMap<>());
			}
		};
	}

	protected class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return CompanyIndexFactoryFixture.this.getIndexName();
		}

	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;
	private final String _indexName;

}