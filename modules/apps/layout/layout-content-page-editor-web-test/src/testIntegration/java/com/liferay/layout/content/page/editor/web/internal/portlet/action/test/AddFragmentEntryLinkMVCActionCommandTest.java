/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class AddFragmentEntryLinkMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = _addLayout();
	}

	@Test
	public void testAddFragmentEntryLinkFromGlobalFragmentEntry()
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry(_company.getGroupId());

		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_company.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", fragmentEntry.getFragmentEntryKey());

		FragmentEntryLink fragmentEntryLink = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Assert.assertNotNull(fragmentEntryLink);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNotNull(persistedFragmentEntryLink);

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			persistedFragmentEntryLink.getFragmentEntryId());
	}

	@Test
	public void testAddFragmentEntryLinkFromPersistedFragmentEntry()
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry(_group.getGroupId());

		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", fragmentEntry.getFragmentEntryKey());

		FragmentEntryLink fragmentEntryLink = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		Assert.assertNotNull(fragmentEntryLink);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNotNull(persistedFragmentEntryLink);

		Assert.assertEquals(
			fragmentEntry.getFragmentEntryId(),
			persistedFragmentEntryLink.getFragmentEntryId());
		Assert.assertEquals(
			_layout.getPlid(), persistedFragmentEntryLink.getPlid());
		Assert.assertEquals(
			fragmentEntry.getCss(), persistedFragmentEntryLink.getCss());
		Assert.assertEquals(
			fragmentEntry.getHtml(), persistedFragmentEntryLink.getHtml());
		Assert.assertEquals(
			fragmentEntry.getJs(), persistedFragmentEntryLink.getJs());
		Assert.assertEquals(
			fragmentEntry.getConfiguration(),
			persistedFragmentEntryLink.getConfiguration());
		Assert.assertEquals(
			StringPool.BLANK, persistedFragmentEntryLink.getRendererKey());
	}

	@Test
	public void testAddFragmentEntryLinkToLayout() throws Exception {
		FragmentEntry fragmentEntry = _getFragmentEntry(_group.getGroupId());

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", fragmentEntry.getFragmentEntryKey());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "addFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 1,
			actualFragmentEntryLinks.size());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testAddInvalidFragmentEntryToLayout() throws Exception {
		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", RandomTestUtil.randomString());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "addFragmentEntryLink",
			new Class<?>[] {ActionRequest.class}, actionRequest);
	}

	private Layout _addLayout() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		return _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
			StringPool.BLANK, serviceContext);
	}

	private FragmentEntry _getFragmentEntry(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), groupId, StringUtil.randomString(),
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "{fieldSets: []}", 0,
			FragmentConstants.TYPE_COMPONENT, WorkflowConstants.STATUS_APPROVED,
			serviceContext);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			long groupId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(groupId));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/add_fragment_entry_link")
	private MVCActionCommand _mvcActionCommand;

}