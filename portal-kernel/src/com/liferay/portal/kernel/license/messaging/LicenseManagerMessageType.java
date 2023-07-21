/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.license.messaging;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;

/**
 * @author Igor Beslic
 */
public enum LicenseManagerMessageType {

	LCS_AVAILABLE, SUBSCRIPTION_VALID, VALIDATE_LCS, VALIDATE_SUBSCRIPTION;

	public static String MESSAGE_BUS_DESTINATION_REQUEST =
		"liferay/lcs_request";

	public static String MESSAGE_BUS_DESTINATION_STATUS = "liferay/lcs_status";

	public static LicenseManagerMessageType valueOf(JSONObject jsonObject) {
		String type = jsonObject.getString("type");

		return valueOf(type);
	}

	public Message createMessage() {
		Message message = new Message();

		message.setDestinationName(getDestinationName());
		message.setPayload(String.format("{\"type\": \"%s\"}", name()));

		return message;
	}

	public Message createMessage(LCSPortletState lcsPortletState) {
		Message message = new Message();

		message.setDestinationName(getDestinationName());

		message.setPayload(
			String.format(
				"{\"state\": %d, \"type\": \"%s\"}", lcsPortletState.intValue(),
				name()));

		return message;
	}

	public String getDestinationName() {
		if ((this == LCS_AVAILABLE) || (this == SUBSCRIPTION_VALID)) {
			return MESSAGE_BUS_DESTINATION_STATUS;
		}
		else if ((this == VALIDATE_LCS) || (this == VALIDATE_SUBSCRIPTION)) {
			return MESSAGE_BUS_DESTINATION_REQUEST;
		}

		return null;
	}

}