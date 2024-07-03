/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import com.liferay.osb.asah.common.entity.BQIndividual;
import com.liferay.osb.asah.common.util.SetUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Marcos Martins
 */
public class IndividualTest {

	@Test
	public void testGetDemographicFields() {
		Date date = new Date();

		BQIndividual bqIndividual = new BQIndividual();

		bqIndividual.setFields(
			Arrays.asList(
				new BQIndividual.Field(0L, "birthday", "test"),
				new BQIndividual.Field(0L, "emailAddress", "test"),
				new BQIndividual.Field(0L, "firstName", "test"),
				new BQIndividual.Field(0L, "lastName", "test"),
				new BQIndividual.Field(0L, "middleName", "test")));

		Individual individual = new Individual(0L, bqIndividual, date, date);

		Set<String> names = new HashSet<>();
		Set<String> sourceNames = new HashSet<>();

		for (Field field : individual.getFields()) {
			names.add(field.getName());
			sourceNames.add(field.getSourceName());
		}

		Assertions.assertTrue(
			CollectionUtils.isEqualCollection(
				SetUtil.of(
					"additionalName", "birthDate", "email", "familyName",
					"givenName"),
				names));
		Assertions.assertTrue(
			CollectionUtils.isEqualCollection(
				SetUtil.of(
					"birthday", "emailAddress", "firstName", "lastName",
					"middleName"),
				sourceNames));
	}

}