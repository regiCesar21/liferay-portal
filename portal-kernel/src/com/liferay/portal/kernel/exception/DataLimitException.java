/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author     Javier de Arcos
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             DataLimitExceededException}
 */
@Deprecated
public class DataLimitException extends PortalException {

	public DataLimitException() {
	}

	public DataLimitException(String msg) {
		super(msg);
	}

	public DataLimitException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataLimitException(Throwable throwable) {
		super(throwable);
	}

}