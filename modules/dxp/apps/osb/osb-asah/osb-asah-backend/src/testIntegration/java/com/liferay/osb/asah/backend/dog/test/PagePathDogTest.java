/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.backend.dog.PagePathDog;
import com.liferay.osb.asah.backend.model.AdjacentPageViewsMetric;
import com.liferay.osb.asah.common.dog.BQMembershipDog;
import com.liferay.osb.asah.common.dog.PreferenceDog;
import com.liferay.osb.asah.common.dog.SegmentDog;
import com.liferay.osb.asah.common.entity.Preference;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.util.SetUtil;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.annotation.SQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcellus Tavares
 */
public class PagePathDogTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@AfterEach
	public void tearDown() {
		Preference preference = _preferenceDog.getPreference("time-zone-id");

		if (!StringUtils.equals(preference.getValue(), "UTC")) {
			_preferenceDog.savePreference("time-zone-id", "UTC");
		}
	}

	@BQSQLResource(resourcePath = "page_path_events.sql")
	@Test
	public void testGetAdjacentPagesViewsMetric() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"http://google.com", Boolean.TRUE, Boolean.TRUE,
					"http://google.com", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"http://liferay.com/b", Boolean.FALSE, Boolean.FALSE,
					"B - Liferay DXP", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"http://liferay.com/d", Boolean.FALSE, Boolean.FALSE,
					"D - Liferay DXP", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"http://liferay.com/d", Boolean.FALSE, Boolean.TRUE,
					"D - Liferay DXP", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"http://liferay.com/e", Boolean.FALSE, Boolean.TRUE,
					"E - Liferay DXP", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					BigDecimal.ONE)),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"http://liferay.com/a", 1L, null, TimeRange.LAST_30_DAYS,
				"A - Liferay DXP"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_custom_date_range.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricCustomDateRange() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricLast7Days() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.FALSE, "Try Our Online Calculator", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(3))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_7_DAYS, "From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_last_24_hours.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricLast24Hours() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.TRUE, "Are Calculators Computers?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.FALSE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.FALSE, "Try Our Online Calculator", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(3))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_24_HOURS,
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricLast28Days() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.TRUE, "Are Calculators Computers?",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.FALSE, "Try Our Online Calculator", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(3))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_28_DAYS, "From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricLast30Days() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.TRUE, "Are Calculators Computers?",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.FALSE, "Try Our Online Calculator", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.FALSE, "How Do Computers Talk?", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(3))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_30_DAYS, "From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricLast90Days() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(5)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.FALSE, "How Do Computers Talk?", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(4))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_90_DAYS, "From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricLast180Days() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.TRUE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(6)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(5))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.LAST_180_DAYS,
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_no_direct_access.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricNoDirectAccess() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-03"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_vews_metric_no_next_page_views.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricNoNextPageViews() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE)),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_view_metric_no_previous_page_views.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricNoPreviousPageViews() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					new BigDecimal(6)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(resourcePath = "test_get_adjacent_pages_views_metric.sql")
	@Test
	public void testGetAdjacentPagesViewsMetricNoResults() {
		_assertEquals(
			Collections.emptySet(),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("1971-01-01"),
					LocalDate.parse("1970-01-01")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_one_next_page_view.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricOneNextPageView() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.FALSE, "Encoding Decimal to Binary",
					new BigDecimal(5))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_one_previous_page_view.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricOnePreviousPageView() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.TRUE, "Are Calculators Computers?",
					new BigDecimal(6)),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_three_next_page_views.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricThreeNextPageViews() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_three_previous_page_views.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricThreePreviousPageViews() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/calculator", Boolean.FALSE,
					Boolean.TRUE, "Are Calculators Computers?",
					new BigDecimal(3)),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-03"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_segment_id_bq.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricWithSegmentId1() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.FALSE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE)),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, 24680L,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_segment_id_bq.sql"
	)
	@SQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_segment_id.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricWithSegmentId2() {
		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.FALSE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE)),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, 24680L,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));

		_bqMembershipDog.updateBQMemberships(
			"(activities.filterByCount(filter='(activityKey eq " +
				"''Page#pageViewed#10981d0044ea936f74b42f2a68c41fe7f11c15b0bd" +
					"8858f85dbf940d79704ca6'')', operator='ge', value=1))",
			true, _segmentDog.fetchSegment(24680L));

		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE)),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, 24680L,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_time_zone.sql"
	)
	@Test
	public void testGetAdjacentPagesViewsMetricWithTimeZone() {
		_preferenceDog.savePreference("time-zone-id", "America/Los_Angeles");

		_assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/binary", Boolean.FALSE,
					Boolean.FALSE, "How Binary Encoding is Used in a Computer",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.FALSE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE,
					Boolean.FALSE, "Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/encoding", Boolean.FALSE,
					Boolean.TRUE, "How Do Computers Talk?", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.FALSE, "others",
					new BigDecimal(2)),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"others", Boolean.TRUE, Boolean.TRUE, "others",
					new BigDecimal(2))),
			_pagePathDog.getAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L, null,
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_segment_id_bq.sql"
	)
	@Test
	public void testGetPreviousAdjacentPagesViewsMetric() {
		org.junit.jupiter.api.Assertions.assertEquals(
			SetUtil.of(
				new AdjacentPageViewsMetric(
					"https://www.computer.com/files", Boolean.FALSE,
					Boolean.TRUE, "Computer File Systems", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/decimal", Boolean.FALSE,
					Boolean.TRUE, "Encoding Decimal to Binary", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"https://www.computer.com/gui", Boolean.FALSE, Boolean.TRUE,
					"Graphical User Interface", BigDecimal.ONE),
				new AdjacentPageViewsMetric(
					"direct", Boolean.TRUE, Boolean.TRUE, "direct",
					BigDecimal.ONE)),
			_pagePathDog.getPreviousAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L,
				Arrays.asList(14579L, 24680L),
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	@BQSQLResource(
		resourcePath = "test_get_adjacent_pages_views_metric_with_segment_id_bq.sql"
	)
	@Test
	public void testGetPreviousAdjacentPagesViewsMetricWithNoSegmentIds() {
		_assertEquals(
			Collections.emptySet(),
			_pagePathDog.getPreviousAdjacentPagesViewsMetric(
				"https://www.computer.com/abacus", 12345L,
				Collections.emptyList(),
				TimeRange.of(
					LocalDate.parse("2023-11-05"),
					LocalDate.parse("2023-10-25")),
				"From Abacus to Modern Day Computers"));
	}

	private void _assertEquals(
		Set<AdjacentPageViewsMetric> actualAdjacentPageViewsMetrics,
		Set<AdjacentPageViewsMetric> expectedAdjacentPageViewsMetrics) {

		Assertions.assertThat(
			expectedAdjacentPageViewsMetrics
		).usingRecursiveFieldByFieldElementComparatorIgnoringFields(
			"_eventDate"
		).containsAll(
			actualAdjacentPageViewsMetrics
		);
	}

	@Autowired
	private BQMembershipDog _bqMembershipDog;

	@Autowired
	private PagePathDog _pagePathDog;

	@Autowired
	private PreferenceDog _preferenceDog;

	@Autowired
	private SegmentDog _segmentDog;

}