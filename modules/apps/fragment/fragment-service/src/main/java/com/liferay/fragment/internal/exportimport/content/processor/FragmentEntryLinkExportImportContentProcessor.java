/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntryLink",
	service = {
		ExportImportContentProcessor.class,
		FragmentEntryLinkExportImportContentProcessor.class
	}
)
public class FragmentEntryLinkExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		String portletId = editableValuesJSONObject.getString("portletId");

		if (Validator.isNotNull(portletId)) {
			return content;
		}

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);
		content =
			_layoutReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content,
					exportReferencedContent, escapeContent);

		editableValuesJSONObject = JSONFactoryUtil.createJSONObject(content);

		for (ExportImportContentProcessor<JSONObject>
				exportImportContentProcessor :
					_fragmentEntryLinkEditableValuesExportImportProcessors) {

			editableValuesJSONObject =
				exportImportContentProcessor.replaceExportContentReferences(
					portletDataContext, stagedModel, editableValuesJSONObject,
					exportReferencedContent, escapeContent);
		}

		return editableValuesJSONObject.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		String portletId = editableValuesJSONObject.getString("portletId");

		if (Validator.isNotNull(portletId)) {
			return content;
		}

		content =
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);
		content =
			_layoutReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);

		editableValuesJSONObject = JSONFactoryUtil.createJSONObject(content);

		for (ExportImportContentProcessor<JSONObject>
				exportImportContentProcessor :
					_fragmentEntryLinkEditableValuesExportImportProcessors) {

			editableValuesJSONObject =
				exportImportContentProcessor.replaceImportContentReferences(
					portletDataContext, stagedModel, editableValuesJSONObject);
		}

		return editableValuesJSONObject.toString();
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_fragmentEntryLinkEditableValuesExportImportProcessors =
			ServiceTrackerListFactory.open(
				bundleContext,
				(Class<ExportImportContentProcessor<JSONObject>>)
					(Class<?>)ExportImportContentProcessor.class,
				"(content.processor.type=FragmentEntryLinkEditableValues)");
	}

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	private ServiceTrackerList
		<ExportImportContentProcessor<JSONObject>,
		 ExportImportContentProcessor<JSONObject>>
			_fragmentEntryLinkEditableValuesExportImportProcessors;

	@Reference(target = "(content.processor.type=LayoutReferences)")
	private ExportImportContentProcessor<String>
		_layoutReferencesExportImportContentProcessor;

}