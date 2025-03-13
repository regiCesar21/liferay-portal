/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.json.JSONUtil;
import com.liferay.osb.asah.common.util.BeanUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import org.springframework.data.relational.core.mapping.Column;

/**
 * @author Rachael Koestartyo
 */
public class IndividualActivity {

	public IndividualActivity(Map<String, Object> source) {
		BeanUtils.copyProperties(source, this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IndividualActivity)) {
			return false;
		}

		IndividualActivity individualActivity = (IndividualActivity)obj;

		if (Objects.equals(_applicationId, individualActivity._applicationId) &&
			Objects.equals(_channelId, individualActivity._channelId) &&
			Objects.equals(
				JSONUtil.toMap(_contextJSONObject),
				JSONUtil.toMap(individualActivity._contextJSONObject)) &&
			Objects.equals(_eventDate, individualActivity._eventDate) &&
			Objects.equals(_eventId, individualActivity._eventId) &&
			Objects.equals(_id, individualActivity._id) &&
			Objects.equals(_individualId, individualActivity._individualId) &&
			Objects.equals(
				JSONUtil.toMap(_propertiesJSONObject),
				JSONUtil.toMap(individualActivity._propertiesJSONObject))) {

			return true;
		}

		return false;
	}

	public String getApplicationId() {
		return _applicationId;
	}

	public Long getChannelId() {
		return _channelId;
	}

	@Column("context")
	public JSONObject getContextJSONObject() {
		return _contextJSONObject;
	}

	@JsonFormat(
		pattern = DateUtil.PATTERN_ISO_8601, shape = JsonFormat.Shape.STRING,
		timezone = "UTC"
	)
	public Date getEventDate() {
		if (_eventDate == null) {
			return null;
		}

		return new Date(_eventDate.getTime());
	}

	public String getEventId() {
		return _eventId;
	}

	public String getId() {
		return _id;
	}

	public String getIndividualId() {
		return _individualId;
	}

	@Column("properties")
	public JSONObject getPropertiesJSONObject() {
		return _propertiesJSONObject;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_applicationId, _channelId, _contextJSONObject, _eventDate,
			_eventId, _id, _individualId, _propertiesJSONObject);
	}

	public void setApplicationId(String applicationId) {
		_applicationId = applicationId;
	}

	public void setChannelId(Long channelId) {
		_channelId = channelId;
	}

	public void setContextJSONObject(JSONObject contextJSONObject) {
		_contextJSONObject = contextJSONObject;
	}

	public void setEventDate(Date eventDate) {
		if (eventDate != null) {
			_eventDate = new Date(eventDate.getTime());
		}
	}

	public void setEventId(String eventId) {
		_eventId = eventId;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualId(String individualId) {
		_individualId = individualId;
	}

	public void setPropertiesJSONObject(JSONObject propertiesJSONObject) {
		_propertiesJSONObject = propertiesJSONObject;
	}

	private String _applicationId;
	private Long _channelId;
	private JSONObject _contextJSONObject;
	private Date _eventDate;
	private String _eventId;
	private String _id;
	private String _individualId;
	private JSONObject _propertiesJSONObject;

}