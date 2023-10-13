/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.experiment.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.display.context.SegmentsExperimentDisplayContext;
import com.liferay.segments.display.context.SegmentsExperimentDisplayContextProvider;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.experiment.web.internal.product.navigation.control.menu.SegmentsExperimentProductNavigationControlMenuEntry;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Segments Experiment",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = {Portlet.class, SegmentsExperimentPortlet.class}
)
public class SegmentsExperimentPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		SegmentsExperimentDisplayContext segmentsExperimentDisplayContext =
			_segmentsExperimentDisplayContextProvider.
				getSegmentsExperimentDisplayContext(
					httpServletRequest, renderResponse);

		renderRequest.setAttribute(
			SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_DISPLAY_CONTEXT,
			segmentsExperimentDisplayContext);

		renderRequest.setAttribute(
			SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT_PANEL_STATE_OPEN,
			_segmentsExperimentProductNavigationControlMenuEntry.
				isPanelStateOpen(httpServletRequest));

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperimentDisplayContextProvider
		_segmentsExperimentDisplayContextProvider;

	@Reference
	private SegmentsExperimentProductNavigationControlMenuEntry
		_segmentsExperimentProductNavigationControlMenuEntry;

}