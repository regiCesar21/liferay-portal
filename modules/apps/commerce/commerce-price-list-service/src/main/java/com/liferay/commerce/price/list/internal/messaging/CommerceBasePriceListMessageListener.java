/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.messaging;

import com.liferay.commerce.constants.CommerceDestinationNames;
import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "destination.name=" + CommerceDestinationNames.BASE_PRICE_LIST,
	service = MessageListener.class
)
public class CommerceBasePriceListMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long commerceCatalogId = message.getLong("commerceCatalogId");

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalog(
				commerceCatalogId);

		if (commerceCatalog == null) {
			return;
		}

		try {
			_commerceBasePriceListHelper.addCatalogBaseCommercePriceList(
				commerceCatalog);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceBasePriceListMessageListener.class);

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

}