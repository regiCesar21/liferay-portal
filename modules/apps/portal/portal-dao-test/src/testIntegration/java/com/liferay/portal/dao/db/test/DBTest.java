/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_db = DBManagerUtil.getDB();

		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME, "(id LONG not null primary key, ",
				"notNilColumn VARCHAR(75) not null, nilColumn VARCHAR(75) ",
				"null)"));
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("drop table " + _TABLE_NAME);

		DataAccess.cleanUp(_connection);
	}

	@Test
	public void testAlterColumnTypeAlterSize() throws Exception {
		_db.runSQL(_getAlterColumType("notNilColumn", "VARCHAR(200) not null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(200) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToNotNull() throws Exception {
		_db.runSQL(_getAlterColumType("nilColumn", "VARCHAR(75) not null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "nilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToNull() throws Exception {
		_db.runSQL(_getAlterColumType("notNilColumn", "VARCHAR(75) null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeNoChangesNotNull() throws Exception {
		_db.runSQL(_getAlterColumType("notNilColumn", "VARCHAR(75) not null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeNoChangesNull() throws Exception {
		_db.runSQL(_getAlterColumType("nilColumn", "VARCHAR(75) null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "nilColumn", "VARCHAR(75) null"));
	}

	private String _getAlterColumType(String columnName, String newType) {
		StringBundler sb = new StringBundler(6);

		sb.append("alter_column_type ");
		sb.append(_TABLE_NAME);
		sb.append(StringPool.SPACE);
		sb.append(columnName);
		sb.append(StringPool.SPACE);
		sb.append(newType);

		return sb.toString();
	}

	private static final String _TABLE_NAME = "DBTest";

	private Connection _connection;
	private DB _db;
	private DBInspector _dbInspector;

}