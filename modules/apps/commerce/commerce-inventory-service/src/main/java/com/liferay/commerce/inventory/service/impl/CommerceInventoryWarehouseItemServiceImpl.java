/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseItemServiceImpl
	extends CommerceInventoryWarehouseItemServiceBaseImpl {

	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				userId, commerceInventoryWarehouseId, sku, quantity);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommerceInventoryWarehouseItem(String,
	 *             long, long, String, int)}
	 */
	@Deprecated
	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseId,
			String externalReferenceCode, String sku, int quantity)
		throws PortalException {

		return addCommerceInventoryWarehouseItem(
			externalReferenceCode, userId, commerceInventoryWarehouseId, sku,
			quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			String externalReferenceCode, long userId,
			long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, userId, commerceInventoryWarehouseId,
				sku, quantity);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItems(
			long companyId, String sku)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		commerceInventoryWarehouseItemLocalService.
			deleteCommerceInventoryWarehouseItems(companyId, sku);
	}

	@Override
	public CommerceInventoryWarehouseItem fetchCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku);
	}

	@Override
	public CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItemByReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			fetchCommerceInventoryWarehouseItemByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getCommerceInventoryWarehouseItemByReferenceCode(String,
	 *             long)}
	 */
	@Deprecated
	@Override
	public CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return getCommerceInventoryWarehouseItemByReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemByReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long commerceInventoryWarehouseId, int start, int end)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItems(
				commerceInventoryWarehouseId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long companyId, String sku, int start, int end)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItems(companyId, sku, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyId(
				long companyId, int start, int end)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByCompanyId(
				companyId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
				long companyId, String sku, int start, int end)
		throws PrincipalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
				companyId, sku, start, end);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long commerceInventoryWarehouseId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(
				commerceInventoryWarehouseId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
			long companyId, String sku)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCount(companyId, sku);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByCompanyId(
			long companyId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCountByCompanyId(companyId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByModifiedDate(
			long companyId, Date startDate, Date endDate)
		throws PrincipalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				long companyId, Date startDate, Date endDate, int start,
				int end)
		throws PrincipalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			getCommerceInventoryWarehouseItemsByModifiedDate(
				companyId, startDate, endDate, start, end);
	}

	@Override
	public CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long commerceInventoryWarehouseItemId, int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			increaseCommerceInventoryWarehouseItemQuantity(
				getUserId(), commerceInventoryWarehouseItemId, quantity);
	}

	@Override
	public void moveQuantitiesBetweenWarehouses(
			long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		commerceInventoryWarehouseItemLocalService.
			moveQuantitiesBetweenWarehouses(
				getUserId(), fromCommerceInventoryWarehouseId,
				toCommerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId, int quantity,
			int reservedQuantity, long mvccVersion)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				getUserId(), commerceInventoryWarehouseItemId, quantity,
				reservedQuantity, mvccVersion);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseItemId, int quantity,
			long mvccVersion)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				getUserId(), commerceInventoryWarehouseItemId, quantity,
				mvccVersion);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommerceInventoryWarehouseItem(String,
	 *             long, long, long, String, int)}
	 */
	@Deprecated
	@Override
	public CommerceInventoryWarehouseItem upsertCommerceInventoryWarehouseItem(
			long companyId, long userId, long commerceInventoryWarehouseId,
			String externalReferenceCode, String sku, int quantity)
		throws PortalException {

		return upsertCommerceInventoryWarehouseItem(
			externalReferenceCode, companyId, userId,
			commerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem upsertCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			upsertCommerceInventoryWarehouseItem(
				userId, commerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem upsertCommerceInventoryWarehouseItem(
			String externalReferenceCode, long companyId, long userId,
			long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryWarehouseItemLocalService.
			upsertCommerceInventoryWarehouseItem(
				externalReferenceCode, companyId, userId,
				commerceInventoryWarehouseId, sku, quantity);
	}

}