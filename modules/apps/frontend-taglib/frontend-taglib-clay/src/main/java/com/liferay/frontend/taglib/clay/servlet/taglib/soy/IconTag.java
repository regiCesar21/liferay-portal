/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;

/**
 * @author     Chema Balsas
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.frontend.taglib.clay.servlet.taglib.IconTag}
 */
@Deprecated
public class IconTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayIcon");
		setModuleBaseName("icon");

		return super.doStartTag();
	}

	public void setMonospaced(Boolean monospaced) {
		putValue("monospaced", monospaced);
	}

	public void setSymbol(String symbol) {
		putValue("symbol", symbol);
	}

}