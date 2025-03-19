/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.upgrade;

import com.liferay.osb.asah.common.dog.ProjectDog;
import com.liferay.osb.asah.common.entity.Project;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
@ConditionalOnProperty(
	matchIfMissing = true, value = "osb.asah.upgrade.process.enabled"
)
public class UpgradeProcessRunner {

	public void run() {
		if (_log.isInfoEnabled()) {
			_log.info("Upgrade started");
		}

		runGlobalUpgrades();
		runProjectUpgrades();

		if (_log.isInfoEnabled()) {
			_log.info("Upgrade finished");
		}
	}

	public void runGlobalUpgrades() {
		try {
			ProjectIdThreadLocal.setGlobalContext(true);

			DatabasePopulatorUtils.execute(
				new ResourceDatabasePopulator(
					new ClassPathResource("v4_12_0/upgrade_global.sql")),
				_dataSource);

			if (_log.isInfoEnabled()) {
				_log.info(
					"ProjectFeature table has been been successfully created " +
						"in the global namespace");
			}
		}
		finally {
			ProjectIdThreadLocal.setGlobalContext(false);
		}
	}

	public void runProjectUpgrades() {
		for (Project project : _projectDog.getProjects()) {
			try {
				ProjectIdThreadLocal.setProjectId(project.getId());

				if (_log.isInfoEnabled()) {
					_log.info(
						"Checking upgrades for project: " + project.getId());
				}

				_run(project);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Finished upgrades for project: " + project.getId());
				}
			}
			catch (Exception exception) {
				_log.error(
					"Failed upgrades for project: " + project.getId(),
					exception);

				if (!_legacyMode) {
					throw new RuntimeException(exception);
				}
			}
		}
	}

	private void _run(List<UpgradeStep> upgradeSteps, String version)
		throws Exception {

		for (UpgradeStep upgradeStep : upgradeSteps) {
			Class<? extends UpgradeStep> upgradeStepClass =
				upgradeStep.getClass();

			if (_log.isInfoEnabled()) {
				_log.info("Starting " + upgradeStepClass.getCanonicalName());
			}

			upgradeStep.upgrade(version);

			if (_log.isInfoEnabled()) {
				_log.info("Finished " + upgradeStepClass.getCanonicalName());
			}
		}
	}

	private void _run(Project project) throws Exception {
		String currentVersion = project.getVersion();

		List<UpgradeStep> upgradeSteps = _upgradeProcess.getUpgradeSteps(
			currentVersion);

		while (!upgradeSteps.isEmpty()) {
			String toVersionString = _upgradeProcess.getToVersionString(
				currentVersion);

			_run(upgradeSteps, toVersionString);

			currentVersion = _updateProjectVersion(
				project.getId(), toVersionString);

			upgradeSteps = _upgradeProcess.getUpgradeSteps(currentVersion);
		}
	}

	private String _updateProjectVersion(String projectId, String version) {
		_projectDog.updateVersion(projectId, version);

		return version;
	}

	private static final Log _log = LogFactory.getLog(
		UpgradeProcessRunner.class);

	@Autowired
	private DataSource _dataSource;

	@Value("${osb.asah.upgrade.legacy.mode:true}")
	private boolean _legacyMode;

	@Autowired
	private ProjectDog _projectDog;

	@Autowired
	private UpgradeProcess _upgradeProcess;

}