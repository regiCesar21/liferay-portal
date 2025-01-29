/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.dog.ExperimentDog;
import com.liferay.osb.asah.backend.dto.ExperimentDTO;
import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;

import com.liferay.osb.asah.common.entity.Experiment;
import com.liferay.osb.asah.common.spring.http.exception.OSBAsahException;
import graphql.GraphQLContext;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author André Miranda
 */
@Component
@GraphQLTypeWiring(fieldName = "experiment", typeName = "QueryType")
public class ExperimentDataFetcher implements DataFetcher<ExperimentDTO> {

	@Override
	public ExperimentDTO get(DataFetchingEnvironment dataFetchingEnvironment) throws OSBAsahException {
		String experimentId = dataFetchingEnvironment.getArgument(
			"experimentId");
		GraphQLContext graphQLContext =
			dataFetchingEnvironment.getGraphQlContext();

		graphQLContext.put("experimentId", experimentId);
		graphQLContext.put("channelId", channelId);

		Experiment experiment = _experimentDog.fetchExperiment(Long.valueOf(experimentId));
		String channelId = dataFetchingEnvironment.getArgument("channelId");
		if (!Objects.equals(experiment.getChannelId(), Long.valueOf(channelId))) {
			 throw new OSBAsahException(
                    HttpStatus.NOT_FOUND,
                    "no experiment was found");
        }
        return new ExperimentDTO(
			experiment);
	}

	@Autowired
	private ExperimentDog _experimentDog;

}