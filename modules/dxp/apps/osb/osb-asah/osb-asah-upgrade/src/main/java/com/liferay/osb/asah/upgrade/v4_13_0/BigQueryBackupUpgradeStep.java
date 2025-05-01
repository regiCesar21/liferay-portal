/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade.v4_13_0;

import com.liferay.osb.asah.common.bigquery.BigQuerySchemaManager;
import com.liferay.osb.asah.common.dog.ProjectDog;
import com.liferay.osb.asah.common.entity.Project;
import com.liferay.osb.asah.common.util.ListUtil;
import com.liferay.osb.asah.upgrade.UpgradeStep;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Leslie Wong
 */
@Component
public class BigQueryBackupUpgradeStep implements UpgradeStep {

	@Override
	public void upgrade(String version) throws Exception {
		Set<String> datasetIds = _bigQuerySchemaManager.getDatasetIds();

		Stream<String> stream = datasetIds.stream();

		Set<String> backupDatasetIds = stream.map(
			datasetId -> {
				if (datasetId.endsWith("_bkp")) {
					return datasetId.replaceAll("_bkp$", "");
				}

				return null;
			}
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toSet()
		);

		List<String> projectIds = ListUtil.map(
			_projectDog.getProjects(), Project::getId);

		Set<String> copy = Set.copyOf(backupDatasetIds);

		projectIds.forEach(backupDatasetIds::remove);

		backupDatasetIds.forEach(
			backupDatasetId -> _bigQuerySchemaManager.deleteBackup(
				backupDatasetId));

		projectIds.removeAll(copy);

		projectIds.forEach(_bigQuerySchemaManager::createBackup);
	}

	@Autowired
	private BigQuerySchemaManager _bigQuerySchemaManager;

	@Autowired
	private ProjectDog _projectDog;

}