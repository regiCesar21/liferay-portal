/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import java.time.Instant;

import java.util.Date;

import org.springframework.scheduling.TriggerContext;

/**
 * @author Marcos Martins
 */
public class SimpleTriggerContext implements TriggerContext {

	public SimpleTriggerContext(
		Date lastActualExecutionTime, Date lastCompletionTime,
		Date lastScheduledExecutionTime) {

		_lastActualExecutionTime = lastActualExecutionTime;
		_lastCompletionTime = lastCompletionTime;
		_lastScheduledExecutionTime = lastScheduledExecutionTime;
	}

	@Override
	public Instant lastActualExecution() {
		return _lastActualExecutionTime.toInstant();
	}

	@Override
	public Date lastActualExecutionTime() {
		return _lastActualExecutionTime;
	}

	@Override
	public Instant lastCompletion() {
		return _lastCompletionTime.toInstant();
	}

	@Override
	public Date lastCompletionTime() {
		return _lastCompletionTime;
	}

	@Override
	public Instant lastScheduledExecution() {
		return _lastScheduledExecutionTime.toInstant();
	}

	@Override
	public Date lastScheduledExecutionTime() {
		return _lastScheduledExecutionTime;
	}

	private final Date _lastActualExecutionTime;
	private final Date _lastCompletionTime;
	private final Date _lastScheduledExecutionTime;

}