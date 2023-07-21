/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;
import com.liferay.segments.context.vocabulary.internal.constants.SegmentsContextVocabularyWebKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.cm.Configuration;

/**
 * @author Cristina González
 */
public class SegmentsContextVocabularyConfigurationFactoryDisplayContext {

	public SegmentsContextVocabularyConfigurationFactoryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_configurations = (List<Configuration>)renderRequest.getAttribute(
			SegmentsContextVocabularyWebKeys.
				SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATIONS);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddConfigurationURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/edit_segments_context_vocabulary_configuration");
		portletURL.setParameter(
			"factoryPid",
			SegmentsContextVocabularyConfiguration.class.getCanonicalName());

		return portletURL;
	}

	public PortletURL getDeleteConfigurationURL(Configuration configuration) {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/delete_segments_context_vocabulary_configuration");
		portletURL.setParameter("factoryPid", configuration.getFactoryPid());
		portletURL.setParameter("pid", configuration.getPid());
		portletURL.setParameter("redirect", String.valueOf(_getRedirect()));

		return portletURL;
	}

	public PortletURL getEditConfigurationURL(Configuration configuration) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/edit_segments_context_vocabulary_configuration");
		portletURL.setParameter("factoryPid", configuration.getFactoryPid());
		portletURL.setParameter("pid", configuration.getPid());

		return portletURL;
	}

	public String getEmptyResultMessage() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(),
			SegmentsContextVocabularyConfigurationFactoryDisplayContext.class);

		return LanguageUtil.format(
			resourceBundle, "no-entries-for-x-have-been-added-yet",
			"segments-context-vocabulary-configuration-name");
	}

	public PortletURL getIteratorURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey",
			"segments-context-vocabulary-configuration-name");

		return portletURL;
	}

	public List<Configuration> getResults(int start, int end) {
		return ListUtil.subList(_configurations, start, end);
	}

	public String getTitle() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(), getClass());

		return ResourceBundleUtil.getString(
			resourceBundle,
			"segments-context-vocabulary-configuration-entity-field-name");
	}

	public int getTotal() {
		return _configurations.size();
	}

	private PortletURL _getRedirect() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey",
			"segments-context-vocabulary-configuration-name");

		return portletURL;
	}

	private final List<Configuration> _configurations;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}