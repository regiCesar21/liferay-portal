/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.data.integration;

import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.data.integration.AnalyticsScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsDownloadUserCommerceMLRecommendationProcessType;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.ProductInteractionRecommendation;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.UserRecommendation;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Athanasius (7.3.x)
 */
@Component(
	enabled = false, immediate = true,
	property = "data.integration.service.executor.key=" + AnalyticsDownloadUserCommerceMLRecommendationProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
@Deprecated
public class
	AnalyticsDownloadUserCommerceMLRecommendScheduledTaskExecutorServiceImpl
		implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AnalyticsDownloadUserCommerceMLRecommendationProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		List<BatchEngineTaskItemDelegateResourceMapper> importResources =
			new ArrayList<>();

		importResources.add(
			new BatchEngineTaskItemDelegateResourceMapper(
				UserRecommendation.class.getName(),
				HashMapBuilder.put(
					"assetCategoryIds", "assetCategoryIds"
				).put(
					"createDate", "createDate"
				).put(
					"entryClassPK", "productId"
				).put(
					"jobId", "jobId"
				).put(
					"recommendedEntryClassPK", "recommendedProductId"
				).put(
					"score", "score"
				).build(),
				null));

		importResources.add(
			new BatchEngineTaskItemDelegateResourceMapper(
				ProductInteractionRecommendation.class.getName(),
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
				null));

		_analyticsScheduledTaskExecutorService.downloadResources(
			commerceDataIntegrationProcessId,
			importResources.toArray(
				new BatchEngineTaskItemDelegateResourceMapper[0]));
	}

	@Reference
	private AnalyticsScheduledTaskExecutorService
		_analyticsScheduledTaskExecutorService;

}