/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.snapshots.SnapshotInfo;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchSearchEngineBackupTest {

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
				ElasticsearchSearchEngineBackupTest.class.getSimpleName()
			).build();

		ElasticsearchSearchEngineFixture elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				elasticsearchConnectionFixture);

		elasticsearchSearchEngineFixture.setUp();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;

		_elasticsearchSearchEngineFixture = elasticsearchSearchEngineFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchSearchEngineFixture.tearDown();
	}

	@Test
	public void testBackup() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.backup(companyId, "backup_test");

		GetSnapshotsResponse getSnapshotsResponse = getGetSnapshotsResponse(
			"liferay_backup", new String[] {"backup_test"}, true);

		List<SnapshotInfo> snapshotInfos = getSnapshotsResponse.getSnapshots();

		Assert.assertTrue(snapshotInfos.size() == 1);

		deleteSnapshot("liferay_backup", "backup_test");
	}

	@Test
	public void testRestore() throws SearchException {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		elasticsearchSearchEngine.createBackupRepository();

		createSnapshot(
			"liferay_backup", "restore_test", true, String.valueOf(companyId));

		elasticsearchSearchEngine.restore(companyId, "restore_test");

		deleteSnapshot("liferay_backup", "restore_test");
	}

	protected void createSnapshot(
		String repositoryName, String snapshotName, boolean waitForCompletion,
		String... indexNames) {

		CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest(
			repositoryName, snapshotName);

		createSnapshotRequest.indices(indexNames);
		createSnapshotRequest.waitForCompletion(waitForCompletion);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			snapshotClient.create(
				createSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void deleteSnapshot(String repository, String snapshot) {
		DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest(
			repository, snapshot);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			snapshotClient.delete(
				deleteSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected GetSnapshotsResponse getGetSnapshotsResponse(
		String repository, String[] snapshots, boolean ignoreUnavailable) {

		GetSnapshotsRequest getSnapshotsRequest = new GetSnapshotsRequest();

		getSnapshotsRequest.ignoreUnavailable(ignoreUnavailable);
		getSnapshotsRequest.repository(repository);
		getSnapshotsRequest.snapshots(snapshots);

		SnapshotClient snapshotClient = getSnapshotClient();

		try {
			return snapshotClient.get(
				getSnapshotsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected SnapshotClient getSnapshotClient() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionFixture.getRestHighLevelClient();

		return restHighLevelClient.snapshot();
	}

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private static ElasticsearchSearchEngineFixture
		_elasticsearchSearchEngineFixture;

}