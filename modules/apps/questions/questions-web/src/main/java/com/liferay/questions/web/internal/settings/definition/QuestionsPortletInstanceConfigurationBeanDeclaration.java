/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.questions.web.internal.settings.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import com.liferay.questions.web.internal.configuration.QuestionsConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 */
@Component(service = ConfigurationBeanDeclaration.class)
public class QuestionsPortletInstanceConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return QuestionsConfiguration.class;
	}

}