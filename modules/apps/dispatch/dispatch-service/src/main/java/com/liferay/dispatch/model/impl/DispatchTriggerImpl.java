/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model.impl;

import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	@Override
	public UnicodeProperties getDispatchTaskSettingsUnicodeProperties() {
		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties = new UnicodeProperties(
				true);

			_dispatchTaskSettingsUnicodeProperties.fastLoad(
				getDispatchTaskSettings());
		}

		return _dispatchTaskSettingsUnicodeProperties;
	}

	@Override
	public DispatchTaskStatus getDispatchTaskStatus() {
		if (_dispatchTaskStatus != null) {
			return _dispatchTaskStatus;
		}

		DispatchLog dispatchLog =
			DispatchLogLocalServiceUtil.fetchLatestDispatchLog(
				getDispatchTriggerId());

		if (dispatchLog == null) {
			return DispatchTaskStatus.NEVER_RAN;
		}

		_dispatchTaskStatus = DispatchTaskStatus.valueOf(
			dispatchLog.getStatus());

		return _dispatchTaskStatus;
	}

	@Override
	public void setDispatchTaskSettings(String dispatchTaskSettings) {
		super.setDispatchTaskSettings(dispatchTaskSettings);

		_dispatchTaskSettingsUnicodeProperties = null;
	}

	@Override
	public void setDispatchTaskSettingsUnicodeProperties(
		UnicodeProperties dispatchTaskSettingsUnicodeProperties) {

		_dispatchTaskSettingsUnicodeProperties =
			dispatchTaskSettingsUnicodeProperties;

		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setDispatchTaskSettings(
			_dispatchTaskSettingsUnicodeProperties.toString());
	}

	private transient UnicodeProperties _dispatchTaskSettingsUnicodeProperties;
	private transient DispatchTaskStatus _dispatchTaskStatus;

}