/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.retriever;

import com.liferay.info.pagination.Pagination;

import java.util.Optional;

/**
 * @author Eudaldo Alonso
 */
public class DefaultLayoutListRetrieverContext
	implements LayoutListRetrieverContext {

	@Override
	public Optional<Pagination> getPaginationOptional() {
		return Optional.ofNullable(_pagination);
	}

	@Override
	public Optional<long[]> getSegmentsEntryIdsOptional() {
		return Optional.ofNullable(_segmentsEntryIds);
	}

	@Override
	public Optional<long[]> getSegmentsExperienceIdsOptional() {
		return Optional.ofNullable(_segmentsExperienceIds);
	}

	public void setPagination(Pagination pagination) {
		_pagination = pagination;
	}

	public void setSegmentsEntryIds(long[] segmentsEntryIds) {
		_segmentsEntryIds = segmentsEntryIds;
	}

	public void setSegmentsExperienceIdsOptional(
		long[] segmentsExperienceIdsOptional) {

		_segmentsExperienceIds = segmentsExperienceIdsOptional;
	}

	private Pagination _pagination;
	private long[] _segmentsEntryIds;
	private long[] _segmentsExperienceIds;

}