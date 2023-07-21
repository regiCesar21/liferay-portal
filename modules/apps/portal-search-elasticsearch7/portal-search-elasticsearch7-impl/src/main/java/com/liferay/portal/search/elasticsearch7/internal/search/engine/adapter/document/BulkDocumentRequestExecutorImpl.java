/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.util.SearchLogHelperUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = BulkDocumentRequestExecutor.class)
public class BulkDocumentRequestExecutorImpl
	implements BulkDocumentRequestExecutor {

	@Override
	public BulkDocumentResponse execute(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest bulkRequest = createBulkRequest(bulkDocumentRequest);

		BulkResponse bulkResponse = getBulkResponse(
			bulkRequest, bulkDocumentRequest);

		SearchLogHelperUtil.logActionResponse(_log, bulkResponse);

		TimeValue timeValue = bulkResponse.getTook();

		BulkDocumentResponse bulkDocumentResponse = new BulkDocumentResponse(
			timeValue.getMillis());

		for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
			BulkDocumentItemResponse bulkDocumentItemResponse =
				new BulkDocumentItemResponse();

			bulkDocumentResponse.addBulkDocumentItemResponse(
				bulkDocumentItemResponse);

			bulkDocumentItemResponse.setId(bulkItemResponse.getId());
			bulkDocumentItemResponse.setIndex(bulkItemResponse.getIndex());
			bulkDocumentItemResponse.setFailureMessage(
				bulkItemResponse.getFailureMessage());
			bulkDocumentItemResponse.setType(bulkItemResponse.getType());
			bulkDocumentItemResponse.setVersion(bulkItemResponse.getVersion());

			RestStatus restStatus = bulkItemResponse.status();

			if (bulkItemResponse.isFailed()) {
				bulkDocumentResponse.setErrors(true);

				BulkItemResponse.Failure bulkItemFailureResponse =
					bulkItemResponse.getFailure();

				bulkDocumentItemResponse.setAborted(
					bulkItemFailureResponse.isAborted());
				bulkDocumentItemResponse.setCause(
					bulkItemFailureResponse.getCause());
				restStatus = bulkItemFailureResponse.getStatus();
			}

			bulkDocumentItemResponse.setStatus(restStatus.getStatus());
		}

		return bulkDocumentResponse;
	}

	protected BulkRequest createBulkRequest(
		BulkDocumentRequest bulkDocumentRequest) {

		BulkRequest bulkRequest = new BulkRequest();

		if (bulkDocumentRequest.isRefresh()) {
			bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		for (BulkableDocumentRequest<?> bulkableDocumentRequest :
				bulkDocumentRequest.getBulkableDocumentRequests()) {

			bulkableDocumentRequest.accept(
				request -> {
					if (request instanceof DeleteDocumentRequest) {
						DeleteRequest deleteRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((DeleteDocumentRequest)request);

						bulkRequest.add(deleteRequest);
					}
					else if (request instanceof IndexDocumentRequest) {
						IndexRequest indexRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((IndexDocumentRequest)request);

						bulkRequest.add(indexRequest);
					}
					else if (request instanceof UpdateDocumentRequest) {
						UpdateRequest updateRequest =
							_elasticsearchBulkableDocumentRequestTranslator.
								translate((UpdateDocumentRequest)request);

						bulkRequest.add(updateRequest);
					}
					else {
						throw new IllegalArgumentException(
							"No translator available for: " + request);
					}
				});
		}

		return bulkRequest;
	}

	protected BulkResponse getBulkResponse(
		BulkRequest bulkRequest, BulkDocumentRequest bulkDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				bulkDocumentRequest.getConnectionId(),
				bulkDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.bulk(
				bulkRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setElasticsearchBulkableDocumentRequestTranslator(
		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator) {

		_elasticsearchBulkableDocumentRequestTranslator =
			elasticsearchBulkableDocumentRequestTranslator;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BulkDocumentRequestExecutorImpl.class);

	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}