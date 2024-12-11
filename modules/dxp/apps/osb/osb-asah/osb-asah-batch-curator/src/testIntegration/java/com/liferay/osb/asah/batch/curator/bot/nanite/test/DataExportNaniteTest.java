/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.batch.curator.bot.nanite.test;

import com.liferay.osb.asah.batch.curator.bot.nanite.DataExportNanite;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.DataExportTask;
import com.liferay.osb.asah.common.repository.DataExportTaskRepository;
import com.liferay.osb.asah.test.util.spring.OSBAsahRepositoryTestExecutionListener;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Ivica Cardic
 */
@TestExecutionListeners(
	mergeMode = TestExecutionListeners.MergeMode.REPLACE_DEFAULTS,
	value = {
		DependencyInjectionTestExecutionListener.class,
		OSBAsahRepositoryTestExecutionListener.class
	}
)
public class DataExportNaniteTest extends BaseNaniteTestCase {

	@AfterEach
	public void tearDown() {
		_dataExportTaskRepository.deleteAll();
	}

	@Test
	public void testRun() throws Exception {
		DataExportTask dataExportTask = _createDataExportTask(
			DateUtil.toUTCDate("2024-04-01T00:00:00.000Z"), 100L,
			DateUtil.toUTCDate("2024-04-28T23:23:59.000Z"));

		_dataExportTaskRepository.save(dataExportTask);

		_dataExportNanite.run(null);

		Optional<DataExportTask> dataExportTaskOptional =
			_dataExportTaskRepository.findById(100L);

		dataExportTask = dataExportTaskOptional.get();

		Assertions.assertEquals(
			DataExportTask.Status.COMPLETED, dataExportTask.getStatus());
	}

	private DataExportTask _createDataExportTask(
		Date fromDate, long id, Date toDate) {

		DataExportTask dataExportTask = new DataExportTask();

		dataExportTask.setFromDate(fromDate);
		dataExportTask.setId(id);
		dataExportTask.setIsNew(true);
		dataExportTask.setStatus(DataExportTask.Status.PENDING);
		dataExportTask.setToDate(toDate);
		dataExportTask.setType(DataExportTask.Type.SEGMENT);

		return dataExportTask;
	}

	@Autowired
	private DataExportNanite _dataExportNanite;

	@Autowired
	private DataExportTaskRepository _dataExportTaskRepository;

}