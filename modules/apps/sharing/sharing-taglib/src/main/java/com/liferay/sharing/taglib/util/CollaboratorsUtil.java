/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.taglib.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.model.SharingEntryModel;
import com.liferay.sharing.service.SharingEntryLocalServiceUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Alejandro Tardín
 */
public class CollaboratorsUtil {

	public static JSONObject getCollaboratorsJSONObject(
		long classNameId, long classPK, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"collaborators",
			_getSharingEntryToUsersJSONArray(classPK, classNameId, themeDisplay)
		).put(
			"owner", _getUserJSONObject(classNameId, classPK, themeDisplay)
		).put(
			"total",
			SharingEntryLocalServiceUtil.getSharingEntriesCount(
				classNameId, classPK)
		);
	}

	private static AssetRenderer<?> _getAssetRenderer(
		long classNameId, long classPK) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		try {
			return assetRendererFactory.getAssetRenderer(classPK);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return null;
	}

	private static String _getDisplayURL(ThemeDisplay themeDisplay, User user) {
		try {
			if ((user == null) || user.isDefaultUser()) {
				return StringPool.BLANK;
			}

			return user.getDisplayURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private static String _getPortraitURL(
		ThemeDisplay themeDisplay, User user) {

		try {
			if (user.getPortraitId() == 0) {
				return null;
			}

			return user.getPortraitURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private static JSONArray _getSharingEntryToUsersJSONArray(
		long classPK, long classNameId, ThemeDisplay themeDisplay) {

		List<SharingEntry> sharingEntries =
			SharingEntryLocalServiceUtil.getSharingEntries(
				classNameId, classPK, 0, 4);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Stream<SharingEntry> stream = sharingEntries.stream();

		stream.map(
			SharingEntryModel::getToUserId
		).map(
			UserLocalServiceUtil::fetchUserById
		).filter(
			Objects::nonNull
		).map(
			user -> _getUserJSONObject(user, themeDisplay)
		).forEach(
			jsonArray::put
		);

		return jsonArray;
	}

	private static JSONObject _getUserJSONObject(
		long classNameId, long classPK, ThemeDisplay themeDisplay) {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(
			classNameId, classPK);

		if (assetRenderer == null) {
			return JSONUtil.put(
				"fullName",
				LanguageUtil.get(themeDisplay.getLocale(), "deleted-user")
			).put(
				"userId", 0
			);
		}

		User user = UserLocalServiceUtil.fetchUser(assetRenderer.getUserId());

		if (user != null) {
			return _getUserJSONObject(user, themeDisplay);
		}

		return JSONUtil.put(
			"fullName", assetRenderer.getUserName()
		).put(
			"userId", assetRenderer.getUserId()
		);
	}

	private static JSONObject _getUserJSONObject(
		User user, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"displayURL", _getDisplayURL(themeDisplay, user)
		).put(
			"fullName", user.getFullName()
		).put(
			"portraitURL", _getPortraitURL(themeDisplay, user)
		).put(
			"userId", user.getUserId()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollaboratorsUtil.class);

}