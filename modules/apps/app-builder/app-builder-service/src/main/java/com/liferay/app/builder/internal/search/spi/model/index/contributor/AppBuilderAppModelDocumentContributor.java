/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.internal.search.spi.model.index.contributor;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.app.builder.model.AppBuilderApp",
	service = ModelDocumentContributor.class
)
public class AppBuilderAppModelDocumentContributor
	implements ModelDocumentContributor<AppBuilderApp> {

	@Override
	public void contribute(Document document, AppBuilderApp appBuilderApp) {
		document.addKeyword("active", appBuilderApp.isActive());
		document.addKeyword(
			"ddlRecordSetId", appBuilderApp.getDdlRecordSetId());
		document.addKeyword(
			"ddmStructureId", appBuilderApp.getDdmStructureId());
		document.addKeyword(
			"deploymentTypes",
			Stream.of(
				_appBuilderAppDeploymentLocalService.
					getAppBuilderAppDeployments(
						appBuilderApp.getAppBuilderAppId())
			).flatMap(
				List::stream
			).map(
				AppBuilderAppDeployment::getType
			).toArray(
				String[]::new
			));

		String[] languageIds = getLanguageIds(
			appBuilderApp.getDefaultLanguageId(), appBuilderApp.getName());

		for (String languageId : languageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				appBuilderApp.getName(languageId));
		}

		document.addLocalizedKeyword(
			"localized_name",
			LocalizationUtil.populateLocalizationMap(
				appBuilderApp.getNameMap(),
				appBuilderApp.getDefaultLanguageId(),
				appBuilderApp.getGroupId()),
			true, true);
		document.addKeyword("scope", appBuilderApp.getScope());
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	private AppBuilderAppDeploymentLocalService
		_appBuilderAppDeploymentLocalService;

}