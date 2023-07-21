/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.application.item.selector.web.internal.search;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationModelItemSelectorChecker
	extends EmptyOnClickRowChecker {

	public CommerceApplicationModelItemSelectorChecker(
		RenderResponse renderResponse,
		long[] checkedCommerceApplicationModelIds) {

		super(renderResponse);

		_checkedCommerceApplicationModelIds = SetUtil.fromArray(
			checkedCommerceApplicationModelIds);
	}

	@Override
	public boolean isChecked(Object object) {
		CommerceApplicationModel commerceApplicationModel =
			(CommerceApplicationModel)object;

		return _checkedCommerceApplicationModelIds.contains(
			commerceApplicationModel.getCommerceApplicationModelId());
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final Set<Long> _checkedCommerceApplicationModelIds;

}