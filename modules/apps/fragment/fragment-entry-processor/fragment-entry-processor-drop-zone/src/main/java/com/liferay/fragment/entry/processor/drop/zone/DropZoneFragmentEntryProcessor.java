/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.drop.zone;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.renderer.FragmentDropZoneRenderer;
import com.liferay.layout.constants.LayoutWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=6",
	service = FragmentEntryProcessor.class
)
public class DropZoneFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		return JSONUtil.put(
			JSONUtil.put(
				"content", "<lfr-drop-zone></lfr-drop-zone>"
			).put(
				"name", "lfr-drop-zone"
			));
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("lfr-drop-zone");

		if (elements.size() <= 0) {
			return html;
		}

		HttpServletRequest httpServletRequest =
			fragmentEntryProcessorContext.getHttpServletRequest();

		LayoutStructure layoutStructure =
			(LayoutStructure)httpServletRequest.getAttribute(
				LayoutWebKeys.LAYOUT_STRUCTURE);

		if (layoutStructure == null) {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						fragmentEntryLink.getGroupId(),
						fragmentEntryLink.getPlid());

			if (layoutPageTemplateStructure == null) {
				return html;
			}

			layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					fragmentEntryLink.getSegmentsExperienceId()));
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());

		if (layoutStructureItem == null) {
			return html;
		}

		List<String> dropZoneItemIds = layoutStructureItem.getChildrenItemIds();

		if (Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.EDIT)) {

			boolean idsAvailable = true;

			for (Element element : elements) {
				String dropZoneId = element.attr("data-lfr-drop-zone-id");

				if (Validator.isBlank(dropZoneId)) {
					idsAvailable = false;

					break;
				}
			}

			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-167932")) ||
				!idsAvailable) {

				for (int i = 0;
					 (i < dropZoneItemIds.size()) && (i < elements.size());
					 i++) {

					Element element = elements.get(i);

					element.attr("uuid", dropZoneItemIds.get(i));
				}
			}
			else {
				Map<String, String> fragmentDropZoneIdsMap =
					new LinkedHashMap<>();

				List<String> noFragmentDropZoneItemIds = new LinkedList<>();

				for (String dropZoneItemId : dropZoneItemIds) {
					LayoutStructureItem childLayoutStructureItem =
						layoutStructure.getLayoutStructureItem(dropZoneItemId);

					if (!(childLayoutStructureItem instanceof
							FragmentDropZoneLayoutStructureItem)) {

						continue;
					}

					FragmentDropZoneLayoutStructureItem
						fragmentDropZoneLayoutStructureItem =
							(FragmentDropZoneLayoutStructureItem)
								childLayoutStructureItem;

					String fragmentDropZoneId =
						fragmentDropZoneLayoutStructureItem.
							getFragmentDropZoneId();

					if (Validator.isBlank(fragmentDropZoneId)) {
						noFragmentDropZoneItemIds.add(dropZoneItemId);
					}
					else {
						fragmentDropZoneIdsMap.put(
							fragmentDropZoneId, dropZoneItemId);
					}
				}

				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);

					String dropZoneId = element.attr("data-lfr-drop-zone-id");

					if (fragmentDropZoneIdsMap.containsKey(dropZoneId)) {
						element.attr(
							"uuid", fragmentDropZoneIdsMap.get(dropZoneId));
					}
					else if (ListUtil.isNotEmpty(noFragmentDropZoneItemIds)) {
						element.attr(
							"uuid", noFragmentDropZoneItemIds.remove(0));
					}
				}
			}

			Element bodyElement = document.body();

			return bodyElement.html();
		}

		Optional<Map<String, Object>> fieldValuesOptional =
			fragmentEntryProcessorContext.getFieldValuesOptional();

		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);

			String dropZoneHTML = _fragmentDropZoneRenderer.renderDropZone(
				fragmentEntryProcessorContext.getHttpServletRequest(),
				fragmentEntryProcessorContext.getHttpServletResponse(),
				fieldValuesOptional.orElse(null),
				fragmentEntryLink.getGroupId(), 0, dropZoneItemIds.get(i),
				fragmentEntryProcessorContext.getMode(), true);

			Element dropZoneElement = new Element("div");

			dropZoneElement.html(dropZoneHTML);

			element.replaceWith(dropZoneElement);
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-167932"))) {
			return;
		}

		Document document = _getDocument(html);

		Elements elements = document.select("lfr-drop-zone");

		if (elements.isEmpty()) {
			return;
		}

		Set<String> elementDropZoneIds = new LinkedHashSet<>();

		for (Element element : elements) {
			String dropZoneId = element.attr("data-lfr-drop-zone-id");

			if (!Validator.isBlank(dropZoneId)) {
				elementDropZoneIds.add(dropZoneId);
			}
		}

		if (!elementDropZoneIds.isEmpty() &&
			(elementDropZoneIds.size() != elements.size())) {

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				_language.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-drop-zone"));
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	@Reference
	private FragmentDropZoneRenderer _fragmentDropZoneRenderer;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}