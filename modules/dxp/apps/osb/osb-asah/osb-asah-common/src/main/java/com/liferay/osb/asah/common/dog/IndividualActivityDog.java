/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog;

import com.liferay.osb.asah.common.date.dog.TimeZoneDog;
import com.liferay.osb.asah.common.entity.IndividualActivity;
import com.liferay.osb.asah.common.model.Sort;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.IndividualActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author Rachael Koestartyo
 */
@Component
public class IndividualActivityDog {

	public Page<IndividualActivity> getIndividualActivityPage(
		@Nullable Long channelId, String individualId, int page, int size,
		TimeRange timeRange) {

		PageRequest pageRequest = PageRequest.of(
			page, size, Sort.desc("eventDate"));

		String timeZoneId = _timeZoneDog.getTimeZoneId();

		return PageableExecutionUtils.getPage(
			_individualActivityRepository.searchIndividualActivities(
				channelId, individualId, pageRequest,
				timeRange.getEndLocalDateTime(),
				timeRange.getStartLocalDateTime(), timeZoneId),
			pageRequest,
			() -> _individualActivityRepository.countIndividualActivities(
				channelId, individualId, timeRange.getEndLocalDateTime(),
				timeRange.getStartLocalDateTime(), timeZoneId));
	}

	@Autowired
	private IndividualActivityRepository _individualActivityRepository;

	@Autowired
	private TimeZoneDog _timeZoneDog;

}