/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.http;

import org.json.JSONArray;

import org.springframework.http.ResponseEntity;

/**
 * @author Shinn Lok
 */
public interface NanitesHttp {

	public ResponseEntity<String> removeSchedule();

	public ResponseEntity<String> rescheduleNanites();

	public ResponseEntity<String> run(JSONArray jsonArray);

	public ResponseEntity<String> scheduleAsahTask(Long asahTaskId);

	public ResponseEntity<String> unscheduleAsahTask(Long asahTaskId);

}