/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelDocumentContributor.class
)
public class UserModelDocumentContributor
	implements ModelDocumentContributor<User> {

	@Override
	public void contribute(Document document, User user) {
		try {
			long[] segmentsEntryIds = getSegmentsEntryIds(user);

			if (ArrayUtil.isNotEmpty(segmentsEntryIds)) {
				document.addKeyword("segmentsEntryIds", segmentsEntryIds);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index user " + user.getUserId(), exception);
			}
		}
	}

	protected long[] getSegmentsEntryIds(User user) throws Exception {
		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				_portal.getClassNameId(User.class), user.getUserId());

		Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

		return stream.mapToLong(
			SegmentsEntryRel::getSegmentsEntryId
		).toArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelDocumentContributor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}