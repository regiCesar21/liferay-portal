/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetFieldMappingsResponse.FieldMappingMetadata;

import org.junit.Assert;

/**
 * @author Artur Aquino
 * @author André de Oliveira
 */
public class FieldMappingAssert {

	public static void assertAnalyzer(
			String expectedValue, String field, String type, String index,
			IndicesClient indicesClient)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, "analyzer", field, type, index, indicesClient);
	}

	public static void assertFieldMappingMetadata(
			final String expectedValue, final String key, final String field,
			final String type, final String index,
			final IndicesClient indicesClient)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> doAssertFieldMappingMetadata(
				expectedValue, key, field, type, index, indicesClient));
	}

	public static void assertType(
			String expectedValue, String field, String type, String index,
			IndicesClient indicesClient)
		throws Exception {

		assertFieldMappingMetadata(
			expectedValue, "type", field, type, index, indicesClient);
	}

	protected static void doAssertFieldMappingMetadata(
		String expectedValue, String key, String field, String type,
		String index, IndicesClient indicesClient) {

		FieldMappingMetadata fieldMappingMetadata = getFieldMapping(
			field, type, index, indicesClient);

		String value = getFieldMappingMetadataValue(
			fieldMappingMetadata, field, key);

		Assert.assertEquals(expectedValue, value);
	}

	protected static FieldMappingMetadata getFieldMapping(
		String field, String type, String index, IndicesClient indicesClient) {

		GetFieldMappingsRequest getFieldMappingsRequest =
			new GetFieldMappingsRequest();

		getFieldMappingsRequest.fields(field);
		getFieldMappingsRequest.indices(index);

		try {
			GetFieldMappingsResponse getFieldMappingsResponse =
				indicesClient.getFieldMapping(
					getFieldMappingsRequest, RequestOptions.DEFAULT);

			return getFieldMappingsResponse.fieldMappings(index, field);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected static String getFieldMappingMetadataValue(
		FieldMappingMetadata fieldMappingMetadata, String field, String key) {

		Map<String, Object> mappings = fieldMappingMetadata.sourceAsMap();

		Map<String, Object> mapping = (Map<String, Object>)mappings.get(field);

		return (String)mapping.get(key);
	}

}