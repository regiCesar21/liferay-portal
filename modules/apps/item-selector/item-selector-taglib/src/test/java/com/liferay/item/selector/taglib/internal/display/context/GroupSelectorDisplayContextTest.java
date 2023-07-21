/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorTrackerUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class GroupSelectorDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			GroupItemSelectorTrackerUtil.class, "_serviceTrackerMap",
			new MockServiceTrackerMap());
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			GroupItemSelectorTrackerUtil.class, "_serviceTrackerMap", null);
	}

	@Test
	public void testGetGroupItemSelectorIcon() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(
				"test", new MockLiferayResourceRequest());

		Assert.assertEquals(
			"icon", groupSelectorDisplayContext.getGroupItemSelectorIcon());
	}

	@Test
	public void testGetGroupItemSelectorLabel() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(new MockLiferayResourceRequest());

		Assert.assertEquals(
			"label",
			groupSelectorDisplayContext.getGroupItemSelectorLabel("test"));
	}

	@Test
	public void testGetGroupTypes() {
		GroupSelectorDisplayContext groupSelectorDisplayContext =
			new GroupSelectorDisplayContext(new MockLiferayResourceRequest());

		Assert.assertEquals(
			Collections.singleton("test"),
			groupSelectorDisplayContext.getGroupTypes());
	}

	private static class MockGroupItemSelectorProvider
		implements GroupItemSelectorProvider {

		@Override
		public String getEmptyResultsMessage() {
			return null;
		}

		@Override
		public List<Group> getGroups(
			long companyId, long groupId, String keywords, int start, int end) {

			return Collections.singletonList(Mockito.mock(Group.class));
		}

		@Override
		public int getGroupsCount(
			long companyId, long groupId, String keywords) {

			return 3;
		}

		@Override
		public String getGroupType() {
			return "test";
		}

		@Override
		public String getIcon() {
			return "icon";
		}

		@Override
		public String getLabel(Locale locale) {
			return "label";
		}

	}

	private static class MockServiceTrackerMap
		implements ServiceTrackerMap<String, GroupItemSelectorProvider> {

		public MockServiceTrackerMap() {
			_groupItemSelectorProviderMap = Collections.singletonMap(
				"test", new MockGroupItemSelectorProvider());
		}

		@Override
		public void close() {
		}

		@Override
		public boolean containsKey(String key) {
			return _groupItemSelectorProviderMap.containsKey(key);
		}

		@Override
		public GroupItemSelectorProvider getService(String key) {
			return _groupItemSelectorProviderMap.get(key);
		}

		@Override
		public Set<String> keySet() {
			return _groupItemSelectorProviderMap.keySet();
		}

		@Override
		public Collection<GroupItemSelectorProvider> values() {
			return _groupItemSelectorProviderMap.values();
		}

		private final Map<String, GroupItemSelectorProvider>
			_groupItemSelectorProviderMap;

	}

}