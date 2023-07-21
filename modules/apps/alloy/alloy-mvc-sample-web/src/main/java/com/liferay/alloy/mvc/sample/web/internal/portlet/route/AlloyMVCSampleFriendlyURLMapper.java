/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.alloy.mvc.sample.web.internal.portlet.route;

import com.liferay.alloy.mvc.AlloyFriendlyURLMapper;
import com.liferay.alloy.mvc.sample.web.internal.constants.AlloyMVCSamplePortletKeys;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.friendly-url-routes=com/liferay/alloy/mvc/alloy-friendly-url-routes.xml?controller=todo_lists",
		"javax.portlet.name=" + AlloyMVCSamplePortletKeys.ALLOY_MVC_SAMPLE,
		"name=alloy-mvc-sample-friendly-url-mapper"
	},
	service = FriendlyURLMapper.class
)
public class AlloyMVCSampleFriendlyURLMapper extends AlloyFriendlyURLMapper {

	@Override
	public String getMapping() {
		return _MAPPING;
	}

	private static final String _MAPPING = "todo";

}