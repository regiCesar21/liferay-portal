/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeFactory;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeFactoryTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactory.class)
public class JournalArticleContentDashboardItemFactory
	implements ContentDashboardItemFactory<JournalArticle> {

	@Override
	public ContentDashboardItem<JournalArticle> create(long classPK)
		throws PortalException {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle(
				classPK, WorkflowConstants.STATUS_ANY, false);

		AssetEntry assetEntry = null;

		if (!journalArticle.isApproved() && !journalArticle.isExpired() &&
			(journalArticle.getVersion() !=
				JournalArticleConstants.VERSION_DEFAULT)) {

			assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(), journalArticle.getPrimaryKey());
		}
		else {
			assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());
		}

		if (assetEntry == null) {
			throw new NoSuchModelException(
				"Unable to find an asset entry for journal article " +
					journalArticle.getPrimaryKey());
		}

		Optional<ContentDashboardItemTypeFactory>
			contentDashboardItemTypeFactoryOptional =
				_contentDashboardItemTypeFactoryTracker.
					getContentDashboardItemTypeFactoryOptional(
						DDMStructure.class.getName());

		ContentDashboardItemTypeFactory contentDashboardItemTypeFactory =
			contentDashboardItemTypeFactoryOptional.orElseThrow(
				NoSuchModelException::new);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		InfoItemFieldValuesProvider<JournalArticle>
			infoItemFieldValuesProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					JournalArticle.class.getName());

		JournalArticle latestApprovedJournalArticle =
			_journalArticleLocalService.fetchLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);

		return new JournalArticleContentDashboardItem(
			assetEntry.getCategories(), assetEntry.getTags(),
			_contentDashboardItemActionProviderTracker,
			contentDashboardItemTypeFactory.create(
				ddmStructure.getStructureId()),
			_groupLocalService.fetchGroup(journalArticle.getGroupId()),
			infoItemFieldValuesProvider, journalArticle, _language,
			latestApprovedJournalArticle);
	}

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;

	@Reference
	private ContentDashboardItemTypeFactoryTracker
		_contentDashboardItemTypeFactoryTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}