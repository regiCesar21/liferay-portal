/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.data.integration;

import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.data.integration.AnalyticsScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsDownloadProductContentCommerceMLRecommendationProcessType;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductContentRecommendation;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Athanasius (7.3.x)
 */
@Component(
	enabled = false, immediate = true,
	property = "data.integration.service.executor.key=" + AnalyticsDownloadProductContentCommerceMLRecommendationProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
@Deprecated
public class
	AnalyticsDownloadProductContentCommerceMLRecommendScheduledTaskExecutorServiceImpl
		implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AnalyticsDownloadProductContentCommerceMLRecommendationProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		BatchEngineTaskItemDelegateResourceMapper
			batchEngineTaskItemDelegateResourceMapper =
				new BatchEngineTaskItemDelegateResourceMapper(
					ProductContentRecommendation.class.getName(),
					HashMapBuilder.put(
						"createDate", "createDate"
					).put(
						"entryClassPK", "productId"
					).put(
						"jobId", "jobId"
					).put(
						"rank", "rank"
					).put(
						"recommendedEntryClassPK", "recommendedProductId"
					).put(
						"score", "score"
					).build(),
					null);

		_analyticsScheduledTaskExecutorService.downloadResources(
			commerceDataIntegrationProcessId,
			new BatchEngineTaskItemDelegateResourceMapper[] {
				batchEngineTaskItemDelegateResourceMapper
			});
	}

	@Reference
	private AnalyticsScheduledTaskExecutorService
		_analyticsScheduledTaskExecutorService;

}