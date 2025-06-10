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
		Date lastActualExecutionTimeDate, Date lastCompletionTimeDate,
		Date lastScheduledExecutionTimeDate) {

		_lastActualExecutionTimeDate = lastActualExecutionTimeDate;
		_lastCompletionTimeDate = lastCompletionTimeDate;

		_lastScheduledExecutionTimeDaet = lastScheduledExecutionTimeDate;
	}

	@Override
	public Instant lastActualExecution() {
		return _lastActualExecutionTimeDate.toInstant();
	}

	@Override
	public Date lastActualExecutionTime() {
		return _lastActualExecutionTimeDate;
	}

	@Override
	public Instant lastCompletion() {
		return _lastCompletionTimeDate.toInstant();
	}

	@Override
	public Date lastCompletionTime() {
		return _lastCompletionTimeDate;
	}

	@Override
	public Instant lastScheduledExecution() {
		return _lastScheduledExecutionTimeDaet.toInstant();
	}

	@Override
	public Date lastScheduledExecutionTime() {
		return _lastScheduledExecutionTimeDaet;
	}

	private final Date _lastActualExecutionTimeDate;
	private final Date _lastCompletionTimeDate;
	private final Date _lastScheduledExecutionTimeDaet;

}