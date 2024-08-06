/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.common.dog.BQGroupDog;
import com.liferay.osb.asah.common.dog.BQIndividualDog;
import com.liferay.osb.asah.common.dog.BQRoleDog;
import com.liferay.osb.asah.common.dog.BQTeamDog;
import com.liferay.osb.asah.common.dog.BQUserGroupDog;
import com.liferay.osb.asah.common.dog.EventPropertyDog;
import com.liferay.osb.asah.common.model.ResultBag;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @author Alejo Ceballos
 */
@Component
@GraphQLTypeWiring(fieldName = "eventAttributeValues", typeName = "QueryType")
public class EventAttributeValueBagDataFetcher
	implements DataFetcher<ResultBag<String>> {

	@Override
	public ResultBag<String> get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		Page<String> valuePage = null;

		String eventAttributeDefinitionId = dataFetchingEnvironment.getArgument(
			"eventAttributeDefinitionId");

		if (Objects.equals(eventAttributeDefinitionId, "jobTitle") ||
			Objects.equals(eventAttributeDefinitionId, "languageId")) {

			valuePage = _bqIndividualDog.getBQIndividualFieldValuePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				null, "demographics/" + eventAttributeDefinitionId + "/value",
				dataFetchingEnvironment.getArgument("start"),
				dataFetchingEnvironment.getArgument("size"));
		}
		else if (Objects.equals(eventAttributeDefinitionId, "group")) {
			valuePage = _bqGroupDog.getBQGroupNamePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				dataFetchingEnvironment.getArgument("keywords"),
				dataFetchingEnvironment.getArgument("size"),
				dataFetchingEnvironment.getArgument("start"));
		}
		else if (Objects.equals(eventAttributeDefinitionId, "role")) {
			valuePage = _bqRoleDog.getBQRoleNamePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				dataFetchingEnvironment.getArgument("keywords"),
				dataFetchingEnvironment.getArgument("size"),
				dataFetchingEnvironment.getArgument("start"));
		}
		else if (Objects.equals(eventAttributeDefinitionId, "team")) {
			valuePage = _bqTeamDog.getBQTeamNamePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				dataFetchingEnvironment.getArgument("keywords"),
				dataFetchingEnvironment.getArgument("size"),
				dataFetchingEnvironment.getArgument("start"));
		}
		else if (Objects.equals(eventAttributeDefinitionId, "userGroup")) {
			valuePage = _bqUserGroupDog.getBQUserGroupNamePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				dataFetchingEnvironment.getArgument("keywords"),
				dataFetchingEnvironment.getArgument("size"),
				dataFetchingEnvironment.getArgument("start"));
		}
		else {
			valuePage = _eventPropertyDog.getBQEventPropertyValuePage(
				Long.valueOf(dataFetchingEnvironment.getArgument("channelId")),
				Long.valueOf(eventAttributeDefinitionId),
				Long.valueOf(
					dataFetchingEnvironment.getArgument("eventDefinitionId")),
				dataFetchingEnvironment.getArgument("keywords"),
				dataFetchingEnvironment.getArgument("size"),
				dataFetchingEnvironment.getArgument("start"));
		}

		return new ResultBag<>(
			valuePage.getContent(), valuePage.getTotalElements());
	}

	@Autowired
	private BQGroupDog _bqGroupDog;

	@Autowired
	private BQIndividualDog _bqIndividualDog;

	@Autowired
	private BQRoleDog _bqRoleDog;

	@Autowired
	private BQTeamDog _bqTeamDog;

	@Autowired
	private BQUserGroupDog _bqUserGroupDog;

	@Autowired
	private EventPropertyDog _eventPropertyDog;

}