/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item.type;

import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Optional;

/**
 * @author Cristina González
 */
public class ContentDashboardItemTypeUtil {

	public static Optional<ContentDashboardItemType>
		toContentDashboardItemTypeOptional(
			ContentDashboardItemTypeFactoryTracker
				contentDashboardItemTypeFactoryTracker,
			Document document) {

		return toContentDashboardItemTypeOptional(
			contentDashboardItemTypeFactoryTracker,
			new InfoItemReference(
				GetterUtil.getString(document.get(Field.ENTRY_CLASS_NAME)),
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	public static Optional<ContentDashboardItemType>
		toContentDashboardItemTypeOptional(
			ContentDashboardItemTypeFactoryTracker
				contentDashboardItemTypeFactoryTracker,
			InfoItemReference infoItemReference) {

		Optional<ContentDashboardItemTypeFactory>
			contentDashboardItemTypeFactoryOptional =
				contentDashboardItemTypeFactoryTracker.
					getContentDashboardItemTypeFactoryOptional(
						infoItemReference.getClassName());

		return contentDashboardItemTypeFactoryOptional.flatMap(
			contentDashboardItemTypeFactory ->
				_toContentDashboardItemTypeOptional(
					contentDashboardItemTypeFactoryOptional,
					infoItemReference.getClassPK()));
	}

	public static Optional<ContentDashboardItemType>
		toContentDashboardItemTypeOptional(
			ContentDashboardItemTypeFactoryTracker
				contentDashboardItemTypeFactoryTracker,
			JSONObject contentDashboardItemTypePayloadJSONObject) {

		return toContentDashboardItemTypeOptional(
			contentDashboardItemTypeFactoryTracker,
			new InfoItemReference(
				GetterUtil.getString(
					contentDashboardItemTypePayloadJSONObject.getString(
						"className")),
				GetterUtil.getLong(
					contentDashboardItemTypePayloadJSONObject.getLong(
						"classPK"))));
	}

	public static Optional<ContentDashboardItemType>
		toContentDashboardItemTypeOptional(
			ContentDashboardItemTypeFactoryTracker
				contentDashboardItemTypeFactoryTracker,
			String contentDashboardItemTypePayload) {

		try {
			return toContentDashboardItemTypeOptional(
				contentDashboardItemTypeFactoryTracker,
				JSONFactoryUtil.createJSONObject(
					contentDashboardItemTypePayload));
		}
		catch (JSONException jsonException) {
			_log.error(jsonException, jsonException);

			return Optional.empty();
		}
	}

	private static Optional<ContentDashboardItemType>
		_toContentDashboardItemTypeOptional(
			Optional<ContentDashboardItemTypeFactory>
				contentDashboardItemTypeFactoryOptional,
			Long classPK) {

		return contentDashboardItemTypeFactoryOptional.flatMap(
			contentDashboardItemTypeFactory -> {
				try {
					return Optional.of(
						contentDashboardItemTypeFactory.create(classPK));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return Optional.empty();
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemTypeUtil.class);

}