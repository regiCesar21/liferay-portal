/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.customer.release.tool.web.internal.messaging;

import com.liferay.osb.customer.jira.rest.connector.service.JIRAComponentRESTService;
import com.liferay.osb.customer.release.tool.model.JIRAComponent;
import com.liferay.osb.customer.release.tool.service.JIRAComponentLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jenny Chen
 */
@Component(
	immediate = true, service = SynchronizeJIRAComponentMessageListener.class
)
public class SynchronizeJIRAComponentMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, 1, TimeUnit.DAY);

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
		synchronizeJIRAComponents("COMMERCE");
		synchronizeJIRAComponents("LPD");
	}

	protected void synchronizeJIRAComponents(String jiraProject)
		throws Exception {

		JSONArray jsonArray = _jiraComponentRESTService.getJIRAComponents(
			jiraProject);

		List<JIRAComponent> jiraComponents = ListUtil.copy(
			_jiraComponentLocalService.getJIRAComponents(jiraProject));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long jiraComponentRemoteId = jsonObject.getLong("id");
			String name = jsonObject.getString("name");

			JIRAComponent jiraComponent =
				_jiraComponentLocalService.updateJIRAComponent(
					jiraComponentRemoteId, jiraProject, name);

			jiraComponents.remove(jiraComponent);
		}

		for (JIRAComponent jiraComponent : jiraComponents) {
			_jiraComponentLocalService.deleteJIRAComponent(jiraComponent);
		}
	}

	@Reference
	private JIRAComponentLocalService _jiraComponentLocalService;

	@Reference
	private JIRAComponentRESTService _jiraComponentRESTService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}