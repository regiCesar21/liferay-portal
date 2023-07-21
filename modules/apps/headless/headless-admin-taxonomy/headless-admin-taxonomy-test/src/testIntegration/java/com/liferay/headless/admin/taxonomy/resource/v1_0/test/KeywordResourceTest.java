/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KeywordResourceTest extends BaseKeywordResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		testGroup = testDepotEntry.getGroup();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Keyword randomKeyword() throws Exception {
		Keyword keyword = super.randomKeyword();

		keyword.setName(StringUtil.toLowerCase(keyword.getName()));

		return keyword;
	}

	@Override
	protected Keyword testGetKeywordsRankedPage_addKeyword(Keyword keyword)
		throws Exception {

		keyword = testPostSiteKeyword_addKeyword(keyword);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			testGroup.getGroupId());

		AssetTagLocalServiceUtil.addAssetEntryAssetTag(
			assetEntry.getEntryId(), keyword.getId());

		return keyword;
	}

}