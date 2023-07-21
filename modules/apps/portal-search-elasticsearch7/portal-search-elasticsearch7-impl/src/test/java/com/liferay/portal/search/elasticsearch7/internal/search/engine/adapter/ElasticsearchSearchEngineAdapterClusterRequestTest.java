/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster.ClusterRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchSearchEngineAdapterClusterRequestTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				_CLUSTER_NAME
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	@AfterClass
	public static void tearDownClass() {
		_elasticsearchConnectionFixture.destroyNode();
	}

	@Before
	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_elasticsearchConnectionFixture);

		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionFixture.getRestHighLevelClient();

		_indicesClient = restHighLevelClient.indices();

		_createIndex();
	}

	@After
	public void tearDown() {
		_deleteIndex();
	}

	@Test
	public void testExecuteHealthClusterRequest() {
		HealthClusterRequest healthClusterRequest = new HealthClusterRequest(
			new String[] {_INDEX_NAME});

		HealthClusterResponse healthClusterResponse =
			_searchEngineAdapter.execute(healthClusterRequest);

		assertHealthy(healthClusterResponse.getClusterHealthStatus());

		JSONObject jsonObject = createJSONObject(
			healthClusterResponse.getHealthStatusMessage());

		assertClusterName(jsonObject);

		Assert.assertEquals(
			_ELASTICSEARCH_DEFAULT_NUMBER_OF_SHARDS,
			jsonObject.getString("active_shards"));
	}

	@Test
	public void testExecuteStateClusterRequest() {
		StateClusterRequest stateClusterRequest = new StateClusterRequest(
			new String[] {_INDEX_NAME});

		StateClusterResponse stateClusterResponse =
			_searchEngineAdapter.execute(stateClusterRequest);

		assertNodesContainLocalhost(stateClusterResponse.getStateMessage());
	}

	@Test
	public void testExecuteStatsClusterRequest() {
		doTestExecuteStatsClusterRequest(null);
	}

	@Test
	public void testExecuteStatsClusterRequestWithNodeId() {
		doTestExecuteStatsClusterRequest(new String[] {"liferay"});
	}

	protected static ClusterRequestExecutor createClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterRequestExecutorFixture clusterRequestExecutorFixture =
			new ClusterRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		clusterRequestExecutorFixture.setUp();

		return clusterRequestExecutorFixture.getClusterRequestExecutor();
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setClusterRequestExecutor(
					createClusterRequestExecutor(elasticsearchClientResolver));
			}
		};
	}

	protected void assertClusterName(JSONObject jsonObject) {
		Assert.assertEquals(
			_CLUSTER_NAME, jsonObject.getString("cluster_name"));
	}

	protected void assertHealthy(ClusterHealthStatus clusterHealthStatus) {
		Assert.assertTrue(
			clusterHealthStatus.equals(ClusterHealthStatus.GREEN) ||
			clusterHealthStatus.equals(ClusterHealthStatus.YELLOW));
	}

	protected void assertNodesContainLocalhost(String message) {
		JSONObject jsonObject = createJSONObject(message);

		String nodesString = jsonObject.getString("nodes");

		Assert.assertTrue(nodesString.contains("127.0.0.1"));
	}

	protected void assertOneIndex(String message) {
		JSONObject jsonObject = createJSONObject(message);

		JSONObject indicesJSONObject = jsonObject.getJSONObject("indices");

		Assert.assertEquals("1", indicesJSONObject.getString("count"));
	}

	protected JSONObject createJSONObject(String message) {
		try {
			return JSONFactoryUtil.createJSONObject(message);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	protected void doTestExecuteStatsClusterRequest(String[] nodeIds) {
		StatsClusterRequest statsClusterRequest = new StatsClusterRequest(
			nodeIds);

		StatsClusterResponse statsClusterResponse =
			_searchEngineAdapter.execute(statsClusterRequest);

		assertHealthy(statsClusterResponse.getClusterHealthStatus());

		assertOneIndex(statsClusterResponse.getStatsMessage());
	}

	private void _createIndex() {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private void _deleteIndex() {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			_INDEX_NAME);

		try {
			_indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final String _CLUSTER_NAME =
		ElasticsearchSearchEngineAdapterClusterRequestTest.class.
			getSimpleName();

	private static final String _ELASTICSEARCH_DEFAULT_NUMBER_OF_SHARDS = "1";

	private static final String _INDEX_NAME = "test_request_index";

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;

	private IndicesClient _indicesClient;
	private SearchEngineAdapter _searchEngineAdapter;

}