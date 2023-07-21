/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsExperimentModelListener
	extends BaseModelListener<SegmentsExperiment> {

	@Override
	public void onAfterUpdate(SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		if (!_requiresDefaultExperienceReplacement(segmentsExperiment)) {
			return;
		}

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			SegmentsExperienceUtil.copySegmentsExperienceData(
				segmentsExperiment.getClassPK(), _commentManager,
				segmentsExperiment.getGroupId(), _portletRegistry,
				segmentsExperiment.getWinnerSegmentsExperienceId(),
				SegmentsExperienceConstants.ID_DEFAULT,
				className -> serviceContext, segmentsExperiment.getUserId());

			Layout draftLayout = _layoutLocalService.fetchDraftLayout(
				segmentsExperiment.getClassPK());

			if (draftLayout != null) {
				SegmentsExperienceUtil.copySegmentsExperienceData(
					draftLayout.getPlid(), _commentManager,
					segmentsExperiment.getGroupId(), _portletRegistry,
					segmentsExperiment.getWinnerSegmentsExperienceId(),
					SegmentsExperienceConstants.ID_DEFAULT,
					className -> serviceContext,
					segmentsExperiment.getUserId());
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(
				"Unable to update segments experiment " +
					segmentsExperiment.getSegmentsExperimentId(),
				portalException);
		}
	}

	private boolean _requiresDefaultExperienceReplacement(
		SegmentsExperiment segmentsExperiment) {

		if ((segmentsExperiment.getSegmentsExperienceId() ==
				SegmentsExperienceConstants.ID_DEFAULT) &&
			(segmentsExperiment.getStatus() ==
				SegmentsExperimentConstants.STATUS_COMPLETED) &&
			(segmentsExperiment.getWinnerSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT)) {

			return true;
		}

		return false;
	}

	@Reference
	private CommentManager _commentManager;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

}