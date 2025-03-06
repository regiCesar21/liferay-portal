/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog;

import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.repository.IndividualInterestRepository;

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
public class IndividualInterestDog {

	public Page<IndividualInterest> getIndividualInterestPage(
		@Nullable Long channelId, String individualId, int size, int start) {

		return PageableExecutionUtils.getPage(
			_individualInterestRepository.findByChannelIdAndIndividualId(
				channelId, individualId, PageRequest.of(start / size, size)),
			PageRequest.of(start / size, size),
			() -> _individualInterestRepository.countByChannelIdAndIndividualId(
				channelId, individualId));
	}

	@Autowired
	private IndividualInterestRepository _individualInterestRepository;

}