/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.repository.BQReportIndividualRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.configuration.JDBCTestConfiguration;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

/**
 * @author Marcellus Tavares
 */
@Import(JDBCTestConfiguration.class)
public class BQReportIndividualRepositoryTest
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testCountReportIndividuals() {
		Assertions.assertEquals(
			15L,
			_bqReportIndividualRepository.countReportIndividuals(
				null, null, null));
		Assertions.assertEquals(
			1L,
			_bqReportIndividualRepository.countReportIndividuals(
				1L, "3", 11111L));
		Assertions.assertEquals(
			0L,
			_bqReportIndividualRepository.countReportIndividuals(
				2L, "3", 11111L));
		Assertions.assertEquals(
			1,
			_bqReportIndividualRepository.countReportIndividuals(
				2L, "1", 11111L));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithChannelId() {
		Assertions.assertEquals(
			8L,
			_bqReportIndividualRepository.countReportIndividuals(
				1L, null, null));
		Assertions.assertEquals(
			8L,
			_bqReportIndividualRepository.countReportIndividuals(
				2L, null, null));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithQuery() {
		Assertions.assertEquals(
			2L,
			_bqReportIndividualRepository.countReportIndividuals(
				null, "5", null));
		Assertions.assertEquals(
			1L,
			_bqReportIndividualRepository.countReportIndividuals(
				null, "15", null));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithSegmentId() {
		Assertions.assertEquals(
			2L,
			_bqReportIndividualRepository.countReportIndividuals(
				null, null, 11111L));
		Assertions.assertEquals(
			1L,
			_bqReportIndividualRepository.countReportIndividuals(
				null, null, 33333L));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testFindReportIndividualById() {
		Optional<ReportIndividual> reportIndividualOptional =
			_bqReportIndividualRepository.findReportIndividualById("1");

		Assertions.assertTrue(reportIndividualOptional.isPresent());

		ReportIndividual reportIndividual = new ReportIndividual();

		reportIndividual.setId("1");
		reportIndividual.setSegmentIds(
			new HashSet<>(Arrays.asList(11111L, 22222L, 33333L)));

		Assertions.assertEquals(
			reportIndividual, reportIndividualOptional.get());

		reportIndividualOptional =
			_bqReportIndividualRepository.findReportIndividualById("2");

		Assertions.assertFalse(reportIndividualOptional.isPresent());

		reportIndividualOptional =
			_bqReportIndividualRepository.findReportIndividualById("abc");

		Assertions.assertFalse(reportIndividualOptional.isPresent());
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testSearchReportIndividuals() {
		ReportIndividual reportIndividual1 = new ReportIndividual();

		reportIndividual1.setId("3");
		reportIndividual1.setSegmentIds(Collections.singleton(11111L));

		ReportIndividual reportIndividual2 = new ReportIndividual();

		reportIndividual2.setId("1");
		reportIndividual2.setSegmentIds(Collections.singleton(22222L));

		Assertions.assertEquals(
			Collections.singletonList(reportIndividual1),
			_bqReportIndividualRepository.searchReportIndividuals(
				1L, PageRequest.of(0, 5), "3", 11111L));
		Assertions.assertEquals(
			Collections.emptyList(),
			_bqReportIndividualRepository.searchReportIndividuals(
				2L, PageRequest.of(0, 5), "3", 11111L));
		Assertions.assertEquals(
			Collections.singletonList(reportIndividual2),
			_bqReportIndividualRepository.searchReportIndividuals(
				2L, PageRequest.of(0, 5), "1", 11111L));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testSearchReportIndividualsWithChannelId() {
		ReportIndividual reportIndividual1 = new ReportIndividual();

		reportIndividual1.setId("1");
		reportIndividual1.setSegmentIds(
			new HashSet<>(Arrays.asList(11111L, 33333L)));

		ReportIndividual reportIndividual2 = new ReportIndividual();

		reportIndividual2.setId("3");
		reportIndividual2.setSegmentIds(Collections.singleton(11111L));

		ReportIndividual reportIndividual3 = new ReportIndividual();

		reportIndividual3.setId("5");
		reportIndividual3.setSegmentIds(Collections.emptySet());

		ReportIndividual reportIndividual4 = new ReportIndividual();

		reportIndividual4.setId("11");
		reportIndividual4.setSegmentIds(Collections.emptySet());

		ReportIndividual reportIndividual5 = new ReportIndividual();

		reportIndividual5.setId("12");
		reportIndividual5.setSegmentIds(Collections.emptySet());

		Assertions.assertEquals(
			Arrays.asList(
				reportIndividual1, reportIndividual2, reportIndividual3,
				reportIndividual4, reportIndividual5),
			_bqReportIndividualRepository.searchReportIndividuals(
				1L, PageRequest.of(0, 5), null, null));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testSearchReportIndividualsWithQuery() {
		ReportIndividual reportIndividual1 = new ReportIndividual();

		reportIndividual1.setId("5");
		reportIndividual1.setSegmentIds(Collections.emptySet());

		ReportIndividual reportIndividual2 = new ReportIndividual();

		reportIndividual2.setId("15");
		reportIndividual2.setSegmentIds(Collections.emptySet());

		Assertions.assertEquals(
			Arrays.asList(reportIndividual1, reportIndividual2),
			_bqReportIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), "5", null));
		Assertions.assertEquals(
			Collections.singletonList(reportIndividual2),
			_bqReportIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), "15", null));
	}

	@BQSQLResource(resourcePath = "test_search_bq_report_individuals.sql")
	@Test
	public void testSearchReportIndividualsWithSegmentId() {
		ReportIndividual reportIndividual1 = new ReportIndividual();

		reportIndividual1.setId("1");
		reportIndividual1.setSegmentIds(
			new HashSet<>(Arrays.asList(11111L, 22222L, 33333L)));

		ReportIndividual reportIndividual2 = new ReportIndividual();

		reportIndividual2.setId("3");
		reportIndividual2.setSegmentIds(Collections.singleton(11111L));

		Assertions.assertEquals(
			Collections.singletonList(reportIndividual1),
			_bqReportIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), null, 33333L));
		Assertions.assertEquals(
			Arrays.asList(reportIndividual1, reportIndividual2),
			_bqReportIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), null, 11111L));
	}

	@Autowired
	private BQReportIndividualRepository _bqReportIndividualRepository;

}