/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDetailDTO {

	public ProjectDetailDTO(
		Boolean accountsSelected, Boolean commerceChannelsSelected,
		Boolean contactsSelected,
		Boolean contentRecommenderMostPopularItemsEnabled,
		Boolean contentRecommenderUserPersonalizationEnabled,
		List<Long> dataSourceIds, String id, Boolean sitesSelected,
		String timeZoneId) {

		_accountsSelected = accountsSelected;
		_commerceChannelsSelected = commerceChannelsSelected;
		_contactsSelected = contactsSelected;
		_contentRecommenderMostPopularItemsEnabled =
			contentRecommenderMostPopularItemsEnabled;
		_contentRecommenderUserPersonalizationEnabled =
			contentRecommenderUserPersonalizationEnabled;
		_dataSourceIds = dataSourceIds;
		_id = id;
		_sitesSelected = sitesSelected;
		_timeZoneId = timeZoneId;
	}

	public Boolean getAccountsSelected() {
		return _accountsSelected;
	}

	public Boolean getCommerceChannelsSelected() {
		return _commerceChannelsSelected;
	}

	public Boolean getContactsSelected() {
		return _contactsSelected;
	}

	public Boolean getContentRecommenderMostPopularItemsEnabled() {
		return _contentRecommenderMostPopularItemsEnabled;
	}

	public Boolean getContentRecommenderUserPersonalizationEnabled() {
		return _contentRecommenderUserPersonalizationEnabled;
	}

	public List<Long> getDataSourceIds() {
		return _dataSourceIds;
	}

	public String getId() {
		return _id;
	}

	public Boolean getSitesSelected() {
		return _sitesSelected;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	private final Boolean _accountsSelected;
	private final Boolean _commerceChannelsSelected;
	private final Boolean _contactsSelected;
	private final Boolean _contentRecommenderMostPopularItemsEnabled;
	private final Boolean _contentRecommenderUserPersonalizationEnabled;
	private final List<Long> _dataSourceIds;
	private final String _id;
	private final Boolean _sitesSelected;
	private final String _timeZoneId;

}