/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.group.capability;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.GroupCapabilityContributor;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alejandro Tardín
 */
public class GroupCapabilityUtil {

	public static boolean isSupportsPages(Group group) {
		for (GroupCapability groupCapability : _getGroupCapabilities(group)) {
			if (!groupCapability.isSupportsPages()) {
				return false;
			}
		}

		return true;
	}

	public static boolean isSupportsPortlet(Group group, Portlet portlet) {
		for (GroupCapability groupCapability : _getGroupCapabilities(group)) {
			if (!groupCapability.isSupportPortlet(portlet)) {
				return false;
			}
		}

		return true;
	}

	private static List<GroupCapability> _getGroupCapabilities(Group group) {
		List<GroupCapability> groupCapabilities = new ArrayList<>();

		if (group == null) {
			return groupCapabilities;
		}

		for (GroupCapabilityContributor groupCapabilityContributor :
				_groupCapabilityContributors) {

			Optional<GroupCapability> capabilityOptional =
				groupCapabilityContributor.getGroupCapabilityOptional(group);

			capabilityOptional.ifPresent(groupCapabilities::add);
		}

		return groupCapabilities;
	}

	private static final ServiceTrackerList<GroupCapabilityContributor>
		_groupCapabilityContributors = ServiceTrackerCollections.openList(
			GroupCapabilityContributor.class);

}