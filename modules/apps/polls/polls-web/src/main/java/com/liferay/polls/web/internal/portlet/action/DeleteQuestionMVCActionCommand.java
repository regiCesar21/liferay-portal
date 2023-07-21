/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.polls.web.internal.portlet.action;

import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.service.PollsQuestionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS,
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls/delete_question"
	},
	service = MVCActionCommand.class
)
public class DeleteQuestionMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteQuestionIds = null;

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		if (questionId > 0) {
			deleteQuestionIds = new long[] {questionId};
		}
		else {
			deleteQuestionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteQuestionIds"), 0L);
		}

		for (long deleteQuestionId : deleteQuestionIds) {
			_pollsQuestionService.deleteQuestion(deleteQuestionId);
		}
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionService(
		PollsQuestionService pollsQuestionService) {

		_pollsQuestionService = pollsQuestionService;
	}

	private PollsQuestionService _pollsQuestionService;

}