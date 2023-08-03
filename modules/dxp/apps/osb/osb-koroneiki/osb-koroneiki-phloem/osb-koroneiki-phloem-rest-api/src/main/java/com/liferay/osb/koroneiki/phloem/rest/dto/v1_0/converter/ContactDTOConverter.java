/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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