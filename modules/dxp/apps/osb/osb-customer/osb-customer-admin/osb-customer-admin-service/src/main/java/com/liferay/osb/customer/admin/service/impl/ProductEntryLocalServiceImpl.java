/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.admin.service.impl;

import com.liferay.osb.customer.admin.constants.ExternalIdMapperConstants;
import com.liferay.osb.customer.admin.constants.ProductEntryConstants;
import com.liferay.osb.customer.admin.exception.DuplicateProductEntryException;
import com.liferay.osb.customer.admin.exception.ProductEntryKoroneikiProductKeyException;
import com.liferay.osb.customer.admin.exception.ZendeskTagException;
import com.liferay.osb.customer.admin.model.ExternalIdMapper;
import com.liferay.osb.customer.admin.model.ProductEntry;
import com.liferay.osb.customer.admin.service.base.ProductEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class ProductEntryLocalServiceImpl
	extends ProductEntryLocalServiceBaseImpl {

	public ProductEntry addProductEntry(
			long userId, String koroneikiProductKey, String name,
			int environment, boolean accountEnvironments, boolean licenses,
			String versionsListType, String zendeskTag)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(0, koroneikiProductKey, zendeskTag);

		long productEntryId = counterLocalService.increment();

		ProductEntry productEntry = productEntryPersistence.create(
			productEntryId);

		productEntry.setUserId(user.getUserId());
		productEntry.setUserName(user.getFullName());
		productEntry.setCreateDate(now);
		productEntry.setModifiedDate(now);
		productEntry.setKoroneikiProductKey(koroneikiProductKey);
		productEntry.setName(name);
		productEntry.setEnvironment(environment);
		productEntry.setAccountEnvironments(accountEnvironments);
		productEntry.setLicenses(licenses);
		productEntry.setVersionsListType(versionsListType);

		productEntry = productEntryPersistence.update(productEntry);

		long classNameId = classNameLocalService.getClassNameId(
			ProductEntry.class);

		if (Validator.isNotNull(zendeskTag)) {
			externalIdMapperLocalService.addExternalIdMapper(
				classNameId, productEntryId,
				ExternalIdMapperConstants.TYPE_ZENDESK, zendeskTag);
		}

		return productEntry;
	}

	@Override
	public ProductEntry deleteProductEntry(long productEntryId)
		throws PortalException {

		// Product entry

		ProductEntry productEntry = productEntryPersistence.remove(
			productEntryId);

		// External ids

		long classNameId = classNameLocalService.getClassNameId(
			ProductEntry.class.getName());

		externalIdMapperPersistence.removeByC_C(classNameId, productEntryId);

		return productEntry;
	}

	public ProductEntry deleteProductEntry(String koroneikiProductKey)
		throws PortalException {

		return productEntryPersistence.removeByKoroneikiProductKey(
			koroneikiProductKey);
	}

	public ProductEntry fetchProductEntryByKoroneikiKey(
		String koroneikiProductKey) {

		return productEntryPersistence.fetchByKoroneikiProductKey(
			koroneikiProductKey);
	}

	public ProductEntry fetchProductEntryByName(String name) {
		return productEntryPersistence.fetchByName(name);
	}

	public ProductEntry getDeveloperProductEntry(long productEntryId)
		throws PortalException {

		ProductEntry productEntry = productEntryPersistence.findByPrimaryKey(
			productEntryId);

		List<ProductEntry> productEntries =
			productEntryPersistence.findByEnvironment(
				ProductEntryConstants.ENVIRONMENT_DEVELOPMENT);

		for (ProductEntry curProductEntry : productEntries) {
			if ((curProductEntry.isCommerceSubscription() &&
				 (productEntry.isCommerceForDXPCloud() ||
				  productEntry.isCommerceSubscription())) ||
				(curProductEntry.isDXP() && productEntry.isDXP()) ||
				(curProductEntry.isDXP() &&
				 (productEntry.isDXPCloud() || productEntry.isLiferayPaas())) ||
				(curProductEntry.isPortal() && productEntry.isPortal())) {

				return curProductEntry;
			}
		}

		return null;
	}

	public List<ProductEntry> getProductEntries(boolean licenses) {
		return productEntryPersistence.findByLicenses(
			licenses, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<ProductEntry> getProductEntriesByEnvironment(int environment)
		throws PortalException {

		return productEntryPersistence.findByEnvironment(environment);
	}

	public ProductEntry getProductEntryByKoroneikiKey(
			String koroneikiProductKey)
		throws PortalException {

		return productEntryPersistence.findByKoroneikiProductKey(
			koroneikiProductKey);
	}

	public ProductEntry getProductEntryByName(String name)
		throws PortalException {

		return productEntryPersistence.findByName(name);
	}

	public List<ProductEntry> search(
		String name, LinkedHashMap<String, Object> params, int start, int end) {

		return productEntryFinder.findByName(name, params, start, end);
	}

	public int searchCount(String name, LinkedHashMap<String, Object> params) {
		return productEntryFinder.countByName(name, params);
	}

	public ProductEntry updateName(long productEntryId, String name)
		throws PortalException {

		ProductEntry productEntry = productEntryPersistence.findByPrimaryKey(
			productEntryId);

		productEntry.setName(name);

		return productEntryPersistence.update(productEntry);
	}

	public ProductEntry updateProductEntry(
			long productEntryId, String koroneikiProductKey, String name,
			int environment, boolean accountEnvironments, boolean licenses,
			String versionsListType, String zendeskTag)
		throws PortalException {

		validate(productEntryId, koroneikiProductKey, zendeskTag);

		ProductEntry productEntry = productEntryPersistence.findByPrimaryKey(
			productEntryId);

		productEntry.setModifiedDate(new Date());
		productEntry.setKoroneikiProductKey(koroneikiProductKey);
		productEntry.setName(name);
		productEntry.setEnvironment(environment);
		productEntry.setAccountEnvironments(accountEnvironments);
		productEntry.setLicenses(licenses);
		productEntry.setVersionsListType(versionsListType);

		productEntry = productEntryPersistence.update(productEntry);

		long classNameId = classNameLocalService.getClassNameId(
			ProductEntry.class);

		List<ExternalIdMapper> externalIdMappers =
			externalIdMapperLocalService.getExternalIdMappers(
				classNameId, productEntryId,
				ExternalIdMapperConstants.TYPE_ZENDESK);

		if (Validator.isNotNull(zendeskTag)) {
			if (!externalIdMappers.isEmpty()) {
				ExternalIdMapper externalIdMapper = externalIdMappers.get(0);

				externalIdMapperLocalService.updateExternalIdMapper(
					externalIdMapper.getExternalIdMapperId(), classNameId,
					productEntryId, ExternalIdMapperConstants.TYPE_ZENDESK,
					zendeskTag);
			}
			else {
				externalIdMapperLocalService.addExternalIdMapper(
					classNameId, productEntryId,
					ExternalIdMapperConstants.TYPE_ZENDESK, zendeskTag);
			}
		}
		else if (!externalIdMappers.isEmpty()) {
			ExternalIdMapper externalIdMapper = externalIdMappers.get(0);

			externalIdMapperLocalService.deleteExternalIdMapper(
				externalIdMapper.getExternalIdMapperId());
		}

		return productEntry;
	}

	protected void validate(
			long productEntryId, String koroneikiProductKey, String zendeskTag)
		throws PortalException {

		if (Validator.isNull(koroneikiProductKey)) {
			throw new ProductEntryKoroneikiProductKeyException();
		}

		ProductEntry productEntry =
			productEntryPersistence.fetchByKoroneikiProductKey(
				koroneikiProductKey);

		if ((productEntry != null) &&
			(productEntry.getProductEntryId() != productEntryId)) {

			throw new DuplicateProductEntryException();
		}

		if (Validator.isNotNull(zendeskTag) &&
			!zendeskTag.matches("^[a-z0-9_-]*$")) {

			throw new ZendeskTagException();
		}
	}

}