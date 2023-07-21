/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc.aop;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
@ProviderType
public interface DynamicDataSourceTargetSource {

	public Operation getOperation();

	public DataSource getReadDataSource();

	public Object getTarget() throws Exception;

	public DataSource getWriteDataSource();

	public Operation popOperation();

	public void pushOperation(Operation operation);

	public void setReadDataSource(DataSource readDataSource);

	public void setWriteDataSource(DataSource writeDataSource);

}