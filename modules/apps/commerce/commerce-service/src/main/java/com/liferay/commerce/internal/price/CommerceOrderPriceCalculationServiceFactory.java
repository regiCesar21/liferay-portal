/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.price;

import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	configurationPid = "com.liferay.commerce.pricing.configuration.CommercePricingConfiguration",
	enabled = false, immediate = true, service = ServiceFactory.class
)
public class CommerceOrderPriceCalculationServiceFactory
	implements ServiceFactory<CommerceOrderPriceCalculation> {

	@Override
	public CommerceOrderPriceCalculation getService(
		Bundle bundle,
		ServiceRegistration<CommerceOrderPriceCalculation>
			serviceRegistration) {

		return new CommerceOrderPriceCalculationV2Impl(
			_commerceChannelLocalService, _commerceDiscountCalculationV2,
			_commerceMoneyFactory, _commerceTaxCalculation);
	}

	@Override
	public void ungetService(
		Bundle bundle,
		ServiceRegistration<CommerceOrderPriceCalculation> serviceRegistration,
		CommerceOrderPriceCalculation commerceProductPriceCalculation) {
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_serviceRegistration = bundleContext.registerService(
			CommerceOrderPriceCalculation.class, this,
			new Hashtable<String, Object>());

		_commercePricingConfiguration = ConfigurableUtil.createConfigurable(
			CommercePricingConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(target = "(commerce.discount.calculation.key=v2.0)")
	private CommerceDiscountCalculation _commerceDiscountCalculationV2;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	private volatile CommercePricingConfiguration _commercePricingConfiguration;

	@Reference
	private CommerceTaxCalculation _commerceTaxCalculation;

	private ServiceRegistration<CommerceOrderPriceCalculation>
		_serviceRegistration;

}