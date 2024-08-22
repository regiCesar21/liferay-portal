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
public class PostAnalyticsEventsSpikeSimulation extends Simulation {

	private final ChainBuilder _postAnalyticsEvents = SimulationUtil.post(
		SimulationUtil.generateRandomAnalyticsMessageBody(),
		"Post Analytics Events", "/");
	private final ScenarioBuilder _postAnalyticsEventsScenario =
		CoreDsl.scenario(
			"Post Analytics Events Scenario"
		).exec(
			_postAnalyticsEvents
		);
	private final ScenarioBuilder _postAnalyticsEventsSpikeScenario =
		CoreDsl.scenario(
			"Post Analytics Events Spike Scenario"
		).exec(
			_postAnalyticsEvents
		);

	{
		setUp(
			_postAnalyticsEventsScenario.injectOpen(
				CoreDsl.rampUsers(
					SimulationUtil.loadRampUsers()
				).during(
					SimulationUtil.loadDuring()
				)),
			_postAnalyticsEventsSpikeScenario.injectOpen(
				CoreDsl.nothingFor(SimulationUtil.spikeNothingFor()),
				CoreDsl.rampUsers(
					SimulationUtil.spikeRampUsers()
				).during(
					SimulationUtil.spikeDuring()
				))
		).protocols(
			SimulationUtil.httpProtocol()
		);
	}

}