/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.model;

import com.liferay.osb.asah.backend.test.util.BaseBeanTestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Supplier;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

import org.junit.jupiter.api.Test;

/**
 * @author Marcellus Tavares
 */
public class FormPageMetricTest extends BaseBeanTestCase<FormPageMetric> {

	public FormPageMetricTest() {
		super(
			new HashMap<Class<?>, Supplier<?>>() {
				{
					put(Metric.class, () -> new Metric(null));
				}
			},
			Arrays.asList("getAvailableMetrics"));
	}

	@Override
	@Test
	public void testEqualsAndHashCode() {
		SingleTypeEqualsVerifierApi<?> singleTypeEqualsVerifierApi =
			EqualsVerifier.forClass(FormPageMetric.class);

		singleTypeEqualsVerifierApi = singleTypeEqualsVerifierApi.suppress(
			Warning.NONFINAL_FIELDS, Warning.STRICT_INHERITANCE);

		Metric metric = new Metric(null);

		metric.setMetrics(Collections.emptyList());

		singleTypeEqualsVerifierApi =
			singleTypeEqualsVerifierApi.withPrefabValues(
				Metric.class, metric, new Metric(null));

		singleTypeEqualsVerifierApi.verify();
	}

	@Override
	protected FormPageMetric newInstance() {
		return new FormPageMetric();
	}

}