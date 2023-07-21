/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/delete_item"
	},
	service = MVCActionCommand.class
)
public class DeleteItemMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected JSONObject deleteItemJSONObject(
			long companyId, long groupId, String itemId, long plid,
			long segmentsExperienceId)
		throws PortalException {

		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		LayoutStructureUtil.updateLayoutPageTemplateData(
			groupId, segmentsExperienceId, plid,
			layoutStructure -> deletedLayoutStructureItems.addAll(
				layoutStructure.deleteLayoutStructureItem(itemId)));

		List<Long> deletedFragmentEntryLinkIds = new ArrayList<>();

		for (long fragmentEntryLinkId :
				LayoutStructureUtil.getFragmentEntryLinkIds(
					deletedLayoutStructureItems)) {

			FragmentEntryLinkUtil.deleteFragmentEntryLink(
				_contentPageEditorListenerTracker, fragmentEntryLinkId, plid);

			deletedFragmentEntryLinkIds.add(fragmentEntryLinkId);
		}

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				groupId, plid, segmentsExperienceId);

		return JSONUtil.put(
			"deletedFragmentEntryLinkIds", deletedFragmentEntryLinkIds.toArray()
		).put(
			"layoutData", layoutStructure.toJSONObject()
		);
	}

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String itemId = ParamUtil.getString(actionRequest, "itemId");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);

		return deleteItemJSONObject(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), itemId,
			themeDisplay.getPlid(), segmentsExperienceId);
	}

	@Reference
	private ContentPageEditorListenerTracker _contentPageEditorListenerTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

}