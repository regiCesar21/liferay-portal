/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.gatlin.simulation;

import com.liferay.osb.asah.backend.gatlin.simulation.util.SimulationUtil;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;

/**
 * @author Ivica Cardic
 */
public class GetIndividualsSpikeSimulation extends Simulation {

	protected ChainBuilder getIndividuals = SimulationUtil.get(
		"Get Individuals", "/individuals");
	protected ScenarioBuilder getIndividualsScenario = CoreDsl.scenario(
		"Get Individuals Scenario"
	).exec(
		getIndividuals
	);
	protected ScenarioBuilder getIndividualsSpikeScenario = CoreDsl.scenario(
		"Get Individuals Spike Scenario"
	).exec(
		getIndividuals
	);

	{
		setUp(
			getIndividualsScenario.injectOpen(
				CoreDsl.rampUsers(
					SimulationUtil.loadRampUsers()
				).during(
					SimulationUtil.loadDuring()
				)),
			getIndividualsSpikeScenario.injectOpen(
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