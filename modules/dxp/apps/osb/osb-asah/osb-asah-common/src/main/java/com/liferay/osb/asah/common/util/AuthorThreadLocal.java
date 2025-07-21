/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.util;

import com.liferay.osb.asah.common.model.Author;

/**
 * @author Marcellus Tavares
 */
public class AuthorThreadLocal {

	public static void forAuthor(Author author, Runnable runnable) {
		try {
			setAuthor(author);

			runnable.run();
		}
		finally {
			remove();
		}
	}

	public static Author getAuthor() {
		Author author = _author.get();

		if (author != null) {
			return author;
		}

		return Author.UNKNOWN;
	}

	public static Long getUserId() {
		Author author = getAuthor();

		return author.getUserId();
	}

	public static String getUserName() {
		Author author = getAuthor();

		return author.getUserName();
	}

	public static void remove() {
		_author.remove();
	}

	public static void setAuthor(Author author) {
		_author.set(author);
	}

	private static final ThreadLocal<Author> _author = new ThreadLocal<>();

}