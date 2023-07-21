/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.model.listener;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Marco Leo
 */
public class PortalInstanceLifecycleListenerImpl
	extends BasePortalInstanceLifecycleListener {

	public PortalInstanceLifecycleListenerImpl(
		CommerceCurrencyLocalService commerceCurrencyLocalService) {

		_commerceCurrencyLocalService = commerceCurrencyLocalService;
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			CommerceCurrency commerceCurrency =
				_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
					company.getCompanyId());

			if (commerceCurrency != null) {
				return;
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(company.getCompanyId());
			serviceContext.setLanguageId(
				LocaleUtil.toLanguageId(company.getLocale()));

			User defaultUser = company.getDefaultUser();

			serviceContext.setUserId(defaultUser.getUserId());

			_commerceCurrencyLocalService.importDefaultValues(serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstanceLifecycleListenerImpl.class);

	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;

}