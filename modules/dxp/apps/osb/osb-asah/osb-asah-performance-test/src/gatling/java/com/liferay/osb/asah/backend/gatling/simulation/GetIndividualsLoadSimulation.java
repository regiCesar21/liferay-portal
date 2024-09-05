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
public class GetIndividualsLoadSimulation extends Simulation {

	private final ChainBuilder _getIndividuals = SimulationUtil.get(
		"Get Individuals", "/individuals");
	private final ScenarioBuilder _getIndividualsScenario = CoreDsl.scenario(
		"Get Individuals Load Scenario"
	).exec(
		CoreDsl.repeat(
			SimulationUtil.loadRequestsPerSec()
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
				))
		).protocols(
			SimulationUtil.httpProtocol()
		);
	}

}