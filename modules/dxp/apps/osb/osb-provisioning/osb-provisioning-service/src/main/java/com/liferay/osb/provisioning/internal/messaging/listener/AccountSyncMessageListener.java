/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.messaging.listener;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchaseView;
import com.liferay.osb.provisioning.koroneiki.web.service.ProductPurchaseViewWebService;
import com.liferay.osb.provisioning.search.FilterQuery;
import com.liferay.petra.process.LoggingOutputProcessor;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = AccountSyncMessageListener.class)
public class AccountSyncMessageListener extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_accountSyncFilePath = GetterUtil.getString(
			properties.get("accountSyncFilePath"));

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, "0 0 0 * * ?");

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Date now = new Date();

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, -1);

		FilterQuery filterQuery = new FilterQuery();

		FilterQuery endDateFilterQuery = new FilterQuery();

		endDateFilterQuery.addGreaterThanEquals(
			true, "supportLifeEndDate", calendar.getTime());
		endDateFilterQuery.addLessThanEquals(true, "supportLifeEndDate", now);

		filterQuery.addFilterQuery(false, endDateFilterQuery);

		FilterQuery startDateFilterQuery = new FilterQuery();

		startDateFilterQuery.addGreaterThanEquals(
			true, "supportLifeStartDate", calendar.getTime());
		startDateFilterQuery.addLessThanEquals(
			true, "supportLifeStartDate", now);

		filterQuery.addFilterQuery(false, startDateFilterQuery);

		List<ProductPurchaseView> productPurchaseViews =
			_productPurchaseViewWebService.search(
				StringPool.BLANK, filterQuery, 1, 1000, StringPool.BLANK);

		Set<String> accountKeys = new HashSet<>();

		for (ProductPurchaseView productPurchaseView : productPurchaseViews) {
			ProductPurchase productPurchase =
				productPurchaseView.getProductPurchases()[0];

			accountKeys.add(productPurchase.getAccountKey());
		}

		if (!accountKeys.isEmpty()) {
			StringBundler sb = new StringBundler(accountKeys.size() * 4);

			sb.append("accountSyncFilter=entitlements/any(s:contains(s, ");
			sb.append("'Subscription') or s eq 'Partner') and (");

			Iterator<String> iterator = accountKeys.iterator();

			while (iterator.hasNext()) {
				String accountKey = iterator.next();

				sb.append("accountKey eq '");
				sb.append(accountKey);
				sb.append("'");

				if (iterator.hasNext()) {
					sb.append(" or ");
				}
			}

			sb.append(")");

			Future<?> future = ProcessUtil.execute(
				new LoggingOutputProcessor(
					(stdErr, line) -> {
						if (_log.isInfoEnabled()) {
							_log.info(line);
						}
					}),
				new String[] {
					_accountSyncFilePath, "--context_param", sb.toString()
				});

			future.get();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountSyncMessageListener.class);

	private String _accountSyncFilePath;

	@Reference
	private ProductPurchaseViewWebService _productPurchaseViewWebService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}