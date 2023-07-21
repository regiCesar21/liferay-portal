/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;
import com.liferay.segments.context.vocabulary.internal.constants.SegmentsContextVocabularyWebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ConfigurationScreen.class)
public class SegmentsContextVocabularyConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "segments";
	}

	@Override
	public String getKey() {
		return "segments-context-vocabulary-configuration-name";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()), getKey());
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.SYSTEM.getValue();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/edit_segments_context_vocabulary_configuration_factory." +
						"jsp");

			_setHttpServletRequestAttributes(httpServletRequest);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render edit_segments_context_vocabulary_" +
					"configuration_factory.jsp",
				exception);
		}
	}

	private void _setHttpServletRequestAttributes(
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<Configuration> configurations = Stream.of(
			Optional.ofNullable(
				_configurationAdmin.listConfigurations(
					StringBundler.concat(
						"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
						SegmentsContextVocabularyConfiguration.class.
							getCanonicalName(),
						")"))
			).orElse(
				new Configuration[0]
			)
		).collect(
			Collectors.toList()
		);

		httpServletRequest.setAttribute(
			SegmentsContextVocabularyWebKeys.
				SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATIONS,
			configurations);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.segments.context.vocabulary)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}