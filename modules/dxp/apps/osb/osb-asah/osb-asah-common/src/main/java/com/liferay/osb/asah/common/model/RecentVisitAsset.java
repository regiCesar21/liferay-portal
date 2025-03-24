/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import com.liferay.osb.asah.common.util.BeanUtils;
import com.liferay.osb.asah.common.util.SetUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Leslie Wong
 */
public class RecentVisitAsset extends RecentVisit {

	public RecentVisitAsset() {
	}

	public RecentVisitAsset(Map<String, Object> source) {
		BeanUtils.copyProperties(source, this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RecentVisitAsset)) {
			return false;
		}

		RecentVisitAsset recentVisitAsset = (RecentVisitAsset)obj;

		if (Objects.equals(dataSourceId, recentVisitAsset.dataSourceId) &&
			Objects.equals(firstVisitDate, recentVisitAsset.firstVisitDate) &&
			Objects.equals(groupId, recentVisitAsset.groupId) &&
			Objects.equals(lastVisitDate, recentVisitAsset.lastVisitDate) &&
			Objects.equals(visits, recentVisitAsset.visits) &&
			Objects.equals(_assetId, recentVisitAsset._assetId) &&
			Objects.equals(_assetTitle, recentVisitAsset._assetTitle) &&
			Objects.equals(_contentType, recentVisitAsset._contentType) &&
			Objects.equals(_url, recentVisitAsset._url)) {

			return true;
		}

		return false;
	}

	public String getAssetId() {
		return _assetId;
	}

	public String getAssetTitle() {
		return _assetTitle;
	}

	public ContentType getContentType() {
		return _contentType;
	}

	public String getURL() {
		return _url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			dataSourceId, firstVisitDate, groupId, lastVisitDate, visits,
			_assetId, _assetTitle, _contentType, _url);
	}

	public void setAssetId(String assetId) {
		_assetId = assetId;
	}

	public void setAssetTitle(String assetTitle) {
		_assetTitle = assetTitle;
	}

	public void setContentType(ContentType contentType) {
		_contentType = contentType;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public enum ContentType {

		BLOG("Blog", Collections.singleton("blogViewed"), "blog"),
		DOCUMENT(
			"Document",
			SetUtil.of("documentImpressionMade", "documentPreviewed"),
			"document"),
		FORM("Form", Collections.singleton("formViewed"), "form"),
		WEBCONTENT(
			"WebContent", "webContentResourcePk",
			Collections.singleton("webContentViewed"), "web-content");

		public static ContentType of(String value) {
			return Optional.ofNullable(
				_contentTypes.get(value)
			).orElseThrow(
				IllegalArgumentException::new
			);
		}

		public String getApplicationId() {
			return _applicationId;
		}

		public String getAssetIdFieldName() {
			return _assetIdFieldName;
		}

		public Set<String> getEventIds() {
			return _eventIds;
		}

		public String getValue() {
			return _value;
		}

		private ContentType(
			String applicationId, Set<String> eventIds, String value) {

			_applicationId = applicationId;
			_eventIds = eventIds;
			_value = value;

			_assetIdFieldName = null;
		}

		private ContentType(
			String applicationId, String assetiIdFieldName,
			Set<String> eventIds, String value) {

			_applicationId = applicationId;
			_eventIds = eventIds;
			_value = value;

			_assetIdFieldName = assetiIdFieldName;
		}

		private static final Map<String, ContentType> _contentTypes =
			new HashMap<>();

		static {
			for (ContentType contentType : values()) {
				_contentTypes.put(contentType.getValue(), contentType);
			}
		}

		private final String _applicationId;
		private final String _assetIdFieldName;
		private final Set<String> _eventIds;
		private final String _value;

	}

	private String _assetId;
	private String _assetTitle;
	private ContentType _contentType;
	private String _url;

}