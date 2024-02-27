/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.internal.test.BlogPosting;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class BatchEngineImportTaskServiceTest
	extends BaseBatchEngineTaskServiceTest {

	@Before
	public void setUp() throws Exception {
		_companyAdminBatchEngineImportTask = _addBatchEngineImportTask(
			companyAdminUser);
		_defaultCompanyBatchEngineImportTask = _addBatchEngineImportTask(
			omniadminUser);
		_userBatchEngineImportTask = _addBatchEngineImportTask(user);
	}

	@Test
	public void testAddBatchEngineImportTask() throws Exception {
		UserTestUtil.setUser(user);

		try {
			_batchEngineImportTaskService.addBatchEngineImportTask(
				defaultCompany.getCompanyId(), user.getUserId(), 10, null,
				BlogPosting.class.getName(), new byte[0], "JSON",
				BatchEngineTaskExecuteStatus.INITIAL.name(), null,
				BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);

			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}

		_batchEngineImportTaskService.addBatchEngineImportTask(
			company.getCompanyId(), user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	@Test
	public void testGetBatchEngineImportTask() throws Exception {

		// Company admin

		UserTestUtil.setUser(companyAdminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_companyAdminBatchEngineImportTask.getBatchEngineImportTaskId());

		try {
			_batchEngineImportTaskService.getBatchEngineImportTask(
				_defaultCompanyBatchEngineImportTask.
					getBatchEngineImportTaskId());
			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_userBatchEngineImportTask.getBatchEngineImportTaskId());

		// Omniadmin

		UserTestUtil.setUser(omniadminUser);

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_companyAdminBatchEngineImportTask.getBatchEngineImportTaskId());
		_batchEngineImportTaskService.getBatchEngineImportTask(
			_defaultCompanyBatchEngineImportTask.getBatchEngineImportTaskId());
		_batchEngineImportTaskService.getBatchEngineImportTask(
			_userBatchEngineImportTask.getBatchEngineImportTaskId());

		// User

		UserTestUtil.setUser(user);

		try {
			_batchEngineImportTaskService.getBatchEngineImportTask(
				_companyAdminBatchEngineImportTask.
					getBatchEngineImportTaskId());
			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}

		try {
			_batchEngineImportTaskService.getBatchEngineImportTask(
				_defaultCompanyBatchEngineImportTask.
					getBatchEngineImportTaskId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}

		_batchEngineImportTaskService.getBatchEngineImportTask(
			_userBatchEngineImportTask.getBatchEngineImportTaskId());
	}

	private BatchEngineImportTask _addBatchEngineImportTask(User user)
		throws Exception {

		return _batchEngineImportTaskLocalService.addBatchEngineImportTask(
			user.getCompanyId(), user.getUserId(), 10, null,
			BlogPosting.class.getName(), new byte[0], "JSON",
			BatchEngineTaskExecuteStatus.INITIAL.name(), null,
			BatchEngineTaskOperation.CREATE.name(), new HashMap<>(), null);
	}

	@Inject
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Inject
	private BatchEngineImportTaskService _batchEngineImportTaskService;

	private BatchEngineImportTask _companyAdminBatchEngineImportTask;
	private BatchEngineImportTask _defaultCompanyBatchEngineImportTask;
	private BatchEngineImportTask _userBatchEngineImportTask;

}