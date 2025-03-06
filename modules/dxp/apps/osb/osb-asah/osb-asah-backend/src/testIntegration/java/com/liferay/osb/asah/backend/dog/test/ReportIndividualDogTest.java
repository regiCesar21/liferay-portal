/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dog.ReportIndividualDog;
import com.liferay.osb.asah.backend.dog.helper.SearchQueryContext;
import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.BlogMetricType;
import com.liferay.osb.asah.backend.model.Individual;
import com.liferay.osb.asah.common.entity.DataSource;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.model.ResultBag;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.DataSourceRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.annotation.SQLResource;
import com.liferay.osb.asah.test.util.faro.FaroInfoTestUtil;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 * @author André Miranda
 */
public class ReportIndividualDogTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BeforeEach
	public void setUp() {
		DataSource dataSource = FaroInfoTestUtil.buildLiferayDataSource();

		_dataSourceRepository.save(dataSource);

		// TODO Add BQFieldMapping with _FIELD_NAMES

	}

	@SQLResource(resourcePath = "test_search_report_individual_page.sql")
	@Test
	public void testFetchReportIndividual() {
		ReportIndividual expectedReportIndividual = new ReportIndividual();

		expectedReportIndividual.setId("1");
		expectedReportIndividual.setSegmentIds(
			new HashSet<>(Arrays.asList(11111L, 22222L, 33333L)));

		Assertions.assertEquals(
			expectedReportIndividual,
			_reportIndividualDog.fetchReportIndividual("1"));

		Assertions.assertNull(_reportIndividualDog.fetchReportIndividual("2"));
		Assertions.assertNull(_reportIndividualDog.fetchReportIndividual("4"));
		Assertions.assertNull(_reportIndividualDog.fetchReportIndividual("6"));
		Assertions.assertNull(_reportIndividualDog.fetchReportIndividual("8"));
		Assertions.assertNull(_reportIndividualDog.fetchReportIndividual("10"));
		Assertions.assertNull(
			_reportIndividualDog.fetchReportIndividual("abc"));
	}

	@SQLResource(resourcePath = "test_search_report_individual_page.sql")
	@Test
	public void testSearchReportIndividualPage() {
		Page<ReportIndividual> reportIndividualPage =
			_reportIndividualDog.searchReportIndividualPage(
				1L, 0, null, null, 20);

		Assertions.assertEquals(1, reportIndividualPage.getTotalPages());
		Assertions.assertEquals(15, reportIndividualPage.getTotalElements());

		for (ReportIndividual reportIndividual :
				reportIndividualPage.getContent()) {

			if (Objects.equals(reportIndividual.getId(), "1")) {
				Assertions.assertEquals(
					reportIndividual.getSegmentIds(),
					new HashSet<>(Arrays.asList(11111L, 22222L, 33333L)));
			}
			else if (Objects.equals(reportIndividual.getId(), "3")) {
				Assertions.assertEquals(
					reportIndividual.getSegmentIds(),
					Collections.singleton(11111L));
			}
			else {
				Assertions.assertEquals(
					reportIndividual.getSegmentIds(), Collections.emptySet());
			}
		}

		reportIndividualPage = _reportIndividualDog.searchReportIndividualPage(
			1L, 0, "first1", null, 20);

		Assertions.assertEquals(1, reportIndividualPage.getTotalPages());
		Assertions.assertEquals(1, reportIndividualPage.getTotalElements());

		List<ReportIndividual> reportIndividuals =
			reportIndividualPage.getContent();

		ReportIndividual reportIndividual = reportIndividuals.get(0);

		Assertions.assertEquals("1", reportIndividual.getId());
	}

	@BQSQLResource(resourcePath = "test_report_individual_dog.sql")
	@Test
	public void testSegmentIndividuals() {
		SearchQueryContext searchQueryContext = new SearchQueryContext(
			"1", AssetType.BLOG);

		searchQueryContext.setTimeRange(TimeRange.LAST_30_DAYS);

		ResultBag<Individual> individualResultBag =
			_reportIndividualDog.getIndividualResultBag(
				null, BlogMetricType.VIEWS, searchQueryContext, 1, 1);

		Assertions.assertEquals(
			3, individualResultBag.getTotal(), individualResultBag.toString());

		List<Individual> individuals = individualResultBag.getResults();

		Individual individual = individuals.get(0);

		Assertions.assertEquals("Test1 Test1", individual.getName());
		Assertions.assertEquals(
			"test1@liferay.com", individual.getEmailAddress());

		individualResultBag = _reportIndividualDog.getIndividualResultBag(
			"Test1", BlogMetricType.VIEWS, searchQueryContext, 10, 0);

		Assertions.assertEquals(
			1, individualResultBag.getTotal(), individualResultBag.toString());

		individuals = individualResultBag.getResults();

		individual = individuals.get(0);

		Assertions.assertEquals("Test1 Test1", individual.getName());
		Assertions.assertEquals(
			"test1@liferay.com", individual.getEmailAddress());
	}

	@BQSQLResource(resourcePath = "test_report_individual_dog.sql")
	@Test
	public void testSegmentIndividualsSearch() {
		SearchQueryContext searchQueryContext = new SearchQueryContext(
			"1", AssetType.BLOG);

		searchQueryContext.setTimeRange(TimeRange.LAST_30_DAYS);

		ResultBag<Individual> individualResultBag =
			_reportIndividualDog.getIndividualResultBag(
				"john", BlogMetricType.VIEWS, searchQueryContext, 10, 0);

		Assertions.assertEquals(
			1, individualResultBag.getTotal(), individualResultBag.toString());

		List<Individual> individuals = individualResultBag.getResults();

		Individual individual = individuals.get(0);

		Assertions.assertEquals("John Doe", individual.getName());
		Assertions.assertEquals("john@acme.com", individual.getEmailAddress());
	}

	@BQSQLResource(
		resourcePath = "test_report_individual_dog_unknown_individuals.sql"
	)
	@Test
	public void testSegmentUnknownIndividuals() {
		SearchQueryContext searchQueryContext = new SearchQueryContext(
			"1", AssetType.BLOG);

		searchQueryContext.setTimeRange(TimeRange.LAST_30_DAYS);

		ResultBag<Individual> individualResultBag =
			_reportIndividualDog.getIndividualResultBag(
				null, BlogMetricType.VIEWS, searchQueryContext, 2, 0);

		Assertions.assertEquals(
			1, individualResultBag.getTotal(), individualResultBag.toString());

		List<Individual> individuals = individualResultBag.getResults();

		Individual individual = individuals.get(0);

		Assertions.assertEquals("1", individual.getId());
		Assertions.assertEquals("john@acme.com", individual.getEmailAddress());
	}

	@Autowired
	private DataSourceRepository _dataSourceRepository;

	@Autowired
	private ReportIndividualDog _reportIndividualDog;

}