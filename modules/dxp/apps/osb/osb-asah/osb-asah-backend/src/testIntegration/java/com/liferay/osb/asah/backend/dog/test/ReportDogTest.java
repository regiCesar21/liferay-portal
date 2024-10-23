/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dog.ReportDog;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.ChannelRepository;
import com.liferay.osb.asah.common.repository.SegmentRepository;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.annotation.SQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.io.FileInputStream;
import java.io.InputStream;

import java.time.LocalDate;

import org.apache.commons.io.IOUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Marcos Martins
 */
public class ReportDogTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@AfterEach
	public void tearDown() {
		_segmentRepository.deleteAll();
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVCountReportAssetForm() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetBlog() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_blog_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"blog"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetBlogFilteredByQuery() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_blog_filtered_by_" +
				"query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Blog 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"blog"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetBlogSortedByViews() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies" +
				"/test_get_csv_report_asset_blog_sorted_by_views_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"viewsMetric", "asc"},
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"blog"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetCountDocumentLibraryFilteredByQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Document 3",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetDocumentLibrary() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies" +
				"/test_get_csv_report_asset_document_library_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"document"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetDocumentLibraryFilteredByQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_" +
				"document_library_filtered_by_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Document 3", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"document"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetDocumentLibrarySortedByDownloads()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_" +
				"document_library_sorted_by_downloads_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"downloadsMetric", "asc"},
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"document"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetEvent() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_event_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"event"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetForm() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_form_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"form"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetFormFilteredByQuery() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_form_filtered_by_" +
				"query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Form 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"form"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetFormSortedBySubmissions()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_form_sorted_by_" +
				"submissions_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"submissionsMetric", "asc"},
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"form"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividual() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"https://www.beryl.com/delivery", "page", 1L, null, null,
					null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualEvent() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies" +
				"/test_get_csv_report_asset_individual_event_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, "1", null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"event"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenBlogFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"blog_filtered_by_email_address_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"3", "blog", 1L, null, "test3@liferay.com", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenBlogFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"blog_filtered_by_name_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"1", "blog", 1L, null, "Test 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenDocumentLibraryFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"document_library_filtered_by_email_address_in_query_" +
					"expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"3", "document", 1L, null, "test3@liferay.com", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenDocumentLibraryFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"document_library_filtered_by_name_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"1", "document", 1L, null, "Test 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenFormFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"form_filtered_by_email_address_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"2", "form", 1L, null, "test2@liferay.com", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenFormFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"form_filtered_by_name_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"1", "form", 1L, null, "Test 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenJournalFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"journal_filtered_by_email_address_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"3", "journal", 1L, null, "test3@liferay.com", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenJournalFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_individual_for_a_given_" +
				"journal_filtered_by_name_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"2", "journal", 1L, null, "Test 2", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenPageFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_" +
				"individual_for_a_given_page_filtered_by_" +
					"email_address_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"https://www.beryl.com/delivery", "page", 1L, null,
					"test3@liferay.com", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetIndividualForAGivenPageFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_" +
				"individual_for_a_given_page_filtered_by_" +
					"name_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					"https://www.beryl.com/delivery", "page", 1L, null,
					"Test 1", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetJournal() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_journal_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"journal"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetJournalFilteredByQuery() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_journal_filtered_by_" +
				"query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Journal 3", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"journal"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetJournalSortedByViews() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_journal_sorted_by_" +
				"views_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"viewsMetric", "asc"},
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"journal"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetPage() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_page_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"page"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetPageFilteredByQuery() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_page_filtered_by_" +
				"query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Liferay", null, null,
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"page"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportAssetPageSortedByEntrances() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_asset_page_sorted_by_" +
				"entrances_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"entrancesMetric", "asc"},
					TimeRange.of(
						LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
					"page"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetBlog() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetBlogFilteredByQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Blog 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetBlogSortedByViews() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"blog"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetDocumentLibrary() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetDocumentLibrarySortedByDownloads()
		throws Exception {

		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"document"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetEvent() throws Exception {
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

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetFormFilteredByQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Form 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetFormSortedBySubmissions()
		throws Exception {

		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"form"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividual() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenBlogFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "blog", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenBlogFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "blog", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenDocumentLibraryFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "document", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenDocumentLibraryFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "document", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenFormFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"2", "form", 1L, null, "test2@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenFormFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"1", "form", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenJournalFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"3", "journal", 1L, null, "test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenJournalFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"2", "journal", 1L, null, "Test 2",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenPageFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null,
				"test3@liferay.com",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetIndividualForAGivenPageFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				"https://www.beryl.com/delivery", "page", 1L, null, "Test 1",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetJournal() throws Exception {
		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetJournalFilteredByQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Journal 3",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetJournalSortedByViews()
		throws Exception {

		Assertions.assertEquals(
			3,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"journal"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetPage() throws Exception {
		Assertions.assertEquals(
			2,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetPageFilteredByQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Liferay",
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountAssetPageSortedByEntrances()
		throws Exception {

		Assertions.assertEquals(
			2,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null,
				TimeRange.of(
					LocalDate.of(2023, 11, 6), LocalDate.of(2023, 11, 3)),
				"page"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountIndividual() throws Exception {
		Assertions.assertEquals(
			4,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null, null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountIndividualFilteredByEmailAddressInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "test2@liferay.com", null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountIndividualFilteredByNameInQuery()
		throws Exception {

		Assertions.assertEquals(
			1,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, "Test 3", null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportCountIndividualSortedByJobTitle()
		throws Exception {

		Assertions.assertEquals(
			4,
			_reportDog.getCSVReportCount(
				null, null, 1L, null, null, null, "individual"));
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportIndividual() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_individual_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null, null, null,
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportIndividualFilteredByEmailAddressInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_individual_filtered_by_email_" +
				"address_in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "test2@liferay.com", null, null, null,
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportIndividualFilteredByNameInQuery()
		throws Exception {

		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_individual_filtered_by_name_" +
				"in_query_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, "Test 3", null, null, null,
					"individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportIndividualSortedByJobTitle() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_individual_sorted_by_job_title_" +
				"expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, null,
					new String[] {"jobTitle", "asc"}, null, "individual"))) {

			Assertions.assertTrue(
				IOUtils.contentEquals(
					classPathResource.getInputStream(), inputStream));
		}
	}

	@BQSQLResource(resourcePath = "test_report_dog.sql")
	@SQLResource(resourcePath = "test_segment.sql")
	@Test
	public void testGetCSVReportMembership() throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(
			"dependencies/test_get_csv_report_membership_expected.csv",
			getClass());

		try (InputStream inputStream = new FileInputStream(
				_reportDog.getCSVReport(
					null, null, 1L, null, null, 1001L,
					new String[] {"givenName", "asc", "familyName", "asc"},
					null, "membership"))) {

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