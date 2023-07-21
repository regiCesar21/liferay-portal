/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.web.internal.group.type.contributor.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.roles.admin.group.type.contributor.GroupTypeContributor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = {})
public class GroupTypeContributorUtil {

	public static long[] getClassNameIds() {
		Stream<GroupTypeContributor> stream = _groupTypeContributors.stream();

		return ListUtil.toLongArray(
			stream.filter(
				GroupTypeContributor::isEnabled
			).map(
				GroupTypeContributor::getClassName
			).map(
				PortalUtil::getClassNameId
			).collect(
				Collectors.toList()
			),
			Long::valueOf);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addGroupTypeContributor(
		GroupTypeContributor groupTypeContributor) {

		_groupTypeContributors.add(groupTypeContributor);
	}

	protected void removeGroupTypeContributor(
		GroupTypeContributor groupTypeContributor) {

		_groupTypeContributors.remove(groupTypeContributor);
	}

	private static final List<GroupTypeContributor> _groupTypeContributors =
		new ArrayList<>();

}