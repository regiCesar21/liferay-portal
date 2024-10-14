/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DataSourceFactoryUtil {

	public static DataSource initDataSource(
			String jdbcURL, String password, String userName)
		throws Exception {

		String driverClassName = "com.mysql.cj.jdbc.Driver";

		if (jdbcURL.indexOf("postgresql") > 0) {
			driverClassName = "org.postgresql.Driver";
		}

		Class.forName(driverClassName);

		HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setIdleTimeout(600000);
		hikariConfig.setJdbcUrl(jdbcURL);
		hikariConfig.setMaxLifetime(0);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(10);
		hikariConfig.setPassword(password);
		hikariConfig.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");
		hikariConfig.setUsername(userName);

		return new HikariDataSource(hikariConfig);
	}

}