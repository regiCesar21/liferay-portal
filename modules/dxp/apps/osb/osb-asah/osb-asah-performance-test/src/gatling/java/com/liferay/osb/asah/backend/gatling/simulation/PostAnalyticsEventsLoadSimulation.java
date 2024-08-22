/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.gatling.simulation;

import com.liferay.osb.asah.backend.gatling.simulation.util.SimulationUtil;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

/**
 * @author Ivica Cardic
 */
public class PostAnalyticsEventsLoadSimulation extends Simulation {

	protected ChainBuilder postAnalyticsEvents = SimulationUtil.post(
		SimulationUtil.generateRandomAnalyticsMessageBody(),
		"Post Analytics Events", "/");
	protected ScenarioBuilder postAnalyticsEventsScenario = CoreDsl.scenario(
		"Post Analytics Events Scenario"
	).exec(
		postAnalyticsEvents
	);

	{
		setUp(
			postAnalyticsEventsScenario.injectOpen(
				CoreDsl.rampUsers(
					SimulationUtil.loadRampUsers()
				).during(
					SimulationUtil.loadDuring()
				))
		).protocols(
			SimulationUtil.httpProtocol()
		);
	}

}