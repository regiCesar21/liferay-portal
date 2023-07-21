/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.processor;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.item.InfoItemIdentifier;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public class DefaultFragmentEntryProcessorContext
	implements FragmentEntryProcessorContext {

	public DefaultFragmentEntryProcessorContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String mode, Locale locale) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_mode = mode;
		_locale = locale;
	}

	@Override
	public Optional<Object> getDisplayObjectOptional() {
		return Optional.ofNullable(_displayObject);
	}

	@Override
	public Optional<Map<String, Object>> getFieldValuesOptional() {
		return Optional.ofNullable(_fieldValues);
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	@Override
	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getMode() {
		return _mode;
	}

	@Override
	public long getPreviewClassNameId() {
		return _previewClassNameId;
	}

	@Override
	public long getPreviewClassPK() {
		return _previewClassPK;
	}

	@Override
	public int getPreviewType() {
		return _previewType;
	}

	@Override
	public String getPreviewVersion() {
		return _previewVersion;
	}

	@Override
	public long[] getSegmentsEntryIds() {
		return _segmentsEntryIds;
	}

	@Override
	public long[] getSegmentsExperienceIds() {
		return null;
	}

	public void setDisplayObject(Object object) {
		_displayObject = object;
	}

	public void setFieldValues(Map<String, Object> fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setPreviewClassNameId(long previewClassNameId) {
		_previewClassNameId = previewClassNameId;
	}

	public void setPreviewClassPK(long previewClassPK) {
		_previewClassPK = previewClassPK;
	}

	public void setPreviewType(int previewType) {
		_previewType = previewType;
	}

	public void setPreviewVersion(String previewVersion) {
		_previewVersion = previewVersion;
	}

	public void setSegmentsEntryIds(long[] segmentsEntryIds) {
		_segmentsEntryIds = segmentsEntryIds;
	}

	public void setSegmentsExperienceIds(long[] segmentsExperienceIds) {
	}

	private Object _displayObject;
	private Map<String, Object> _fieldValues;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Locale _locale;
	private final String _mode;
	private long _previewClassNameId;
	private long _previewClassPK;
	private int _previewType = AssetRendererFactory.TYPE_LATEST_APPROVED;
	private String _previewVersion = InfoItemIdentifier.VERSION_LATEST_APPROVED;
	private long[] _segmentsEntryIds = new long[0];

}