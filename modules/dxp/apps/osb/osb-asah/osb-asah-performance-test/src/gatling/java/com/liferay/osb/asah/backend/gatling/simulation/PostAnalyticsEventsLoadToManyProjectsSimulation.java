/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.gatling.simulation;

import com.liferay.osb.asah.backend.gatling.simulation.util.SimulationUtil;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcos Martins
 */
public class PostAnalyticsEventsLoadToManyProjectsSimulation
	extends Simulation {

	private PopulationBuilder _getPopulationBuilder(
		ChainBuilder chainBuilder, String scenarioName) {

		return CoreDsl.scenario(
			scenarioName
		).exec(
			CoreDsl.repeat(
				SimulationUtil.loadRequestsPerSec()
			).on(
				chainBuilder
			)
		).injectOpen(
			CoreDsl.constantUsersPerSec(
				SimulationUtil.loadConstantUsersPerSec()
			).during(
				SimulationUtil.loadDuring()
			)
		);
	}

	{
		List<PopulationBuilder> populationBuilders = new ArrayList<>();

		String projectId = SimulationUtil.projectId();

		String[] projectIds = projectId.split(",");

		for (String currentProjectId : projectIds) {
			populationBuilders.add(
				_getPopulationBuilder(
					SimulationUtil.post(
						SimulationUtil.generateRandomAnalyticsMessageBody(),
						"Post Analytics Events", "/", currentProjectId),
					"Post Analytics Events Load Scenario - " +
						currentProjectId));
		}

		setUp(
			populationBuilders
		).protocols(
			SimulationUtil.httpProtocol()
		);
	}

}