/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration",
	service = ConfigurationModelListener.class
)
public class SegmentsContextVocabularyConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String entityField = String.valueOf(properties.get("entityField"));

		if (Validator.isNull(entityField)) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					getResourceBundle(),
					"please-enter-a-vaid-session-property-name"),
				SegmentsContextVocabularyConfiguration.class, getClass(),
				properties);
		}

		if (_isDefined(pid, entityField)) {
			throw new DuplicatedSegmentsContextVocabularyConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					getResourceBundle(),
					"this-field-is-already-linked-to-one-vocabulary"),
				SegmentsContextVocabularyConfiguration.class, getClass(),
				properties);
		}
	}

	protected ResourceBundle getResourceBundle() {
		if (_resourceBundle == null) {
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			return ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());
		}

		return _resourceBundle;
	}

	private boolean _isDefined(
		Configuration configuration, String entityField, String pid) {

		Dictionary<String, Object> properties = configuration.getProperties();

		if (Objects.equals(entityField, properties.get("entityField")) &&
			!Objects.equals(pid, configuration.getPid())) {

			return true;
		}

		return false;
	}

	private boolean _isDefined(String pid, String entityField)
		throws ConfigurationModelListenerException {

		try {
			return Stream.of(
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
			).filter(
				configuration -> _isDefined(configuration, entityField, pid)
			).findFirst(
			).isPresent();
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(),
				SegmentsContextVocabularyConfiguration.class, getClass(), null);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private ResourceBundle _resourceBundle;

}