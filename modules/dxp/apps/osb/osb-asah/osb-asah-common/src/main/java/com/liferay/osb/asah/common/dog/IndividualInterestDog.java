/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog;

import com.liferay.osb.asah.common.entity.BQIdentityInterestScore;
import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.model.Feature;
import com.liferay.osb.asah.common.repository.BQIdentityInterestScoreRepository;
import com.liferay.osb.asah.common.repository.IndividualInterestRepository;
import com.liferay.osb.asah.common.util.ListUtil;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.util.List;

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

		PageRequest pageRequest = PageRequest.of(start / size, size);

		if (_projectFeatureDog.isFeatureEnabled(
				Feature.API_REPORTS_POSTGRES_CACHE,
				ProjectIdThreadLocal.getProjectId())) {

			return PageableExecutionUtils.getPage(
				_individualInterestRepository.findByChannelIdAndIndividualId(
					channelId, individualId, pageRequest),
				pageRequest,
				() ->
					_individualInterestRepository.
						countByChannelIdAndIndividualId(
							channelId, individualId));
		}

		List<BQIdentityInterestScore> bqIdentityInterestScores =
			_bqIdentityInterestScoreRepository.findByChannelIdAndIndividualId(
				channelId, individualId, pageRequest);

		return PageableExecutionUtils.getPage(
			ListUtil.map(bqIdentityInterestScores, this::_toIndividualInterest),
			pageRequest,
			() ->
				_bqIdentityInterestScoreRepository.
					countByChannelIdAndIndividualId(channelId, individualId));
	}

	private IndividualInterest _toIndividualInterest(
		BQIdentityInterestScore bqIdentityInterestScore) {

		IndividualInterest individualInterest = new IndividualInterest();

		individualInterest.setChannelId(bqIdentityInterestScore.getChannelId());
		individualInterest.setIdentityId(
			bqIdentityInterestScore.getIdentityId());
		individualInterest.setInterested(
			bqIdentityInterestScore.getInterested());
		individualInterest.setInterestScore(
			bqIdentityInterestScore.getInterestScore());
		individualInterest.setKeyword(bqIdentityInterestScore.getKeyword());
		individualInterest.setRecordedDate(
			bqIdentityInterestScore.getRecordedDate());

		return individualInterest;
	}

	@Autowired
	private BQIdentityInterestScoreRepository
		_bqIdentityInterestScoreRepository;

	@Autowired
	private IndividualInterestRepository _individualInterestRepository;

	@Autowired
	private ProjectFeatureDog _projectFeatureDog;

}