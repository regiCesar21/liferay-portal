/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageWidgetInstanceDefinitionDTOConverter.class)
public class PageWidgetInstanceDefinitionDTOConverter {

	public PageWidgetInstanceDefinition toDTO(
		FragmentEntryLink fragmentEntryLink,
		FragmentStyle pageWidgetInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageWidgetInstanceDefinitionFragmentViewports,
		String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				fragmentStyle = pageWidgetInstanceDefinitionFragmentStyle;
				fragmentViewports =
					pageWidgetInstanceDefinitionFragmentViewports;
				widgetInstance = _widgetInstanceDTOConverter.toDTO(
					fragmentEntryLink, portletId);
			}
		};
	}

	public PageWidgetInstanceDefinition toDTO(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		return toDTO(fragmentEntryLink, null, null, portletId);
	}

	@Reference
	private WidgetInstanceDTOConverter _widgetInstanceDTOConverter;

}