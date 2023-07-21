/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.aop;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.aop.DynamicDataSourceTargetSource;
import com.liferay.portal.kernel.dao.jdbc.aop.Operation;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Deque;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class DefaultDynamicDataSourceTargetSource
	implements DynamicDataSourceTargetSource, TargetSource {

	@Override
	public Operation getOperation() {
		Deque<Operation> operations = _operations.get();

		Operation operation = operations.peek();

		if (operation == null) {
			operation = Operation.WRITE;
		}

		return operation;
	}

	@Override
	public DataSource getReadDataSource() {
		return _readDataSource;
	}

	@Override
	public Object getTarget() {
		Operation operationType = getOperation();

		if (operationType == Operation.READ) {
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

	@Override
	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	@Override
	public DataSource getWriteDataSource() {
		return _writeDataSource;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public Operation popOperation() {
		Deque<Operation> operations = _operations.get();

		return operations.pop();
	}

	@Override
	public void pushOperation(Operation operation) {
		Deque<Operation> operations = _operations.get();

		operations.push(operation);
	}

	@Override
	public void releaseTarget(Object target) throws Exception {
	}

	@Override
	public void setReadDataSource(DataSource readDataSource) {
		_readDataSource = readDataSource;
	}

	@Override
	public void setWriteDataSource(DataSource writeDataSource) {
		_writeDataSource = writeDataSource;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultDynamicDataSourceTargetSource.class);

	private static final ThreadLocal<Deque<Operation>> _operations =
		new CentralizedThreadLocal<>(
			DefaultDynamicDataSourceTargetSource.class + "._operations",
			LinkedList::new);

	private DataSource _readDataSource;
	private DataSource _writeDataSource;

}