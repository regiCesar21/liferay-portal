/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;

import java.io.IOException;

import java.util.Map;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = UpdateByQueryDocumentRequestExecutor.class
)
public class UpdateByQueryDocumentRequestExecutorImpl
	implements UpdateByQueryDocumentRequestExecutor {

	@Override
	public UpdateByQueryDocumentResponse execute(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		UpdateByQueryRequest updateByQueryRequest = createUpdateByQueryRequest(
			updateByQueryDocumentRequest);

		BulkByScrollResponse bulkByScrollResponse = getBulkByScrollResponse(
			updateByQueryRequest, updateByQueryDocumentRequest);

		TimeValue timeValue = bulkByScrollResponse.getTook();

		return new UpdateByQueryDocumentResponse(
			bulkByScrollResponse.getUpdated(), timeValue.getMillis());
	}

	protected UpdateByQueryRequest createUpdateByQueryRequest(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();

		updateByQueryRequest.indices(
			updateByQueryDocumentRequest.getIndexNames());

		if (updateByQueryDocumentRequest.getPortalSearchQuery() != null) {
			QueryBuilder queryBuilder = _queryTranslator.translate(
				updateByQueryDocumentRequest.getPortalSearchQuery());

			updateByQueryRequest.setQuery(queryBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			QueryBuilder queryBuilder = _legacyQueryTranslator.translate(
				updateByQueryDocumentRequest.getQuery(), null);

			updateByQueryRequest.setQuery(queryBuilder);
		}

		updateByQueryRequest.setRefresh(
			updateByQueryDocumentRequest.isRefresh());

		if (updateByQueryDocumentRequest.getScript() != null) {
			Script script = _scriptTranslator.translate(
				updateByQueryDocumentRequest.getScript());

			updateByQueryRequest.setScript(script);
		}
		else if (updateByQueryDocumentRequest.getScriptJSONObject() != null) {
			ScriptBuilder builder = _scripts.builder();

			JSONObject scriptJSONObject =
				updateByQueryDocumentRequest.getScriptJSONObject();

			if (scriptJSONObject.has("idOrCode")) {
				builder.idOrCode(scriptJSONObject.getString("idOrCode"));
			}

			if (scriptJSONObject.has("language")) {
				builder.language(scriptJSONObject.getString("language"));
			}

			if (scriptJSONObject.has("optionsMap")) {
				builder.options(
					(Map<String, String>)scriptJSONObject.get("optionsMap"));
			}

			if (scriptJSONObject.has("parametersMap")) {
				builder.parameters(
					(Map<String, Object>)scriptJSONObject.get("parametersMap"));
			}

			if (scriptJSONObject.has("scriptType")) {
				builder.scriptType(
					(ScriptType)scriptJSONObject.get("scriptType"));
			}

			updateByQueryRequest.setScript(
				_scriptTranslator.translate(builder.build()));
		}

		return updateByQueryRequest;
	}

	protected BulkByScrollResponse getBulkByScrollResponse(
		UpdateByQueryRequest updateByQueryRequest,
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				updateByQueryDocumentRequest.getConnectionId(),
				updateByQueryDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.updateByQuery(
				updateByQueryRequest, RequestOptions.DEFAULT);
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

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setLegacyQueryTranslator(
		com.liferay.portal.kernel.search.query.QueryTranslator<QueryBuilder>
			legacyQueryTranslator) {

		_legacyQueryTranslator = legacyQueryTranslator;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	@Reference(unbind = "-")
	protected void setScripts(Scripts scripts) {
		_scripts = scripts;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private com.liferay.portal.kernel.search.query.QueryTranslator<QueryBuilder>
		_legacyQueryTranslator;
	private QueryTranslator<QueryBuilder> _queryTranslator;
	private Scripts _scripts;
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}