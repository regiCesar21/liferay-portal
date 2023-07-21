/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.upgrade.UpgradeViewCount;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Samuel Trong Tran
 */
@RunWith(Arquillian.class)
public class UpgradeViewCountTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_className = _classNameLocalService.getClassName(
			UpgradeViewCountTest.class.getName());

		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table UpgradeViewCount (primaryKey LONG not null primary " +
				"key, companyId LONG not null, readCount LONG)");

		_db.runSQL("insert into UpgradeViewCount values (1, 2, 3)");
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL(
			"delete from ViewCountEntry where classNameId = " +
				_className.getClassNameId());

		_db.runSQL("drop table UpgradeViewCount");
	}

	@Test
	public void testUpgrade() throws Exception {
		UpgradeViewCount upgradeCTModel = new UpgradeViewCount(
			"UpgradeViewCount", UpgradeViewCountTest.class, "primaryKey",
			"readCount");

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertTrue(
				dbInspector.hasColumn("UpgradeViewCount", "readCount"));
		}

		upgradeCTModel.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select * from ViewCountEntry where companyId = 2 AND ",
					"classNameId = ", _className.getClassNameId(),
					" AND classPK = 1"));
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(3, rs.getLong("viewCount"));

			Assert.assertFalse(rs.next());

			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertFalse(
				dbInspector.hasColumn("UpgradeViewCount", "readCount"));
		}
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private ClassName _className;

	private DB _db;

}