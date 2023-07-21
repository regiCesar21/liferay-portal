/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.commerce.application.list.constants.CommercePanelCategoryKeys;
import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"panel.app.order:Integer=200",
		"panel.category.key=" + CommercePanelCategoryKeys.COMMERCE_PRICING
	},
	service = PanelApp.class
)
public class CommerceDiscountPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CommerceDiscountPortletKeys.COMMERCE_DISCOUNT;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		boolean show = super.isShow(permissionChecker, group);

		if (show) {
			boolean viewCommerceDiscounts = _portletPermission.contains(
				permissionChecker,
				CommerceDiscountPortletKeys.COMMERCE_DISCOUNT, ActionKeys.VIEW);

			if (!viewCommerceDiscounts ||
				!Objects.equals(
					_getCommercePricingConfigurationKey(),
					CommercePricingConstants.VERSION_1_0)) {

				show = false;
			}
		}

		return show;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	private String _getCommercePricingConfigurationKey()
		throws ConfigurationException {

		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		return commercePricingConfiguration.commercePricingCalculationKey();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private PortletPermission _portletPermission;

}