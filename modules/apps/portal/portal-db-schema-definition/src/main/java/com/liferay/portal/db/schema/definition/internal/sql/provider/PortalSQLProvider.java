/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.sql.provider;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.DBManagerImpl;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactory;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Mariano Álvaro Sáiz
 */
public class PortalSQLProvider implements SQLProvider {

	public PortalSQLProvider(DBType dbType) throws Exception {
		_db = _getDB(dbType);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		_appendPortalSQL();

		_appendModulesSQL();

		_appendPluginsSQL();
	}

	@Override
	public String getIndexesSQL() {
		return _indexesSQLSB.toString();
	}

	@Override
	public String getTablesSQL() {
		return _tablesSQLSB.toString();
	}

	private void _appendModulesSQL() throws Exception {
		for (Bundle bundle : _bundleContext.getBundles()) {
			if (_QUARTZ_BUNDLE_SYMBOLIC_NAME.equals(bundle.getSymbolicName())) {
				String quartzSQL = StringUtil.removeSubstring(
					_getSQLTemplateString(bundle, "quartz-tables.sql"),
					"COMMIT_TRANSACTION;");

				_appendSQL(_getIndexesSQL(quartzSQL), _getTablesSQL(quartzSQL));

				continue;
			}

			if (!_isLiferayServiceBundle(bundle)) {
				continue;
			}

			_appendSQL(
				_getSQLTemplateString(bundle, "indexes.sql"),
				_getSQLTemplateString(bundle, "tables.sql"));
		}

		_appendSQL(
			null,
			"create table Configuration_ (configurationId VARCHAR(255) not " +
				"null primary key, dictionary TEXT);");
	}

	private void _appendPluginsSQL() throws Exception {
		Set<String> contextNames = new HashSet<>();

		for (PluginPackage pluginPackage :
				PluginPackageUtil.getInstalledPluginPackages()) {

			String contextName = pluginPackage.getArtifactId();

			if (!contextNames.add(contextName)) {
				continue;
			}

			_appendSQL(
				_read(contextName, "/WEB-INF/sql/indexes.sql"),
				_read(contextName, "/WEB-INF/sql/tables.sql"));
		}
	}

	private void _appendPortalSQL() throws Exception {
		_appendSQL(
			StringUtil.read(
				PortalClassLoaderUtil.getClassLoader(),
				"com/liferay/portal/tools/sql/dependencies/indexes.sql"),
			StringUtil.read(
				PortalClassLoaderUtil.getClassLoader(),
				"com/liferay/portal/tools/sql/dependencies/portal-tables.sql"));
	}

	private void _appendSQL(String indexesSQL, String tablesSQL)
		throws Exception {

		if (Validator.isNotNull(indexesSQL)) {
			_indexesSQLSB.append(_db.buildSQL(indexesSQL));
		}

		if (Validator.isNotNull(tablesSQL)) {
			tablesSQL = _db.buildSQL(tablesSQL);

			if (_db.getDBType() == DBType.POSTGRESQL) {
				tablesSQL = _processRules(tablesSQL);
			}

			_tablesSQLSB.append(tablesSQL);
		}
	}

	private DB _getDB(DBType dbType) {
		ServiceLoader<DBFactory> serviceLoader = ServiceLoader.load(
			DBFactory.class, DBManagerImpl.class.getClassLoader());

		for (DBFactory dbFactory : serviceLoader) {
			if (dbFactory.getDBType() == dbType) {
				return dbFactory.create(0, 0);
			}
		}

		throw new IllegalArgumentException("Database type " + dbType);
	}

	private String _getIndexesSQL(String sql) {
		String[] lines = StringUtil.splitLines(sql);

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (StringUtil.startsWith(line, "create index") ||
				StringUtil.startsWith(line, "create unique index")) {

				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private String _getSQLTemplateString(Bundle bundle, String templateName)
		throws Exception {

		URL resource = bundle.getResource("/META-INF/sql/" + templateName);

		if (resource == null) {
			return null;
		}

		try (InputStream inputStream = resource.openStream()) {
			return StringUtil.read(inputStream);
		}
	}

	private String _getTablesSQL(String sql) {
		String[] lines = StringUtil.splitLines(sql);

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (!StringUtil.startsWith(line, "create index") &&
				!StringUtil.startsWith(line, "create unique index")) {

				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private boolean _isLiferayServiceBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getBoolean(headers.get("Liferay-Service"));
	}

	private String _processRules(String sql) {
		String[] lines = StringUtil.splitLines(sql);

		StringBundler sb = new StringBundler();

		for (String line : lines) {
			if (StringUtil.startsWith(line, "create or replace rule")) {
				line = StringUtil.replace(line, ';', ";\n");
			}

			sb.append(line);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _read(String contextName, String path) throws Exception {
		ServletContext servletContext = ServletContextPool.get(contextName);

		if (servletContext == null) {
			return null;
		}

		InputStream inputStream = servletContext.getResourceAsStream(path);

		if (inputStream == null) {
			return null;
		}

		return StringUtil.read(inputStream);
	}

	private static final String _QUARTZ_BUNDLE_SYMBOLIC_NAME =
		"com.liferay.portal.scheduler.quartz";

	private final BundleContext _bundleContext;
	private final DB _db;
	private final StringBundler _indexesSQLSB = new StringBundler();
	private final StringBundler _tablesSQLSB = new StringBundler();

}