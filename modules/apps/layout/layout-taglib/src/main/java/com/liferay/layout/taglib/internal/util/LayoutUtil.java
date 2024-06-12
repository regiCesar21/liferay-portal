/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtil {

	public static String getLayoutBreadcrumb(
			Layout layout, HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler((4 * ancestors.size()) + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(httpServletRequest, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(httpServletRequest, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	public static JSONObject getLayoutsJSONObject(
			boolean checkDisplayPage, boolean enableCurrentPage, long groupId,
			HttpServletRequest httpServletRequest, boolean privateLayout,
			long parentLayoutId, String selectedLayoutUuid,
			boolean showHiddenLayouts, boolean showDraftLayouts, int start,
			int end)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = ListUtil.filter(
			LayoutServiceUtil.getLayouts(
				groupId, privateLayout, parentLayoutId),
			layout -> {
				if ((layout.isHidden() && !showHiddenLayouts) ||
					(_isContentLayoutDraft(layout) && !showDraftLayouts)) {

					return false;
				}

				return true;
			});

		boolean hasManageLayoutsPermission = GroupPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), groupId,
			ActionKeys.MANAGE_LAYOUTS);
		boolean mobile = BrowserSnifferUtil.isMobile(httpServletRequest);

		for (Layout layout : ListUtil.subList(layouts, start, end)) {
			List<Layout> childLayouts = ListUtil.filter(
				LayoutServiceUtil.getLayouts(
					groupId, layout.isPrivateLayout(), layout.getLayoutId()),
				curLayout -> {
					if ((curLayout.isHidden() && !showHiddenLayouts) ||
						(_isContentLayoutDraft(curLayout) &&
						 !showDraftLayouts)) {

						return false;
					}

					return true;
				});

			JSONObject jsonObject = JSONUtil.put(
				"children", JSONFactoryUtil.createJSONArray());

			if ((checkDisplayPage && !layout.isContentDisplayPage()) ||
				(!enableCurrentPage &&
				 (layout.getPlid() == _getSelPlid(httpServletRequest)))) {

				jsonObject.put("disabled", true);
			}

			jsonObject.put(
				"expanded", false
			).put(
				"groupId", layout.getGroupId()
			).put(
				"hasChildren", ListUtil.isNotEmpty(childLayouts)
			).put(
				"icon", _getIcon(layout)
			).put(
				"id", layout.getUuid()
			).put(
				"layoutId", layout.getLayoutId()
			).put(
				"name", layout.getName(themeDisplay.getLocale())
			).put(
				"plid", layout.getPlid()
			).put(
				"privateLayout", layout.isPrivateLayout()
			).put(
				"sortable",
				hasManageLayoutsPermission && !mobile &&
				SitesUtil.isLayoutSortable(layout)
			).put(
				"totalChildren", childLayouts.size()
			).put(
				"type", layout.getType()
			).put(
				"url",
				PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false)
			);

			boolean paginated = false;

			if (childLayouts.size() >
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

				paginated = true;
			}

			jsonObject.put("paginated", paginated);

			if (Objects.equals(layout.getUuid(), selectedLayoutUuid)) {
				jsonObject.put("selected", true);
			}

			jsonObject.put(
				"value", getLayoutBreadcrumb(layout, httpServletRequest));

			jsonArray.put(jsonObject);
		}

		return JSONUtil.put(
			"items", jsonArray
		).put(
			"total", layouts.size()
		);
	}

	private static String _getIcon(Layout layout) {
		if (Objects.equals(
				layout.getType(), LayoutConstants.TYPE_LINK_TO_LAYOUT) ||
			Objects.equals(layout.getType(), LayoutConstants.TYPE_URL)) {

			return "link";
		}

		return "page";
	}

	private static long _getSelPlid(HttpServletRequest httpServletRequest) {
		return ParamUtil.getLong(
			httpServletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private static boolean _isContentLayoutDraft(Layout layout) {
		if (!layout.isTypeContent()) {
			return false;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			boolean published = GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"));

			return !published;
		}

		if (layout.isApproved() && !layout.isHidden() && !layout.isSystem()) {
			return false;
		}

		return true;
	}

}