/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.rest.controller.api.data.source.v1.DataControlTasksRestController;
import com.liferay.osb.asah.common.entity.DataControlTask;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.model.DataControlTaskStatus;
import com.liferay.osb.asah.common.repository.DataControlTaskRepository;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Marcos Martins
 */
public class DataControlTasksRestControllerTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@Test
	public void testPostDataControlTask() {
		ResponseEntity responseEntity =
			_dataControlTasksRestController.createDataControlTask(
				JSONUtil.put(
					"emailAddresses", JSONUtil.put("test@liferay.com")
				).put(
					"types", JSONUtil.put("SUPPRESS")
				).toString());

		Assertions.assertEquals(
			HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

		List<DataControlTask> dataControlTasks =
			_dataControlTaskRepository.getDataControlTasks(
				Collections.singletonList(
					DataControlTaskStatus.PENDING.toString()));

		Assertions.assertEquals(
			1, dataControlTasks.size(), dataControlTasks.toString());

		DataControlTask dataControlTask = dataControlTasks.get(0);

		Assertions.assertEquals(
			dataControlTask.getType(), DataControlTask.Type.SUPPRESS);
	}

	@Autowired
	private DataControlTaskRepository _dataControlTaskRepository;

	@Autowired
	private DataControlTasksRestController _dataControlTasksRestController;

}