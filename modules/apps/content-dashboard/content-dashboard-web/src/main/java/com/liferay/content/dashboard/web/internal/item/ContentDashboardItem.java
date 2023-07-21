/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public interface ContentDashboardItem<T> {

	public List<AssetCategory> getAssetCategories();

	public List<AssetCategory> getAssetCategories(long vocabularyId);

	public List<AssetTag> getAssetTags();

	public List<Locale> getAvailableLocales();

	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		HttpServletRequest httpServletRequest,
		ContentDashboardItemAction.Type... types);

	public ContentDashboardItemType getContentDashboardItemType();

	public Date getCreateDate();

	public Map<String, Object> getData(Locale locale);

	public Locale getDefaultLocale();

	public Object getDisplayFieldValue(String fieldName, Locale locale);

	public InfoItemReference getInfoItemReference();

	public Date getModifiedDate();

	public String getScopeName(Locale locale);

	public String getTitle(Locale locale);

	public long getUserId();

	public String getUserName();

	public List<Version> getVersions(Locale locale);

	public boolean isViewable(HttpServletRequest httpServletRequest);

	public static class Version {

		public Version(String label, String style, double version) {
			_label = label;
			_style = style;
			_version = version;
		}

		public String getLabel() {
			return _label;
		}

		public String getStyle() {
			return _style;
		}

		public double getVersion() {
			return _version;
		}

		public JSONObject toJSONObject() {
			return JSONUtil.put(
				"statusLabel", getLabel()
			).put(
				"statusStyle", getStyle()
			).put(
				"version", getVersion()
			);
		}

		private final String _label;
		private final String _style;
		private final double _version;

	}

}