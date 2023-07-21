/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.util;

import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = {})
public class WikiEngineCreoleComponentProvider {

	public static WikiEngineCreoleComponentProvider
		getWikiEngineCreoleComponentProvider() {

		return _wikiEngineCreoleComponentProvider;
	}

	public WikiGroupServiceConfiguration getWikiGroupServiceConfiguration() {
		return _wikiGroupServiceConfiguration;
	}

	public void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	@Activate
	protected void activate() {
		_wikiEngineCreoleComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiEngineCreoleComponentProvider = null;
	}

	private static WikiEngineCreoleComponentProvider
		_wikiEngineCreoleComponentProvider;

	@Reference
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}