/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.display.context.util;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public abstract class BaseStrutsRequestHelper extends BaseRequestHelper {

	public BaseStrutsRequestHelper(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getMVCRenderCommandName()}
	 */
	@Deprecated
	public String getMVCrenderCommandName() {
		return getMVCRenderCommandName();
	}

	public String getMVCRenderCommandName() {
		if (_mvcRenderCommandName == null) {
			_mvcRenderCommandName = ParamUtil.getString(
				getRequest(), "mvcRenderCommandName");
		}

		return _mvcRenderCommandName;
	}

	private String _mvcRenderCommandName;

}