/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.servlet.filter;

import com.liferay.osb.asah.common.constants.HeaderConstants;
import com.liferay.osb.asah.common.dog.DataSourceDog;
import com.liferay.osb.asah.common.servlet.filter.BaseSecurityOncePerRequestFilter;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * @author Shinn Lok
 */
@Component
@ConditionalOnProperty("osb.asah.security.enabled")
public class SecurityOncePerRequestFilter
	extends BaseSecurityOncePerRequestFilter {

	@Override
	protected boolean isInvalidRequest(HttpServletRequest httpServletRequest) {
		String method = httpServletRequest.getMethod();
		String requestURI = httpServletRequest.getRequestURI();

		if (method.equals(HttpMethod.GET.name()) &&
			requestURI.startsWith("/actuator")) {

			return false;
		}

		String faroBackendSecuritySignature = httpServletRequest.getHeader(
			HeaderConstants.FARO_BACKEND_SECURITY_SIGNATURE);

		if (faroBackendSecuritySignature == null) {
			logInvalidRequest(null, httpServletRequest);

			return true;
		}

		if (StringUtils.equals(httpServletRequest.getMethod(), "GET") &&
			StringUtils.contains(
				httpServletRequest.getRequestURI(), "/api/1.0/data-sources")) {

			return false;
		}

		if (_environment.acceptsProfiles(Profiles.of("!prod")) &&
			StringUtils.equals(httpServletRequest.getMethod(), "POST") &&
			(_functionalRequestURIs.contains(
				httpServletRequest.getRequestURI()) ||
			 StringUtils.contains(
				 httpServletRequest.getRequestURI(), "/nanites/")) &&
			StringUtils.isNotBlank(_functionalRequestToken) &&
			_functionalRequestToken.equals(faroBackendSecuritySignature)) {

			return false;
		}

		if (StringUtils.contains(httpServletRequest.getRequestURI(), "/api/") &&
			!StringUtils.contains(
				httpServletRequest.getRequestURI(), "/recommendations") &&
			!StringUtils.contains(
				httpServletRequest.getRequestURI(), "/reports")) {

			if (!_dataSourceDog.existsDataSource(
					faroBackendSecuritySignature)) {

				logInvalidRequest(
					faroBackendSecuritySignature, httpServletRequest);

				return true;
			}

			return false;
		}

		return super.isInvalidRequest(httpServletRequest);
	}

	private static final Set<String> _functionalRequestURIs = new HashSet<>() {
		{
			add("/functional/blogsdaily");
			add("/functional/documentlibrariesdaily");
			add("/functional/event-definition");
			add("/functional/events");
			add("/functional/identities");
			add("/functional/individuals");
			add("/functional/journalsdaily");
			add("/functional/pagesdaily");
			add("/functional/sessions");
		}
	};

	@Autowired
	private DataSourceDog _dataSourceDog;

	@Autowired
	private Environment _environment;

	@Value("${osb.asah.functional.request.token:}")
	private String _functionalRequestToken;

}