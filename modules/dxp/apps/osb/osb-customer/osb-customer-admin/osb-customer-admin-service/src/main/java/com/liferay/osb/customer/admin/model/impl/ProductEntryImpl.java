/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.model.impl;

import com.liferay.osb.customer.admin.constants.ExternalIdMapperConstants;
import com.liferay.osb.customer.admin.constants.ProductEntryConstants;
import com.liferay.osb.customer.admin.model.ExternalIdMapper;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.service.ExternalIdMapperLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class ProductEntryImpl extends ProductEntryBaseImpl {

	public ProductEntryImpl() {
	}

	public List<ListType> getAllLicenseVersionsListTypes() {
		if (isCommerce()) {
			return getVersionsListTypes();
		}

		return getAllVersionsListTypes();
	}

	public List<ListType> getAllVersionsListTypes() {
		if (isCommerce()) {
			return ListTypeServiceUtil.getListTypes(
				ProductEntryConstants.LIST_TYPE_COMMERCE_ALL_VERSIONS);
		}

		if (isPortal()) {
			return ListTypeServiceUtil.getListTypes(
				ProductEntryConstants.LIST_TYPE_PORTAL_ALL_VERSIONS);
		}

		if (isSocialOffice()) {
			return ListTypeServiceUtil.getListTypes(
				ProductEntryConstants.LIST_TYPE_SOCIAL_OFFICE_ALL_VERSIONS);
		}

		String majorListType =
			ProductEntry.class.getName() + StringPool.PERIOD +
				getVersionsListType();

		String allVersionsListType = ProductEntryConstants.getAllListType(
			majorListType);

		if (Validator.isNotNull(allVersionsListType)) {
			return ListTypeServiceUtil.getListTypes(allVersionsListType);
		}

		return ListTypeServiceUtil.getListTypes(
			ProductEntryConstants.LIST_TYPE_DIGITAL_ENTERPRISE_ALL_VERSIONS);
	}

	public String getEnvironmentLabel() {
		return ProductEntryConstants.getEnvironmentLabel(getEnvironment());
	}

	public List<ListType> getVersionsListTypes() {
		String listType =
			ProductEntry.class.getName() + StringPool.PERIOD +
				getVersionsListType();

		return ListTypeServiceUtil.getListTypes(listType);
	}

	public String getZendeskTag() {
		long classNameId = PortalUtil.getClassNameId(
			ProductEntry.class.getName());

		List<ExternalIdMapper> externalIdMappers =
			ExternalIdMapperLocalServiceUtil.getExternalIdMappers(
				classNameId, getProductEntryId(),
				ExternalIdMapperConstants.TYPE_ZENDESK);

		if (!externalIdMappers.isEmpty()) {
			ExternalIdMapper externalIdMapper = externalIdMappers.get(0);

			return externalIdMapper.getExternalId();
		}

		return StringPool.BLANK;
	}

	public boolean isAnalyticsCloud() {
		String name = getName();

		if (name.contains("Analytics Cloud")) {
			return true;
		}

		return false;
	}

	public boolean isAnalyticsCloudBusiness() {
		String name = getName();

		if (name.equals("Liferay Analytics Cloud Business")) {
			return true;
		}

		return false;
	}

	public boolean isAnalyticsCloudEnterprise() {
		String name = getName();

		if (name.equals("Liferay Analytics Cloud Enterprise")) {
			return true;
		}

		return false;
	}

	public boolean isCommerce() {
		String name = getName();

		if (name.contains("Commerce")) {
			return true;
		}

		return false;
	}

	public boolean isCommerceForDXPCloud() {
		String name = getName();

		if (name.startsWith("Commerce for DXP Cloud") ||
			name.contains("Commerce for Liferay PaaS")) {

			return true;
		}

		return false;
	}

	public boolean isCommerceSubscription() {
		String name = getName();

		if (name.contains(ProductEntryConstants.ROOT_COMMERCE_SUBSCRIPTION)) {
			return true;
		}

		return false;
	}

	public boolean isDeveloperTools() {
		String name = getName();

		if (name.contains("Developer Tools")) {
			return true;
		}

		return false;
	}

	public boolean isDeviceDetection() {
		String name = getName();

		if (name.contains("Device Detection")) {
			return true;
		}

		return false;
	}

	public boolean isDXP() {
		String name = getName();

		if (name.startsWith(ProductEntryConstants.ROOT_NAME_DXP) &&
			!name.contains(ProductEntryConstants.ROOT_DXP_CLOUD)) {

			return true;
		}

		return false;
	}

	public boolean isDXPCloud() {
		String name = getName();

		if (name.contains(ProductEntryConstants.ROOT_DXP_CLOUD)) {
			return true;
		}

		return false;
	}

	public boolean isEnterpriseSearch() {
		String name = getName();

		if (name.contains("Enterprise Search")) {
			return true;
		}

		return false;
	}

	public boolean isExtendedPremiumSupport() {
		String name = getName();

		if (name.contains("Extended Premium Support")) {
			return true;
		}

		return false;
	}

	public boolean isLiferayPaas() {
		String name = getName();

		if (name.contains(ProductEntryConstants.ROOT_LIFERAY_PAAS)) {
			return true;
		}

		return false;
	}

	public boolean isManagementTools() {
		String name = getName();

		if (name.contains("Management Tools")) {
			return true;
		}

		return false;
	}

	public boolean isMobileExperience() {
		String name = getName();

		if (name.contains("Mobile Experience")) {
			return true;
		}

		return false;
	}

	public boolean isPortal() {
		String name = getName();

		if (name.contains(ProductEntryConstants.ROOT_NAME_PORTAL)) {
			return true;
		}

		return false;
	}

	public boolean isProductivityTools() {
		String name = getName();

		if (name.contains("Productivity Tools")) {
			return true;
		}

		return false;
	}

	public boolean isSocialOffice() {
		String name = getName();

		if (name.contains("Social Office")) {
			return true;
		}

		return false;
	}

	public boolean isUnlimitedEnterpriseWide() {
		String name = getName();

		if (name.contains("Unlimited Enterprise-Wide")) {
			return true;
		}

		return false;
	}

}