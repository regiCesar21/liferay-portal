/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public abstract class BaseInfoItemIdentifier implements InfoItemIdentifier {

	@Override
	public Optional<String> getVersionOptional() {
		return Optional.ofNullable(_version);
	}

	@Override
	public void setVersion(String version) {
		_version = version;
	}

	private String _version;

}