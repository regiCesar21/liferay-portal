/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.exception.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;

import com.liferay.portal.vulcan.jaxrs.exception.mapper.BaseExceptionMapper;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Response;

/**
 * Converts any {@code JsonMappingException} to a {@code 400} error.
 *
 * @author Alejandro Hernández
 * @review
 */
public class JsonMappingExceptionMapper
	extends BaseExceptionMapper<JsonMappingException> {

	@Override
	protected Problem getProblem(JsonMappingException jsonMappingException) {
		List<JsonMappingException.Reference> references =
			jsonMappingException.getPath();

		Stream<JsonMappingException.Reference> stream = references.stream();

		String path = stream.map(
			JsonMappingException.Reference::getFieldName
		).collect(
			Collectors.joining(".")
		);

		return new Problem(
			Response.Status.BAD_REQUEST, "Unable to map JSON path: " + path);
	}

}