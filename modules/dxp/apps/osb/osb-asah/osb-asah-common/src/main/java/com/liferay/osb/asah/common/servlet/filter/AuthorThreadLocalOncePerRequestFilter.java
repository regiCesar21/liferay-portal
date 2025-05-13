/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.servlet.filter;

import com.liferay.osb.asah.common.constants.HeaderConstants;
import com.liferay.osb.asah.common.model.Author;
import com.liferay.osb.asah.common.util.AuthorThreadLocal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Marcellus Tavares
 */
@Component
public class AuthorThreadLocalOncePerRequestFilter
	extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws IOException, ServletException {

		String authorUserId = httpServletRequest.getHeader(
			HeaderConstants.AUTHOR_USER_ID);
		String authorUserName = httpServletRequest.getHeader(
			HeaderConstants.AUTHOR_USER_NAME);

		if (StringUtils.isBlank(authorUserId) &&
			StringUtils.isBlank(authorUserName)) {

			filterChain.doFilter(httpServletRequest, httpServletResponse);

			return;
		}

		try {
			AuthorThreadLocal.setAuthor(
				new Author(authorUserId, authorUserName));

			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
		finally {
			AuthorThreadLocal.remove();
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) {
		if (StringUtils.startsWith(
				httpServletRequest.getRequestURI(), "/api/1.0")) {

			return true;
		}

		return false;
	}

}