/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.internal.dto.v1_0.converter;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Entitlement;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Phone;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.converter.ContactDTOConverter;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.EntitlementUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ExternalLinkUtil;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.TeamUtil;
import com.liferay.osb.koroneiki.root.identity.management.provider.ContactIdentityProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.Contact",
	service = {ContactDTOConverter.class, DTOConverter.class}
)
public class ContactDTOConverterImpl implements ContactDTOConverter {

	@Override
	public String getContentType() {
		return Contact.class.getSimpleName();
	}

	@Override
	public com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact
			toClientDTO(
				DTOConverterContext dtoConverterContext,
				com.liferay.osb.koroneiki.taproot.model.Contact contact)
		throws Exception {

		return new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
			Contact() {

			{
				dateCreated = contact.getCreateDate();
				dateModified = contact.getModifiedDate();
				emailAddress = contact.getEmailAddress();
				emailAddressVerified = contact.getEmailAddressVerified();
				entitlements = TransformUtil.transformToArray(
					contact.getEntitlements(),
					EntitlementUtil::toClientEntitlement,
					com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
						Entitlement.class);
				externalLinks = TransformUtil.transformToArray(
					contact.getExternalLinks(),
					ExternalLinkUtil::toClientExternalLink,
					com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
						ExternalLink.class);
				firstName = contact.getFirstName();
				key = contact.getContactKey();
				languageId = contact.getLanguageId();
				lastName = contact.getLastName();
				middleName = contact.getMiddleName();
				teams = TransformUtil.transformToArray(
					contact.getTeams(), TeamUtil::toClientTeam,
					com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team.
						class);
				uuid = contact.getUuid();

				setPhones(
					() -> {
						List<String> auxillaryFields = null;

						if (dtoConverterContext != null) {
							auxillaryFields =
								(List<String>)dtoConverterContext.getAttribute(
									"auxillaryFields");
						}

						if ((auxillaryFields != null) &&
							auxillaryFields.contains("contact.phones")) {

							return _getClientPhones(contact.getUuid());
						}

						return null;
					});
			}
		};
	}

	@Override
	public Contact toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.osb.koroneiki.taproot.model.Contact contact)
		throws Exception {

		return new Contact() {
			{
				dateCreated = contact.getCreateDate();
				dateModified = contact.getModifiedDate();
				emailAddress = contact.getEmailAddress();
				emailAddressVerified = contact.getEmailAddressVerified();
				entitlements = TransformUtil.transformToArray(
					contact.getEntitlements(), EntitlementUtil::toEntitlement,
					Entitlement.class);
				externalLinks = TransformUtil.transformToArray(
					contact.getExternalLinks(),
					ExternalLinkUtil::toExternalLink, ExternalLink.class);
				firstName = contact.getFirstName();
				key = contact.getContactKey();
				languageId = contact.getLanguageId();
				lastName = contact.getLastName();
				middleName = contact.getMiddleName();
				teams = TransformUtil.transformToArray(
					contact.getTeams(), TeamUtil::toTeam, Team.class);
				uuid = contact.getUuid();

				setPhones(
					() -> {
						List<String> auxillaryFields = null;

						if (dtoConverterContext != null) {
							auxillaryFields =
								(List<String>)dtoConverterContext.getAttribute(
									"auxillaryFields");
						}

						if ((auxillaryFields != null) &&
							auxillaryFields.contains("contact.phones")) {

							return _getPhones(contact.getUuid());
						}

						return null;
					});
			}
		};
	}

	private com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone[]
			_getClientPhones(String contactUuid)
		throws Exception {

		List<com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone>
			phones = new ArrayList<>();

		JSONObject jsonObject = _contactIdentityProvider.fetchRawContactByUuid(
			contactUuid);

		if (jsonObject == null) {
			return null;
		}

		JSONObject profileJSONObject = jsonObject.getJSONObject("profile");

		if (profileJSONObject == null) {
			return null;
		}

		String mobilePhone = profileJSONObject.getString("mobilePhone");

		if (Validator.isNotNull(mobilePhone)) {
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone phone =
				new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
					Phone();

			phone.setNumber(mobilePhone);
			phone.setType(
				com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone.
					Type.MOBILE);

			phones.add(phone);
		}

		String primaryPhone = profileJSONObject.getString("primaryPhone");

		if (Validator.isNotNull(primaryPhone)) {
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone phone =
				new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
					Phone();

			phone.setNumber(primaryPhone);
			phone.setPrimary(true);
			phone.setType(
				com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone.
					Type.OTHER);

			phones.add(phone);
		}

		return phones.toArray(
			new com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Phone[0]);
	}

	private Phone[] _getPhones(String contactUuid) throws Exception {
		List<Phone> phones = new ArrayList<>();

		JSONObject jsonObject = _contactIdentityProvider.fetchRawContactByUuid(
			contactUuid);

		if (jsonObject == null) {
			return null;
		}

		JSONObject profileJSONObject = jsonObject.getJSONObject("profile");

		if (profileJSONObject == null) {
			return null;
		}

		String mobilePhone = profileJSONObject.getString("mobilePhone");

		if (Validator.isNotNull(mobilePhone)) {
			Phone phone = new Phone();

			phone.setNumber(mobilePhone);
			phone.setType(Phone.Type.MOBILE);

			phones.add(phone);
		}

		String primaryPhone = profileJSONObject.getString("primaryPhone");

		if (Validator.isNotNull(primaryPhone)) {
			Phone phone = new Phone();

			phone.setNumber(primaryPhone);
			phone.setPrimary(true);
			phone.setType(Phone.Type.OTHER);

			phones.add(phone);
		}

		return phones.toArray(new Phone[0]);
	}

	@Reference(target = "(provider=okta)")
	private ContactIdentityProvider _contactIdentityProvider;

}