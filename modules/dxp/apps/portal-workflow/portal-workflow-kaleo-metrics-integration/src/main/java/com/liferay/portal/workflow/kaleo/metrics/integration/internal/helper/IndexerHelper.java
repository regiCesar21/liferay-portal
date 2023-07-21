/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = IndexerHelper.class)
public class IndexerHelper {

	public Map<Locale, String> createAssetTitleLocalizationMap(
		String className, long classPK, long groupId) {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(className, classPK);

		if (assetRenderer != null) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				assetRenderer.getClassName(), assetRenderer.getClassPK());

			if (assetEntry != null) {
				return LocalizationUtil.populateLocalizationMap(
					assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId());
			}
		}

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

		if (workflowHandler != null) {
			Map<Locale, String> localizationMap = new HashMap<>();

			for (Locale availableLocale :
					LanguageUtil.getAvailableLocales(groupId)) {

				localizationMap.put(
					availableLocale,
					workflowHandler.getTitle(classPK, availableLocale));
			}

			return localizationMap;
		}

		return Collections.emptyMap();
	}

	public Map<Locale, String> createAssetTypeLocalizationMap(
		String className, long groupId) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(groupId)) {

			localizationMap.put(
				availableLocale,
				ResourceActionsUtil.getModelResource(
					availableLocale, className));
		}

		return localizationMap;
	}

	private AssetRenderer<?> _getAssetRenderer(String className, long classPK) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			try {
				return assetRendererFactory.getAssetRenderer(classPK);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(IndexerHelper.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}