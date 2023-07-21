/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.alloy.mvc;

/**
 * @author Brian Wing Shun Chan
 */
public class AlloyException extends Exception {

	public AlloyException() {
	}

	public AlloyException(String msg) {
		super(msg);
	}

	public AlloyException(String msg, boolean log) {
		super(msg);

		this.log = log;
	}

	public AlloyException(String msg, Object[] arguments) {
		super(msg);

		this.arguments = arguments;
	}

	public AlloyException(String msg, Object[] arguments, boolean log) {
		super(msg);

		this.arguments = arguments;
		this.log = log;
	}

	public AlloyException(
		String msg, Object[] arguments, boolean log, Throwable throwable) {

		super(msg, throwable);

		this.arguments = arguments;
		this.log = log;
	}

	public AlloyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AlloyException(Throwable throwable) {
		super(throwable);
	}

	protected Object[] arguments;
	protected boolean log = true;

}