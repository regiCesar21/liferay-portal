/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.graphql.schema;

import com.liferay.osb.asah.backend.graphql.annotation.GraphQLTypeWiring;
import com.liferay.osb.asah.common.dog.DataControlTaskDog;
import com.liferay.osb.asah.common.spring.http.exception.OSBAsahException;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashSet;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Matthew Kong
 */
@Component
@GraphQLTypeWiring(fieldName = "dataControlTasks", typeName = "MutationType")
public class DataControlTasksMutationDataFetcher
	implements DataFetcher<Boolean> {

	@Override
	public Boolean get(DataFetchingEnvironment dataFetchingEnvironment) {
		String userId = dataFetchingEnvironment.getArgument("userId");

		if (StringUtils.isBlank(userId)) {
			userId = "0";
		}

		return _dataControlTaskDog.addDataControlTasks(
			new HashSet<String>(
				dataFetchingEnvironment.getArgument("emailAddresses")),
			_getPath(dataFetchingEnvironment.getArgument("fileName")),
			dataFetchingEnvironment.getArgument("ownerId"),
			dataFetchingEnvironment.getArgument("types"), Long.valueOf(userId),
			dataFetchingEnvironment.getArgument("userName"));
	}

	private Path _getPath(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}

		Path path = Paths.get(_TMP_PATH_NAME, FilenameUtils.getName(fileName));

		path = path.normalize();

		if (!path.startsWith(_TMP_PATH_NAME)) {
			throw new OSBAsahException(
				HttpStatus.BAD_REQUEST, "Invalid file name: " + fileName);
		}

		return path;
	}

	private static final String _TMP_PATH_NAME = System.getProperty(
		"java.io.tmpdir");

	@Autowired
	private DataControlTaskDog _dataControlTaskDog;

}