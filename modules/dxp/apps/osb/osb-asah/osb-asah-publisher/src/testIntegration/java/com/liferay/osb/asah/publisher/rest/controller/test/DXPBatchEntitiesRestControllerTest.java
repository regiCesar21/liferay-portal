/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.rest.controller.test;

import com.liferay.osb.asah.common.constants.HeaderConstants;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.common.storage.GoogleStorage;
import com.liferay.osb.asah.common.zip.ZipFileBuilder;
import com.liferay.osb.asah.publisher.OSBAsahPublisherSpringTestContext;
import com.liferay.osb.asah.test.util.util.RandomTestUtil;

import java.io.File;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Riccardo Ferrari
 */
@TestPropertySource(
	properties = "osb.asah.dxp.batch.entities.storage.path=/tmp"
)
public class DXPBatchEntitiesRestControllerTest
	implements OSBAsahPublisherSpringTestContext {

	@BeforeEach
	public void setUp() {
		DataSource dataSource1 = new DataSource("Liferay Italy");

		dataSource1.setContactsSelected(true);
		dataSource1.setCredentialType("Token Authentication");
		dataSource1.setFaroBackendSecuritySignature(
			"faroBackendSecuritySignature");
		dataSource1.setId(123L);
		dataSource1.setIsNew(Boolean.TRUE);
		dataSource1.setProviderType("LIFERAY");
		dataSource1.setState("READY");
		dataSource1.setStatus("STARTED");
		dataSource1.setURL("");

		_dataSource1 = _dataSourceRepository.save(dataSource1);

		DataSource dataSource2 = new DataSource("Liferay Brazil");

		dataSource2.setCredentialType("Token Authentication");
		dataSource2.setFaroBackendSecuritySignature(
			"faroBackendSecuritySignature");
		dataSource2.setId(456L);
		dataSource2.setIsNew(Boolean.TRUE);
		dataSource2.setProviderType("LIFERAY");
		dataSource2.setState("READY");
		dataSource2.setStatus("STARTED");
		dataSource2.setURL("");

		_dataSource2 = _dataSourceRepository.save(dataSource2);
	}

	@Test
	public void testGetNoContent() {
		ResponseEntity<Resource> responseEntity = _exchange(_getHttpHeaders());

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(204)
		);

		Assertions.assertThat(
			responseEntity.getBody()
		).isNull();
	}

	@Test
	public void testGetStatusCode200() throws Exception {
		Mockito.when(
			_googleStorage.readSparkJobResult(
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.any(Date.class),
				ArgumentMatchers.anyString())
		).thenReturn(
			File.createTempFile(RandomTestUtil.randomString(), null)
		);

		ResponseEntity<Resource> responseEntity = _exchange(_getHttpHeaders());

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(200)
		);
	}

	@Test
	public void testGetStatusCode400() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add("If-Modified-Since", _getModifiedSince());

		ResponseEntity<Resource> responseEntity = _exchange(httpHeaders);

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(400)
		);
	}

	@Test
	public void testGetWithInvalidIfModifiedSince() throws Exception {
		Mockito.when(
			_googleStorage.readSparkJobResult(
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.isNull(),
				ArgumentMatchers.anyString())
		).thenReturn(
			File.createTempFile(RandomTestUtil.randomString(), null)
		);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(HeaderConstants.DATA_SOURCE_ID, "test-data-source-id");
		httpHeaders.add(HeaderConstants.PROJECT_ID, "test");
		httpHeaders.add("If-Modified-Since", DateUtil.newDateString());

		ResponseEntity<Resource> responseEntity = _exchange(httpHeaders);

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(200)
		);
	}

	@Test
	public void testGetWithNullIfModifiedSince() throws Exception {
		Mockito.when(
			_googleStorage.readSparkJobResult(
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.isNull(),
				ArgumentMatchers.anyString())
		).thenReturn(
			File.createTempFile(RandomTestUtil.randomString(), null)
		);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(HeaderConstants.DATA_SOURCE_ID, "test-data-source-id");
		httpHeaders.add(HeaderConstants.PROJECT_ID, "test");

		ResponseEntity<Resource> responseEntity = _exchange(httpHeaders);

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(200)
		);
	}

	@Test
	public void testPostContactsSelected() throws Exception {
		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

		multipartBodyBuilder.part(
			"file", _getFileSystemResource()
		).filename(
			"com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity"
		);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(
			HeaderConstants.DATA_SOURCE_ID,
			String.valueOf(_dataSource1.getId()));
		httpHeaders.add(HeaderConstants.PROJECT_ID, "test");
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		ResponseEntity<Resource> responseEntity = _testRestTemplate.exchange(
			"/dxp-batch-entities", HttpMethod.POST,
			new HttpEntity<>(multipartBodyBuilder.build(), httpHeaders),
			Resource.class);

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(200)
		);
	}

	@Test
	public void testPostNoContactsSelected() throws Exception {
		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

		multipartBodyBuilder.part(
			"file", _getFileSystemResource()
		).filename(
			"com.liferay.analytics.dxp.entity.rest.dto.v1_0.DXPEntity"
		);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(
			HeaderConstants.DATA_SOURCE_ID,
			String.valueOf(_dataSource2.getId()));
		httpHeaders.add(HeaderConstants.PROJECT_ID, "test");
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		ResponseEntity<Resource> responseEntity = _testRestTemplate.exchange(
			"/dxp-batch-entities", HttpMethod.POST,
			new HttpEntity<>(multipartBodyBuilder.build(), httpHeaders),
			Resource.class);

		Assertions.assertThat(
			responseEntity.getStatusCode()
		).isEqualTo(
			HttpStatus.valueOf(400)
		);
	}

	private ResponseEntity<Resource> _exchange(HttpHeaders httpHeaders) {
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(
			String.format("http://localhost:%s/dxp-batch-entities", _serverPort)
		).queryParam(
			"resourceName", RandomTestUtil.randomString()
		).build();

		return _testRestTemplate.exchange(
			uriComponents.toString(), HttpMethod.GET,
			new HttpEntity<>(null, httpHeaders), Resource.class);
	}

	private FileSystemResource _getFileSystemResource() throws Exception {
		ZipFileBuilder zipFileBuilder = new ZipFileBuilder("export", ".zip");

		zipFileBuilder.addToZip(
			"export.json",
			zipOutputStream -> {
				for (int i = 0; i < 5; i++) {
					String jsonString = String.valueOf(
						JSONUtil.put(
							"key1", RandomTestUtil.randomString()
						).put(
							"key2", RandomTestUtil.randomString()
						).put(
							"key3", RandomTestUtil.randomString()
						));

					zipOutputStream.write(jsonString.getBytes());

					zipOutputStream.write("\n".getBytes());
				}
			});

		return new FileSystemResource(zipFileBuilder.build());
	}

	private HttpHeaders _getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(HeaderConstants.DATA_SOURCE_ID, "test-data-source-id");
		httpHeaders.add(HeaderConstants.PROJECT_ID, "test");
		httpHeaders.add("If-Modified-Since", _getModifiedSince());

		return httpHeaders;
	}

	private String _getModifiedSince() {
		LocalDateTime localDateTime = LocalDateTime.now();

		Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

		return _dateTimeFormatter.format(
			instant.atZone(ZoneId.systemDefault()));
	}

	private static final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

	private DataSource _dataSource1;
	private DataSource _dataSource2;

	@Autowired
	private DataSourceRepository _dataSourceRepository;

	@MockBean
	private GoogleStorage _googleStorage;

	@LocalServerPort
	private int _serverPort;

	@Autowired
	private TestRestTemplate _testRestTemplate;

}