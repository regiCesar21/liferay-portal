/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class Author {

	public static final Author UNKNOWN = new Author(0L, "");

	public Author(Long userId, String userName) {
		_userId = userId;
		_userName = userName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof Author)) {
			return false;
		}

		Author author = (Author)obj;

		if (Objects.equals(_userId, author._userId) &&
			Objects.equals(_userName, author._userName)) {

			return true;
		}

		return false;
	}

	public Long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_userId, _userName);
	}

	private final Long _userId;
	private final String _userName;

}