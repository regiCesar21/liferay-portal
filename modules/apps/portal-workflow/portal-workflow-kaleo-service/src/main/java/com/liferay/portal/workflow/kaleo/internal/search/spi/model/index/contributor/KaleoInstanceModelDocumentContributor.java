/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = ModelDocumentContributor.class
)
public class KaleoInstanceModelDocumentContributor
	implements ModelDocumentContributor<KaleoInstance> {

	@Override
	public void contribute(Document document, KaleoInstance kaleoInstance) {
		document.addDateSortable(
			Field.CREATE_DATE, kaleoInstance.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoInstance.getModifiedDate());
		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeywordSortable("completed", kaleoInstance.isCompleted());
		document.addDateSortable(
			"completionDate", kaleoInstance.getCompletionDate());

		try {
			Map<String, Serializable> workflowContext =
				WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext());

			ServiceContext serviceContext = (ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

			KaleoInstanceToken rootKaleoInstanceToken =
				kaleoInstance.getRootKaleoInstanceToken(serviceContext);

			document.addKeywordSortable(
				"currentKaleoNodeName",
				rootKaleoInstanceToken.getCurrentKaleoNodeName());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		document.addKeyword(
			"kaleoDefinitionName", kaleoInstance.getKaleoDefinitionName());
		document.addKeyword(
			"kaleoDefinitionVersionId",
			kaleoInstance.getKaleoDefinitionVersionId());
		document.addKeyword(
			"kaleoDefinitionVersion",
			kaleoInstance.getKaleoDefinitionVersion());
		document.addNumberSortable(
			"kaleoInstanceId", kaleoInstance.getKaleoInstanceId());
		document.addKeyword(
			"rootKaleoInstanceTokenId",
			kaleoInstance.getRootKaleoInstanceTokenId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceModelDocumentContributor.class);

}