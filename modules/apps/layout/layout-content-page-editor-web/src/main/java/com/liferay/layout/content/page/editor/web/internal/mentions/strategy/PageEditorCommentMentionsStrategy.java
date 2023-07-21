/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.mentions.strategy;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.strategy.MentionsStrategy;
import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;
import com.liferay.social.kernel.util.SocialInteractionsConfigurationUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "mentions.strategy=pageEditorCommentStrategy",
	service = MentionsStrategy.class
)
public class PageEditorCommentMentionsStrategy implements MentionsStrategy {

	@Override
	public List<User> getUsers(
			long companyId, long userId, String query, JSONObject jsonObject)
		throws PortalException {

		SocialInteractionsConfiguration socialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(
					companyId, MentionsPortletKeys.MENTIONS);

		List<User> users = _mentionsUserFinder.getUsers(
			companyId, userId, query, socialInteractionsConfiguration);

		long plid = jsonObject.getLong("plid");

		Stream<User> stream = users.stream();

		return stream.filter(
			user -> {
				try {
					return _layoutPermission.contains(
						_permissionCheckerFactory.create(user), plid,
						ActionKeys.UPDATE);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check permission for " + user,
						portalException);

					return false;
				}
			}
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageEditorCommentMentionsStrategy.class);

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private MentionsUserFinder _mentionsUserFinder;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

}