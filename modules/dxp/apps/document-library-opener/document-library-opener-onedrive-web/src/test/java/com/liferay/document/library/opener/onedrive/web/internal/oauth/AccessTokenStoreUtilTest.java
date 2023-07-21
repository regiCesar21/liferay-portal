/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.github.scribejava.core.model.OAuth2AccessToken;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class AccessTokenStoreUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testAdd() {
		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		AccessTokenStoreUtil.add(companyId, userId, initialAccessToken);

		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(companyId, userId);

		AccessToken actualAccessToken = accessTokenOptional.get();

		Assert.assertEquals(
			initialAccessToken.getAccessToken(),
			actualAccessToken.getAccessToken());
	}

	@Test
	public void testDelete() {
		AccessToken initialAccessToken = new AccessToken(
			new OAuth2AccessToken(RandomTestUtil.randomString()));

		long companyId = RandomTestUtil.randomInt();
		long userId = RandomTestUtil.randomInt();

		AccessTokenStoreUtil.add(companyId, userId, initialAccessToken);

		AccessTokenStoreUtil.delete(companyId, userId);

		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(companyId, userId);

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

	@Test
	public void testGetWithEmptyAccessTokenStore() {
		Optional<AccessToken> accessTokenOptional =
			AccessTokenStoreUtil.getAccessTokenOptional(
				RandomTestUtil.randomInt(), RandomTestUtil.randomInt());

		Assert.assertTrue(!accessTokenOptional.isPresent());
	}

}