/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Ivica Cardic
 */
public enum OSBCommercePortalInstanceStatus {

	ACTIVE(WorkflowConstants.STATUS_APPROVED), CANCELLED(1),
	FAILED(WorkflowConstants.STATUS_INCOMPLETE),
	IN_PROGRESS(WorkflowConstants.STATUS_PENDING);

	public static OSBCommercePortalInstanceStatus parse(String statusString) {
		if (statusString == null) {
			return null;
		}

		int status = GetterUtil.getInteger(statusString);

		if (status == OSBCommercePortalInstanceStatus.ACTIVE.getStatus()) {
			return OSBCommercePortalInstanceStatus.ACTIVE;
		}
		else if (status ==
					OSBCommercePortalInstanceStatus.CANCELLED.getStatus()) {

			return OSBCommercePortalInstanceStatus.CANCELLED;
		}

		return OSBCommercePortalInstanceStatus.IN_PROGRESS;
	}

	public int getStatus() {
		return _status;
	}

	private OSBCommercePortalInstanceStatus(int status) {
		_status = status;
	}

	private final int _status;

}