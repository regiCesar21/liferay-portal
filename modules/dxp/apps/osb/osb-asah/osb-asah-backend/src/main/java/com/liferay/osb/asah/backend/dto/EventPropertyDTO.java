/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.liferay.osb.asah.common.entity.EventAttributeDefinition;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Marcos Martins
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("event-property")
public class EventPropertyDTO {

	public EventPropertyDTO() {
	}

	public EventPropertyDTO(EventAttributeDefinition eventAttributeDefinition) {
		_dataType = eventAttributeDefinition.getDataType();
		_displayName = eventAttributeDefinition.getDisplayName();

		_name = eventAttributeDefinition.getName();

		_id = Hex.encodeHexString(_name.getBytes(StandardCharsets.UTF_8));
	}

	@JsonProperty("dataType")
	public EventAttributeDefinition.DataType getDataType() {
		return _dataType;
	}

	@JsonProperty("displayName")
	public String getDisplayName() {
		return _displayName;
	}

	@JsonProperty("id")
	public String getId() {
		return _id;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	private EventAttributeDefinition.DataType _dataType;
	private String _displayName;
	private String _id;
	private String _name;

}