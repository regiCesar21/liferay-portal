/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dog.test;

import com.liferay.osb.asah.backend.OSBAsahBackendSpringTestContext;
import com.liferay.osb.asah.common.dog.BQAssetDog;
import com.liferay.osb.asah.common.entity.BQAsset;
import com.liferay.osb.asah.test.util.annotation.BQSQLResource;
import com.liferay.osb.asah.test.util.spring.OSBAsahTestExecutionListenersContext;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Marcos Martins
 */
public class BQAssetDogTest
	implements OSBAsahBackendSpringTestContext,
			   OSBAsahTestExecutionListenersContext {

	@BQSQLResource(resourcePath = "page_assets.sql")
	@Test
	public void testGetBQAssets() {
		List<BQAsset> bqAssets = _bqAssetDog.getBQAssets(
			Arrays.asList(
				"644b2419ac680dd589310702129d2706ca82f377f49203cfc1ab64054170" +
					"769e",
				"165fc092c908d438eef170d3fd7c07fbbcc01f9d61e27eac376e70" +
					"e227d20130_74cf94d47d4fd038a75f844b7e29bd9efb027cac6f7ed" +
						"2bc77657666304c2928"));

		Assertions.assertEquals(2, bqAssets.size(), bqAssets.toString());
	}

	@Autowired
	private BQAssetDog _bqAssetDog;

}