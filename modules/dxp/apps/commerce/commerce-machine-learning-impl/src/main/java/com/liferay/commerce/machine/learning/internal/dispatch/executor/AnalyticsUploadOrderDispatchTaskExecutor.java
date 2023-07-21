/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.dispatch.executor;

import com.liferay.commerce.machine.learning.internal.batch.engine.mapper.BatchEngineTaskItemDelegateResourceMapper;
import com.liferay.commerce.machine.learning.internal.dispatch.executor.helper.AnalyticsDispatchTaskExecutorHelper;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.headless.commerce.admin.order.constants.v1_0.OrderBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"dispatch.task.executor.name=" + AnalyticsUploadOrderDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + AnalyticsUploadOrderDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class AnalyticsUploadOrderDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY = "analytics-upload-order";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		_analyticsDispatchTaskExecutorHelper.uploadResources(
			dispatchTrigger, dispatchTaskExecutorOutput,
			_EXPORT_RESOURCE_NAMES);
	}

	@Override
	public String getName() {
		return KEY;
	}

	private static final BatchEngineTaskItemDelegateResourceMapper[]
		_EXPORT_RESOURCE_NAMES = {
			new BatchEngineTaskItemDelegateResourceMapper(
				Order.class.getName(), null,
				OrderBatchEngineTaskItemDelegateConstants.COMMERCE_ML_ORDER)
		};

	@Reference
	private AnalyticsDispatchTaskExecutorHelper
		_analyticsDispatchTaskExecutorHelper;

}