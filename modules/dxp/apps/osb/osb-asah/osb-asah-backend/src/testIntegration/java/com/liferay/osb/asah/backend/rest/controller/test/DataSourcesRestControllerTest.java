/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.rest.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.asah.backend.dto.DataSourceDTO;
import com.liferay.osb.asah.backend.rest.controller.DataSourcesRestController;
import com.liferay.osb.asah.backend.spring.OSBAsahBackendSpringBootApplication;
import com.liferay.osb.asah.common.constants.HeaderConstants;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.Asset;
import com.liferay.osb.asah.common.entity.AuditEvent;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.entity.RunLog;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.repository.AssetRepository;
import com.liferay.osb.asah.common.repository.AuditEventRepository;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.common.repository.RunLogRepository;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;
import com.liferay.osb.asah.test.util.faro.FaroInfoTestUtil;
import com.liferay.osb.asah.test.util.spring.OSBAsahRepositoryTestExecutionListener;
import com.liferay.osb.asah.test.util.spring.OSBAsahSQLTestExecutionListener;
import com.liferay.osb.asah.test.util.spring.OSBAsahSpringExtension;
import com.liferay.osb.asah.test.util.util.RandomTestUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author Rachael Koestartyo
 * @author Vishal Reddy
 */
@ContextConfiguration(classes = OSBAsahBackendSpringBootApplication.class)
@ExtendWith(OSBAsahSpringExtension.class)
@TestExecutionListeners(
	mergeMode = TestExecutionListeners.MergeMode.REPLACE_DEFAULTS,
	value = {
		DependencyInjectionTestExecutionListener.class,
		OSBAsahRepositoryTestExecutionListener.class,
		OSBAsahSQLTestExecutionListener.class
	}
)
@WebMvcTest
public class DataSourcesRestControllerTest {

	@Disabled
	@Test
	public void testDeleteDataSource() throws Exception {
		DataSource dataSource = FaroInfoTestUtil.buildLiferayDataSource();

		JSONObject dataSourceJSONObject = new JSONObject(
			_dataSourcesRestController.postDataSource(
				_objectMapper.convertValue(dataSource, DataSourceDTO.class)));

		dataSource.setId(Long.valueOf(dataSourceJSONObject.getString("id")));

		// TODO Add individual related to dataSource

		Asset asset = _assetRepository.save(
			_objectMapper.convertValue(
				FaroInfoTestUtil.buildAssetJSONObject(
					"Page", dataSource.getId()),
				Asset.class));

		// TODO Add BQEvent

		_dataSourcesRestController.deleteDataSource(
			dataSourceJSONObject.getLong("id"));

		JSONObject updateDataSourceJSONObject = _objectMapper.convertValue(
			_dataSourcesRestController.getDataSourceDTO(
				dataSourceJSONObject.getLong("id")),
			JSONObject.class);

		Assertions.assertTrue(updateDataSourceJSONObject.has("deletionDate"));
		Assertions.assertEquals(
			"IN_PROGRESS_DELETING",
			updateDataSourceJSONObject.getString("state"));

		// TODO Assert BQEvent is not deleted

		Assertions.assertTrue(_assetRepository.existsById(asset.getId()));
		Assertions.assertFalse(
			_dataSourceRepository.existsById(
				dataSourceJSONObject.getLong("id")));

		// TODO Assert individuals do not exist

	}

	@Test
	public void testDuplicateDataSourceName() {
		DataSource dataSource = FaroInfoTestUtil.buildLiferayDataSource(
			"Liferay", RandomTestUtil.randomURL());

		for (int i = 0; i < 4; i++) {
			_dataSourcesRestController.postDataSource(
				_objectMapper.convertValue(dataSource, DataSourceDTO.class));
		}

		JSONObject responseJSONObject = _objectMapper.convertValue(
			_dataSourcesRestController.getDataSourceDTOPageDTO(
				null, 0, 10, null),
			JSONObject.class);

		JSONArray dataSourcesJSONArray = (JSONArray)responseJSONObject.query(
			"/_embedded/data-sources");

		Assertions.assertEquals(4, dataSourcesJSONArray.length());

		Set<String> dataSourceNames = new HashSet<>();

		for (int i = 0; i < dataSourcesJSONArray.length(); i++) {
			JSONObject dataSourceJSONObject =
				dataSourcesJSONArray.getJSONObject(i);

			dataSourceNames.add(dataSourceJSONObject.getString("name"));
		}

		Assertions.assertTrue(dataSourceNames.contains("Liferay"));
		Assertions.assertTrue(dataSourceNames.contains("Liferay (1)"));
		Assertions.assertTrue(dataSourceNames.contains("Liferay (2)"));
		Assertions.assertTrue(dataSourceNames.contains("Liferay (3)"));
	}

	@Test
	public void testGetCSVDataSourceProgress() {
		DataSource csvDataSource = _dataSourceRepository.save(
			FaroInfoTestUtil.buildCSVDataSource(1L));

		// CSV individuals nanite is null

		Assertions.assertEquals(
			"{}",
			_dataSourcesRestController.getProgress(csvDataSource.getId()));

		// CSV individuals nanite started

		RunLog runLog1 = new RunLog();

		runLog1.setContextJSONObject(
			JSONUtil.put(
				"processedOperations", 1
			).put(
				"reprocess", false
			).put(
				"totalOperations", 1
			));
		runLog1.setDataSourceId(csvDataSource.getId());
		runLog1.setDateLogged(DateUtil.newDate());
		runLog1.setNaniteClassName("CSVUsersNanite");
		runLog1.setStatus("STARTED");

		_runLogRepository.save(runLog1);

		JSONObject progressJSONObject = new JSONObject(
			_dataSourcesRestController.getProgress(csvDataSource.getId()));

		Assertions.assertEquals(
			1, progressJSONObject.getInt("processedOperations"));
		Assertions.assertEquals(
			"IN_PROGRESS", progressJSONObject.getString("status"));

		// CSV individuals nanite failed

		Date loggedDate = DateUtil.newDate();

		RunLog runLog2 = new RunLog();

		runLog2.setContextJSONObject(
			JSONUtil.put(
				"processedOperations", 1
			).put(
				"reprocess", false
			).put(
				"totalOperations", 1
			));
		runLog2.setDataSourceId(csvDataSource.getId());
		runLog2.setDateLogged(loggedDate);
		runLog2.setNaniteClassName("CSVUsersNanite");
		runLog2.setStatus("FAILED");

		_runLogRepository.save(runLog2);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"dateRecorded", DateUtil.toUTCString(loggedDate)
			).put(
				"status", "FAILED"
			),
			new JSONObject(
				_dataSourcesRestController.getProgress(csvDataSource.getId())),
			true);
	}

	@Test
	public void testGetDataSources() {
		_dataSourcesRestController.postDataSource(
			_objectMapper.convertValue(
				FaroInfoTestUtil.buildLiferayDataSource(),
				DataSourceDTO.class));

		JSONObject dataSourcesJSONObject = _objectMapper.convertValue(
			_dataSourcesRestController.getDataSourceDTOPageDTO(
				null, 0, 20, null),
			JSONObject.class);

		JSONObject embeddedJSONObject = dataSourcesJSONObject.getJSONObject(
			"_embedded");

		JSONArray dataSourcesJSONArray = embeddedJSONObject.getJSONArray(
			"data-sources");

		Assertions.assertEquals(1, dataSourcesJSONArray.length());

		JSONObject dataSourceJSONObject = dataSourcesJSONArray.getJSONObject(0);

		Assertions.assertFalse(
			dataSourceJSONObject.has("faroBackendSecuritySignature"));
	}

	@Test
	public void testPatchDataSource() {
		DataSourceDTO dataSourceDTO = new DataSourceDTO(
			_dataSourceRepository.save(
				FaroInfoTestUtil.buildLiferayDataSource()));

		dataSourceDTO.setName("Liferay DXP");

		DataSourceDTO actualDataSourceDTO =
			_dataSourcesRestController.patchDataSource(
				dataSourceDTO.getId(), dataSourceDTO);

		Assertions.assertNotEquals(
			dataSourceDTO.getModifiedDate(),
			actualDataSourceDTO.getModifiedDate());

		actualDataSourceDTO.setModifiedDate(null);

		dataSourceDTO.setModifiedDate(null);

		JSONAssert.assertEquals(
			_objectMapper.convertValue(dataSourceDTO, JSONObject.class),
			_objectMapper.convertValue(actualDataSourceDTO, JSONObject.class),
			false);
	}

	@Test
	public void testScheduleDataSourceDeletion() throws Exception {

		// Schedule deletion

		JSONObject dataSourceJSONObject = new JSONObject(
			_dataSourcesRestController.postDataSource(
				_objectMapper.convertValue(
					FaroInfoTestUtil.buildLiferayDataSource(),
					DataSourceDTO.class)));

		MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
			MockMvcRequestBuilders.delete(
				"/data-sources/" + dataSourceJSONObject.getString("id"));

		mockHttpServletRequestBuilder.header(
			HeaderConstants.AUTHOR_USER_ID, "1001");
		mockHttpServletRequestBuilder.header(
			HeaderConstants.AUTHOR_USER_NAME, "Caetano Veloso");
		mockHttpServletRequestBuilder.header(
			HeaderConstants.PROJECT_ID, "test");

		_mockMvc.perform(mockHttpServletRequestBuilder);

		// Assert data source state

		ProjectIdThreadLocal.setProjectId("test");

		Optional<DataSource> dataSourceOptional =
			_dataSourceRepository.findById(
				Long.valueOf(dataSourceJSONObject.getString("id")));

		Assertions.assertTrue(dataSourceOptional.isPresent());

		DataSource dataSource = dataSourceOptional.get();

		Assertions.assertEquals("IN_PROGRESS_DELETING", dataSource.getState());

		// Assert audit events

		List<AuditEvent> auditEvents = _auditEventRepository.findByUserId(
			PageRequest.ofSize(20), "1001");

		Assertions.assertEquals(1, auditEvents.size());

		AuditEvent auditEvent = auditEvents.get(0);

		Assertions.assertEquals(
			AuditEvent.Type.DATA_SOURCE_DELETE_REQUEST, auditEvent.getType());
		Assertions.assertEquals("1001", auditEvent.getUserId());
		Assertions.assertEquals("Caetano Veloso", auditEvent.getUserName());
	}

	@Autowired
	private AssetRepository _assetRepository;

	@Autowired
	private AuditEventRepository _auditEventRepository;

	@Autowired
	private DataSourceRepository _dataSourceRepository;

	@Autowired
	private DataSourcesRestController _dataSourcesRestController;

	@Autowired
	private MockMvc _mockMvc;

	@Autowired
	private ObjectMapper _objectMapper;

	@Autowired
	private RunLogRepository _runLogRepository;

}