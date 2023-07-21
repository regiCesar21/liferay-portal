/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = GetFieldMappingIndexRequestExecutor.class
)
public class GetFieldMappingIndexRequestExecutorImpl
	implements GetFieldMappingIndexRequestExecutor {

	@Override
	public GetFieldMappingIndexResponse execute(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingsRequest getFieldMappingsRequest =
			createGetFieldMappingsRequest(getFieldMappingIndexRequest);

		GetFieldMappingsResponse getFieldMappingsResponse =
			getGetFieldMappingsResponse(
				getFieldMappingsRequest, getFieldMappingIndexRequest);

		Map
			<String,
			 Map
				 <String,
				  Map<String, GetFieldMappingsResponse.FieldMappingMetadata>>>
					mappings = getFieldMappingsResponse.mappings();

		Map<String, String> fieldMappings = new HashMap<>();

		for (String indexName : getFieldMappingIndexRequest.getIndexNames()) {
			Map
				<String,
				 Map<String, GetFieldMappingsResponse.FieldMappingMetadata>>
					map1 = mappings.get(indexName);

			Map<String, GetFieldMappingsResponse.FieldMappingMetadata> map2 =
				map1.get(getFieldMappingIndexRequest.getMappingName());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (String fieldName : getFieldMappingIndexRequest.getFields()) {
				GetFieldMappingsResponse.FieldMappingMetadata
					fieldMappingMetadata = map2.get(fieldName);

				Map<String, Object> source = fieldMappingMetadata.sourceAsMap();

				jsonObject.put(fieldName, source);
			}

			fieldMappings.put(indexName, jsonObject.toString());
		}

		return new GetFieldMappingIndexResponse(fieldMappings);
	}

	protected GetFieldMappingsRequest createGetFieldMappingsRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingsRequest getFieldMappingsRequest =
			new GetFieldMappingsRequest();

		getFieldMappingsRequest.fields(getFieldMappingIndexRequest.getFields());
		getFieldMappingsRequest.indices(
			getFieldMappingIndexRequest.getIndexNames());
		getFieldMappingsRequest.types(
			getFieldMappingIndexRequest.getMappingName());

		return getFieldMappingsRequest;
	}

	protected GetFieldMappingsResponse getGetFieldMappingsResponse(
		GetFieldMappingsRequest getFieldMappingsRequest,
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getFieldMappingIndexRequest.getConnectionId(),
				getFieldMappingIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.getFieldMapping(
				getFieldMappingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}