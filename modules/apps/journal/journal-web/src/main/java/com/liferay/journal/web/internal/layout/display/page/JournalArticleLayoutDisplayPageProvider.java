/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRendererFactory;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, property = "service.ranking:Integer=200",
	service = LayoutDisplayPageProvider.class
)
public class JournalArticleLayoutDisplayPageProvider
	implements LayoutDisplayPageProvider<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public LayoutDisplayPageObjectProvider<JournalArticle>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		JournalArticle article = journalArticleLocalService.fetchLatestArticle(
			infoItemReference.getClassPK());

		if ((article == null) || article.isInTrash()) {
			return null;
		}

		try {
			return new JournalArticleLayoutDisplayPageObjectProvider(
				article, assetHelper, journalArticleAssetRendererFactory);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<JournalArticle>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			JournalArticle article = _getArticle(groupId, urlTitle);

			if ((article == null) || article.isExpired() ||
				article.isInTrash()) {

				return null;
			}

			return new JournalArticleLayoutDisplayPageObjectProvider(
				article, assetHelper, journalArticleAssetRendererFactory);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public String getURLSeparator() {
		return "/w/";
	}

	@Reference
	protected AssetHelper assetHelper;

	@Reference
	protected DepotEntryLocalService depotEntryLocalService;

	@Reference
	protected JournalArticleAssetRendererFactory
		journalArticleAssetRendererFactory;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

	@Reference
	protected Portal portal;

	private JournalArticle _getArticle(long groupId, String urlTitle)
		throws PortalException {

		for (long connectedGroupId :
				ArrayUtil.append(
					portal.getCurrentAndAncestorSiteGroupIds(groupId),
					ListUtil.toLongArray(
						depotEntryLocalService.getGroupConnectedDepotEntries(
							groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
						DepotEntry::getGroupId))) {

			JournalArticle article =
				journalArticleLocalService.fetchArticleByUrlTitle(
					connectedGroupId, urlTitle);

			if (article != null) {
				return article;
			}
		}

		return null;
	}

}