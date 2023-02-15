/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowMetricsIndex implements WorkflowMetricsIndex {

	@Override
	public void createIndex(long companyId) throws PortalException {
		workflowMetricsPortalExecutor.execute(
			() -> {
				if ((searchEngineAdapter == null) ||
					hasIndex(getIndexName(companyId))) {

					return;
				}

				CreateIndexRequest createIndexRequest = new CreateIndexRequest(
					getIndexName(companyId));

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					StringUtil.read(
						getClass(), "/META-INF/search/mappings.json"));

				createIndexRequest.setSource(
					JSONUtil.put(
						"mappings",
						JSONUtil.put("_doc", jsonObject.get(getIndexType()))
					).put(
						"settings",
						JSONFactoryUtil.createJSONObject(
							StringUtil.read(
								getClass(), "/META-INF/search/settings.json"))
					).toString());

				searchEngineAdapter.execute(createIndexRequest);
			});
	}

	@Override
	public void removeIndex(long companyId) throws PortalException {
		workflowMetricsPortalExecutor.execute(
			() -> {
				if ((searchEngineAdapter == null) ||
					!hasIndex(getIndexName(companyId))) {

					return;
				}

				searchEngineAdapter.execute(
					new DeleteIndexRequest(getIndexName(companyId)));
			});
	}

	@Activate
	protected void activate() throws Exception {
		for (Company company : companyLocalService.getCompanies()) {
			createIndex(company.getCompanyId());
		}
	}

	protected boolean hasIndex(String indexName) {
		if (searchEngineAdapter == null) {
			return false;
		}

		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	@Reference(
		target = ModuleServiceLifecycle.PORTLETS_INITIALIZED, unbind = "-"
	)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	protected volatile SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected WorkflowMetricsPortalExecutor workflowMetricsPortalExecutor;

}