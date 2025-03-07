/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.util.BeanUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rachael Koestartyo
 */
public class IndividualInterest {

	public IndividualInterest() {
	}

	public IndividualInterest(Map<String, Object> source) {
		BeanUtils.copyProperties(source, this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IndividualInterest)) {
			return false;
		}

		IndividualInterest individualInterest = (IndividualInterest)obj;

		if (Objects.equals(channelId, individualInterest.channelId) &&
			Objects.equals(interested, individualInterest.interested) &&
			Objects.equals(interestScore, individualInterest.interestScore) &&
			Objects.equals(keyword, individualInterest.keyword) &&
			Objects.equals(recordedDate, individualInterest.recordedDate)) {

			return true;
		}

		return false;
	}

	public Long getChannelId() {
		return channelId;
	}

	public Boolean getInterested() {
		return interested;
	}

	public Double getInterestScore() {
		return interestScore;
	}

	public String getKeyword() {
		return keyword;
	}

	@JsonAlias("recordedDate")
	@JsonFormat(
		pattern = DateUtil.PATTERN_ISO_8601, shape = JsonFormat.Shape.STRING,
		timezone = "UTC"
	)
	@JsonProperty("dateRecorded")
	public Date getRecordedDate() {
		if (recordedDate == null) {
			return null;
		}

		return new Date(recordedDate.getTime());
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			channelId, interested, interestScore, keyword, recordedDate);
	}

	public Boolean isInterested() {
		return interested;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public void setInterested(Boolean interested) {
		this.interested = interested;
	}

	public void setInterestScore(Double interestScore) {
		this.interestScore = interestScore;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setRecordedDate(Date recordedDate) {
		if (recordedDate != null) {
			this.recordedDate = new Date(recordedDate.getTime());
		}
	}

	protected Long channelId;
	protected Boolean interested;
	protected Double interestScore;
	protected String keyword;
	protected Date recordedDate;

}