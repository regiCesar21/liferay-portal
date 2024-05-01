/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.liferay.osb.asah.backend.graphql.annotation.GraphQLType;

/**
 * @author Ivica Cardic
 */
@GraphQLType("SegmentPageViews")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SegmentPageViewsDTO {

	public SegmentPageViewsDTO(String segmentId, long views) {
		_segmentId = segmentId;
		_views = views;
	}

	public String getSegmentId() {
		return _segmentId;
	}

	public long getViews() {
		return _views;
	}

	private final String _segmentId;
	private final long _views;

}