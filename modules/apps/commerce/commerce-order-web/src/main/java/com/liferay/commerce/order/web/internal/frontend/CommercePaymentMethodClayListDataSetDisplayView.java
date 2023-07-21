/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend;

import com.liferay.commerce.order.web.internal.frontend.constants.CommerceOrderDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.list.BaseListClayDataSetDisplayView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceOrderDataSetConstants.COMMERCE_DATA_SET_KEY_PAYMENT_METHODS,
	service = ClayDataSetDisplayView.class
)
public class CommercePaymentMethodClayListDataSetDisplayView
	extends BaseListClayDataSetDisplayView {

	@Override
	public String getDescription() {
		return "description";
	}

	@Override
	public String getThumbnail() {
		return "thumbnail";
	}

	@Override
	public String getTitle() {
		return "title";
	}

}