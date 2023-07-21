/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.measurement.unit.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.commerce.application.list.constants.CommercePanelCategoryKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"panel.app.order:Integer=300",
		"panel.category.key=" + CommercePanelCategoryKeys.COMMERCE_SETTINGS
	},
	service = PanelApp.class
)
public class CommerceMeasurementUnitPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CPPortletKeys.CP_MEASUREMENT_UNIT;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CPPortletKeys.CP_MEASUREMENT_UNIT + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}