/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.spi.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Inácio Nery
 */
public class SPINodeResource<T> {

	public SPINodeResource(
		long companyId,
		WorkflowMetricsIndexNameBuilder nodeWorkflowMetricsIndexNameBuilder,
		WorkflowMetricsIndexNameBuilder processWorkflowMetricsIndexNameBuilder,
		Queries queries, SearchRequestExecutor searchRequestExecutor,
		UnsafeFunction<Document, T, SystemException> transformUnsafeFunction) {

		_companyId = companyId;
		_nodeWorkflowMetricsIndexNameBuilder =
			nodeWorkflowMetricsIndexNameBuilder;
		_processWorkflowMetricsIndexNameBuilder =
			processWorkflowMetricsIndexNameBuilder;
		_queries = queries;
		_searchRequestExecutor = searchRequestExecutor;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public Page<T> getProcessNodesPage(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(_companyId));

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", _companyId),
				_queries.term("deleted", Boolean.FALSE),
				_queries.term("processId", processId),
				_queries.term("version", _getLatestProcessVersion(processId))));

		searchSearchRequest.setSize(10000);

		return Page.of(
			Stream.of(
				_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
			).map(
				SearchSearchResponse::getSearchHits
			).map(
				SearchHits::getSearchHits
			).flatMap(
				List::stream
			).map(
				SearchHit::getDocument
			).map(
				_transformUnsafeFunction::apply
			).collect(
				Collectors.toList()
			));
	}

	private String _getLatestProcessVersion(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(_companyId));

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", _companyId),
				_queries.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> document.getString("version")
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private final long _companyId;
	private final WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;
	private final WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;
	private final Queries _queries;
	private final SearchRequestExecutor _searchRequestExecutor;
	private final UnsafeFunction<Document, T, SystemException>
		_transformUnsafeFunction;

}