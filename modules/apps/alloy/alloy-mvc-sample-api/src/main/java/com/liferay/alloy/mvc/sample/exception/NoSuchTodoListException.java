/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.alloy.mvc.sample.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchTodoListException extends NoSuchModelException {

	public NoSuchTodoListException() {
	}

	public NoSuchTodoListException(String msg) {
		super(msg);
	}

	public NoSuchTodoListException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchTodoListException(Throwable throwable) {
		super(throwable);
	}

}