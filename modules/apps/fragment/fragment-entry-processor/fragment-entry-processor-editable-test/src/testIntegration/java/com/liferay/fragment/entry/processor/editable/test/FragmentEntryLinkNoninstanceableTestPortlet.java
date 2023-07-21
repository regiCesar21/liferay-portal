/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.editable.test;

import com.liferay.fragment.entry.processor.editable.test.constants.FragmentEntryLinkPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.fragment.entry.processor.portlet.alias=fragment-entry-link-noninstanceable",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.scopeable=true", "javax.portlet.display-name=Test",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + FragmentEntryLinkPortletKeys.FRAGMENT_ENTRY_LINK_NONINSTANCEABLE_TEST_PORTLET
	},
	service = Portlet.class
)
public class FragmentEntryLinkNoninstanceableTestPortlet extends MVCPortlet {
}