/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.DataSourceFactoryUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaImporterProcess {

	public DBSchemaImporterProcess(
			String path, String sourceJDBCURL, String sourcePassword,
			String sourceUser, String targetJDBCURL, String targetPassword,
			String targetUser)
		throws Exception {

		_path = path;
		_sourceJDBCURL = sourceJDBCURL;
		_sourcePassword = sourcePassword;
		_sourceUser = sourceUser;
		_targetJDBCURL = targetJDBCURL;
		_targetPassword = targetPassword;
		_targetUser = targetUser;

		_sourceDataSource = DataSourceFactoryUtil.initDataSource(
			sourceJDBCURL, sourcePassword, sourceUser);

		_targetDataSource = DataSourceFactoryUtil.initDataSource(
			_targetJDBCURL, _targetPassword, _targetUser);
	}

	public String getDataSourceInfos() {
		StringBundler sb = new StringBundler(_dataSourceInfos.size() * 2);

		for (String dataSourceInfo : _dataSourceInfos) {
			sb.append(dataSourceInfo);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	public String getReleaseInfo() throws Exception {
		StringBundler sb = new StringBundler();

		try (Connection connection = _sourceDataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select buildDate, buildNumber, schemaVersion from Release_ " +
					"where servletContextName = 'portal'")) {

			resultSet.next();

			sb.append(StringPool.NEW_LINE);
			sb.append("Portal build number: ");
			sb.append(resultSet.getLong("buildNumber"));
			sb.append(StringPool.NEW_LINE);
			sb.append("Portal schema version: ");
			sb.append(resultSet.getString("schemaVersion"));
		}

		return sb.toString();
	}

	public void run() throws Exception {
		_createTables();

		_copyTables();

		_createIndexes();

		_executorService.shutdownNow();

		_executorService.awaitTermination(10, TimeUnit.SECONDS);
	}

	private <T> Set<T> _asymmetricDifference(
		Collection<T> collection1, Collection<T> collection2) {

		if (collection1.isEmpty()) {
			return Collections.emptySet();
		}

		Set<T> set1 = new HashSet<>(collection1);
		Set<T> set2 = new HashSet<>(collection2);

		Set<T> symmetricDifferenceSet = SetUtil.symmetricDifference(set1, set2);

		symmetricDifferenceSet.removeAll(set2);

		return symmetricDifferenceSet;
	}

	private void _copyTables() throws Exception {
		AutoBatchPreparedStatementUtil.start();

		new DBCopyTablesProcess(
			_sourceDataSource, _targetDataSource
		).run();

		_dataSourceInfos.add(
			0, _getDataSourceInfo(_sourceDataSource, _targetDataSource));

		AutoBatchPreparedStatementUtil.stop();
	}

	private void _createIndexes() throws Exception {
		_runSQLTemplate(
			_targetDataSource, _readFile(new File(_path, "indexes.sql")));
	}

	private void _createTables() throws Exception {
		_runSQLTemplate(
			_targetDataSource, _readFile(new File(_path, "tables.sql")));
	}

	private String _difference(Set<String> set1, Set<String> set2) {
		return StringUtil.merge(
			_asymmetricDifference(set1, set2), StringPool.COMMA_AND_SPACE);
	}

	private String _getDataSourceInfo(
			DataSource sourceDataSource, DataSource targetDataSource)
		throws Exception {

		Set<String> sourceTableNames = _getTableNames(sourceDataSource);
		Set<String> targetTableNames = _getTableNames(targetDataSource);

		return StringUtil.merge(
			new Object[] {
				"Source tables: " + sourceTableNames.size(),
				"Target tables: " + targetTableNames.size(),
				"Missing source tables: " +
					_difference(targetTableNames, sourceTableNames),
				"Missing target tables: " +
					_difference(sourceTableNames, targetTableNames),
				StringPool.NEW_LINE
			},
			StringPool.NEW_LINE);
	}

	private Set<String> _getTableNames(DataSource dataSource) throws Exception {
		Set<String> names = new HashSet<>();

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getTables(
					connection.getCatalog(), connection.getSchema(), null,
					new String[] {"TABLE"})) {

				while (resultSet.next()) {
					names.add(
						StringUtil.toLowerCase(
							resultSet.getString("TABLE_NAME")));
				}
			}
		}

		return names;
	}

	private void _preprocessSQLTemplate(String template) throws Exception {
		template = StringUtil.trim(template);

		if ((template == null) || template.isEmpty()) {
			return;
		}

		if (!template.endsWith(StringPool.SEMICOLON)) {
			template += StringPool.SEMICOLON;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(template))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.isEmpty() || line.startsWith("##")) {
					continue;
				}

				sb.append(line);
				sb.append(StringPool.NEW_LINE);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sb.setIndex(0);

					if (StringUtil.startsWith(sql, "create or replace rule")) {
						_syncFinalSQLs.add(sql);
					}
					else {
						_asyncSQLs.add(sql);
					}
				}
			}
		}
	}

	private String _readFile(File file) throws Exception {
		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private void _runSQLTemplate(DataSource dataSource, String template)
		throws Exception {

		_preprocessSQLTemplate(template);

		List<Future<?>> futures = new ArrayList<>();
		ThrowableCollector throwableCollector = new ThrowableCollector();

		for (String sql : _asyncSQLs) {
			futures.add(
				_executorService.submit(
					() -> {
						try (Connection connection = dataSource.getConnection();
							Statement statement =
								connection.createStatement()) {

							statement.executeUpdate(sql);
						}
						catch (Exception exception) {
							throwableCollector.collect(exception);
						}
					}));
		}

		_asyncSQLs.clear();

		for (Future<?> future : futures) {
			future.get();
		}

		Throwable throwable = throwableCollector.getThrowable();

		if (throwable != null) {
			ReflectionUtil.throwException(throwable);
		}

		for (String sql : _syncFinalSQLs) {
			try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {

				statement.executeUpdate(sql);
			}
		}

		_syncFinalSQLs.clear();
	}

	private final List<String> _asyncSQLs = new ArrayList<>();
	private final List<String> _dataSourceInfos = Collections.synchronizedList(
		new ArrayList<>());
	private final ExecutorService _executorService =
		Executors.newFixedThreadPool(5);
	private final String _path;
	private final DataSource _sourceDataSource;
	private final String _sourceJDBCURL;
	private final String _sourcePassword;
	private final String _sourceUser;
	private final List<String> _syncFinalSQLs = new ArrayList<>();
	private final DataSource _targetDataSource;
	private final String _targetJDBCURL;
	private final String _targetPassword;
	private final String _targetUser;

}