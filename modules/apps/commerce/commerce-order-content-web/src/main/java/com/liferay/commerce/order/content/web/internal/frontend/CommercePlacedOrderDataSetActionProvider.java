/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.commerce.order.content.web.internal.frontend.util.CommerceOrderClayTableUtil;
import com.liferay.commerce.order.content.web.internal.model.Order;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PLACED_ORDERS,
	service = ClayDataSetActionProvider.class
)
public class CommercePlacedOrderDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		List<DropdownItem> dropdownItems = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Order order = (Order)model;

		if (_modelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), order.getOrderId(),
				ActionKeys.VIEW)) {

			String viewURL = CommerceOrderClayTableUtil.getOrderViewDetailURL(
				order.getOrderId(), themeDisplay);

			DropdownItem dropdownItem = new DropdownItem();

			dropdownItem.setHref(viewURL);
			dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, "view"));

			dropdownItems.add(dropdownItem);
		}

		return dropdownItems;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder> _modelResourcePermission;

}