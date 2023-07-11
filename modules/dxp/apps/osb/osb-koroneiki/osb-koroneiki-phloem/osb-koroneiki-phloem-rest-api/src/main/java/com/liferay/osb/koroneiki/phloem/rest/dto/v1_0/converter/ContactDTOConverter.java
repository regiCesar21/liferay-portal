/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.converter;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author Amos Fong
 */
public interface ContactDTOConverter
	extends DTOConverter
		<com.liferay.osb.koroneiki.taproot.model.Contact, Contact> {

	public com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact
			toClientDTO(
				DTOConverterContext dtoConverterContext,
				com.liferay.osb.koroneiki.taproot.model.Contact contact)
		throws Exception;

	public Contact toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.osb.koroneiki.taproot.model.Contact contact)
		throws Exception;

}