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
public class GetIndividualSegmentsSpikeSimulation extends Simulation {

	private final ChainBuilder _getIndividuals = SimulationUtil.get(
		"Get Individual Segments", "/individual-segments");
	private final ScenarioBuilder _getIndividualsScenario = CoreDsl.scenario(
		"Get Individual Segments Load Scenario"
	).exec(
		CoreDsl.repeat(
			SimulationUtil.loadRequestsPerSec()
		).on(
			_getIndividuals
		)
	);
	private final ScenarioBuilder _getIndividualsSpikeScenario =
		CoreDsl.scenario(
			"Get Individual Segments Spike Scenario"
		).exec(
			CoreDsl.repeat(
				SimulationUtil.spikeRequestsPerSec()
			).on(
				_getIndividuals
			)
		);

	{
		setUp(
			_getIndividualsScenario.injectOpen(
				CoreDsl.constantUsersPerSec(
					SimulationUtil.loadConstantUsersPerSec()
				).during(
					SimulationUtil.loadDuring()
				)),
			_getIndividualsSpikeScenario.injectOpen(
				CoreDsl.nothingFor(SimulationUtil.spikeNothingFor()),
				CoreDsl.constantUsersPerSec(
					SimulationUtil.spikeConstantUsersPerSec()
				).during(
					SimulationUtil.spikeDuring()
				))
		).protocols(
			SimulationUtil.httpProtocol()
		);
	}

}