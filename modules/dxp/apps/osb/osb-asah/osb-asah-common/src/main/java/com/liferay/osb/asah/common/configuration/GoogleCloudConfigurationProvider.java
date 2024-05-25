/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

/**
 * @author Marcellus Tavares
 */
@Configuration
public class GoogleCloudConfigurationProvider {

	@Bean
	@Profile({"dev", "test"})
	public GoogleCloudConfiguration devConfiguration() {
		return new GoogleCloudConfiguration(
			null, null, "liferaycloud-customer-ac");
	}

	@Bean
	@Primary
	@Profile("prod")
	public GoogleCloudConfiguration prodConfiguration(Environment environment) {
		return new GoogleCloudConfiguration(
			environment.getRequiredProperty("osb.asah.composer.endpoint"),
			environment.getRequiredProperty("gcloud.compute.region"),
			environment.getRequiredProperty("osb.asah.gcloud.project.id"));
	}

}