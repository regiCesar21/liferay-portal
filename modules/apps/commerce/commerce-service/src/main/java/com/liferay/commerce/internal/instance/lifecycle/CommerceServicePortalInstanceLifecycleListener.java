/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.instance.lifecycle;

import com.liferay.commerce.constants.CommerceSAPConstants;
import com.liferay.commerce.helper.CommerceSAPHelper;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, property = "sap.scope.finder=true",
	service = {
		PortalInstanceLifecycleListener.class, ScopeFinder.class,
		ScopeMapper.class
	}
)
public class CommerceServicePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements ScopeFinder, ScopeMapper {

	@Override
	public Collection<String> findScopes() {
		return _scopeAliasesList;
	}

	@Override
	public Set<String> map(String scope) {
		return Collections.singleton(scope);
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_commerceSAPHelper.addCommerceDefaultSAPEntries(
			company.getCompanyId(), user.getUserId());
	}

	@Activate
	protected void activate() {
		Stream<String[]> stream = Arrays.stream(
			CommerceSAPConstants.SAP_ENTRY_OBJECT_ARRAYS);

		_scopeAliasesList = stream.map(
			sapEntryObjectArray -> StringUtil.replaceFirst(
				sapEntryObjectArray[0], "OAUTH2_", StringPool.BLANK)
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private CommerceSAPHelper _commerceSAPHelper;

	private List<String> _scopeAliasesList;

	@Reference
	private UserLocalService _userLocalService;

}