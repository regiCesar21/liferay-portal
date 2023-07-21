/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.Objects;

import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class PriceTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		CommerceContext commerceContext = (CommerceContext)request.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Map<String, Object> context = getContext();

			long cpInstanceId = (Long)context.getOrDefault("CPInstanceId", 0);

			int quantity = (Integer)context.getOrDefault("quantity", 1);

			if (quantity <= 0) {
				ProductSettingsModel productSettingsModel =
					_productHelper.getProductSettingsModel(cpInstanceId);

				quantity = productSettingsModel.getMinQuantity();
			}

			PriceModel priceModel = null;

			if (cpInstanceId > 0) {
				priceModel = _productHelper.getPriceModel(
					cpInstanceId, quantity, commerceContext, StringPool.BLANK,
					themeDisplay.getLocale());
			}
			else {
				long cpDefinitionId = (Long)context.getOrDefault(
					"CPDefinitionId", 0);

				priceModel = _productHelper.getMinPrice(
					cpDefinitionId, commerceContext, themeDisplay.getLocale());
			}

			CommercePriceConfiguration commercePriceConfiguration =
				_configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.PRICE_SERVICE_NAME));

			putValue(
				"displayDiscountLevels",
				commercePriceConfiguration.displayDiscountLevels());

			putValue("prices", priceModel);

			boolean netPrice = true;

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.fetchCommerceChannel(
					commerceContext.getCommerceChannelId());

			if ((commerceChannel != null) &&
				Objects.equals(
					commerceChannel.getPriceDisplayType(),
					CommercePricingConstants.TAX_INCLUDED_IN_PRICE)) {

				netPrice = false;
			}

			putValue("netPrice", netPrice);

			setTemplateNamespace("Price.render");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/price/Price.es");
	}

	public void setAdditionalDiscountClasses(String additionalDiscountClasses) {
		putValue("additionalDiscountClasses", additionalDiscountClasses);
	}

	public void setAdditionalPriceClasses(String additionalPriceClasses) {
		putValue("additionalPriceClasses", additionalPriceClasses);
	}

	public void setAdditionalPromoPriceClasses(
		String additionalPromoPriceClasses) {

		putValue("additionalPromoPriceClasses", additionalPromoPriceClasses);
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		putValue("CPDefinitionId", cpDefinitionId);
	}

	public void setCPInstanceId(long cpInstanceId) {
		putValue("CPInstanceId", cpInstanceId);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_commerceChannelLocalService =
			ServletContextUtil.getCommerceChannelLocalService();
		_configurationProvider = ServletContextUtil.getConfigurationProvider();
		_productHelper = ServletContextUtil.getProductHelper();
	}

	public void setQuantity(String quantity) {
		putValue("quantity", quantity);
	}

	private static final Log _log = LogFactoryUtil.getLog(PriceTag.class);

	private CommerceChannelLocalService _commerceChannelLocalService;
	private ConfigurationProvider _configurationProvider;
	private ProductHelper _productHelper;

}