/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.impl.CommerceCountryImpl;
import com.liferay.commerce.service.persistence.CommerceCountryFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCountryFinderImpl
	extends CommerceCountryFinderBaseImpl implements CommerceCountryFinder {

	public static final String FIND_BY_COMMERCE_WAREHOUSES =
		CommerceCountryFinder.class.getName() +
			".findByCommerceInventoryWarehouses";

	public static final String FIND_BY_COMMERCE_CHANNEL =
		CommerceCountryFinder.class.getName() + ".findByCommerceChannel";

	@Override
	public List<CommerceCountry> findByCommerceInventoryWarehouses(
		long companyId, boolean all) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_BY_COMMERCE_WAREHOUSES);

			if (all) {
				sql = StringUtil.removeSubstring(sql, _ALL_SQL);
			}
			else {
				sql = StringUtil.replace(sql, _ALL_SQL, _ACTIVE_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("CommerceCountry", CommerceCountryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceCountry>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<CommerceCountry> findByCommerceChannel(
		long commerceChannelId, boolean shippingAllowed, boolean billingAllowed,
		int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_COMMERCE_CHANNEL);

			if (billingAllowed) {
				sql = StringUtil.replace(
					sql, _BILLING_SQL, _BILLING_ALLOWED_SQL);
			}
			else {
				sql = StringUtil.removeSubstring(sql, _BILLING_SQL);
			}

			if (shippingAllowed) {
				sql = StringUtil.replace(
					sql, _SHIPPING_SQL, _SHIPPING_ALLOWED_SQL);
			}
			else {
				sql = StringUtil.removeSubstring(sql, _SHIPPING_SQL);
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("CommerceCountry", CommerceCountryImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(
				ClassNameLocalServiceUtil.getClassNameId(
					CommerceCountry.class));
			queryPos.add(commerceChannelId);

			return (List<CommerceCountry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _ACTIVE_SQL =
		"AND (CIWarehouse.active_ = [$TRUE$])";

	private static final String _ALL_SQL = "[$ALL$]";

	private static final String _BILLING_ALLOWED_SQL =
		"AND (CommerceCountry.billingAllowed = [$TRUE$])";

	private static final String _BILLING_SQL = "[$BILLING$]";

	private static final String _SHIPPING_ALLOWED_SQL =
		"AND (CommerceCountry.shippingAllowed = [$TRUE$])";

	private static final String _SHIPPING_SQL = "[$SHIPPING$]";

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}