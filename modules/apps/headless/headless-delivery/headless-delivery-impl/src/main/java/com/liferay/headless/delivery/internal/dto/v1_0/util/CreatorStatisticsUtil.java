/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.CreatorStatistics;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Luis Miguel Barcos
 */
public class CreatorStatisticsUtil {

	public static CreatorStatistics toCreatorStatistics(
			long groupId, String languageId,
			MBStatsUserLocalService mbStatsUserLocalService, UriInfo uriInfo,
			User user)
		throws PortalException {

		String[] ranks = mbStatsUserLocalService.getUserRank(
			groupId, languageId, user.getUserId());

		return new CreatorStatistics() {
			{
				joinDate = user.getCreateDate();
				postsNumber = Math.toIntExact(
					mbStatsUserLocalService.getMessageCountByUserId(
						user.getUserId()));
				rank = ranks[1].equals(StringPool.BLANK) ? ranks[0] : ranks[1];

				setLastPostDate(
					() -> {
						boolean hasLastPostDateField = Optional.ofNullable(
							uriInfo
						).map(
							UriInfo::getQueryParameters
						).map(
							parameters -> parameters.getFirst("nestedFields")
						).map(
							fields -> fields.contains("lastPostDate")
						).orElse(
							false
						);

						if (hasLastPostDateField) {
							return mbStatsUserLocalService.
								getLastPostDateByUserId(
									user.getGroupId(), user.getUserId());
						}

						return null;
					});
			}
		};
	}

}