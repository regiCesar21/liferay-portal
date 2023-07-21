/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.PageFragmentInstanceDefinitionDTOConverter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter.PageWidgetInstanceDefinitionDTOConverter;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class FragmentLayoutStructureItemExporter
	extends BaseStyledLayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return FragmentStyledLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)layoutStructureItem;

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			return null;
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}

		String portletId = editableValuesJSONObject.getString("portletId");

		JSONObject itemConfigJSONObject =
			fragmentStyledLayoutStructureItem.getItemConfigJSONObject();

		if (Validator.isNull(portletId)) {
			return new PageElement() {
				{
					definition =
						_pageFragmentInstanceDefinitionDTOConverter.toDTO(
							fragmentStyledLayoutStructureItem,
							toFragmentStyle(
								itemConfigJSONObject.getJSONObject("styles"),
								saveMappingConfiguration),
							getFragmentViewPorts(itemConfigJSONObject),
							saveInlineContent, saveMappingConfiguration);
					type = PageElement.Type.FRAGMENT;
				}
			};
		}

		String instanceId = editableValuesJSONObject.getString("instanceId");

		return new PageElement() {
			{
				definition = _pageWidgetInstanceDefinitionDTOConverter.toDTO(
					fragmentEntryLink,
					toFragmentStyle(
						itemConfigJSONObject.getJSONObject("styles"),
						saveMappingConfiguration),
					getFragmentViewPorts(
						itemConfigJSONObject.getJSONObject("style")),
					PortletIdCodec.encode(portletId, instanceId));
				type = PageElement.Type.WIDGET;
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentLayoutStructureItemExporter.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private PageFragmentInstanceDefinitionDTOConverter
		_pageFragmentInstanceDefinitionDTOConverter;

	@Reference
	private PageWidgetInstanceDefinitionDTOConverter
		_pageWidgetInstanceDefinitionDTOConverter;

}