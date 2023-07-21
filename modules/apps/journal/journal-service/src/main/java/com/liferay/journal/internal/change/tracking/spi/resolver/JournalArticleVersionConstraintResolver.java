/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = ConstraintResolver.class)
public class JournalArticleVersionConstraintResolver
	implements ConstraintResolver<JournalArticle> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-article-version";
	}

	@Override
	public Class<JournalArticle> getModelClass() {
		return JournalArticle.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-article-version-was-updated-to-latest";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, "com.liferay.journal.lang");
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "articleId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<JournalArticle> constraintResolverContext)
		throws PortalException {

		JournalArticle ctArticle = constraintResolverContext.getSourceCTModel();

		double latestVersion = constraintResolverContext.getInTarget(
			() -> {
				JournalArticle latestProductionArticle =
					_journalArticleLocalService.getLatestArticle(
						ctArticle.getResourcePrimKey(),
						WorkflowConstants.STATUS_ANY, false);

				return latestProductionArticle.getVersion();
			});

		List<JournalArticle> articles = ListUtil.filter(
			_journalArticleLocalService.getArticles(
				ctArticle.getGroupId(), ctArticle.getArticleId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator()),
			article ->
				article.getCtCollectionId() == ctArticle.getCtCollectionId());

		double currentVersion = MathUtil.format(
			latestVersion + (0.1 * articles.size()), 1, 1);

		CTPersistence ctPersistence =
			_journalArticleLocalService.getCTPersistence();

		for (JournalArticle article : articles) {
			article.setVersion(currentVersion);

			_journalArticleLocalService.updateJournalArticle(article);

			ctPersistence.flush();

			currentVersion = MathUtil.format(currentVersion - 0.1, 1, 1);
		}
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}