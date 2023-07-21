/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera
 */
@ProviderType
public interface FrontendTokenDefinition {

	public default Collection<FrontendTokenCategory>
		getFrontendTokenCategories() {

		return new ArrayList<>();
	}

	public default Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return new ArrayList<>();
	}

	public default Collection<FrontendToken> getFrontendTokens() {
		return new ArrayList<>();
	}

	public default Collection<FrontendTokenSet> getFrontendTokenSets() {
		return new ArrayList<>();
	}

	public String getJSON(Locale locale);

}