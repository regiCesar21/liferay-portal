/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.provider;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AssetVocabulariesProvider.class)
public class AssetVocabulariesProvider {

	public List<AssetVocabulary> getAssetVocabularies(
		String[] assetVocabularyNames, long companyId) {

		Group group = _groupLocalService.fetchCompanyGroup(companyId);

		if (group == null) {
			return Collections.emptyList();
		}

		try {
			return Stream.of(
				assetVocabularyNames
			).map(
				assetVocabularyName ->
					_assetVocabularyLocalService.fetchGroupVocabulary(
						group.getGroupId(), assetVocabularyName)
			).filter(
				Objects::nonNull
			).filter(
				assetVocabulary -> assetVocabulary.getCategoriesCount() > 0
			).collect(
				Collectors.toList()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get content dashboard admin configuration",
					exception);
			}
		}

		return Collections.emptyList();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabulariesProvider.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}