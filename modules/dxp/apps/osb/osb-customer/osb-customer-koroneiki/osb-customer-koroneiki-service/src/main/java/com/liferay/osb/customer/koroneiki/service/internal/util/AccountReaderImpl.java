/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.koroneiki.service.internal.util;

import com.liferay.osb.customer.admin.constants.WorkflowConstants;
import com.liferay.osb.customer.koroneiki.constants.EntitlementConstants;
import com.liferay.osb.customer.koroneiki.constants.ProductConstants;
import com.liferay.osb.customer.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.customer.koroneiki.constants.TeamRoleConstants;
import com.liferay.osb.customer.koroneiki.util.AccountReader;
import com.liferay.osb.customer.koroneiki.web.service.ProductPurchaseWebService;
import com.liferay.osb.customer.koroneiki.web.service.TeamRoleWebService;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Entitlement;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.TeamRole;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = AccountReader.class)
public class AccountReaderImpl implements AccountReader {

	public long getCorpProjectId(ExternalLink[] externalLinks) {
		if (externalLinks != null) {
			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();

				if (domain.equals(ExternalLinkDomain.LCS)) {
					String entityName = externalLink.getEntityName();

					if (entityName.equals(
							ExternalLinkEntityName.LCS_CORP_PROJECT)) {

						return Long.valueOf(externalLink.getEntityId());
					}
				}
			}
		}

		return 0;
	}

	public String getCorpProjectUuid(ExternalLink[] externalLinks) {
		if (externalLinks != null) {
			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();

				if (domain.equals(ExternalLinkDomain.WEB)) {
					String entityName = externalLink.getEntityName();

					if (entityName.equals(
							ExternalLinkEntityName.WEB_CORP_PROJECT)) {

						return externalLink.getEntityId();
					}
				}
			}
		}

		return StringPool.BLANK;
	}

	public String getDossieraAccountKey(ExternalLink[] externalLinks) {
		if (externalLinks != null) {
			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();

				if (domain.equals(ExternalLinkDomain.SALESFORCE)) {
					String entityName = externalLink.getEntityName();

					if (entityName.equals(
							ExternalLinkEntityName.SALESFORCE_ACCOUNT)) {

						return externalLink.getEntityId();
					}
				}
			}
		}

		return StringPool.BLANK;
	}

	public Team getFirstLineSupportTeam(Account account) throws Exception {
		Team[] assignedTeams = account.getAssignedTeams();

		if (assignedTeams != null) {
			for (Team team : assignedTeams) {
				List<TeamRole> teamRoles = _teamRoleWebService.getTeamRoles(
					account.getKey(), team.getKey());

				for (TeamRole teamRole : teamRoles) {
					String teamRoleName = teamRole.getName();

					if (teamRoleName.equals(
							TeamRoleConstants.NAME_FIRST_LINE_SUPPORT)) {

						return team;
					}
				}
			}
		}

		return null;
	}

	public List<ProductPurchase> getProductPurchases(String accountKey)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("accountKey eq '");
		sb.append(accountKey);
		sb.append("'");

		return _productPurchaseWebService.search(sb.toString(), 1, 1000);
	}

	public ProductPurchase getSLAProductPurchase(
		List<ProductPurchase> productPurchases) {

		ProductPurchase slaProductPurchase = null;

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isActive(productPurchase)) {
				continue;
			}

			Product product = productPurchase.getProduct();

			if (!ArrayUtil.contains(
					ProductConstants.NAMES_SUBSCRIPTION, product.getName())) {

				continue;
			}

			if (_isHigherSLA(slaProductPurchase, productPurchase)) {
				slaProductPurchase = productPurchase;
			}
		}

		return slaProductPurchase;
	}

	public String getState(String accountKey) throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("accountKey eq '");
		sb.append(accountKey);
		sb.append("'");

		return getSubscriptionState(
			_productPurchaseWebService.search(sb.toString(), 1, 1000));
	}

	public int getStatus(Account account) {
		if (account.getStatus() == Account.Status.ACTIVE) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_CLOSED;
	}

	public String getSubscriptionState(List<ProductPurchase> productPurchases) {
		String state = StringPool.BLANK;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			if (!ArrayUtil.contains(
					ProductConstants.NAMES_PARTNERSHIP, product.getName()) &&
				!ArrayUtil.contains(
					ProductConstants.NAMES_SUBSCRIPTION, product.getName())) {

				continue;
			}

			String curState = _getProductPurchaseState(productPurchase);

			if (_isHigherState(state, curState)) {
				state = curState;
			}
		}

		return state;
	}

	public Date getSupportEndDate(List<ProductPurchase> productPurchases) {
		Date supportEndDate = null;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			String name = product.getName();

			if (name.equals(ProductConstants.NAME_GOLD) ||
				name.equals(ProductConstants.NAME_LIMITED) ||
				name.equals(ProductConstants.NAME_PLATINUM) ||
				name.equals(ProductConstants.NAME_PREMIUM)) {

				if (_getProductPurchaseState(productPurchase) ==
						ProductPurchaseConstants.STATE_UNACTIVATED) {

					continue;
				}

				if (productPurchase.getPerpetual()) {
					return _END_DATE_PERPETUAL;
				}

				Date endDate = productPurchase.getEndDate();

				if ((supportEndDate == null) ||
					supportEndDate.before(endDate)) {

					supportEndDate = endDate;
				}
			}
		}

		return supportEndDate;
	}

	public Date getTicketSupportEndDate(
		List<ProductPurchase> productPurchases) {

		Date ticketSupportEndDate = null;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			String name = product.getName();

			if (name.equals(ProductConstants.NAME_GOLD) ||
				name.equals(ProductConstants.NAME_PLATINUM) ||
				name.equals(ProductConstants.NAME_PREMIUM)) {

				if (_getProductPurchaseState(productPurchase) ==
						ProductPurchaseConstants.STATE_UNACTIVATED) {

					continue;
				}

				if (productPurchase.getPerpetual()) {
					return _END_DATE_PERPETUAL;
				}

				Date endDate = productPurchase.getEndDate();

				if ((ticketSupportEndDate == null) ||
					ticketSupportEndDate.before(endDate)) {

					ticketSupportEndDate = endDate;
				}
			}
		}

		return ticketSupportEndDate;
	}

	public boolean isActiveSupport(Entitlement[] entitlements) {
		for (Entitlement entitlement : entitlements) {
			if (ArrayUtil.contains(
					EntitlementConstants.SUPPORT_ENTITLEMENTS,
					entitlement.getName())) {

				return true;
			}
		}

		return false;
	}

	public boolean isActiveTicketSupport(Entitlement[] entitlements) {
		for (Entitlement entitlement : entitlements) {
			if (ArrayUtil.contains(
					EntitlementConstants.TICKET_SUPPORT_ENTITLEMENTS,
					entitlement.getName())) {

				return true;
			}
		}

		return false;
	}

	public boolean isSyncAccount(List<ProductPurchase> productPurchases)
		throws Exception {

		Date now = new Date();

		for (ProductPurchase productPurchase : productPurchases) {
			if (productPurchase.getStatus() !=
					ProductPurchase.Status.APPROVED) {

				continue;
			}

			Product product = productPurchase.getProduct();

			if (_isSyncProduct(product)) {
				if (productPurchase.getPerpetual()) {
					return true;
				}

				Date endDate = productPurchase.getEndDate();

				if (now.before(endDate)) {
					return true;
				}
			}
		}

		return false;
	}

	private String _getProductPurchaseState(ProductPurchase productPurchase) {
		if (productPurchase.getStatus() == ProductPurchase.Status.CANCELLED) {
			return ProductPurchaseConstants.STATE_CANCELLED;
		}

		Date now = new Date();

		if ((productPurchase.getEndDate() != null) &&
			now.after(productPurchase.getEndDate())) {

			return ProductPurchaseConstants.STATE_EXPIRED;
		}

		if ((productPurchase.getStartDate() != null) &&
			now.before(productPurchase.getStartDate())) {

			return ProductPurchaseConstants.STATE_UNACTIVATED;
		}

		return ProductPurchaseConstants.STATE_ACTIVE;
	}

	private int _getSLARank(Product product) {
		String name = product.getName();

		if (name.equals(ProductConstants.NAME_GOLD)) {
			return 3;
		}
		else if (name.equals(ProductConstants.NAME_LIMITED)) {
			return 1;
		}
		else if (name.equals(ProductConstants.NAME_PLATINUM)) {
			return 4;
		}
		else if (name.equals(ProductConstants.NAME_PREMIUM)) {
			return 5;
		}
		else if (name.equals(ProductConstants.NAME_SILVER)) {
			return 2;
		}

		return 0;
	}

	private int _getStateRank(String state) {
		if (state.equals(ProductPurchaseConstants.STATE_ACTIVE)) {
			return 4;
		}
		else if (state.equals(ProductPurchaseConstants.STATE_CANCELLED)) {
			return 1;
		}
		else if (state.equals(ProductPurchaseConstants.STATE_EXPIRED)) {
			return 2;
		}
		else if (state.equals(ProductPurchaseConstants.STATE_UNACTIVATED)) {
			return 3;
		}

		return 0;
	}

	private boolean _isActive(ProductPurchase productPurchase) {
		if (productPurchase.getStatus() == ProductPurchase.Status.CANCELLED) {
			return false;
		}

		Date now = new Date();

		if ((productPurchase.getEndDate() != null) &&
			now.after(productPurchase.getEndDate())) {

			return false;
		}

		if ((productPurchase.getStartDate() != null) &&
			now.before(productPurchase.getStartDate())) {

			return false;
		}

		return true;
	}

	private boolean _isHigherSLA(
		ProductPurchase curProductPurchase, ProductPurchase productPurchase) {

		if (curProductPurchase == null) {
			return true;
		}

		int curSLARank = _getSLARank(curProductPurchase.getProduct());
		int slaRank = _getSLARank(productPurchase.getProduct());

		if (slaRank > curSLARank) {
			return true;
		}

		if (slaRank < curSLARank) {
			return false;
		}

		if (productPurchase.getPerpetual() &&
			!curProductPurchase.getPerpetual()) {

			return true;
		}

		if (!productPurchase.getPerpetual() &&
			curProductPurchase.getPerpetual()) {

			return false;
		}

		Date curEndDate = curProductPurchase.getEndDate();
		Date endDate = productPurchase.getEndDate();

		if (endDate.after(curEndDate)) {
			return true;
		}

		return false;
	}

	private boolean _isHigherState(String curState, String newState) {
		if (_getStateRank(newState) > _getStateRank(curState)) {
			return true;
		}

		return false;
	}

	private boolean _isSyncProduct(Product product) {
		String name = product.getName();

		if (ArrayUtil.contains(ProductConstants.NAMES_PARTNERSHIP, name) ||
			ArrayUtil.contains(ProductConstants.NAMES_SUBSCRIPTION, name)) {

			return true;
		}

		if (name.startsWith("Commerce") || name.startsWith("DXP") ||
			name.startsWith("LXC") || name.startsWith("Portal")) {

			return true;
		}

		if (name.contains("DXP Cloud") || name.contains("LXC SM")) {
			return true;
		}

		return false;
	}

	private static final Date _END_DATE_PERPETUAL = new Date(4102444800000L);

	@Reference
	private ProductPurchaseWebService _productPurchaseWebService;

	@Reference
	private TeamRoleWebService _teamRoleWebService;

}