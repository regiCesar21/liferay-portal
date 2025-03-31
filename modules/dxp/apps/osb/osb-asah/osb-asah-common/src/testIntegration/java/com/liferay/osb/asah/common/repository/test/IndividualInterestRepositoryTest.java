/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.repository.IndividualInterestRepository;
import com.liferay.osb.asah.test.util.annotation.SQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

/**
 * @author Rachael Koestartyo
 */
public class IndividualInterestRepositoryTest
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@SQLResource(resourcePath = "test_individual_interest_repository.sql")
	@Test
	public void testCountByIndividualId() {
		Assertions.assertEquals(
			2,
			_individualInterestRepository.countByChannelIdAndIndividualId(
				null, "374790572703144534"));
	}

	@SQLResource(resourcePath = "test_individual_interest_repository.sql")
	@Test
	public void testFindByIndividualId() {
		List<IndividualInterest> individualInterests =
			_individualInterestRepository.findByChannelIdAndIndividualId(
				null, "374790572703144534", PageRequest.of(0, 10));

		Assertions.assertEquals(
			2, individualInterests.size(), individualInterests.toString());

		IndividualInterest individualInterest1 = new IndividualInterest();

		individualInterest1.setChannelId(1L);
		individualInterest1.setIdentityId("identity3");
		individualInterest1.setInterested(Boolean.FALSE);
		individualInterest1.setInterestScore(0.77022254);
		individualInterest1.setKeyword("compelling metrics");
		individualInterest1.setRecordedDate(
			DateUtil.toUTCDate("2021-09-14T00:00:00.000Z"));

		IndividualInterest individualInterest2 = new IndividualInterest();

		individualInterest2.setChannelId(1L);
		individualInterest2.setIdentityId("identity4");
		individualInterest2.setInterested(Boolean.TRUE);
		individualInterest2.setInterestScore(1.454685);
		individualInterest2.setKeyword("sales");
		individualInterest2.setRecordedDate(
			DateUtil.toUTCDate("2021-09-14T00:00:00.000Z"));

		Assertions.assertEquals(
			Arrays.asList(individualInterest1, individualInterest2),
			individualInterests);
	}

	@Autowired
	private IndividualInterestRepository _individualInterestRepository;

}