/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseTermsFilterTestCase extends BaseIndexingTestCase {

	@Test
	public void testBasicSearch() {
		index("One");
		index("Two");
		index("Three");

		assertTermsFilterValue(new String[] {"Two", "Three"});
	}

	@Test
	public void testFilterWithEmptyStringValue() {
		index("One");

		assertTermsFilterValue(new String[] {""});
	}

	@Test
	public void testLuceneSpecialCharacters() {
		index("One\\+-!():^[]\"{}~*?|&/Two");
		index("Three");

		assertTermsFilterValue(
			new String[] {"One\\+-!():^[]\"{}~*?|&/Two", "Three"});
	}

	@Test
	public void testSpacedFieldName() {
		String fieldName = "expando__keyword__custom_fields__spaced name";

		index(fieldName, "one");

		assertTermsFilterFieldName(fieldName, new String[] {"one"});
	}

	@Test
	public void testSpaces() {
		index("One Two");
		index("Three");

		assertTermsFilterValue(new String[] {"One Two", "Three"});
	}

	@Test
	public void testSpecialCharacters() {
		index("One\\+-!():^[]\"{}~*?|&/; Two");
		index("Three");

		assertTermsFilterValue(
			new String[] {"One\\+-!():^[]\"{}~*?|&/; Two", "Three"});
	}

	protected void assertTermsFilterFieldName(
		String fieldName, String[] values) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(
					new TermsFilter(fieldName) {
						{
							addValues(values);
						}
					});

				indexingTestHelper.search();

				StringBuilder sb = new StringBuilder(3);

				sb.append("Expected \"");
				sb.append(fieldName);
				sb.append("\" to be escaped in Solr and return a result.");

				Assert.assertEquals(
					sb.toString(), 1, indexingTestHelper.searchCount());
			});
	}

	protected void assertTermsFilterValue(String[] values) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(
					new TermsFilter(_FIELD) {
						{
							addValues(values);
						}
					});

				indexingTestHelper.search();

				indexingTestHelper.assertValues(_FIELD, Arrays.asList(values));
			});
	}

	protected void index(String value) {
		index(_FIELD, value);
	}

	protected void index(String fieldName, String value) {
		addDocument(DocumentCreationHelpers.singleKeyword(fieldName, value));
	}

	private static final String _FIELD = Field.FOLDER_ID;

}