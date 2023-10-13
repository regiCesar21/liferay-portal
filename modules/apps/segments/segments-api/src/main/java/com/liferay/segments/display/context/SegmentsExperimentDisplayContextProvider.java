/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.display.context;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Ferrari
 */
public interface SegmentsExperimentDisplayContextProvider {

	public SegmentsExperimentDisplayContext getSegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse);

}