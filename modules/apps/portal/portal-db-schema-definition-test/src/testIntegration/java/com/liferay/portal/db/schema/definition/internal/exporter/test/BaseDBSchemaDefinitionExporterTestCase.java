/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.exporter.test;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.db.schema.definition.internal.test.util.ConfigurationTestUtil;
import com.liferay.portal.db.schema.definition.internal.test.util.DatabaseTestUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.File;

import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.cm.PersistenceManager;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.Assert;
import org.junit.Assume;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mariano Álvaro Sáiz
 */
public abstract class BaseDBSchemaDefinitionExporterTestCase {

	protected static void assumeDB() {
		DB db = DBManagerUtil.getDB();

		Assume.assumeTrue(
			(db.getDBType() == DBType.MYSQL) ||
			(db.getDBType() == DBType.POSTGRESQL));
	}

	protected static void setUpClassBaseDBSchemaDefinitionExporterTestCase()
		throws Exception {

		DB db = DBManagerUtil.getDB();

		databaseType = String.valueOf(db.getDBType());

		folder = FileUtil.createTempFolder();
	}

	protected static void tearDownClassBaseDBSchemaDefinitionExporterTestCase()
		throws Exception {

		Files.deleteIfExists(ConfigurationTestUtil.getConfigurationPath(PID));

		FileUtil.deltree(folder);
	}

	protected void assertIndexes(
			DataSource dataSource, DataSource copyDataSource)
		throws Exception {

		List<String> copyIndexColumnNames =
			DatabaseTestUtil.getIndexColumnNames(copyDataSource);
		List<String> indexColumnNames = DatabaseTestUtil.getIndexColumnNames(
			dataSource);

		Assert.assertEquals(
			StringUtils.difference(
				copyIndexColumnNames.toString(), indexColumnNames.toString()),
			indexColumnNames.size(), copyIndexColumnNames.size());

		for (int i = 0; i < indexColumnNames.size(); i++) {
			Assert.assertEquals(
				indexColumnNames.get(i), copyIndexColumnNames.get(i));
		}
	}

	protected void assertTables(
			DataSource dataSource, DataSource copyDataSource)
		throws Exception {

		List<String> copyTableColumnNames =
			DatabaseTestUtil.getTableColumnNames(copyDataSource);
		List<String> tableColumnNames = DatabaseTestUtil.getTableColumnNames(
			dataSource);

		Assert.assertEquals(
			StringUtils.difference(
				copyTableColumnNames.toString(), tableColumnNames.toString()),
			tableColumnNames.size(), copyTableColumnNames.size());

		for (int i = 0; i < tableColumnNames.size(); i++) {
			Assert.assertEquals(
				tableColumnNames.get(i), copyTableColumnNames.get(i));
		}
	}

	protected String getReportContent() throws Exception {
		ConfigurationTestUtil.deployConfiguration(
			configurationAdmin, databaseType, folder.getAbsolutePath(), PID);

		return FileUtil.read(
			new File(folder, "db_schema_definition_export_report.txt"));
	}

	protected void testExportImportDBSchemaDefinition(
			UnsafeRunnable<Exception> runnable)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.db.schema.definition.internal." +
						"exporter.DBSchemaDefinitionExporter",
					Level.INFO)) {

			ConfigurationTestUtil.deployConfiguration(
				configurationAdmin, databaseType, folder.getAbsolutePath(),
				PID);

			runnable.run();

			Assert.assertFalse(
				Files.exists(ConfigurationTestUtil.getConfigurationPath(PID)));
			Assert.assertNull(
				configurationAdmin.listConfigurations(
					"(service.pid=" + PID + ")"));
			Assert.assertNull(
				ReflectionTestUtil.invoke(
					_persistenceManager, "getDictionary",
					new Class<?>[] {String.class}, PID));

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			Assert.assertEquals(
				loggingEvents.toString(), 2, loggingEvents.size());

			List<Object> logMessages = new ArrayList<>();

			for (LoggingEvent loggingEvent : loggingEvents) {
				logMessages.add(loggingEvent.getMessage());
			}

			Assert.assertEquals(
				"Start database schema definition export", logMessages.get(0));
			Assert.assertEquals(
				"Finished database schema definition export to " +
					folder.getAbsolutePath(),
				logMessages.get(1));
		}
	}

	protected static final String COPY_DB_SCHEMA_NAME = "testschema";

	protected static final String PID =
		"com.liferay.portal.db.schema.definition.internal.configuration." +
			"DBSchemaDefinitionExporterConfiguration";

	protected static String databaseType;
	protected static File folder;

	@Inject
	protected ConfigurationAdmin configurationAdmin;

	@Inject
	private PersistenceManager _persistenceManager;

}