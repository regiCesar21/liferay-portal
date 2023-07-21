/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.info.item.renderer.template.InfoItemRendererTemplate;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_available_templates"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableTemplatesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(resourceRequest, "className");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<InfoItemRenderer<?>> infoItemRenderers =
			_infoItemRendererTracker.getInfoItemRenderers(className);

		Object infoItemObject = _getInfoItemObject(className, classPK);

		for (InfoItemRenderer<?> infoItemRenderer : infoItemRenderers) {
			if (infoItemRenderer instanceof InfoItemTemplatedRenderer) {
				JSONArray templatesJSONArray =
					JSONFactoryUtil.createJSONArray();

				InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer =
					(InfoItemTemplatedRenderer<Object>)infoItemRenderer;

				List<InfoItemRendererTemplate> infoItemRendererTemplates =
					infoItemTemplatedRenderer.getInfoItemRendererTemplates(
						infoItemObject, themeDisplay.getLocale());

				Collections.sort(
					infoItemRendererTemplates,
					Comparator.comparing(InfoItemRendererTemplate::getLabel));

				for (InfoItemRendererTemplate infoItemRendererTemplate :
						infoItemRendererTemplates) {

					templatesJSONArray.put(
						JSONUtil.put(
							"infoItemRendererKey", infoItemRenderer.getKey()
						).put(
							"label", infoItemRendererTemplate.getLabel()
						).put(
							"templateKey",
							infoItemRendererTemplate.getTemplateKey()
						));
				}

				jsonArray.put(
					JSONUtil.put(
						"label",
						infoItemTemplatedRenderer.
							getInfoItemRendererTemplatesGroupLabel(
								infoItemObject, themeDisplay.getLocale())
					).put(
						"templates", templatesJSONArray
					));
			}
			else {
				jsonArray.put(
					JSONUtil.put(
						"infoItemRendererKey", infoItemRenderer.getKey()
					).put(
						"label",
						infoItemRenderer.getLabel(themeDisplay.getLocale())
					));
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private Object _getInfoItemObject(String className, long classPK) {
		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		try {
			if (infoItemObjectProvider != null) {
				InfoItemIdentifier infoItemIdentifier =
					new ClassPKInfoItemIdentifier(classPK);

				return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
			}
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}

		return null;
	}

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}