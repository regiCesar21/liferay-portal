/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductConsumptionWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = UpgradeProductConsumptions.class)
public class UpgradeProductConsumptions extends UpgradeProcess {

	public void upgradeProductConsumptions() throws Exception {
		updateProductConsumptions(null);
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	protected void updateProductConsumptions(Date endDate) throws Exception {
		FilterQuery filterQuery = new FilterQuery();

		filterQuery.addEquals(true, "productPurchaseKey", (String)null, true);

		if (endDate != null) {
			filterQuery.addGreaterThanEquals(true, "endDate", endDate);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Upgrading product consumptions after " + endDate);
		}

		Date latestEndDate = null;

		for (int i = 1; i <= 10; i++) {
			List<ProductConsumption> productConsumptions =
				_productConsumptionWebService.search(
					filterQuery, i, 1000, "endDate");

			for (ProductConsumption productConsumption : productConsumptions) {
				ProductPurchase productPurchase =
					_productPurchaseWebService.getProductPurchase(
						productConsumption.getProductPurchaseKey());

				latestEndDate = productConsumption.getEndDate();

				if (((latestEndDate != null) &&
					 latestEndDate.equals(productPurchase.getEndDate())) ||
					((latestEndDate == null) &&
					 (productPurchase.getEndDate() == null))) {

					continue;
				}

				productConsumption.setEndDate(productPurchase.getEndDate());
				productConsumption.setStartDate(productPurchase.getStartDate());

				_productConsumptionWebService.updateProductConsumption(
					StringPool.BLANK, StringPool.BLANK,
					productConsumption.getKey(), productConsumption);
			}
		}

		if (latestEndDate != null) {
			if (latestEndDate.equals(endDate)) {
				latestEndDate = new Date(latestEndDate.getTime() + 1000);
			}

			updateProductConsumptions(latestEndDate);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeProductConsumptions.class);

	@Reference
	private ProductConsumptionWebService _productConsumptionWebService;

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

}