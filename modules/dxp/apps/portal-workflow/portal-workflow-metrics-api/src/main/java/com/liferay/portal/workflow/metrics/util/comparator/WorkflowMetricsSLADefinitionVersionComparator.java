/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;

/**
 * @author     Rafael Praxedes
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             WorkflowMetricsSLADefinitionVersionIdComparator}
 */
@Deprecated
public class WorkflowMetricsSLADefinitionVersionComparator
	extends OrderByComparator<WorkflowMetricsSLADefinitionVersion> {

	public WorkflowMetricsSLADefinitionVersionComparator() {
		this(false);
	}

	public WorkflowMetricsSLADefinitionVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion1,
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion2) {

		Version version1 = Version.parseVersion(
			workflowMetricsSLADefinitionVersion1.getVersion());
		Version version2 = Version.parseVersion(
			workflowMetricsSLADefinitionVersion2.getVersion());

		int value = version1.compareTo(version2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private static final String _ORDER_BY_ASC =
		"WorkflowMetricsSLADefinitionVersion.version ASC";

	private static final String _ORDER_BY_DESC =
		"WorkflowMetricsSLADefinitionVersion.version DESC";

	private static final String[] _ORDER_BY_FIELDS = {"version"};

	private final boolean _ascending;

}