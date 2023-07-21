/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.test.layout.display.page;

import com.liferay.analytics.reports.test.MockObject;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<MockObject> {

	public static Builder builder(ClassNameLocalService classNameLocalService) {
		return new Builder(classNameLocalService);
	}

	@Override
	public String getClassName() {
		return MockObject.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<MockObject>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		return new MockLayoutDisplayPageObjectProvider(
			_classNameLocalService, _title);
	}

	@Override
	public LayoutDisplayPageObjectProvider<MockObject>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		return null;
	}

	@Override
	public String getURLSeparator() {
		return "/mock_separator";
	}

	public static class Builder {

		public Builder(ClassNameLocalService classNameLocalService) {
			_classNameLocalService = classNameLocalService;
		}

		public MockLayoutDisplayPageProvider build() {
			return new MockLayoutDisplayPageProvider(
				_classNameLocalService, _title);
		}

		public Builder title(String title) {
			_title = title;

			return this;
		}

		private final ClassNameLocalService _classNameLocalService;
		private String _title;

	}

	private MockLayoutDisplayPageProvider(
		ClassNameLocalService classNameLocalService, String title) {

		_classNameLocalService = classNameLocalService;
		_title = title;
	}

	private final ClassNameLocalService _classNameLocalService;
	private final String _title;

	private static class MockLayoutDisplayPageObjectProvider
		implements LayoutDisplayPageObjectProvider<MockObject> {

		public MockLayoutDisplayPageObjectProvider(
			ClassNameLocalService classNameLocalService, String title) {

			ClassName className = classNameLocalService.getClassName(
				MockObject.class.getName());

			_classNameId = className.getClassNameId();

			_title = title;
		}

		@Override
		public long getClassNameId() {
			return _classNameId;
		}

		@Override
		public long getClassPK() {
			return 0;
		}

		@Override
		public long getClassTypeId() {
			return 0;
		}

		@Override
		public String getDescription(Locale locale) {
			return null;
		}

		@Override
		public MockObject getDisplayObject() {
			return new MockObject();
		}

		@Override
		public long getGroupId() {
			return 0;
		}

		@Override
		public String getKeywords(Locale locale) {
			return null;
		}

		@Override
		public String getTitle(Locale locale) {
			return _title;
		}

		@Override
		public String getURLTitle(Locale locale) {
			return null;
		}

		private final long _classNameId;
		private final String _title;

	}

}