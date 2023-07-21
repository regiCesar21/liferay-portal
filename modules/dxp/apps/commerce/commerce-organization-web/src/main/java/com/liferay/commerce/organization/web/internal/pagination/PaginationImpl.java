/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.pagination;

import com.liferay.frontend.taglib.clay.data.Pagination;

/**
 * @author Marco Leo
 */
public class PaginationImpl implements Pagination {

	public PaginationImpl(int pageSize, int page) {
		_pageSize = pageSize;
		_page = page;
	}

	@Override
	public int getEndPosition() {
		return _page * _pageSize;
	}

	@Override
	public int getPage() {
		return _page;
	}

	@Override
	public int getPageSize() {
		return _pageSize;
	}

	@Override
	public int getStartPosition() {
		return (_page - 1) * _pageSize;
	}

	private final int _page;
	private final int _pageSize;

}