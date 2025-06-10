/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dog.ReportDog;
import com.liferay.osb.asah.common.entity.Channel;
import com.liferay.osb.asah.common.entity.Segment;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.ChannelRepository;
import com.liferay.osb.asah.common.repository.SegmentRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.time.LocalDate;

import org.apache.commons.io.IOUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Marcos Martins
 */
public class ReportDogTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BeforeEach
	public void setUp() {
		Channel channel = new Channel();

		channel.setId(1L);
		channel.setIsNew(true);
		channel.setName("Channel Test");

		channel = _channelRepository.save(channel);

		Segment segment = new Segment();

		segment.setChannelId(channel.getId());
		segment.setId(1001L);
		segment.setIsNew(true);
		segment.setName("Segment 1");

		_segmentRepository.save(segment);
	}

	@AfterEach
	public void tearDown() {
		_segmentRepository.deleteAll();

		_channelRepository.deleteAll();
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_blog.sql")
	@Test
	public void testGetCSVReportAssetBlog() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_blog_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_blog_filtered_by_" +
				"query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Blog 1", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
		_assertCSVFile(
			"dependencies" +
				"/test_get_csv_report_asset_blog_sorted_by_views_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"viewsMetric", "asc"},
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
	}

	@BQSQLResource(
		resourcePath = "test_get_csv_report_asset_document_library.sql"
	)
	@Test
	public void testGetCSVReportAssetDocumentLibrary() throws Exception {
		_assertCSVFile(
			"dependencies" +
				"/test_get_csv_report_asset_document_library_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_" +
				"document_library_filtered_by_query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Document 3", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_" +
				"document_library_sorted_by_downloads_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"downloadsMetric", "asc"},
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_form.sql")
	@Test
	public void testGetCSVReportAssetForm() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_form_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_form_filtered_by_" +
				"query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Form 1", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_form_sorted_by_" +
				"submissions_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"submissionsMetric", "asc"},
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_individual.sql")
	@Test
	public void testGetCSVReportAssetIndividual() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_expected.csv",
			_reportDog.getCSVReport(
				"https://www.beryl.com/delivery", "page", 1L, null, null, null,
				null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"blog_filtered_by_email_address_in_query_expected.csv",
			_reportDog.getCSVReport(
				"3", "blog", 1L, null, "test3@liferay.com", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"blog_filtered_by_name_in_query_expected.csv",
			_reportDog.getCSVReport(
				"1", "blog", 1L, null, "Test 1", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"document_library_filtered_by_email_address_in_query_" +
					"expected.csv",
			_reportDog.getCSVReport(
				"3", "document", 1L, null, "test3@liferay.com", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"document_library_filtered_by_name_in_query_expected.csv",
			_reportDog.getCSVReport(
				"1", "document", 1L, null, "Test 1", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"form_filtered_by_email_address_in_query_expected.csv",
			_reportDog.getCSVReport(
				"2", "form", 1L, null, "test2@liferay.com", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"form_filtered_by_name_in_query_expected.csv",
			_reportDog.getCSVReport(
				"1", "form", 1L, null, "Test 1", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"journal_filtered_by_email_address_in_query_expected.csv",
			_reportDog.getCSVReport(
				"3", "journal", 1L, null, "test3@liferay.com", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"journal_filtered_by_name_in_query_expected.csv",
			_reportDog.getCSVReport(
				"2", "journal", 1L, null, "Test 2", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_" +
				"individual_for_a_given_page_filtered_by_" +
					"email_address_in_query_expected.csv",
			_reportDog.getCSVReport(
				"https://www.beryl.com/delivery", "page", 1L, null,
				"test3@liferay.com", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_" +
				"individual_for_a_given_page_filtered_by_" +
					"name_in_query_expected.csv",
			_reportDog.getCSVReport(
				"https://www.beryl.com/delivery", "page", 1L, null, "Test 1",
				null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_journal.sql")
	@Test
	public void testGetCSVReportAssetJournal() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_journal_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_journal_filtered_by_" +
				"query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Journal 3", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_journal_sorted_by_" +
				"views_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"viewsMetric", "asc"},
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_page.sql")
	@Test
	public void testGetCSVReportAssetPage() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_page_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_page_filtered_by_" +
				"query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Liferay", null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_page_sorted_by_" +
				"entrances_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"entrancesMetric", "asc"},
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_blog.sql")
	@Test
	public void testGetCSVReportCountAssetBlog() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Blog 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
	}

	@BQSQLResource(
		resourcePath = "test_get_csv_report_asset_document_library.sql"
	)
	@Test
	public void testGetCSVReportCountAssetDocumentLibrary() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Document 3",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_form.sql")
	@Test
	public void testGetCSVReportCountAssetForm() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Form 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_individual.sql")
	@Test
	public void testGetCSVReportCountAssetIndividual() throws Exception {
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "blog", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "blog", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "document", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "document", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "form", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"2", "form", 1L, null, "test2@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"2", "journal", 1L, null, "Test 2",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "journal", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null,
				"test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_journal.sql")
	@Test
	public void testGetCSVReportCountAssetJournal() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Journal 3",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_asset_page.sql")
	@Test
	public void testGetCSVReportCountAssetPage() throws Exception {
		Assertions.assertEquals(
			2,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Liferay",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_event.sql")
	@Test
	public void testGetCSVReportCountEvent() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"event"));
		Assertions.assertEquals(
			2,
			_reportDog.getCSVReportCount(
				null, null, 1L, "1", null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"event"));
		Assertions.assertEquals(
			0,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "3",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"event"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_individual.sql")
	@Test
	public void testGetCSVReportCountIndividual() throws Exception {
		Assertions.assertEquals(
			4,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null, null, "individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Test 3", null, "individual"));
		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "test2@liferay.com", null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_event.sql")
	@Test
	public void testGetCSVReportEvent() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_asset_event_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"event"));
		_assertCSVFile(
			"dependencies" +
				"/test_get_csv_report_asset_individual_event_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, "1", null, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"event"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_individual.sql")
	@Test
	public void testGetCSVReportIndividual() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_individual_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null, null, "individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_individual_filtered_by_email_" +
				"address_in_query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "test2@liferay.com", null, null, null,
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_individual_filtered_by_name_" +
				"in_query_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, "Test 3", null, null, null,
				"individual"));
		_assertCSVFile(
			"dependencies/test_get_csv_report_individual_sorted_by_job_title_" +
				"expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null,
				new String[] {"jobTitle", "asc"}, null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_membership.sql")
	@Test
	public void testGetCSVReportMembership() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_membership_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, 1001L,
				new String[] {"givenName", "asc", "familyName", "asc"}, null,
				"membership"));
	}

	@BQSQLResource(resourcePath = "test_get_csv_report_search_terms.sql")
	@Test
	public void testGetCSVReportSearchTerms() throws Exception {
		_assertCSVFile(
			"dependencies/test_get_csv_report_search_terms_expected.csv",
			_reportDog.getCSVReport(
				null, null, 1L, null, null, null, null,
				TimeRange.of(
					LocalDate.now(),
					LocalDate.now(
					).minusDays(
						7
					)),
				"search-terms"));
	}

	private void _assertCSVFile(String expectedFileName, File file)
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			expectedFileName, getClass());

		try (InputStream inputStream = new FileInputStream(file)) {
			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@Autowired
	private ChannelRepository _channelRepository;

	@Autowired
	private ReportDog _reportDog;

	@Autowired
	private SegmentRepository _segmentRepository;

}