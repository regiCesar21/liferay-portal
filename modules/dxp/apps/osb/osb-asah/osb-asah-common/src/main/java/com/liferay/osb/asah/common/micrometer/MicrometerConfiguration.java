/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.micrometer;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Marcellus Tavares
 */
@Configuration
public class MicrometerConfiguration {

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
		return registry -> {
			MeterRegistry.Config config = registry.config();

			config.meterFilter(new OSBAsahMeterFilter());
		};
	}

	private static class OSBAsahMeterFilter implements MeterFilter {

		@Override
		public DistributionStatisticConfig configure(
			Meter.Id id,
			DistributionStatisticConfig distributionStatisticConfig) {

			if (StringUtils.equals(id.getName(), "pubsub_requests_seconds") ||
				StringUtils.equals(id.getName(), "redis_requests_seconds")) {

				DistributionStatisticConfig customDistributionStatisticConfig =
					_buildCustomDistributionStatisticConfig();

				return customDistributionStatisticConfig.merge(
					distributionStatisticConfig);
			}

			return distributionStatisticConfig;
		}

		private DistributionStatisticConfig
			_buildCustomDistributionStatisticConfig() {

			DistributionStatisticConfig.Builder builder =
				DistributionStatisticConfig.builder();

			return builder.serviceLevelObjectives(
				TimeUnit.MILLISECONDS.toNanos(10),
				TimeUnit.MILLISECONDS.toNanos(50),
				TimeUnit.MILLISECONDS.toNanos(100),
				TimeUnit.MILLISECONDS.toNanos(500),
				TimeUnit.MILLISECONDS.toNanos(1000),
				TimeUnit.MILLISECONDS.toNanos(5000),
				TimeUnit.MILLISECONDS.toNanos(10000)
			).build();
		}

	}

}