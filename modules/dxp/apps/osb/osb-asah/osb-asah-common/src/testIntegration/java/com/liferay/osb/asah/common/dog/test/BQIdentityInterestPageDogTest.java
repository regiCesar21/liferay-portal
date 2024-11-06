/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog.test;

import com.liferay.osb.asah.common.OSBAsahCommonSpringTestContext;
import com.liferay.osb.asah.common.dog.BQIdentityInterestPageDog;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.math.BigDecimal;

import org.json.JSONArray;

import org.junit.jupiter.api.Test;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leslie Wong
 */
public class BQIdentityInterestPageDogTest
	implements OSBAsahCommonSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "test_bq_identity_interest_page_dog_1.sql")
	@Test
	public void testGetVisitedPagesTransformationsActivePages1() {
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"title", "Know Your Ratios - Volume"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(7)
				).put(
					"url", "https://www.know-your-ratios.com/volume"
				),
				JSONUtil.put(
					"title", "Know Your Ratios - Weight"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(6)
				).put(
					"url", "https://www.know-your-ratios.com/weight"
				)),
			new JSONArray(
				_bqIdentityInterestPageDog.getActivePagesTransformations(
					1L, "interestName eq 'ratio'", "1234567891011",
					"individual-segment", 1, 2,
					new String[] {"uniqueVisitsCount", "desc"})),
			true);
	}

	@BQSQLResource(resourcePath = "test_bq_identity_interest_page_dog_2.sql")
	@Test
	public void testGetVisitedPagesTransformationsActivePages2() {
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"title", "Liferay - Football Club"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(10)
				).put(
					"url", "https://www.liferay-fc.com/football"
				),
				JSONUtil.put(
					"title", "These are my interests - Football"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(7)
				).put(
					"url", "https://www.these-are-my-interests.com/football"
				)),
			new JSONArray(
				_bqIdentityInterestPageDog.getActivePagesTransformations(
					1L, "interestName eq 'football'", "1", "individual", 0, 2,
					new String[] {"uniqueVisitsCount", "desc"})),
			true);
	}

	@BQSQLResource(resourcePath = "test_bq_identity_interest_page_dog_1.sql")
	@Test
	public void testGetVisitedPagesTransformationsActivePages3() {
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"title", "Know Your Ratios - Distance"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(13)
				).put(
					"url", "https://www.know-your-ratios.com/distance"
				),
				JSONUtil.put(
					"title", "Know Your Ratios - Time"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(7)
				).put(
					"url", "https://www.know-your-ratios.com/time"
				),
				JSONUtil.put(
					"title", "Know Your Ratios - Volume"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(7)
				).put(
					"url", "https://www.know-your-ratios.com/volume"
				),
				JSONUtil.put(
					"title", "Know Your Ratios - Weight"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(6)
				).put(
					"url", "https://www.know-your-ratios.com/weight"
				),
				JSONUtil.put(
					"title", "Know Your Ratios - Home"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(2)
				).put(
					"url", "https://www.know-your-ratios.com"
				)),
			new JSONArray(
				_bqIdentityInterestPageDog.getActivePagesTransformations(
					1L, "interestName eq 'ratio'", "1234567891011",
					"individual-segment", 0, 20,
					new String[] {"uniqueVisitsCount", "desc"})),
			true);
	}

	@BQSQLResource(resourcePath = "test_bq_identity_interest_page_dog_1.sql")
	@Test
	public void testGetVisitedPagesTransformationsInactivePages1() {
		JSONAssert.assertEquals(
			JSONUtil.put(
				JSONUtil.put(
					"title", "Know Your Ratios - Distance"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(0)
				).put(
					"url", "https://www.know-your-ratios.com/distance"
				)),
			new JSONArray(
				_bqIdentityInterestPageDog.getInactivePagesTransformations(
					1L, "interestName eq 'ratio'", "3456789101112",
					"individual-segment", 1, 2,
					new String[] {"title", "desc"})),
			true);
	}

	@BQSQLResource(resourcePath = "test_bq_identity_interest_page_dog_2.sql")
	@Test
	public void testGetVisitedPagesTransformationsInactivePages2() {
		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"title", "Liferay Latam - Football Club"
				).put(
					"uniqueVisitsCount", BigDecimal.valueOf(0)
				).put(
					"url", "https://www.liferay-latam-fc.com/football"
				)),
			new JSONArray(
				_bqIdentityInterestPageDog.getInactivePagesTransformations(
					1L, "interestName eq 'football'", "1", "individual", 0, 2,
					new String[] {"title", "desc"})),
			true);
	}

	@Autowired
	private BQIdentityInterestPageDog _bqIdentityInterestPageDog;

}