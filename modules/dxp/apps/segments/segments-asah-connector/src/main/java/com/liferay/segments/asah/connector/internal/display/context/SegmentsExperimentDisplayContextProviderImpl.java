/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.display.context;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.cache.AsahExperimentCache;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.JSONWebServiceClient;
import com.liferay.segments.asah.connector.internal.configuration.SegmentsExperimentConfiguration;
import com.liferay.segments.display.context.SegmentsExperimentDisplayContext;
import com.liferay.segments.display.context.SegmentsExperimentDisplayContextProvider;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	configurationPid = "com.liferay.segments.asah.connector.internal.configuration.SegmentsExperimentConfiguration",
	service = SegmentsExperimentDisplayContextProvider.class
)
public class SegmentsExperimentDisplayContextProviderImpl
	implements SegmentsExperimentDisplayContextProvider {

	@Override
	public SegmentsExperimentDisplayContext getSegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		return new SegmentsExperimentDisplayContextImpl(
			_asahFaroBackendClient, httpServletRequest, _layoutLocalService,
			_portal, renderResponse, _segmentsExperienceService,
			_segmentsExperimentConfiguration, _segmentsExperimentRelService,
			_segmentsExperimentService);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_asahExperimentCache, _jsonWebServiceClient);

		_segmentsExperimentConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsExperimentConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

	@Reference
	private AsahExperimentCache _asahExperimentCache;

	private volatile AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	private volatile SegmentsExperimentConfiguration
		_segmentsExperimentConfiguration;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}