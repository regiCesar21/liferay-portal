/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.entity;

import com.liferay.osb.asah.common.util.BeanUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class BQIdentityInterestScore extends IndividualInterest {

	public BQIdentityInterestScore() {
	}

	public BQIdentityInterestScore(Map<String, Object> source) {
		BeanUtils.copyProperties(source, this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!super.equals(obj) || !(obj instanceof BQIdentityInterestScore)) {
			return false;
		}

		BQIdentityInterestScore bqIdentityInterestScore =
			(BQIdentityInterestScore)obj;

		if (Objects.equals(_identityId, bqIdentityInterestScore._identityId)) {
			return true;
		}

		return false;
	}

	public String getIdentityId() {
		return _identityId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_identityId, channelId, interested, interestScore, keyword,
			recordedDate);
	}

	public void setIdentityId(String identityId) {
		_identityId = identityId;
	}

	private String _identityId;

}