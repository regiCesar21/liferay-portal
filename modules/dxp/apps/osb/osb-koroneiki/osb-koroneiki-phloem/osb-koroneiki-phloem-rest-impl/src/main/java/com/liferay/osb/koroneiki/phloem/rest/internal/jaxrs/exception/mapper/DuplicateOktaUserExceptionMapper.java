/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.internal.jaxrs.exception.mapper;

import com.liferay.osb.koroneiki.root.exception.DuplicateOktaUserException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any {@code DuplicateOktaUserException} to a {@code 409} error.
 *
 * @author Rebecca Dai
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Koroneiki.REST)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=Liferay.Koroneiki.REST.DuplicateOktaUserExceptionMapper"
	},
	service = ExceptionMapper.class
)
public class DuplicateOktaUserExceptionMapper
	implements ExceptionMapper<DuplicateOktaUserException> {

	@Override
	public Response toResponse(
		DuplicateOktaUserException duplicateOktaUserException) {

		return Response.status(
			409
		).entity(
			"The user already exists"
		).type(
			MediaType.TEXT_PLAIN
		).build();
	}

}