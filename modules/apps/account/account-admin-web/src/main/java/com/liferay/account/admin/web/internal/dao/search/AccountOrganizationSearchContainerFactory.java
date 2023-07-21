/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.retriever.AccountOrganizationRetriever;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = {})
public class AccountOrganizationSearchContainerFactory {

	public static SearchContainer<Organization> create(
			long accountEntryId, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		String emptyResultsMessage =
			"there-are-no-organizations-associated-with-this-account";

		int count =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCount(accountEntryId);

		if (count > 0) {
			emptyResultsMessage = "no-organizations-were-found";
		}

		SearchContainer<Organization> searchContainer = new SearchContainer(
			liferayPortletRequest,
			PortletURLUtil.getCurrent(
				liferayPortletRequest, liferayPortletResponse),
			null, emptyResultsMessage);

		searchContainer.setId("accountOrganizations");

		String orderByCol = ParamUtil.getString(
			liferayPortletRequest, "orderByCol", "name");

		if (orderByCol.equals("id")) {
			orderByCol = "organizationId";
		}

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			liferayPortletRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		if (AccountEntryPermission.contains(
				PermissionCheckerFactoryUtil.create(
					PortalUtil.getUser(liferayPortletRequest)),
				accountEntryId, AccountActionKeys.MANAGE_ORGANIZATIONS)) {

			searchContainer.setRowChecker(
				new EmptyOnClickRowChecker(liferayPortletResponse));
		}

		String keywords = ParamUtil.getString(
			liferayPortletRequest, "keywords", null);

		BaseModelSearchResult<Organization> baseModelSearchResult =
			_accountOrganizationRetriever.searchAccountOrganizations(
				accountEntryId, keywords, searchContainer.getStart(),
				searchContainer.getDelta(), searchContainer.getOrderByCol(),
				Objects.equals(orderByType, "desc"));

		searchContainer.setResults(baseModelSearchResult.getBaseModels());
		searchContainer.setTotal(baseModelSearchResult.getLength());

		return searchContainer;
	}

	@Reference(unbind = "-")
	protected void setAccountEntryOrganizationRelLocalService(
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService) {

		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
	}

	@Reference(unbind = "-")
	protected void setAccountOrganizationRetriever(
		AccountOrganizationRetriever accountOrganizationRetriever) {

		_accountOrganizationRetriever = accountOrganizationRetriever;
	}

	private static AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;
	private static AccountOrganizationRetriever _accountOrganizationRetriever;

}