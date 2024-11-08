/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.upgrade.v1_0_1;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductConsumptionWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.provisioning.license.helper.constants.LicenseSizing;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.license.service.LicenseKeyLocalService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Karoline Silva
 */
@Component(service = UpgradeConsumptionsSizing.class)
public class UpgradeConsumptionsSizing extends UpgradeProcess {

	public void upgradeConsumptions() {
		try {
			FilterQuery filterQuery = new FilterQuery();

			filterQuery.addEquals(
				true, "state", ProductPurchaseConstants.STATE_ACTIVE);

			long totalCount = _productPurchaseWebService.searchCount(
				filterQuery);

			int pages = (int)Math.ceil((double)totalCount / 1000);

			for (int page = 1; page <= pages; page++) {
				List<ProductPurchase> activeProductPurchases =
					_productPurchaseWebService.search(
						filterQuery, page, 1000, StringPool.BLANK);

				for (ProductPurchase productPurchase : activeProductPurchases) {
					_processLicenseKeys(
						productPurchase.getAccountKey(),
						productPurchase.getKey());
				}
			}
		}
		catch (Exception exception) {
			_log.error("Error during upgradeConsumptions process", exception);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
	}

	private boolean _isSameSizing(List<LicenseKey> licenseKeys) {
		String sizing = null;

		for (LicenseKey licenseKey : licenseKeys) {
			String curSizing = licenseKey.getSizing();

			if (Validator.isNull(curSizing)) {
				return false;
			}

			if (sizing == null) {
				sizing = curSizing;
			}

			if (!curSizing.equals(sizing)) {
				return false;
			}
		}

		return true;
	}

	private void _processLicenseKeys(
		String accountKey, String productPurchaseKey) {

		try {
			List<LicenseKey> licenseKeys = _licenseKeyLocalService.search(
				null, null, null, null, null, null, accountKey,
				productPurchaseKey, null, null, null, new long[0],
				new String[0], null, null, new String[0], new long[0], null,
				null, null, null, null, null, null, null, null,
				new LinkedHashMap<>(), false, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			boolean isSameSizing = _isSameSizing(licenseKeys);

			for (LicenseKey licenseKey : licenseKeys) {
				_updateProductConsumptions(licenseKey, isSameSizing);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error processing LicenseKeys for accountKey: " + accountKey,
				exception);
		}
	}

	private void _updateProductConsumptions(
		LicenseKey licenseKey, boolean isSameSizing) {

		try {
			List<ProductConsumption> productConsumptions =
				_productConsumptionWebService.getProductConsumptions(
					ExternalLinkDomain.PROVISIONING,
					ExternalLinkEntityName.LICENSE_KEY,
					String.valueOf(licenseKey.getLicenseKeyId()), 1, 1000);

			for (ProductConsumption productConsumption : productConsumptions) {
				productConsumption.setEndDate(licenseKey.getExpirationDate());
				productConsumption.setStartDate(licenseKey.getStartDate());

				Map<String, String> properties = new HashMap<>();

				if (Validator.isNotNull(licenseKey.getSizing()) &&
					isSameSizing) {

					int sizing = LicenseSizing.getSizing(
						licenseKey.getSizing());

					if (sizing > 0) {
						properties.put("sizing", String.valueOf(sizing));
					}
				}
				else {
					properties.put("sizing", "");
				}

				productConsumption.setProperties(properties);

				_productConsumptionWebService.updateProductConsumption(
					StringPool.BLANK, StringPool.BLANK,
					productConsumption.getKey(), productConsumption);
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Updates completed for LicenseKey: " +
						licenseKey.getLicenseKeyId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Error updating consumptions for LicenseKey: " +
					licenseKey.getLicenseKeyId(),
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeConsumptionsSizing.class);

	@Reference
	private LicenseKeyLocalService _licenseKeyLocalService;

	@Reference
	private ProductConsumptionWebService _productConsumptionWebService;

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

}