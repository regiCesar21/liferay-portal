/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Riccardo Ferrari
 */
@RunWith(MockitoJUnitRunner.class)
public class AsahExperimentCacheTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_asahExperimentCache, "_portalCache", _portalCache);
	}

	@Test
	public void testGetExperiment() {
		long companyId = RandomTestUtil.randomLong();

		String experimentId = RandomTestUtil.randomString();

		String cacheKey = _generateCacheKey(companyId, experimentId);

		String experimentJSON = RandomTestUtil.randomString();

		Mockito.when(
			_portalCache.get(cacheKey)
		).thenReturn(
			experimentJSON
		);

		Assert.assertEquals(
			experimentJSON,
			_asahExperimentCache.getExperiment(companyId, experimentId));

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).get(
			Mockito.eq(cacheKey)
		);
	}

	@Test
	public void testPutInterestTerms() {
		long companyId = RandomTestUtil.randomLong();

		String experimentId = RandomTestUtil.randomString();

		String cacheKey = _generateCacheKey(companyId, experimentId);

		String experimentJSON = RandomTestUtil.randomString();

		_asahExperimentCache.putExperiment(
			companyId, experimentId, experimentJSON);

		Mockito.verify(
			_portalCache, Mockito.times(1)
		).put(
			cacheKey, experimentJSON, 3600
		);
	}

	private String _generateCacheKey(long companyId, String experimentId) {
		return "experiment-" + companyId + experimentId;
	}

	private final AsahExperimentCache _asahExperimentCache =
		new AsahExperimentCache();

	@Mock
	private PortalCache<String, String> _portalCache;

}