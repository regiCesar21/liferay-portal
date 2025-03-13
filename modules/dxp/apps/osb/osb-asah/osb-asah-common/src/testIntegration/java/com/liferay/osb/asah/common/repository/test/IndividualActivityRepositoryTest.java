/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.entity.IndividualActivity;
import com.liferay.osb.asah.common.model.Sort;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.IndividualActivityRepository;
import com.liferay.osb.asah.test.util.annotation.SQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

/**
 * @author Rachael Koestartyo
 */
public class IndividualActivityRepositoryTest
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@SQLResource(resourcePath = "test_individual_activity_repository.sql")
	@Test
	public void testCountIndividualActivities() {
		Assertions.assertEquals(
			2,
			_individualActivityRepository.countIndividualActivities(
				null, "1", TimeRange.LAST_30_DAYS.getEndLocalDateTime(),
				TimeRange.LAST_30_DAYS.getStartLocalDateTime(),
				_timeZoneDog.getTimeZoneId()));
	}

	@SQLResource(resourcePath = "test_individual_activity_repository.sql")
	@Test
	public void testSearchIndividualActivities() {
		List<IndividualActivity> individualActivities =
			_individualActivityRepository.searchIndividualActivities(
				null, "1", PageRequest.of(0, 10, Sort.desc("eventDate")),
				TimeRange.LAST_30_DAYS.getEndLocalDateTime(),
				TimeRange.LAST_30_DAYS.getStartLocalDateTime(),
				_timeZoneDog.getTimeZoneId());

		Assertions.assertEquals(
			2, individualActivities.size(), individualActivities.toString());

		IndividualActivity individualActivity1 = individualActivities.get(0);

		JSONObject contextJSONObject =
			individualActivity1.getContextJSONObject();

		Set<String> keys = contextJSONObject.keySet();

		Assertions.assertEquals(13, keys.size());

		JSONObject propertiesJSONObject =
			individualActivity1.getPropertiesJSONObject();

		keys = propertiesJSONObject.keySet();

		Assertions.assertEquals(1, keys.size());

		IndividualActivity individualActivity2 = individualActivities.get(1);

		contextJSONObject = individualActivity2.getContextJSONObject();

		keys = contextJSONObject.keySet();

		Assertions.assertEquals(0, keys.size());

		propertiesJSONObject = individualActivity2.getPropertiesJSONObject();

		keys = propertiesJSONObject.keySet();

		Assertions.assertEquals(0, keys.size());
	}

	@Autowired
	private IndividualActivityRepository _individualActivityRepository;

	@Autowired
	private TimeZoneDog _timeZoneDog;

}