/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.Channel;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

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
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_CHANNELS,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductChannelDataSetDataProvider
	implements ClayDataSetDataProvider<Channel> {

	@Override
	public List<Channel> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<Channel> channels = new ArrayList<>();

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CommerceChannelRel> commerceChannelRels =
			_commerceChannelRelService.getCommerceChannelRels(
				CPDefinition.class.getName(), cpDefinitionId,
				filter.getKeywords(), pagination.getStartPosition(),
				pagination.getEndPosition());

		for (CommerceChannelRel commerceChannelRel : commerceChannelRels) {
			CommerceChannel commerceChannel =
				commerceChannelRel.getCommerceChannel();

			channels.add(
				new Channel(
					commerceChannelRel.getCommerceChannelRelId(),
					commerceChannel.getName()));
		}

		return channels;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _commerceChannelRelService.getCommerceChannelRelsCount(
			CPDefinition.class.getName(), cpDefinitionId, filter.getKeywords());
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

}