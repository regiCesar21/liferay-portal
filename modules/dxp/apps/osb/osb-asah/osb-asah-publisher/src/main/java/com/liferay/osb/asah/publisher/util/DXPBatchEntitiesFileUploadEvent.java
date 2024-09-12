/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.publisher.util;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public class DXPBatchEntitiesFileUploadEvent {

	public DXPBatchEntitiesFileUploadEvent(
		String contentEncoding, String dataSourceId, InputStream inputStream,
		String resourceName, @Nullable String uploadType) {

		_contentEncoding = contentEncoding;
		_dataSourceId = dataSourceId;
		_inputStream = inputStream;
		_resourceName = resourceName;

		if (StringUtils.isBlank(uploadType)) {
			uploadType = "FULL";
		}

		_uploadType = uploadType;
	}

	public String getContentEncoding() {
		return _contentEncoding;
	}

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public InputStream getInputStream() {
		return _inputStream;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getUploadType() {
		return _uploadType;
	}

	private final String _contentEncoding;
	private final String _dataSourceId;
	private final InputStream _inputStream;
	private final String _resourceName;
	private final String _uploadType;

}