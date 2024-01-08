/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.distributed.messaging.internal.subscribing;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.ProductPurchaseSerDes;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductConsumptionWebService;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.license.service.LicenseKeyLocalService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	immediate = true,
	property = "topic.pattern=koroneiki.productpurchase.update",
	service = ProductPurchaseMessageSubscriber.class
)
public class ProductPurchaseMessageSubscriber extends BaseMessageSubscriber {

	@Override
	protected void doParse(JSONObject jsonObject) throws Exception {
		ProductPurchase productPurchase = ProductPurchaseSerDes.toDTO(
			jsonObject.getString("productPurchase"));

		if (productPurchase.getStatus() == ProductPurchase.Status.CANCELLED) {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addEquals(
				true, "productPurchaseKey", productPurchase.getKey());

			List<ProductConsumption> productConsumptions =
				_productConsumptionWebService.search(
					filterQuery, 1, 1000, StringPool.BLANK);

			for (ProductConsumption productConsumption : productConsumptions) {
				_productConsumptionWebService.deleteProductConsumption(
					StringPool.BLANK, StringPool.BLANK,
					productConsumption.getKey());
			}
		}
		else {
			List<LicenseKey> licenseKeys =
				_licenseKeyLocalService.getLicenseKeys(
					productPurchase.getKey(), false, true);

			for (LicenseKey licenseKey : licenseKeys) {
				List<ProductConsumption> productConsumptions =
					_productConsumptionWebService.getProductConsumptions(
						ExternalLinkDomain.PROVISIONING,
						ExternalLinkEntityName.LICENSE_KEY,
						String.valueOf(licenseKey.getLicenseKeyId()), 1, 1000);

				for (ProductConsumption productConsumption :
						productConsumptions) {

					productConsumption.setEndDate(productPurchase.getEndDate());
					productConsumption.setStartDate(
						productPurchase.getStartDate());

					_productConsumptionWebService.updateProductConsumption(
						StringPool.BLANK, StringPool.BLANK,
						productConsumption.getKey(), productConsumption);
				}
			}
		}
	}

	@Reference
	private LicenseKeyLocalService _licenseKeyLocalService;

	@Reference
	private ProductConsumptionWebService _productConsumptionWebService;

}