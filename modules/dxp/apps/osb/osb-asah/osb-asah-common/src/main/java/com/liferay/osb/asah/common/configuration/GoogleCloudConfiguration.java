/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.configuration;

/**
 * @author Marcellus Tavares
 */
public class GoogleCloudConfiguration {

	public GoogleCloudConfiguration(
		String composerEndpoint, String location, String projectId) {

		_composerEndpoint = composerEndpoint;
		_location = location;
		_projectId = projectId;
	}

	public String getComposerEndpoint() {
		return _composerEndpoint;
	}

	public String getDXPEntitiesBucketName() {
		return _projectId + "-dxp-entities";
	}

	public String getExportBucketName() {
		return _projectId + "-export";
	}

	public String getLocation() {
		return _location;
	}

	public String getProjectId() {
		return _projectId;
	}

	private final String _composerEndpoint;
	private final String _location;
	private final String _projectId;

}