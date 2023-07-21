/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductOption;
import com.liferay.commerce.product.definitions.web.internal.security.permission.resource.CommerceCatalogPermission;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS,
	service = ClayDataSetActionProvider.class
)
public class CommerceProductOptionDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ProductOption productOption = (ProductOption)model;

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				productOption.getCPDefinitionOptionRelId());

		return DropdownItemListBuilder.add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				cpDefinitionOptionRel.getCPDefinition(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductOptionEditURL(
						cpDefinitionOptionRel, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				cpDefinitionOptionRel.getCPDefinition(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.putData("method", "delete");
				dropdownItem.setHref(
					_getProductOptionDeleteURL(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId()));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
				dropdownItem.setTarget("async");
			}
		).build();
	}

	private String _getProductOptionDeleteURL(long cpDefinitionOptionRelId) {
		return "/o/headless-commerce-admin-catalog/v1.0/productOptions/" +
			cpDefinitionOptionRelId;
	}

	private PortletURL _getProductOptionEditURL(
			CPDefinitionOptionRel cpDefinitionOptionRel,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/cp_definitions/edit_cp_definition_option_rel");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(cpDefinitionOptionRel.getCPDefinitionId()));
		portletURL.setParameter(
			"cpDefinitionOptionRelId",
			String.valueOf(cpDefinitionOptionRel.getCPDefinitionOptionRelId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductOptionDataSetActionProvider.class);

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private Portal _portal;

}