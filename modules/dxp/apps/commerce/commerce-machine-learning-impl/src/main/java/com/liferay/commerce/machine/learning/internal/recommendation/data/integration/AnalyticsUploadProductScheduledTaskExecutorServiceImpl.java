/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.data.integration;

import com.liferay.commerce.data.integration.service.ScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.data.integration.AnalyticsScheduledTaskExecutorService;
import com.liferay.commerce.machine.learning.internal.recommendation.data.integration.process.type.AnalyticsUploadProductProcessType;
import com.liferay.headless.commerce.admin.catalog.constants.v1_0.ProductBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 * @deprecated As of Athanasius (7.3.x)
 */
@Component(
	enabled = false, immediate = true,
	property = "data.integration.service.executor.key=" + AnalyticsUploadProductProcessType.KEY,
	service = ScheduledTaskExecutorService.class
)
@Deprecated
public class AnalyticsUploadProductScheduledTaskExecutorServiceImpl
	implements ScheduledTaskExecutorService {

	@Override
	public String getName() {
		return AnalyticsUploadProductProcessType.KEY;
	}

	@Override
	public void runProcess(long commerceDataIntegrationProcessId)
		throws IOException, PortalException {

		_analyticsScheduledTaskExecutorService.uploadResources(
			commerceDataIntegrationProcessId, _EXPORT_RESOURCE_NAMES);
	}

	private static final BatchEngineTaskItemDelegateResourceMapper[]
		_EXPORT_RESOURCE_NAMES = {
			new BatchEngineTaskItemDelegateResourceMapper(
				Product.class.getName(), null,
				ProductBatchEngineTaskItemDelegateConstants.COMMERCE_ML_PRODUCT)
		};

	@Reference
	private AnalyticsScheduledTaskExecutorService
		_analyticsScheduledTaskExecutorService;

}