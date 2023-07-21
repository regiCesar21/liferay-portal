/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.alloy.mvc.sample.web.internal.portlet;

import com.liferay.alloy.mvc.AlloyPortlet;
import com.liferay.alloy.mvc.sample.web.internal.constants.AlloyMVCSamplePortletKeys;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.display-name=Alloy MVC Sample",
		"javax.portlet.info.keywords=Alloy MVC Sample",
		"javax.portlet.info.short-title=Alloy MVC Sample",
		"javax.portlet.info.title=Alloy MVC Sample",
		"javax.portlet.mime-type=text/html",
		"javax.portlet.name=" + AlloyMVCSamplePortletKeys.ALLOY_MVC_SAMPLE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class AlloyMVCSamplePortlet extends AlloyPortlet {

	@Override
	@Reference(
		target = "(name=alloy-mvc-sample-friendly-url-mapper)", unbind = "-"
	)
	protected void setFriendlyURLMapper(FriendlyURLMapper friendlyURLMapper) {
		this.friendlyURLMapper = friendlyURLMapper;
	}

}