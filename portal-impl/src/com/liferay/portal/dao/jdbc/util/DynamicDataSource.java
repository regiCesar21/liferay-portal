/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.SpringHibernateThreadLocalUtil;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @author Dante Wang
 */
public class DynamicDataSource implements DataSource {

	public DynamicDataSource(
		DataSource readDataSource, DataSource writeDataSource) {

		_readDataSource = readDataSource;
		_writeDataSource = writeDataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return _getDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String userName, String password)
		throws SQLException {

		return _getDataSource().getConnection(userName, password);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return _getDataSource().getLoginTimeout();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return _getDataSource().getLogWriter();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return _getDataSource().getParentLogger();
	}

	public DataSource getReadDataSource() {
		return _readDataSource;
	}

	public DataSource getWriteDataSource() {
		return _writeDataSource;
	}

	@Override
	public boolean isWrapperFor(Class<?> clazz) throws SQLException {
		return _getDataSource().isWrapperFor(clazz);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		_getDataSource().setLoginTimeout(seconds);
	}

	@Override
	public void setLogWriter(PrintWriter printWriter) throws SQLException {
		_getDataSource().setLogWriter(printWriter);
	}

	@Override
	public <T> T unwrap(Class<T> clazz) throws SQLException {
		return _getDataSource().unwrap(clazz);
	}

	private DataSource _getDataSource() {
		if (SpringHibernateThreadLocalUtil.isCurrentTransactionReadOnly()) {
			if (_log.isTraceEnabled()) {
				_log.trace("Returning read data source");
			}

			return _readDataSource;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Returning write data source");
		}

		return _writeDataSource;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicDataSource.class);

	private final DataSource _readDataSource;
	private final DataSource _writeDataSource;

}