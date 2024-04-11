/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.account.internal.search;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRelModel;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, immediate = true, service = Indexer.class)
public class CommerceAccountIndexer extends BaseIndexer<CommerceAccount> {

	public static final String CLASS_NAME = CommerceAccount.class.getName();

	public static final String FIELD_ACTIVE = "active";

	public static final String FIELD_PARENT_COMMERCE_ACCOUNT_ID =
		"parentCommerceAccountId";

	public static final String FIELD_TAX_ID = "taxId";

	public CommerceAccountIndexer() {
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return _modelResourcePermission.contains(
			permissionChecker, entryClassPK, actionId);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		Boolean active = (Boolean)searchContext.getAttribute(FIELD_ACTIVE);

		if (active != null) {
			contextBooleanFilter.addTerm(
				FIELD_ACTIVE, String.valueOf(active), BooleanClauseOccur.MUST);
		}

		long parentCommerceAccountId = GetterUtil.getLong(
			searchContext.getAttribute(FIELD_PARENT_COMMERCE_ACCOUNT_ID));

		contextBooleanFilter.addRequiredTerm(
			FIELD_PARENT_COMMERCE_ACCOUNT_ID, parentCommerceAccountId);

		int type = GetterUtil.getInteger(
			searchContext.getAttribute(Field.TYPE), -1);

		if (type > -1) {
			contextBooleanFilter.addRequiredTerm(Field.TYPE, type);
		}

		long[] organizationIds = (long[])searchContext.getAttribute(
			"organizationIds");

		if ((organizationIds != null) && (organizationIds.length > 0)) {
			BooleanFilter organizationBooleanFilter = new BooleanFilter();

			for (long organizationId : organizationIds) {
				Filter termFilter = new TermFilter(
					"organizationIds", String.valueOf(organizationId));

				organizationBooleanFilter.add(
					termFilter, BooleanClauseOccur.SHOULD);
			}

			organizationBooleanFilter.add(
				new MissingFilter("organizationIds"),
				BooleanClauseOccur.SHOULD);

			contextBooleanFilter.add(
				organizationBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, false);
	}

	@Override
	protected void doDelete(CommerceAccount commerceAccount) throws Exception {
		deleteDocument(
			commerceAccount.getCompanyId(),
			commerceAccount.getCommerceAccountId());
	}

	@Override
	protected Document doGetDocument(CommerceAccount commerceAccount)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing commerce account " + commerceAccount);
		}

		Document document = getBaseModelDocument(CLASS_NAME, commerceAccount);

		document.addText(Field.NAME, commerceAccount.getName());
		document.addKeyword(FIELD_ACTIVE, commerceAccount.isActive());
		document.addNumber(
			FIELD_PARENT_COMMERCE_ACCOUNT_ID,
			commerceAccount.getParentCommerceAccountId());
		document.addText(FIELD_TAX_ID, commerceAccount.getTaxId());
		document.addNumber(Field.TYPE, commerceAccount.getType());
		document.addKeyword(
			"organizationIds", _getOrganizationIds(commerceAccount));

		if (_log.isDebugEnabled()) {
			_log.debug("Document " + commerceAccount + " indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(
			document, Field.ENTRY_CLASS_PK, Field.NAME);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(CommerceAccount commerceAccount) throws Exception {
		_indexWriterHelper.updateDocument(
			getSearchEngineId(), commerceAccount.getCompanyId(),
			getDocument(commerceAccount), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(_commerceAccountLocalService.getCommerceAccount(classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCommerceAccounts(companyId);
	}

	protected void reindexCommerceAccounts(long companyId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_commerceAccountLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(CommerceAccount commerceAccount) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(commerceAccount));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to index commerce account " +
								commerceAccount.getCommerceAccountId(),
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private long[] _getOrganizationIds(CommerceAccount commerceAccount) {
		return ListUtil.toLongArray(
			_commerceAccountOrganizationRelLocalService.
				getCommerceAccountOrganizationRels(
					commerceAccount.getCommerceAccountId()),
			CommerceAccountOrganizationRelModel::getOrganizationId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountIndexer.class);

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.account.model.CommerceAccount)"
	)
	private ModelResourcePermission<CommerceAccount> _modelResourcePermission;

}