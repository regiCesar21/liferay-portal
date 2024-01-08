/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateRelatedSalesforceProjectKeyException
	extends PortalException {

	public DuplicateRelatedSalesforceProjectKeyException() {
	}

	public DuplicateRelatedSalesforceProjectKeyException(String msg) {
		super(msg);
	}

	public DuplicateRelatedSalesforceProjectKeyException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateRelatedSalesforceProjectKeyException(Throwable throwable) {
		super(throwable);
	}

}