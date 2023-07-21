/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductLink;
import com.liferay.commerce.product.definitions.web.internal.security.permission.resource.CommerceCatalogPermission;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
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
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_LINKS,
	service = ClayDataSetActionProvider.class
)
public class CommerceProductDefinitionLinkDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ProductLink productLink = (ProductLink)model;

		CPDefinitionLink cpDefinitionLink =
			_cpDefinitionLinkService.getCPDefinitionLink(
				productLink.getCPDefinitionLinkId());

		return DropdownItemListBuilder.add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				cpDefinitionLink.getCPDefinition(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductLinkEditURL(
						cpDefinitionLink, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("modal");
			}
		).add(
			() -> CommerceCatalogPermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				cpDefinitionLink.getCPDefinition(), ActionKeys.UPDATE),
			dropdownItem -> {
				dropdownItem.setHref(
					_getProductLinkDeleteURL(
						cpDefinitionLink, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getProductLinkDeleteURL(
			CPDefinitionLink cpDefinitionLink,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			_portal.getOriginalServletRequest(httpServletRequest),
			CPPortletKeys.CP_DEFINITIONS, PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/cp_definitions/edit_cp_definition_link");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(cpDefinitionLink.getCPDefinitionId()));
		portletURL.setParameter(
			"cpDefinitionLinkId",
			String.valueOf(cpDefinitionLink.getCPDefinitionLinkId()));

		return portletURL;
	}

	private PortletURL _getProductLinkEditURL(
			CPDefinitionLink cpDefinitionLink,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CPDefinition.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/cp_definitions/edit_cp_definition_link");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(cpDefinitionLink.getCPDefinitionId()));
		portletURL.setParameter(
			"cpDefinitionLinkId",
			String.valueOf(cpDefinitionLink.getCPDefinitionLinkId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductDefinitionLinkDataSetActionProvider.class);

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

	@Reference
	private Portal _portal;

}