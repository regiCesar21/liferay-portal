/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchAppDeploymentException extends NoSuchModelException {

	public NoSuchAppDeploymentException() {
	}

	public NoSuchAppDeploymentException(String msg) {
		super(msg);
	}

	public NoSuchAppDeploymentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAppDeploymentException(Throwable throwable) {
		super(throwable);
	}

}