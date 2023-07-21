/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.dispatch.executor;

import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.dispatch.executor.helper.AnalyticsDispatchTaskExecutorHelper;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
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
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"dispatch.task.executor.name=" + AnalyticsDownloadUserCommerceMLRecommendDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsDownloadUserCommerceMLRecommendDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AnalyticsDownloadUserCommerceMLRecommendDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY =
		"analytics-download-user-commerce-ml-recommendation";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
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

		_analyticsDispatchTaskExecutorHelper.downloadResources(
			dispatchTrigger, dispatchTaskExecutorOutput,
			importResources.toArray(
				new BatchEngineTaskItemDelegateResourceMapper[0]));
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Reference
	private AnalyticsDispatchTaskExecutorHelper
		_analyticsDispatchTaskExecutorHelper;

}