/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.workflow.metrics.search.index.TransitionWorkflowMetricsIndexer;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = TransitionWorkflowMetricsIndexer.class)
public class TransitionWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer
	implements TransitionWorkflowMetricsIndexer {

	@Override
	public Document addTransition(
		long companyId, Date createDate, Date modifiedDate, String name,
		long nodeId, long processId, String processVersion, long sourceNodeId,
		String sourceNodeName, long targetNodeId, String targetNodeName,
		long transitionId, long userId) {

		if (searchEngineAdapter == null) {
			return null;
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"createDate", getDate(createDate)
		).setValue(
			"deleted", false
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"sourceNodeId", sourceNodeId
		).setString(
			"sourceNodeName", sourceNodeName
		).setLong(
			"targetNodeId", targetNodeId
		).setString(
			"targetNodeName", targetNodeName
		).setString(
			"uid", digest(companyId, transitionId)
		).setLong(
			"userId", userId
		).setString(
			"version", processVersion
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteTransition(long companyId, long transitionId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"transitionId", transitionId
		).setString(
			"uid", digest(companyId, transitionId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _transitionWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _transitionWorkflowMetricsIndex.getIndexType();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndex _transitionWorkflowMetricsIndex;

}