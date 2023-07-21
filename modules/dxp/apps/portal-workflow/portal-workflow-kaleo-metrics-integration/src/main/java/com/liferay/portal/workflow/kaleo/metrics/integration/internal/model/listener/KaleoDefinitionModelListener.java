/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			kaleoDefinition.getTitle());

		_processWorkflowMetricsIndexer.addProcess(
			kaleoDefinition.isActive(), kaleoDefinition.getCompanyId(),
			kaleoDefinition.getCreateDate(), kaleoDefinition.getDescription(),
			kaleoDefinition.getModifiedDate(), kaleoDefinition.getName(),
			kaleoDefinition.getKaleoDefinitionId(),
			kaleoDefinition.getTitle(defaultLanguageId),
			kaleoDefinition.getTitleMap(),
			StringBundler.concat(
				kaleoDefinition.getVersion(), CharPool.PERIOD, 0));
	}

	@Override
	public void onAfterUpdate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			kaleoDefinition.getTitle());

		_processWorkflowMetricsIndexer.updateProcess(
			kaleoDefinition.isActive(), kaleoDefinition.getCompanyId(),
			kaleoDefinition.getDescription(), kaleoDefinition.getModifiedDate(),
			kaleoDefinition.getKaleoDefinitionId(),
			kaleoDefinition.getTitle(defaultLanguageId),
			kaleoDefinition.getTitleMap(),
			StringBundler.concat(
				kaleoDefinition.getVersion(), CharPool.PERIOD, 0));
	}

	@Override
	public void onBeforeRemove(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		_processWorkflowMetricsIndexer.deleteProcess(
			kaleoDefinition.getCompanyId(),
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

}