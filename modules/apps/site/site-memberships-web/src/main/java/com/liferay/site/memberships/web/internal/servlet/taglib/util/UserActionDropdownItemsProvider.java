/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserActionDropdownItemsProvider {

	public UserActionDropdownItemsProvider(
		User user, RenderRequest renderRequest, RenderResponse renderResponse) {

		_user = user;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() throws Exception {
		return DropdownItemListBuilder.add(
			() -> GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getSiteGroupIdOrLiveGroupId(),
				ActionKeys.ASSIGN_USER_ROLES),
			_getAssignRolesActionUnsafeConsumer()
		).add(
			() ->
				GroupPermissionUtil.contains(
					_themeDisplay.getPermissionChecker(),
					_themeDisplay.getSiteGroupIdOrLiveGroupId(),
					ActionKeys.ASSIGN_MEMBERS) &&
				!SiteMembershipPolicyUtil.isMembershipProtected(
					_themeDisplay.getPermissionChecker(), _user.getUserId(),
					_themeDisplay.getSiteGroupIdOrLiveGroupId()) &&
				!SiteMembershipPolicyUtil.isMembershipRequired(
					_user.getUserId(),
					_themeDisplay.getSiteGroupIdOrLiveGroupId()),
			_getDeleteGroupUsersActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
			_getAssignRolesActionUnsafeConsumer()
		throws Exception {

		PortletURL assignRolesURL = _renderResponse.createRenderURL();

		assignRolesURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));
		assignRolesURL.setParameter("mvcPath", "/users_roles.jsp");
		assignRolesURL.setParameter(
			"groupId",
			String.valueOf(_themeDisplay.getSiteGroupIdOrLiveGroupId()));

		Group group = _themeDisplay.getScopeGroup();

		if (!group.isSite() && group.isDepot()) {
			assignRolesURL.setParameter(
				"roleType", String.valueOf(RoleConstants.TYPE_DEPOT));
		}

		assignRolesURL.setWindowState(LiferayWindowState.POP_UP);

		PortletURL editUserGroupRoleURL = _renderResponse.createActionURL();

		editUserGroupRoleURL.setParameter(
			ActionRequest.ACTION_NAME, "editUserGroupRole");
		editUserGroupRoleURL.setParameter(
			"p_u_i_d", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "assignRoles");
			dropdownItem.putData("assignRolesURL", assignRolesURL.toString());
			dropdownItem.putData(
				"editUserGroupRoleURL", editUserGroupRoleURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "assign-roles"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteGroupUsersActionUnsafeConsumer() {

		PortletURL deleteGroupUsersURL = _renderResponse.createActionURL();

		deleteGroupUsersURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteGroupUsers");
		deleteGroupUsersURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteGroupUsersURL.setParameter(
			"groupId",
			String.valueOf(_themeDisplay.getSiteGroupIdOrLiveGroupId()));
		deleteGroupUsersURL.setParameter(
			"removeUserId", String.valueOf(_user.getUserId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteGroupUsers");
			dropdownItem.putData(
				"deleteGroupUsersURL", deleteGroupUsersURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-membership"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final User _user;

}