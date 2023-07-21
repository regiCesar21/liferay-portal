/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.messaging;

import com.liferay.portal.background.task.internal.BackgroundTaskImpl;
import com.liferay.portal.background.task.internal.lock.BackgroundTaskLockHelper;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskQueuingMessageListener extends BaseMessageListener {

	public BackgroundTaskQueuingMessageListener(
		BackgroundTaskLocalService backgroundTaskLocalService,
		LockManager lockManager) {

		_backgroundTaskLocalService = backgroundTaskLocalService;

		_backgroundTaskLockHelper = new BackgroundTaskLockHelper(lockManager);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String taskExecutorClassName = (String)message.get(
			"taskExecutorClassName");

		if (Validator.isNull(taskExecutorClassName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Message " + message +
						" is missing the key \"taskExecutorClassName\"");
			}

			return;
		}

		int status = (Integer)message.get("status");

		if ((status == BackgroundTaskConstants.STATUS_CANCELLED) ||
			(status == BackgroundTaskConstants.STATUS_FAILED) ||
			(status == BackgroundTaskConstants.STATUS_SUCCESSFUL)) {

			_executeQueuedBackgroundTasks(taskExecutorClassName);
		}
		else if (status == BackgroundTaskConstants.STATUS_QUEUED) {
			long backgroundTaskId = (Long)message.get(
				BackgroundTaskConstants.BACKGROUND_TASK_ID);

			if (!_backgroundTaskLockHelper.isLockedBackgroundTask(
					new BackgroundTaskImpl(
						_backgroundTaskLocalService.fetchBackgroundTask(
							backgroundTaskId)))) {

				_executeQueuedBackgroundTasks(taskExecutorClassName);
			}
		}
	}

	private void _executeQueuedBackgroundTasks(String taskExecutorClassName) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Acquiring next queued background task for " +
					taskExecutorClassName);
		}

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchFirstBackgroundTask(
				taskExecutorClassName, BackgroundTaskConstants.STATUS_QUEUED);

		if (backgroundTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No additional queued background tasks for " +
						taskExecutorClassName);
			}

			return;
		}

		_backgroundTaskLocalService.resumeBackgroundTask(
			backgroundTask.getBackgroundTaskId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskQueuingMessageListener.class);

	private final BackgroundTaskLocalService _backgroundTaskLocalService;
	private final BackgroundTaskLockHelper _backgroundTaskLockHelper;

}