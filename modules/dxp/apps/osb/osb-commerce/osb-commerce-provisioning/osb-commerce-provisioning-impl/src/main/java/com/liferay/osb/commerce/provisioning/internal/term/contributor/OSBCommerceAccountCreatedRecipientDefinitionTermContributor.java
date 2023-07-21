/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.commerce.provisioning.internal.term.contributor;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.osb.commerce.provisioning.constants.OSBCommerceNotificationConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(
	immediate = true,
	property = {
		"commerce.definition.term.contributor.key=" + OSBCommerceAccountCreatedRecipientDefinitionTermContributor.KEY,
		"commerce.notification.type.key=" + OSBCommerceNotificationConstants.OSB_COMMERCE_PROVISIONING_ACCOUNT_CREATED
	},
	service = CommerceDefinitionTermContributor.class
)
public class OSBCommerceAccountCreatedRecipientDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public static final String KEY =
		CommerceDefinitionTermConstants.RECIPIENT_DEFINITION_TERMS_CONTRIBUTOR;

	@Override
	public Map<String, String> getDefinitionTerms(Locale locale) {
		Map<String, String> map = new HashMap<>();

		List<String> terms = getTerms();

		for (String term : terms) {
			map.put(term, getLabel(term, locale));
		}

		return map;
	}

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		if (!(object instanceof CommerceAccount)) {
			return term;
		}

		if (term.equals("[%ACCOUNT_CREATOR%]")) {
			CommerceAccount commerceAccount = (CommerceAccount)object;

			List<CommerceAccountUserRel> commerceAccountUserRels =
				commerceAccount.getCommerceAccountUserRels();

			CommerceAccountUserRel commerceAccountUserRel =
				commerceAccountUserRels.get(0);

			User user = commerceAccountUserRel.getUser();

			return String.valueOf(user.getUserId());
		}

		return term;
	}

	@Override
	public String getLabel(String term, Locale locale) {
		return LanguageUtil.get(
			locale, _commerceOrderDefinitionTermsMap.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_commerceOrderDefinitionTermsMap.keySet());
	}

	private static final Map<String, String> _commerceOrderDefinitionTermsMap =
		HashMapBuilder.put(
			"[%ACCOUNT_CREATOR%]", "osb-commerce-provisioning-account-creator"
		).build();

}