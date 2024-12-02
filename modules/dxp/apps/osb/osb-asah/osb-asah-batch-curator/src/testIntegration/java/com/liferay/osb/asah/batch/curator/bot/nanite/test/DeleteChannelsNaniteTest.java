/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite.test;

import com.liferay.osb.asah.batch.curator.OSBAsahBatchCuratorSpringTestContext;
import com.liferay.osb.asah.batch.curator.bot.nanite.DeleteChannelsNanite;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.repository.executor.BigQueryQueryExecutor;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcos Martins
 */
public class DeleteChannelsNaniteTest
	implements OSBAsahBatchCuratorSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "delete_channels_nanite_test.sql")
	@Test
	public void testRun() throws Exception {
		_deleteChannelsNanite.run(
			JSONUtil.put(
				"channelIds", JSONUtil.put(1L)
			).put(
				"createDate", DateUtil.newDateString()
			).put(
				"userId", "1"
			).put(
				"userName", "User"
			));

		_assertEquals(
			1L, 0,
			new String[] {
				"BlogDaily", "BQEvent", "BQMembership", "CustomAssetDaily",
				"DocumentLibraryDaily", "FormDaily",
				"BQIdentityActivitySummary", "BQIdentityInterestPage",
				"BQIdentityInterestScore", "JournalDaily", "PageDaily",
				"BQSession", "BQSessionInterestScore"
			});
	}

	private void _assertEquals(
		long channelId, long expectedValue, String[] tableNames) {

		for (String tableName : tableNames) {
			Assertions.assertEquals(
				expectedValue,
				_bigQueryQueryExecutor.queryForLong(
					_dslContext.selectCount(
					).from(
						tableName
					).where(
						DSL.field(
							"channelId"
						).eq(
							channelId
						)
					)));
		}
	}

	@Autowired
	private BigQueryQueryExecutor _bigQueryQueryExecutor;

	@Autowired
	private DeleteChannelsNanite _deleteChannelsNanite;

	@Autowired
	private DSLContext _dslContext;

}