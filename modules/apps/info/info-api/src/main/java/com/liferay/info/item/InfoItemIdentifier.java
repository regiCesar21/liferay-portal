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
public interface InfoItemIdentifier {

	public static final String VERSION_LATEST = "VERSION_LATEST";

	public static final String VERSION_LATEST_APPROVED =
		"VERSION_LATEST_APPROVED";

	public Optional<String> getVersionOptional();

	public void setVersion(String version);

}