/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.display.internal.request.attributes.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, service = InfoDisplayRequestAttributesContributor.class
)
public class AssetInfoDisplayRequestAttributesContributor
	implements InfoDisplayRequestAttributesContributor {

	@Override
	public void addAttributes(HttpServletRequest httpServletRequest) {
		Object layoutAssetEntry = httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (!(layoutAssetEntry instanceof AssetEntry)) {
			return;
		}

		AssetEntry assetEntry = (AssetEntry)layoutAssetEntry;

		if (assetEntry != null) {
			return;
		}

		long assetEntryId = ParamUtil.getLong(
			httpServletRequest, "assetEntryId");

		assetEntry = _assetEntryLocalService.fetchEntry(assetEntryId);

		if (assetEntry == null) {
			return;
		}

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					assetEntry.getClassName());

		httpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
			layoutDisplayPageProvider);

		try {
			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
				layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
					new InfoItemReference(
						assetEntry.getClassName(), assetEntry.getClassPK()));

			if (layoutDisplayPageObjectProvider != null) {
				httpServletRequest.setAttribute(
					LayoutDisplayPageWebKeys.
						LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
					layoutDisplayPageObjectProvider);
			}

			InfoItemObjectProvider<?> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class, assetEntry.getClassName());

			if (infoItemObjectProvider != null) {
				Object infoItem = infoItemObjectProvider.getInfoItem(
					assetEntry.getClassPK());

				httpServletRequest.setAttribute(
					InfoDisplayWebKeys.INFO_ITEM, infoItem);

				InfoItemDetailsProvider infoItemDetailsProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemDetailsProvider.class,
						assetEntry.getClassName());

				httpServletRequest.setAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS,
					infoItemDetailsProvider.getInfoItemDetails(infoItem));
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get info display object provider", exception);
		}

		httpServletRequest.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetInfoDisplayRequestAttributesContributor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

}