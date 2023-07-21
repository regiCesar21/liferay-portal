/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.portal.kernel.scheduler;

import com.liferay.dispatch.scheduler.SchedulerResponseManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = SchedulerResponseManager.class)
public class SchedulerResponseManagerImpl implements SchedulerResponseManager {

	public Date getNextFireDate(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getNextFireTime(
			jobName, groupName, storageType);
	}

	public List<SchedulerResponse> getSchedulerResponses(int start, int end) {
		List<SchedulerResponse> schedulerResponses = new ArrayList<>();

		try {
			schedulerResponses = _schedulerEngineHelper.getScheduledJobs();
		}
		catch (SchedulerException schedulerException) {
			_log.error("Unable to get scheduler responses", schedulerException);

			return schedulerResponses;
		}

		schedulerResponses = ListUtil.filter(
			schedulerResponses,
			schedulerResponse -> {
				String jobName = schedulerResponse.getJobName();

				return !jobName.startsWith("DISPATCH_JOB_");
			});

		Collections.sort(
			schedulerResponses,
			Comparator.comparing(
				schedulerResponse -> getSimpleJobName(
					schedulerResponse.getJobName())));

		return schedulerResponses.subList(
			start, Math.min(end, schedulerResponses.size()));
	}

	public int getSchedulerResponsesCount() {
		List<SchedulerResponse> schedulerResponses = null;

		try {
			schedulerResponses = _schedulerEngineHelper.getScheduledJobs();
		}
		catch (SchedulerException schedulerException) {
			_log.error("Unable to get scheduler responses", schedulerException);

			return 0;
		}

		return ListUtil.count(
			schedulerResponses,
			schedulerResponse -> {
				String jobName = schedulerResponse.getJobName();

				return !jobName.startsWith("DISPATCH_JOB_");
			});
	}

	public String getSimpleJobName(String jobName) {
		return jobName.substring(jobName.lastIndexOf(StringPool.PERIOD) + 1);
	}

	public TriggerState getTriggerState(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		return _schedulerEngineHelper.getJobState(
			jobName, groupName, storageType);
	}

	@Override
	public void pause(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.pause(jobName, groupName, storageType);
	}

	@Override
	public void resume(
			String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		_schedulerEngineHelper.resume(jobName, groupName, storageType);
	}

	@Override
	public void run(String jobName, String groupName, StorageType storageType)
		throws SchedulerException {

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, groupName, storageType);

		Message message = schedulerResponse.getMessage();

		message.put(
			SchedulerEngine.DESTINATION_NAME,
			schedulerResponse.getDestinationName());
		message.put(SchedulerEngine.GROUP_NAME, groupName);
		message.put(SchedulerEngine.JOB_NAME, jobName);

		_messageBus.sendMessage(
			schedulerResponse.getDestinationName(), message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerResponseManagerImpl.class);

	@Reference
	private MessageBus _messageBus;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}