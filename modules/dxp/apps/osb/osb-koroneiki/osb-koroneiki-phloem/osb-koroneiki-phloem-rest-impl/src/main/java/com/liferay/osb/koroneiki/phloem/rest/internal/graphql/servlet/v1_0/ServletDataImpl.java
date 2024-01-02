/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.internal.graphql.servlet.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.osb.koroneiki.phloem.rest.internal.graphql.query.v1_0.Query;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.AccountResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.AuditEntryResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ContactAccountViewResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ContactResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ContactRoleResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.CountryRegionResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.CountryResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.EntitlementDefinitionResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ExternalLinkResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.NoteResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.OktaUserResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.PostalAddressResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ProductConsumptionResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ProductPurchaseResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ProductPurchaseViewResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.ProductResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.TeamResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0.TeamRoleResourceImpl;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AuditEntryResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactAccountViewResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactRoleResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.CountryRegionResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.CountryResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.EntitlementDefinitionResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ExternalLinkResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.NoteResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.OktaUserResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.PostalAddressResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductConsumptionResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductPurchaseViewResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamRoleResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Amos Fong
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Mutation.setAuditEntryResourceComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects);
		Mutation.setContactResourceComponentServiceObjects(
			_contactResourceComponentServiceObjects);
		Mutation.setContactRoleResourceComponentServiceObjects(
			_contactRoleResourceComponentServiceObjects);
		Mutation.setEntitlementDefinitionResourceComponentServiceObjects(
			_entitlementDefinitionResourceComponentServiceObjects);
		Mutation.setExternalLinkResourceComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects);
		Mutation.setNoteResourceComponentServiceObjects(
			_noteResourceComponentServiceObjects);
		Mutation.setOktaUserResourceComponentServiceObjects(
			_oktaUserResourceComponentServiceObjects);
		Mutation.setPostalAddressResourceComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects);
		Mutation.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Mutation.setProductConsumptionResourceComponentServiceObjects(
			_productConsumptionResourceComponentServiceObjects);
		Mutation.setProductPurchaseResourceComponentServiceObjects(
			_productPurchaseResourceComponentServiceObjects);
		Mutation.setTeamResourceComponentServiceObjects(
			_teamResourceComponentServiceObjects);
		Mutation.setTeamRoleResourceComponentServiceObjects(
			_teamRoleResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAuditEntryResourceComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects);
		Query.setContactResourceComponentServiceObjects(
			_contactResourceComponentServiceObjects);
		Query.setContactAccountViewResourceComponentServiceObjects(
			_contactAccountViewResourceComponentServiceObjects);
		Query.setContactRoleResourceComponentServiceObjects(
			_contactRoleResourceComponentServiceObjects);
		Query.setCountryResourceComponentServiceObjects(
			_countryResourceComponentServiceObjects);
		Query.setCountryRegionResourceComponentServiceObjects(
			_countryRegionResourceComponentServiceObjects);
		Query.setEntitlementDefinitionResourceComponentServiceObjects(
			_entitlementDefinitionResourceComponentServiceObjects);
		Query.setExternalLinkResourceComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects);
		Query.setNoteResourceComponentServiceObjects(
			_noteResourceComponentServiceObjects);
		Query.setPostalAddressResourceComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects);
		Query.setProductResourceComponentServiceObjects(
			_productResourceComponentServiceObjects);
		Query.setProductConsumptionResourceComponentServiceObjects(
			_productConsumptionResourceComponentServiceObjects);
		Query.setProductPurchaseResourceComponentServiceObjects(
			_productPurchaseResourceComponentServiceObjects);
		Query.setProductPurchaseViewResourceComponentServiceObjects(
			_productPurchaseViewResourceComponentServiceObjects);
		Query.setTeamResourceComponentServiceObjects(
			_teamResourceComponentServiceObjects);
		Query.setTeamRoleResourceComponentServiceObjects(
			_teamRoleResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Koroneiki.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/koroneiki-rest-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createAccount",
						new ObjectValuePair<>(
							AccountResourceImpl.class, "postAccount"));
					put(
						"mutation#deleteAccount",
						new ObjectValuePair<>(
							AccountResourceImpl.class, "deleteAccount"));
					put(
						"mutation#updateAccount",
						new ObjectValuePair<>(
							AccountResourceImpl.class, "putAccount"));
					put(
						"mutation#deleteAccountAccountPermission",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountAccountPermission"));
					put(
						"mutation#updateAccountAccountPermission",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"putAccountAccountPermission"));
					put(
						"mutation#deleteAccountAssignedTeamTeamKeyRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountAssignedTeamTeamKeyRole"));
					put(
						"mutation#updateAccountAssignedTeamTeamKeyRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"putAccountAssignedTeamTeamKeyRole"));
					put(
						"mutation#deleteAccountContactByEmailAddresContactEmailAddressRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountContactByEmailAddresContactEmailAddressRole"));
					put(
						"mutation#updateAccountContactByEmailAddresContactEmailAddressRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"putAccountContactByEmailAddresContactEmailAddressRole"));
					put(
						"mutation#deleteAccountContactByUuidContactUuidRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountContactByUuidContactUuidRole"));
					put(
						"mutation#updateAccountContactByUuidContactUuidRole",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"putAccountContactByUuidContactUuidRole"));
					put(
						"mutation#deleteAccountCustomerContactByEmailAddres",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountCustomerContactByEmailAddres"));
					put(
						"mutation#deleteAccountCustomerContactByUuid",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountCustomerContactByUuid"));
					put(
						"mutation#deleteAccountWorkerContactByEmailAddres",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountWorkerContactByEmailAddres"));
					put(
						"mutation#deleteAccountWorkerContactByUuid",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"deleteAccountWorkerContactByUuid"));
					put(
						"mutation#createAccountAccountKeyAuditEntriesPage",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"postAccountAccountKeyAuditEntriesPage"));
					put(
						"mutation#createContactByUuidContactUuidAuditEntriesPage",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"postContactByUuidContactUuidAuditEntriesPage"));
					put(
						"mutation#createContact",
						new ObjectValuePair<>(
							ContactResourceImpl.class, "postContact"));
					put(
						"mutation#deleteContactByEmailAddresEmailAddress",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"deleteContactByEmailAddresEmailAddress"));
					put(
						"mutation#updateContactByEmailAddresEmailAddress",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"putContactByEmailAddresEmailAddress"));
					put(
						"mutation#deleteContactByUuidContactUuid",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"deleteContactByUuidContactUuid"));
					put(
						"mutation#updateContactByUuidContactUuid",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"putContactByUuidContactUuid"));
					put(
						"mutation#deleteContactByUuidContactUuidContactPermission",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"deleteContactByUuidContactUuidContactPermission"));
					put(
						"mutation#updateContactByUuidContactUuidContactPermission",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"putContactByUuidContactUuidContactPermission"));
					put(
						"mutation#createContactRole",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class, "postContactRole"));
					put(
						"mutation#deleteContactRole",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"deleteContactRole"));
					put(
						"mutation#updateContactRole",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class, "putContactRole"));
					put(
						"mutation#deleteContactRoleContactRolePermission",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"deleteContactRoleContactRolePermission"));
					put(
						"mutation#updateContactRoleContactRolePermission",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"putContactRoleContactRolePermission"));
					put(
						"mutation#createAccountEntitlementDefinition",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"postAccountEntitlementDefinition"));
					put(
						"mutation#createContactEntitlementDefinition",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"postContactEntitlementDefinition"));
					put(
						"mutation#deleteEntitlementDefinition",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"deleteEntitlementDefinition"));
					put(
						"mutation#createEntitlementDefinitionSynchronize",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"postEntitlementDefinitionSynchronize"));
					put(
						"mutation#createAccountAccountKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postAccountAccountKeyExternalLink"));
					put(
						"mutation#createContactRoleContactRoleKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postContactRoleContactRoleKeyExternalLink"));
					put(
						"mutation#createContactByUuidContactUuidExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postContactByUuidContactUuidExternalLink"));
					put(
						"mutation#deleteExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"deleteExternalLink"));
					put(
						"mutation#updateExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class, "putExternalLink"));
					put(
						"mutation#createProductConsumptionProductConsumptionKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postProductConsumptionProductConsumptionKeyExternalLink"));
					put(
						"mutation#createProductPurchaseProductPurchaseKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postProductPurchaseProductPurchaseKeyExternalLink"));
					put(
						"mutation#createProductProductKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postProductProductKeyExternalLink"));
					put(
						"mutation#createTeamTeamKeyExternalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"postTeamTeamKeyExternalLink"));
					put(
						"mutation#createAccountAccountKeyNote",
						new ObjectValuePair<>(
							NoteResourceImpl.class,
							"postAccountAccountKeyNote"));
					put(
						"mutation#deleteNote",
						new ObjectValuePair<>(
							NoteResourceImpl.class, "deleteNote"));
					put(
						"mutation#updateNote",
						new ObjectValuePair<>(
							NoteResourceImpl.class, "putNote"));
					put(
						"mutation#createOktaUser",
						new ObjectValuePair<>(
							OktaUserResourceImpl.class, "postOktaUser"));
					put(
						"mutation#createAccountAccountKeyPostalAddress",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"postAccountAccountKeyPostalAddress"));
					put(
						"mutation#deletePostalAddress",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"deletePostalAddress"));
					put(
						"mutation#updatePostalAddress",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"putPostalAddress"));
					put(
						"mutation#createProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "postProduct"));
					put(
						"mutation#deleteProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "deleteProduct"));
					put(
						"mutation#updateProduct",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "putProduct"));
					put(
						"mutation#deleteProductProductPermission",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"deleteProductProductPermission"));
					put(
						"mutation#updateProductProductPermission",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"putProductProductPermission"));
					put(
						"mutation#createAccountAccountKeyProductConsumption",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"postAccountAccountKeyProductConsumption"));
					put(
						"mutation#deleteProductConsumption",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"deleteProductConsumption"));
					put(
						"mutation#updateProductConsumption",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"putProductConsumption"));
					put(
						"mutation#deleteProductConsumptionProductConsumptionPermission",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"deleteProductConsumptionProductConsumptionPermission"));
					put(
						"mutation#updateProductConsumptionProductConsumptionPermission",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"putProductConsumptionProductConsumptionPermission"));
					put(
						"mutation#createAccountAccountKeyProductPurchase",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"postAccountAccountKeyProductPurchase"));
					put(
						"mutation#deleteProductPurchase",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"deleteProductPurchase"));
					put(
						"mutation#updateProductPurchase",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"putProductPurchase"));
					put(
						"mutation#deleteProductPurchaseProductPurchasePermission",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"deleteProductPurchaseProductPurchasePermission"));
					put(
						"mutation#updateProductPurchaseProductPurchasePermission",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"putProductPurchaseProductPurchasePermission"));
					put(
						"mutation#createAccountAccountKeyTeam",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"postAccountAccountKeyTeam"));
					put(
						"mutation#deleteTeam",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "deleteTeam"));
					put(
						"mutation#updateTeam",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "putTeam"));
					put(
						"mutation#deleteTeamContactByEmailAddres",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"deleteTeamContactByEmailAddres"));
					put(
						"mutation#updateTeamContactByEmailAddres",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"putTeamContactByEmailAddres"));
					put(
						"mutation#deleteTeamContactByEmailAddresEmailAddressRole",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"deleteTeamContactByEmailAddresEmailAddressRole"));
					put(
						"mutation#updateTeamContactByEmailAddresEmailAddressRole",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"putTeamContactByEmailAddresEmailAddressRole"));
					put(
						"mutation#deleteTeamContactByUuid",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "deleteTeamContactByUuid"));
					put(
						"mutation#updateTeamContactByUuid",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "putTeamContactByUuid"));
					put(
						"mutation#deleteTeamContactByUuidContactUuidRole",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"deleteTeamContactByUuidContactUuidRole"));
					put(
						"mutation#updateTeamContactByUuidContactUuidRole",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"putTeamContactByUuidContactUuidRole"));
					put(
						"mutation#deleteTeamTeamPermission",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"deleteTeamTeamPermission"));
					put(
						"mutation#updateTeamTeamPermission",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "putTeamTeamPermission"));
					put(
						"mutation#createTeamRole",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class, "postTeamRole"));
					put(
						"mutation#deleteTeamRole",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class, "deleteTeamRole"));
					put(
						"mutation#updateTeamRole",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class, "putTeamRole"));
					put(
						"mutation#deleteTeamRoleTeamRolePermission",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"deleteTeamRoleTeamRolePermission"));
					put(
						"mutation#updateTeamRoleTeamRolePermission",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"putTeamRoleTeamRolePermission"));

					put(
						"query#accounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class, "getAccountsPage"));
					put(
						"query#accountByExternalLinkDomainEntityNameEntity",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getAccountByExternalLinkDomainEntityNameEntityPage"));
					put(
						"query#account",
						new ObjectValuePair<>(
							AccountResourceImpl.class, "getAccount"));
					put(
						"query#accountChildAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getAccountChildAccountsPage"));
					put(
						"query#contactByUuidContactUuidAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getContactByUuidContactUuidAccountsPage"));
					put(
						"query#teamTeamKeyAssignedAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getTeamTeamKeyAssignedAccountsPage"));
					put(
						"query#accountAccountKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getAccountAccountKeyAuditEntriesPage"));
					put(
						"query#auditEntry",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class, "getAuditEntry"));
					put(
						"query#contactRoleContactRoleKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getContactRoleContactRoleKeyAuditEntriesPage"));
					put(
						"query#contactByUuidContactUuidAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getContactByUuidContactUuidAuditEntriesPage"));
					put(
						"query#teamRoleTeamRoleKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getTeamRoleTeamRoleKeyAuditEntriesPage"));
					put(
						"query#teamTeamKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getTeamTeamKeyAuditEntriesPage"));
					put(
						"query#accountAccountKeyContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyContactsPage"));
					put(
						"query#accountAccountKeyCustomerContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyCustomerContactsPage"));
					put(
						"query#accountAccountKeyWorkerContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyWorkerContactsPage"));
					put(
						"query#contacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class, "getContactsPage"));
					put(
						"query#contactByEmailAddresEmailAddress",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getContactByEmailAddresEmailAddress"));
					put(
						"query#contactByUuidContactUuid",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getContactByUuidContactUuid"));
					put(
						"query#teamTeamKeyContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getTeamTeamKeyContactsPage"));
					put(
						"query#contactByUuidContactUuidContactAccountViews",
						new ObjectValuePair<>(
							ContactAccountViewResourceImpl.class,
							"getContactByUuidContactUuidContactAccountViewsPage"));
					put(
						"query#accountAccountKeyContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#accountAccountKeyContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyContactByUuidContactUuidRolesPage"));
					put(
						"query#accountAccountKeyCustomerContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyCustomerContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#accountAccountKeyCustomerContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyCustomerContactByUuidContactUuidRolesPage"));
					put(
						"query#accountAccountKeyWorkerContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyWorkerContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#accountAccountKeyWorkerContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyWorkerContactByUuidContactUuidRolesPage"));
					put(
						"query#contactRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getContactRolesPage"));
					put(
						"query#contactRoleByTypeContactRoleTypeByNameContactRoleName",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getContactRoleByTypeContactRoleTypeByNameContactRoleName"));
					put(
						"query#contactRole",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class, "getContactRole"));
					put(
						"query#contactRolesContactRoleTypeContactRoleName",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getContactRolesContactRoleTypeContactRoleName"));
					put(
						"query#teamTeamKeyContactByEmailAddresEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getTeamTeamKeyContactByEmailAddresEmailAddressRolesPage"));
					put(
						"query#teamTeamKeyContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getTeamTeamKeyContactByUuidContactUuidRolesPage"));
					put(
						"query#countries",
						new ObjectValuePair<>(
							CountryResourceImpl.class, "getCountriesPage"));
					put(
						"query#countryRegions",
						new ObjectValuePair<>(
							CountryRegionResourceImpl.class,
							"getCountryRegionsPage"));
					put(
						"query#accountEntitlementDefinitions",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"getAccountEntitlementDefinitionsPage"));
					put(
						"query#contactEntitlementDefinitions",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"getContactEntitlementDefinitionsPage"));
					put(
						"query#entitlementDefinition",
						new ObjectValuePair<>(
							EntitlementDefinitionResourceImpl.class,
							"getEntitlementDefinition"));
					put(
						"query#accountAccountKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getAccountAccountKeyExternalLinksPage"));
					put(
						"query#contactRoleContactRoleKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getContactRoleContactRoleKeyExternalLinksPage"));
					put(
						"query#contactByUuidContactUuidExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getContactByUuidContactUuidExternalLinksPage"));
					put(
						"query#externalLink",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class, "getExternalLink"));
					put(
						"query#productConsumptionProductConsumptionKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductConsumptionProductConsumptionKeyExternalLinksPage"));
					put(
						"query#productPurchaseProductPurchaseKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductPurchaseProductPurchaseKeyExternalLinksPage"));
					put(
						"query#productProductKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductProductKeyExternalLinksPage"));
					put(
						"query#teamTeamKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getTeamTeamKeyExternalLinksPage"));
					put(
						"query#accountAccountKeyNotes",
						new ObjectValuePair<>(
							NoteResourceImpl.class,
							"getAccountAccountKeyNotesPage"));
					put(
						"query#note",
						new ObjectValuePair<>(
							NoteResourceImpl.class, "getNote"));
					put(
						"query#accountAccountKeyPostalAddresses",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"getAccountAccountKeyPostalAddressesPage"));
					put(
						"query#postalAddress",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"getPostalAddress"));
					put(
						"query#products",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getProductsPage"));
					put(
						"query#productByExternalLinkDomainEntityNameEntity",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByExternalLinkDomainEntityNameEntityPage"));
					put(
						"query#productByNameProductName",
						new ObjectValuePair<>(
							ProductResourceImpl.class,
							"getProductByNameProductName"));
					put(
						"query#product",
						new ObjectValuePair<>(
							ProductResourceImpl.class, "getProduct"));
					put(
						"query#accountAccountKeyProductConsumptions",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getAccountAccountKeyProductConsumptionsPage"));
					put(
						"query#contactByUuidContactUuidProductConsumptions",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getContactByUuidContactUuidProductConsumptionsPage"));
					put(
						"query#productConsumptions",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getProductConsumptionsPage"));
					put(
						"query#productConsumptionByExternalLinkDomainEntityNameEntity",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getProductConsumptionByExternalLinkDomainEntityNameEntityPage"));
					put(
						"query#productConsumption",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getProductConsumption"));
					put(
						"query#accountAccountKeyProductPurchases",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getAccountAccountKeyProductPurchasesPage"));
					put(
						"query#contactByUuidContactUuidProductPurchases",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getContactByUuidContactUuidProductPurchasesPage"));
					put(
						"query#productPurchases",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getProductPurchasesPage"));
					put(
						"query#productPurchaseByExternalLinkDomainEntityNameEntity",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getProductPurchaseByExternalLinkDomainEntityNameEntityPage"));
					put(
						"query#productPurchase",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getProductPurchase"));
					put(
						"query#accountAccountKeyProductProductKeyProductPurchaseView",
						new ObjectValuePair<>(
							ProductPurchaseViewResourceImpl.class,
							"getAccountAccountKeyProductProductKeyProductPurchaseView"));
					put(
						"query#productPurchaseViews",
						new ObjectValuePair<>(
							ProductPurchaseViewResourceImpl.class,
							"getProductPurchaseViewsPage"));
					put(
						"query#accountAccountKeyAssignedTeams",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"getAccountAccountKeyAssignedTeamsPage"));
					put(
						"query#accountAccountKeyTeams",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"getAccountAccountKeyTeamsPage"));
					put(
						"query#teams",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "getTeamsPage"));
					put(
						"query#teamByExternalLinkDomainEntityNameEntity",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"getTeamByExternalLinkDomainEntityNameEntityPage"));
					put(
						"query#team",
						new ObjectValuePair<>(
							TeamResourceImpl.class, "getTeam"));
					put(
						"query#accountAccountKeyAssignedTeamTeamKeyRoles",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"getAccountAccountKeyAssignedTeamTeamKeyRolesPage"));
					put(
						"query#teamRoles",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class, "getTeamRolesPage"));
					put(
						"query#teamRoleByTypeTeamRoleTypeByNameTeamRoleName",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"getTeamRoleByTypeTeamRoleTypeByNameTeamRoleName"));
					put(
						"query#teamRole",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class, "getTeamRole"));
					put(
						"query#teamRolesTeamRoleTypeTeamRoleName",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"getTeamRolesTeamRoleTypeTeamRoleName"));

					put(
						"query#Account.accountKeyCustomerContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyCustomerContactsPage"));
					put(
						"query#Product.productKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductProductKeyExternalLinksPage"));
					put(
						"query#Account.accountKeyProductPurchases",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getAccountAccountKeyProductPurchasesPage"));
					put(
						"query#Account.accountKeyAssignedTeamTeamKeyRoles",
						new ObjectValuePair<>(
							TeamRoleResourceImpl.class,
							"getAccountAccountKeyAssignedTeamTeamKeyRolesPage"));
					put(
						"query#Account.accountKeyAssignedTeams",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"getAccountAccountKeyAssignedTeamsPage"));
					put(
						"query#Account.accountKeyNotes",
						new ObjectValuePair<>(
							NoteResourceImpl.class,
							"getAccountAccountKeyNotesPage"));
					put(
						"query#Account.accountKeyContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#Account.accountKeyPostalAddresses",
						new ObjectValuePair<>(
							PostalAddressResourceImpl.class,
							"getAccountAccountKeyPostalAddressesPage"));
					put(
						"query#Account.accountKeyWorkerContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyWorkerContactsPage"));
					put(
						"query#Account.accountKeyTeams",
						new ObjectValuePair<>(
							TeamResourceImpl.class,
							"getAccountAccountKeyTeamsPage"));
					put(
						"query#Team.teamKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getTeamTeamKeyExternalLinksPage"));
					put(
						"query#Contact.byUuidContactUuidAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getContactByUuidContactUuidAccountsPage"));
					put(
						"query#Account.accountKeyWorkerContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyWorkerContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#Account.accountKeyWorkerContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyWorkerContactByUuidContactUuidRolesPage"));
					put(
						"query#Account.accountKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getAccountAccountKeyExternalLinksPage"));
					put(
						"query#Account.accountKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getAccountAccountKeyAuditEntriesPage"));
					put(
						"query#Account.accountKeyProductConsumptions",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getAccountAccountKeyProductConsumptionsPage"));
					put(
						"query#Team.teamKeyContactByEmailAddresEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getTeamTeamKeyContactByEmailAddresEmailAddressRolesPage"));
					put(
						"query#Team.teamKeyAssignedAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getTeamTeamKeyAssignedAccountsPage"));
					put(
						"query#Account.accountKeyContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getAccountAccountKeyContactsPage"));
					put(
						"query#Account.childAccounts",
						new ObjectValuePair<>(
							AccountResourceImpl.class,
							"getAccountChildAccountsPage"));
					put(
						"query#Contact.byUuidContactUuidContactAccountViews",
						new ObjectValuePair<>(
							ContactAccountViewResourceImpl.class,
							"getContactByUuidContactUuidContactAccountViewsPage"));
					put(
						"query#Account.accountKeyContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyContactByUuidContactUuidRolesPage"));
					put(
						"query#Account.accountKeyProductProductKeyProductPurchaseView",
						new ObjectValuePair<>(
							ProductPurchaseViewResourceImpl.class,
							"getAccountAccountKeyProductProductKeyProductPurchaseView"));
					put(
						"query#TeamRole.teamRoleKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getTeamRoleTeamRoleKeyAuditEntriesPage"));
					put(
						"query#Team.teamKeyContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getTeamTeamKeyContactByUuidContactUuidRolesPage"));
					put(
						"query#ProductConsumption.productConsumptionKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductConsumptionProductConsumptionKeyExternalLinksPage"));
					put(
						"query#Contact.byUuidContactUuidProductConsumptions",
						new ObjectValuePair<>(
							ProductConsumptionResourceImpl.class,
							"getContactByUuidContactUuidProductConsumptionsPage"));
					put(
						"query#Team.teamKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getTeamTeamKeyAuditEntriesPage"));
					put(
						"query#ContactRole.contactRoleKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getContactRoleContactRoleKeyExternalLinksPage"));
					put(
						"query#Contact.byUuidContactUuidExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getContactByUuidContactUuidExternalLinksPage"));
					put(
						"query#Team.teamKeyContacts",
						new ObjectValuePair<>(
							ContactResourceImpl.class,
							"getTeamTeamKeyContactsPage"));
					put(
						"query#ContactRole.contactRoleKeyAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getContactRoleContactRoleKeyAuditEntriesPage"));
					put(
						"query#Contact.byUuidContactUuidAuditEntries",
						new ObjectValuePair<>(
							AuditEntryResourceImpl.class,
							"getContactByUuidContactUuidAuditEntriesPage"));
					put(
						"query#Account.accountKeyCustomerContactByEmailAddresContactEmailAddressRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyCustomerContactByEmailAddresContactEmailAddressRolesPage"));
					put(
						"query#ProductPurchase.productPurchaseKeyExternalLinks",
						new ObjectValuePair<>(
							ExternalLinkResourceImpl.class,
							"getProductPurchaseProductPurchaseKeyExternalLinksPage"));
					put(
						"query#Contact.byUuidContactUuidProductPurchases",
						new ObjectValuePair<>(
							ProductPurchaseResourceImpl.class,
							"getContactByUuidContactUuidProductPurchasesPage"));
					put(
						"query#Account.accountKeyCustomerContactByUuidContactUuidRoles",
						new ObjectValuePair<>(
							ContactRoleResourceImpl.class,
							"getAccountAccountKeyCustomerContactByUuidContactUuidRolesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AuditEntryResource>
		_auditEntryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactResource>
		_contactResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactRoleResource>
		_contactRoleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<EntitlementDefinitionResource>
		_entitlementDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ExternalLinkResource>
		_externalLinkResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<NoteResource>
		_noteResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OktaUserResource>
		_oktaUserResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductConsumptionResource>
		_productConsumptionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductPurchaseResource>
		_productPurchaseResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TeamResource>
		_teamResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TeamRoleResource>
		_teamRoleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ContactAccountViewResource>
		_contactAccountViewResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CountryResource>
		_countryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<CountryRegionResource>
		_countryRegionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ProductPurchaseViewResource>
		_productPurchaseViewResourceComponentServiceObjects;

}