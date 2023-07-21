/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class FriendlyURLEntryCTDisplayRenderer
	extends BaseCTDisplayRenderer<FriendlyURLEntry> {

	@Override
	public Class<FriendlyURLEntry> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getTitle(Locale locale, FriendlyURLEntry friendlyURLEntry) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return _language.format(
			resourceBundle, "x-for-x",
			new String[] {
				"model.resource." + FriendlyURLEntry.class.getName(),
				friendlyURLEntry.getUrlTitle(locale.getLanguage())
			});
	}

	@Override
	public boolean isHideable(FriendlyURLEntry friendlyURLEntry) {
		return true;
	}

	@Reference
	private Language _language;

}