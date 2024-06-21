/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.date.dog.util.TimeZoneDogUtil;
import com.liferay.osb.asah.common.model.Distribution;
import com.liferay.osb.asah.common.model.Individual;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.repository.BQIndividualRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.configuration.JDBCTestConfiguration;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Ivica Cardic
 */
@Import(JDBCTestConfiguration.class)
public class BQIndividualRepositoryTest
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "test_bq_individual_repository.sql")
	@Test
	public void testCountBQIndividuals() {
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, null, null, null, _SEGMENT_ID));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, "all", null, null, _SEGMENT_ID));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, "fail", null, null, _SEGMENT_ID));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsActivitiesFilter() {
		String assetId =
			"da70dfa4d9f95ac979f921e8e623358236313f334afcd06cddf8a5621cf6a1e9";

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"eq ''2022-12-16'')', operator='ge', value=1))",
				false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(not(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"eq ''2022-12-16'')', operator='ge', value=1)))",
				false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"gt ''2022-12-17'')', operator='ge', value=1))",
				false, null, null));
		Assertions.assertEquals(
			3,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(not(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"gt ''2022-12-17'')', operator='ge', value=1)))",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"lt ''2022-12-17'')', operator='ge', value=1))",
				false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(not(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"lt ''2022-12-17'')', operator='ge', value=1)))",
				false, null, null));

		assetId =
			"0e12831a7047f759733b21f028525039607350b1b1b4fe904595427e72ea0d9b";

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(activities.filterByCount(filter='(activityKey eq ''Blog#" +
					"commentPosted#" + assetId + "'' and day lt ''" +
						"2022-12-17'')', operator='ge', value=1))",
				false, null, null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsActivitiesFilterWithTimeZoneId() {
		TimeZoneDog timeZoneDog = Mockito.mock(TimeZoneDog.class);

		Mockito.when(
			timeZoneDog.getZoneId()
		).thenReturn(
			ZoneId.of("Japan")
		);

		TimeZoneDogUtil.setTimeZoneDog(timeZoneDog);

		String assetId =
			"da70dfa4d9f95ac979f921e8e623358236313f334afcd06cddf8a5621cf6a1e9";

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(activities.filterByCount(filter='(activityKey eq " +
					"''WebContent#webContentViewed#" + assetId + "'' and day " +
						"eq ''2022-12-18'')', operator='ge', value=1))",
				false, null, null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsCustomFieldFilter() {
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "contains(custom/Favorite_Food/value, 'Rice')", false,
				null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "not contains(custom/Favorite_Food/value, 'Rice')", false,
				null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "contains(custom/Favorite_Number/value, 3)", false, null,
				null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "not contains(custom/Favorite_Number/value, 5)", false,
				null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Favorite_Number/value ge 3", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"custom/Favorite_Number/value gt 2 and " +
					"custom/Favorite_Number/value lt 3",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Favorite_Number/value gt 3", false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Favorite_Number/value le 4", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Favorite_Number/value lt 2", false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Hobbies/value eq 'ing'", false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Hobbies/value eq 'Exercise'", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Hobbies/value ne 'Exercise'", false, null, null));
		Assertions.assertEquals(
			3,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Hobbies/value ne 'ing'", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Salary/value ge 120000.30", false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Salary/value gt 100000", false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Salary/value le 100000.00", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Salary/value lt 100001", false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Zip-Code/value eq 91765", false, null, null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsDateCustomFieldFilter() {
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Joined_Date/value eq '2022-04-30'", false, null,
				null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Joined_Date/value ge '2022-04-30'", false, null,
				null));
		Assertions.assertEquals(
			3,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Joined_Date/value gt '2022-01-01'", false, null,
				null));
		Assertions.assertEquals(
			3,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Joined_Date/value le '2022-06-01'", false, null,
				null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "custom/Joined_Date/value lt '2022-05-03'", false, null,
				null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_4.sql")
	@Test
	public void testCountBQIndividualsExcludesHiddenEvents() {
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				null, 1L, null, null, null, null, null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_2.sql")
	@Test
	public void testCountBQIndividualsIndividualSegmentIdsFilter() {
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "individualSegmentIds eq '1'", false, null, null));

		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				11L, "individualSegmentIds eq '1'", PageRequest.of(0, 5), null,
				null);

		Individual individual = individuals.get(0);

		Assertions.assertEquals(
			"5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a",
			individual.getEmailAddressHashed());
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_2.sql")
	@Test
	public void testCountBQIndividualsIndividualSegmentIdsFilter2() {
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "contains(demographics/emailAddress/value, '.com')", false,
				null, 1L));

		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				11L, "contains(demographics/emailAddress/value, '.com')",
				PageRequest.of(0, 5), null, 1L);

		Individual individual = individuals.get(0);

		Assertions.assertEquals(
			"5970d88ec4ed505177361de1b17a3f2debf7c4f630c14f075a823ec97942692a",
			individual.getEmailAddressHashed());
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsInterestFilter() {
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(interests.filter(filter='(name eq ''analytics'' and score " +
					"eq ''true'')'))",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"(interests.filter(filter='(name eq ''analytics'' and score " +
					"eq ''false'')'))",
				false, null, null));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_3.sql")
	@Test
	public void testCountBQIndividualsLastActivityDateSince() {
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividualsLastActivityDateSince(
				DateUtils.addYears(new Date(), 1)));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividualsLastActivityDateSince(
				new Date()));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividualsLastActivityDateSince(
				DateUtils.addDays(new Date(), -10)));
		Assertions.assertEquals(
			5,
			_bqIndividualRepository.countBQIndividualsLastActivityDateSince(
				DateUtils.addDays(new Date(), -20)));
		Assertions.assertEquals(
			8,
			_bqIndividualRepository.countBQIndividualsLastActivityDateSince(
				DateUtils.addDays(new Date(), -30)));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountBQIndividualsOrganizationsFilter() {

		// Custom fields

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"custom/Organization/value eq 'Developer' and " +
					"organizations.filter(filter='(id eq " +
						"''23k92323l923lf0as'')')",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"custom/Birth_Country/value eq 'England' and " +
					"custom/Zip_Code/value eq 91765 and userGroupIds eq '" +
						"newr87232kjhdsf89'",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(custom/Organization_Type" +
					"/value eq ''test'')')",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(custom/Divisions/value ge 35)')",
				false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(custom/Divisions/value gt 35)')",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(custom/Year/value le 2023)')",
				false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(custom/Year/value lt 2023)')",
				false, null, null));

		// Hierarchy path known/unknown

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L, "(organizations.filter(filter='(hierarchyPath ne null)'))",
				false, null, null));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				11L, "(organizations.filter(filter='(hierarchyPath eq null)'))",
				false, null, null));

		// Modified date

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(modifiedDate eq " +
					"''2022-12-18'')')",
				false, null, null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(modifiedDate gt " +
					"''2022-12-17'')')",
				false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(modifiedDate lt " +
					"''2022-12-18'')')",
				false, null, null));

		// Name

		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(name eq ''Organization 1'')')",
				false, null, null));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				11L,
				"organizations.filter(filter='(name ne ''Organization 1'')')",
				false, null, null));
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testCountBQIndividualsWithSuppression() {
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, null, null, null, _SEGMENT_ID));
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, "all", null, null, _SEGMENT_ID));
		Assertions.assertEquals(
			0,
			_bqIndividualRepository.countBQIndividuals(
				null, 11L, null, "fail", null, null, _SEGMENT_ID));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testCountIndividualFieldValuesDemographics() {
		Assertions.assertEquals(
			3,
			_bqIndividualRepository.countIndividualFieldValuesDemographics(
				11L, "firstName", null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countIndividualFieldValuesDemographics(
				11L, "jobTitle", null));
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testCountIndividualFieldValuesDemographicsWithSuppression() {
		Assertions.assertEquals(
			2,
			_bqIndividualRepository.countIndividualFieldValuesDemographics(
				11L, "firstName", null));
		Assertions.assertEquals(
			1,
			_bqIndividualRepository.countIndividualFieldValuesDemographics(
				11L, "jobTitle", null));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
	@Test
	public void testCountReportIndividuals() {
		Assertions.assertEquals(
			15L,
			_bqIndividualRepository.countReportIndividuals(null, null, null));
		Assertions.assertEquals(
			1L,
			_bqIndividualRepository.countReportIndividuals(1L, "3", 11111L));
		Assertions.assertEquals(
			0L,
			_bqIndividualRepository.countReportIndividuals(2L, "3", 11111L));
		Assertions.assertEquals(
			1, _bqIndividualRepository.countReportIndividuals(2L, "1", 11111L));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithChannelId() {
		Assertions.assertEquals(
			8L, _bqIndividualRepository.countReportIndividuals(1L, null, null));
		Assertions.assertEquals(
			8L, _bqIndividualRepository.countReportIndividuals(2L, null, null));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithQuery() {
		Assertions.assertEquals(
			2L,
			_bqIndividualRepository.countReportIndividuals(null, "5", null));
		Assertions.assertEquals(
			1L,
			_bqIndividualRepository.countReportIndividuals(null, "15", null));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
	@Test
	public void testCountReportIndividualsWithSegmentId() {
		Assertions.assertEquals(
			2L,
			_bqIndividualRepository.countReportIndividuals(null, null, 11111L));
		Assertions.assertEquals(
			1L,
			_bqIndividualRepository.countReportIndividuals(null, null, 33333L));
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testFindByChannelIdAndId() {
		Optional<Individual> individualOptional =
			_bqIndividualRepository.findByChannelIdAndId(
				11L,
				"47ff64395860b1d498241d907069f649b98c198a95b3ba5303b870940585" +
					"90c1");

		Assertions.assertTrue(individualOptional.isPresent());

		Individual individual = individualOptional.get();

		Assertions.assertEquals(
			"47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1",
			individual.getId());

		individualOptional = _bqIndividualRepository.findByChannelIdAndId(
			11L,
			"47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c2");

		Assertions.assertTrue(individualOptional.isPresent());

		individual = individualOptional.get();

		Assertions.assertEquals(
			"47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c2",
			individual.getId());

		individualOptional = _bqIndividualRepository.findByChannelIdAndId(
			11L,
			"47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c3");

		Assertions.assertFalse(individualOptional.isPresent());
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
	@Test
	public void testFindReportIndividualById() {
		Optional<ReportIndividual> reportIndividualOptional =
			_bqIndividualRepository.findReportIndividualById("1");

		Assertions.assertTrue(reportIndividualOptional.isPresent());

		ReportIndividual reportIndividual = new ReportIndividual();

		reportIndividual.setId("1");
		reportIndividual.setSegmentIds(
			new HashSet<>(Arrays.asList(11111L, 22222L, 33333L)));

		Assertions.assertEquals(
			reportIndividual, reportIndividualOptional.get());

		reportIndividualOptional =
			_bqIndividualRepository.findReportIndividualById("2");

		Assertions.assertFalse(reportIndividualOptional.isPresent());

		reportIndividualOptional =
			_bqIndividualRepository.findReportIndividualById("abc");

		Assertions.assertFalse(reportIndividualOptional.isPresent());
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testGetIndividualDistributions() {
		List<Distribution> expectedDistributions =
			new ArrayList<Distribution>() {
				{
					add(new Distribution(1, Collections.singletonList("test")));
					add(new Distribution(1, Collections.singletonList("user")));
				}
			};

		Assertions.assertEquals(
			expectedDistributions,
			_bqIndividualRepository.getIndividualDistributions(
				11L, "firstName", "text", null,
				PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name")))));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository.sql")
	@Test
	public void testSearchBQIndividuals() {
		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				null, 11L, null, "all", null,
				PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id"))), null,
				null);

		Assertions.assertEquals(2, individuals.size(), individuals.toString());

		Individual individual = individuals.get(0);

		Assertions.assertEquals(4L, individual.getActivitiesCount());
		Assertions.assertEquals(
			DateUtil.toUTCDate("2022-12-13T23:59:59.999Z"),
			individual.getFirstActivityDate());
		Assertions.assertEquals(
			DateUtil.toUTCDate("2022-12-17T23:59:59.999Z"),
			individual.getLastActivityDate());
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testSearchBQIndividualsWithSuppression() {
		List<Individual> individuals =
			_bqIndividualRepository.searchBQIndividuals(
				null, 11L, null, null, null,
				PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id"))), null,
				null);

		Assertions.assertEquals(2, individuals.size(), individuals.toString());

		Individual individual = individuals.get(0);

		Assertions.assertEquals(4L, individual.getActivitiesCount());
		Assertions.assertEquals(
			DateUtil.toUTCDate("2022-12-13T23:59:59.999Z"),
			individual.getFirstActivityDate());
		Assertions.assertEquals(
			DateUtil.toUTCDate("2022-12-17T23:59:59.999Z"),
			individual.getLastActivityDate());
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testSearchIndividualFieldValuesDemographics() {
		PageRequest pageRequest = PageRequest.of(0, 10);

		Assertions.assertEquals(
			Arrays.asList("Joe", "Marcus", "Nina"),
			_bqIndividualRepository.searchIndividualFieldValuesDemographics(
				11L, "firstName", null, pageRequest));
		Assertions.assertEquals(
			Arrays.asList("Engineer"),
			_bqIndividualRepository.searchIndividualFieldValuesDemographics(
				11L, "jobTitle", null, pageRequest));
	}

	@BQSQLResource(resourcePath = "test_bq_individual_repository_1.sql")
	@Test
	public void testSearchIndividualFieldValuesDemographicsUnknownChannel() {
		Assertions.assertEquals(
			Collections.emptyList(),
			_bqIndividualRepository.searchIndividualFieldValuesDemographics(
				42L, "firstName", null, PageRequest.of(0, 10)));
	}

	@BQSQLResource(
		resourcePath = "test_bq_individual_repository_with_suppression.sql"
	)
	@Test
	public void testSearchIndividualFieldValuesDemographicsWithSuppression() {
		PageRequest pageRequest = PageRequest.of(0, 10);

		Assertions.assertEquals(
			Arrays.asList("Test", "User"),
			_bqIndividualRepository.searchIndividualFieldValuesDemographics(
				11L, "firstName", null, pageRequest));
		Assertions.assertEquals(
			Arrays.asList("Tester"),
			_bqIndividualRepository.searchIndividualFieldValuesDemographics(
				11L, "jobTitle", null, pageRequest));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
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
			_bqIndividualRepository.searchReportIndividuals(
				1L, PageRequest.of(0, 5), "3", 11111L));
		Assertions.assertEquals(
			Collections.emptyList(),
			_bqIndividualRepository.searchReportIndividuals(
				2L, PageRequest.of(0, 5), "3", 11111L));
		Assertions.assertEquals(
			Collections.singletonList(reportIndividual2),
			_bqIndividualRepository.searchReportIndividuals(
				2L, PageRequest.of(0, 5), "1", 11111L));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
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
			_bqIndividualRepository.searchReportIndividuals(
				1L, PageRequest.of(0, 5), null, null));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
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
			_bqIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), "5", null));
		Assertions.assertEquals(
			Collections.singletonList(reportIndividual2),
			_bqIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), "15", null));
	}

	@BQSQLResource(resourcePath = "test_search_report_individuals.sql")
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
			_bqIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), null, 33333L));
		Assertions.assertEquals(
			Arrays.asList(reportIndividual1, reportIndividual2),
			_bqIndividualRepository.searchReportIndividuals(
				null, PageRequest.of(0, 2), null, 11111L));
	}

	private static final Long _SEGMENT_ID = 11L;

	@Autowired
	private BQIndividualRepository _bqIndividualRepository;

}