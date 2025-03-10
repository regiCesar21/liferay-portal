/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.entity;

import com.liferay.osb.asah.common.util.BeanUtils;

import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Marcellus Tavares
 */
public class Individual {

	public Individual() {
	}

	public Individual(Map<String, Object> source) {
		BeanUtils.copyProperties(source, this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Individual)) {
			return false;
		}

		Individual individual = (Individual)obj;

		if (Objects.equals(_emailAddress, individual._emailAddress) &&
			Objects.equals(_fieldsJSONObject, individual._fieldsJSONObject) &&
			Objects.equals(_id, individual._id) &&
			Objects.equals(_suppressed, individual._suppressed)) {

			return true;
		}

		return false;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public JSONObject getFieldsJSONObject() {
		return _fieldsJSONObject;
	}

	public String getId() {
		return _id;
	}

	public Boolean getSuppressed() {
		return _suppressed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_emailAddress, _fieldsJSONObject, _id, _suppressed);
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setFieldsJSONObject(JSONObject fieldsJSONObject) {
		_fieldsJSONObject = fieldsJSONObject;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setSuppressed(Boolean suppressed) {
		_suppressed = suppressed;
	}

	private String _emailAddress;
	private JSONObject _fieldsJSONObject;
	private String _id;
	private Boolean _suppressed;

}