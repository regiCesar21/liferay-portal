/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.provisioning.koroneiki.web.service.ExternalLinkWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductWebService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Veloso
 */
@Component(service = UpgradeProducts.class)
public class UpgradeProducts extends UpgradeProcess {

	public void upgradeProductName(
		String oldProductName, String newProductName, String newDisplayName,
		String newDisplayGroupName) {

		try {
			Product product = _productWebService.fetchProductByName(
				oldProductName);

			if (product == null) {
				List<Product> products = _productWebService.getProducts(
					ExternalLinkDomain.SALESFORCE,
					ExternalLinkEntityName.SALESFORCE_PRODUCT, oldProductName,
					1, 1);

				if (!products.isEmpty()) {
					product = products.get(0);

					_updateExternalLink(
						oldProductName, newProductName,
						product.getExternalLinks());
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Product with name " + oldProductName +
								" was not found.");
					}
				}
			}
			else {
				_updateExternalLink(
					oldProductName, newProductName, product.getExternalLinks());

				product.setName(newProductName);

				Map<String, String> properties = product.getProperties();

				properties.put("display-group-name", newDisplayGroupName);
				properties.put("display-name", newDisplayName);

				product.setProperties(properties);

				_productWebService.updateProduct(
					StringPool.BLANK, StringPool.BLANK, product.getKey(),
					product);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private void _updateExternalLink(
		String oldProductName, String newProductName,
		ExternalLink[] externalLinks) {

		for (ExternalLink externalLink : externalLinks) {
			String entityId = externalLink.getEntityId();

			if (entityId.equals(oldProductName)) {
				externalLink.setEntityId(newProductName);

				try {
					_externalLinkWebService.updateExternalLink(
						StringPool.BLANK, StringPool.BLANK,
						externalLink.getKey(), externalLink);
				}
				catch (Exception exception) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Product with name " + oldProductName +
								" was not update.");
					}
				}

				break;
			}
		}
	}

	@Reference
	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeProducts.class);

	@Reference
	private ExternalLinkWebService _externalLinkWebService;

	@Reference
	private ProductWebService _productWebService;

}