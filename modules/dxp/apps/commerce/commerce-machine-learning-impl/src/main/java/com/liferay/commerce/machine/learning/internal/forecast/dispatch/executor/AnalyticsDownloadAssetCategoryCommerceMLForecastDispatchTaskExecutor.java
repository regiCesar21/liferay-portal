/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.forecast.dispatch.executor;

import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.dispatch.executor.helper.AnalyticsDispatchTaskExecutorHelper;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.AccountCategoryForecast;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"dispatch.task.executor.name=" + AnalyticsDownloadAssetCategoryCommerceMLForecastDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsDownloadAssetCategoryCommerceMLForecastDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class
	AnalyticsDownloadAssetCategoryCommerceMLForecastDispatchTaskExecutor
		extends BaseDispatchTaskExecutor {

	public static final String KEY =
		"analytics-download-asset-category-commerce-ml-forecast";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		BatchEngineTaskItemDelegateResourceMapper
			batchEngineTaskItemDelegateResourceMapper =
				new BatchEngineTaskItemDelegateResourceMapper(
					AccountCategoryForecast.class.getName(),
					HashMapBuilder.put(
						"actual", "actual"
					).put(
						"assetCategoryId", "category"
					).put(
						"commerceAccountId", "account"
					).put(
						"forecast", "forecast"
					).put(
						"forecastLowerBound", "forecastLowerBound"
					).put(
						"forecastUpperBound", "forecastUpperBound"
					).put(
						"timestamp", "timestamp"
					).build(),
					null);

		_analyticsDispatchTaskExecutorHelper.downloadResources(
			dispatchTrigger, dispatchTaskExecutorOutput,
			new BatchEngineTaskItemDelegateResourceMapper[] {
				batchEngineTaskItemDelegateResourceMapper
			});
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Reference
	private AnalyticsDispatchTaskExecutorHelper
		_analyticsDispatchTaskExecutorHelper;

}