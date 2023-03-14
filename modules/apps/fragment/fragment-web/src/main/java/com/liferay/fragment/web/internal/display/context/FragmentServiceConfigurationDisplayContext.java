/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.web.internal.configuration.admin.service.FragmentServiceManagedServiceFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentServiceConfigurationDisplayContext {

	public FragmentServiceConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		FragmentServiceManagedServiceFactory
			fragmentServiceManagedServiceFactory,
		String scope) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_fragmentServiceManagedServiceFactory =
			fragmentServiceManagedServiceFactory;
		_scope = scope;
	}

	public String getEditFragmentServiceConfigurationURL() {
		PortletURL editFragmentServiceConfigurationURL =
			_liferayPortletResponse.createActionURL();

		editFragmentServiceConfigurationURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/instance_settings/edit_fragment_service_configuration");
		editFragmentServiceConfigurationURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_httpServletRequest));
		editFragmentServiceConfigurationURL.setParameter("scope", _scope);
		editFragmentServiceConfigurationURL.setParameter(
			"scopePK", String.valueOf(_getScopePk()));

		return editFragmentServiceConfigurationURL.toString();
	}

	public boolean isPropagateChangesEnabled() {
		return _fragmentServiceManagedServiceFactory.isPropagateChanges(
			_scope, _getScopePk());
	}

	public boolean isPropagateContributedFragmentChangesEnabled() {
		return _fragmentServiceManagedServiceFactory.
			isPropagateContributedFragmentChanges(_scope, _getScopePk());
	}

	private long _getScopePk() {
		if (Objects.equals(
				_scope,
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return themeDisplay.getCompanyId();
		}
		else if (Objects.equals(
					_scope,
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return 0L;
		}

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
	}

	private final FragmentServiceManagedServiceFactory
		_fragmentServiceManagedServiceFactory;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final String _scope;

}