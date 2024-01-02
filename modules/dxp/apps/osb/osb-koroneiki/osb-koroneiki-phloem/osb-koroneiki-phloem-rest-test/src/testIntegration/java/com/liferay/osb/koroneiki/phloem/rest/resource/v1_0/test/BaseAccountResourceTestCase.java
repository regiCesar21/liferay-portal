/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.AccountSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public abstract class BaseAccountResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_accountResource.setContextCompany(testCompany);

		AccountResource.Builder builder = AccountResource.builder();

		accountResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Account account1 = randomAccount();

		String json = objectMapper.writeValueAsString(account1);

		Account account2 = AccountSerDes.toDTO(json);

		Assert.assertTrue(equals(account1, account2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Account account = randomAccount();

		String json1 = objectMapper.writeValueAsString(account);
		String json2 = AccountSerDes.toJSON(account);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Account account = randomAccount();

		account.setCode(regex);
		account.setContactEmailAddress(regex);
		account.setDescription(regex);
		account.setFaxNumber(regex);
		account.setKey(regex);
		account.setName(regex);
		account.setParentAccountKey(regex);
		account.setPhoneNumber(regex);
		account.setProfileEmailAddress(regex);
		account.setWebsite(regex);

		String json = AccountSerDes.toJSON(account);

		Assert.assertFalse(json.contains(regex));

		account = AccountSerDes.toDTO(json);

		Assert.assertEquals(regex, account.getCode());
		Assert.assertEquals(regex, account.getContactEmailAddress());
		Assert.assertEquals(regex, account.getDescription());
		Assert.assertEquals(regex, account.getFaxNumber());
		Assert.assertEquals(regex, account.getKey());
		Assert.assertEquals(regex, account.getName());
		Assert.assertEquals(regex, account.getParentAccountKey());
		Assert.assertEquals(regex, account.getPhoneNumber());
		Assert.assertEquals(regex, account.getProfileEmailAddress());
		Assert.assertEquals(regex, account.getWebsite());
	}

	@Test
	public void testGetAccountsPage() throws Exception {
		Page<Account> page = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		page = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(page, testGetAccountsPage_getExpectedActions());
	}

	protected Map<String, Map<String, String>>
			testGetAccountsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();

		account1 = testGetAccountsPage_addAccount(account1);

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, "between", account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithFilterDoubleEquals() throws Exception {
		testGetAccountsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAccountsPageWithFilterStringContains() throws Exception {
		testGetAccountsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountsPageWithFilterStringEquals() throws Exception {
		testGetAccountsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAccountsPageWithFilter("startswith", EntityField.Type.STRING);
	}

	protected void testGetAccountsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, operator, account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithPagination() throws Exception {
		Page<Account> accountPage = accountResource.getAccountsPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(accountPage.getTotalCount());

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		Account account3 = testGetAccountsPage_addAccount(randomAccount());

		Page<Account> page1 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 = accountResource.getAccountsPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, (int)totalCount + 3), null);

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	@Test
	public void testGetAccountsPageWithSortDateTime() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(
					account1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountsPageWithSortDouble() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(account2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountsPageWithSortInteger() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, account1, account2) -> {
				BeanTestUtil.setProperty(account1, entityField.getName(), 0);
				BeanTestUtil.setProperty(account2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountsPageWithSortString() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, account1, account2) -> {
				Class<?> clazz = account1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						account1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						account2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Account, Account, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();
		Account account2 = randomAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, account1, account2);
		}

		account1 = testGetAccountsPage_addAccount(account1);

		account2 = testGetAccountsPage_addAccount(account2);

		Page<Account> page = accountResource.getAccountsPage(
			null, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<Account> ascPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, (int)page.getTotalCount() + 1),
				entityField.getName() + ":asc");

			assertContains(account1, (List<Account>)ascPage.getItems());
			assertContains(account2, (List<Account>)ascPage.getItems());

			Page<Account> descPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, (int)page.getTotalCount() + 1),
				entityField.getName() + ":desc");

			assertContains(account2, (List<Account>)descPage.getItems());
			assertContains(account1, (List<Account>)descPage.getItems());
		}
	}

	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountsPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPostAccount() throws Exception {
		Account randomAccount = randomAccount();

		Account postAccount = testPostAccount_addAccount(randomAccount);

		assertEquals(randomAccount, postAccount);
		assertValid(postAccount);
	}

	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalLinkDomainEntityNameEntityPage()
		throws Exception {

		String domain =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getDomain();
		String irrelevantDomain =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantDomain();
		String entityName =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityName();
		String irrelevantEntityName =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantEntityName();
		String entityId =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityId();
		String irrelevantEntityId =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantEntityId();

		Page<Account> page =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if ((irrelevantDomain != null) && (irrelevantEntityName != null) &&
			(irrelevantEntityId != null)) {

			Account irrelevantAccount =
				testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
					irrelevantDomain, irrelevantEntityName, irrelevantEntityId,
					randomIrrelevantAccount());

			page =
				accountResource.
					getAccountByExternalLinkDomainEntityNameEntityPage(
						irrelevantDomain, irrelevantEntityName,
						irrelevantEntityId,
						Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(irrelevantAccount, (List<Account>)page.getItems());
			assertValid(
				page,
				testGetAccountByExternalLinkDomainEntityNameEntityPage_getExpectedActions(
					irrelevantDomain, irrelevantEntityName,
					irrelevantEntityId));
		}

		Account account1 =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account2 =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				domain, entityName, entityId, randomAccount());

		page =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(
			page,
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getExpectedActions(
				domain, entityName, entityId));
	}

	protected Map<String, Map<String, String>>
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getExpectedActions(
				String domain, String entityName, String entityId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountByExternalLinkDomainEntityNameEntityPageWithPagination()
		throws Exception {

		String domain =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getDomain();
		String entityName =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityName();
		String entityId =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityId();

		Page<Account> accountPage =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId, null);

		int totalCount = GetterUtil.getInteger(accountPage.getTotalCount());

		Account account1 =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account2 =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account3 =
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				domain, entityName, entityId, randomAccount());

		Page<Account> page1 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId, Pagination.of(1, totalCount + 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntityPage(
				domain, entityName, entityId,
				Pagination.of(1, (int)totalCount + 3));

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	protected Account
			testGetAccountByExternalLinkDomainEntityNameEntityPage_addAccount(
				String domain, String entityName, String entityId,
				Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getDomain()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantDomain()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityName()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantEntityName()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getEntityId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntityPage_getIrrelevantEntityId()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAccountNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountAccountPermission() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPutAccountAccountPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account = testPutAccountAccountPermission_addAccount();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			RoleConstants.TYPE_REGULAR);

		assertHttpResponseStatusCode(
			200,
			accountResource.putAccountAccountPermissionHttpResponse(
				null, null, null, null));

		assertHttpResponseStatusCode(
			404,
			accountResource.putAccountAccountPermissionHttpResponse(
				null, null, null, null));
	}

	protected Account testPutAccountAccountPermission_addAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountAssignedTeamTeamKeyRole() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPutAccountAssignedTeamTeamKeyRole() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetAccountChildAccountsPage() throws Exception {
		String accountKey = testGetAccountChildAccountsPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountChildAccountsPage_getIrrelevantAccountKey();

		Page<Account> page = accountResource.getAccountChildAccountsPage(
			accountKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantAccountKey != null) {
			Account irrelevantAccount =
				testGetAccountChildAccountsPage_addAccount(
					irrelevantAccountKey, randomIrrelevantAccount());

			page = accountResource.getAccountChildAccountsPage(
				irrelevantAccountKey, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(irrelevantAccount, (List<Account>)page.getItems());
			assertValid(
				page,
				testGetAccountChildAccountsPage_getExpectedActions(
					irrelevantAccountKey));
		}

		Account account1 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account2 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		page = accountResource.getAccountChildAccountsPage(
			accountKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(
			page,
			testGetAccountChildAccountsPage_getExpectedActions(accountKey));
	}

	protected Map<String, Map<String, String>>
			testGetAccountChildAccountsPage_getExpectedActions(
				String accountKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountChildAccountsPageWithPagination()
		throws Exception {

		String accountKey = testGetAccountChildAccountsPage_getAccountKey();

		Page<Account> accountPage = accountResource.getAccountChildAccountsPage(
			accountKey, null);

		int totalCount = GetterUtil.getInteger(accountPage.getTotalCount());

		Account account1 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account2 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account3 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Page<Account> page1 = accountResource.getAccountChildAccountsPage(
			accountKey, Pagination.of(1, totalCount + 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 = accountResource.getAccountChildAccountsPage(
			accountKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getAccountChildAccountsPage(
			accountKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	protected Account testGetAccountChildAccountsPage_addAccount(
			String accountKey, Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountChildAccountsPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountChildAccountsPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteAccountContactByEmailAddresContactEmailAddressRole()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPutAccountContactByEmailAddresContactEmailAddressRole()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountContactByUuidContactUuidRole()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPutAccountContactByUuidContactUuidRole() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountCustomerContactByEmailAddres()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountCustomerContactByUuid() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountWorkerContactByEmailAddres() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountWorkerContactByUuid() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetContactByUuidContactUuidAccountsPage() throws Exception {
		String contactUuid =
			testGetContactByUuidContactUuidAccountsPage_getContactUuid();
		String irrelevantContactUuid =
			testGetContactByUuidContactUuidAccountsPage_getIrrelevantContactUuid();

		Page<Account> page =
			accountResource.getContactByUuidContactUuidAccountsPage(
				contactUuid, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantContactUuid != null) {
			Account irrelevantAccount =
				testGetContactByUuidContactUuidAccountsPage_addAccount(
					irrelevantContactUuid, randomIrrelevantAccount());

			page = accountResource.getContactByUuidContactUuidAccountsPage(
				irrelevantContactUuid, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(irrelevantAccount, (List<Account>)page.getItems());
			assertValid(
				page,
				testGetContactByUuidContactUuidAccountsPage_getExpectedActions(
					irrelevantContactUuid));
		}

		Account account1 =
			testGetContactByUuidContactUuidAccountsPage_addAccount(
				contactUuid, randomAccount());

		Account account2 =
			testGetContactByUuidContactUuidAccountsPage_addAccount(
				contactUuid, randomAccount());

		page = accountResource.getContactByUuidContactUuidAccountsPage(
			contactUuid, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(
			page,
			testGetContactByUuidContactUuidAccountsPage_getExpectedActions(
				contactUuid));
	}

	protected Map<String, Map<String, String>>
			testGetContactByUuidContactUuidAccountsPage_getExpectedActions(
				String contactUuid)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetContactByUuidContactUuidAccountsPageWithPagination()
		throws Exception {

		String contactUuid =
			testGetContactByUuidContactUuidAccountsPage_getContactUuid();

		Page<Account> accountPage =
			accountResource.getContactByUuidContactUuidAccountsPage(
				contactUuid, null);

		int totalCount = GetterUtil.getInteger(accountPage.getTotalCount());

		Account account1 =
			testGetContactByUuidContactUuidAccountsPage_addAccount(
				contactUuid, randomAccount());

		Account account2 =
			testGetContactByUuidContactUuidAccountsPage_addAccount(
				contactUuid, randomAccount());

		Account account3 =
			testGetContactByUuidContactUuidAccountsPage_addAccount(
				contactUuid, randomAccount());

		Page<Account> page1 =
			accountResource.getContactByUuidContactUuidAccountsPage(
				contactUuid, Pagination.of(1, totalCount + 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 =
			accountResource.getContactByUuidContactUuidAccountsPage(
				contactUuid, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 =
			accountResource.getContactByUuidContactUuidAccountsPage(
				contactUuid, Pagination.of(1, (int)totalCount + 3));

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	protected Account testGetContactByUuidContactUuidAccountsPage_addAccount(
			String contactUuid, Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactByUuidContactUuidAccountsPage_getContactUuid()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactByUuidContactUuidAccountsPage_getIrrelevantContactUuid()
		throws Exception {

		return null;
	}

	@Test
	public void testGetTeamTeamKeyAssignedAccountsPage() throws Exception {
		String teamKey = testGetTeamTeamKeyAssignedAccountsPage_getTeamKey();
		String irrelevantTeamKey =
			testGetTeamTeamKeyAssignedAccountsPage_getIrrelevantTeamKey();

		Page<Account> page = accountResource.getTeamTeamKeyAssignedAccountsPage(
			teamKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantTeamKey != null) {
			Account irrelevantAccount =
				testGetTeamTeamKeyAssignedAccountsPage_addAccount(
					irrelevantTeamKey, randomIrrelevantAccount());

			page = accountResource.getTeamTeamKeyAssignedAccountsPage(
				irrelevantTeamKey, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(irrelevantAccount, (List<Account>)page.getItems());
			assertValid(
				page,
				testGetTeamTeamKeyAssignedAccountsPage_getExpectedActions(
					irrelevantTeamKey));
		}

		Account account1 = testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			teamKey, randomAccount());

		Account account2 = testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			teamKey, randomAccount());

		page = accountResource.getTeamTeamKeyAssignedAccountsPage(
			teamKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(account1, (List<Account>)page.getItems());
		assertContains(account2, (List<Account>)page.getItems());
		assertValid(
			page,
			testGetTeamTeamKeyAssignedAccountsPage_getExpectedActions(teamKey));
	}

	protected Map<String, Map<String, String>>
			testGetTeamTeamKeyAssignedAccountsPage_getExpectedActions(
				String teamKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetTeamTeamKeyAssignedAccountsPageWithPagination()
		throws Exception {

		String teamKey = testGetTeamTeamKeyAssignedAccountsPage_getTeamKey();

		Page<Account> accountPage =
			accountResource.getTeamTeamKeyAssignedAccountsPage(teamKey, null);

		int totalCount = GetterUtil.getInteger(accountPage.getTotalCount());

		Account account1 = testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			teamKey, randomAccount());

		Account account2 = testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			teamKey, randomAccount());

		Account account3 = testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			teamKey, randomAccount());

		Page<Account> page1 =
			accountResource.getTeamTeamKeyAssignedAccountsPage(
				teamKey, Pagination.of(1, totalCount + 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(
			accounts1.toString(), totalCount + 2, accounts1.size());

		Page<Account> page2 =
			accountResource.getTeamTeamKeyAssignedAccountsPage(
				teamKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 =
			accountResource.getTeamTeamKeyAssignedAccountsPage(
				teamKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(account1, (List<Account>)page3.getItems());
		assertContains(account2, (List<Account>)page3.getItems());
		assertContains(account3, (List<Account>)page3.getItems());
	}

	protected Account testGetTeamTeamKeyAssignedAccountsPage_addAccount(
			String teamKey, Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamTeamKeyAssignedAccountsPage_getTeamKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTeamTeamKeyAssignedAccountsPage_getIrrelevantTeamKey()
		throws Exception {

		return null;
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(Account account, List<Account> accounts) {
		boolean contains = false;

		for (Account item : accounts) {
			if (equals(account, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(accounts + " does not contain " + account, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Account account1, Account account2) {
		Assert.assertTrue(
			account1 + " does not equal " + account2,
			equals(account1, account2));
	}

	protected void assertEquals(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (int i = 0; i < accounts1.size(); i++) {
			Account account1 = accounts1.get(i);
			Account account2 = accounts2.get(i);

			assertEquals(account1, account2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (Account account1 : accounts1) {
			boolean contains = false;

			for (Account account2 : accounts2) {
				if (equals(account1, account2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accounts2 + " does not contain " + account1, contains);
		}
	}

	protected void assertValid(Account account) throws Exception {
		boolean valid = true;

		if (account.getDateCreated() == null) {
			valid = false;
		}

		if (account.getDateModified() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assignedTeams", additionalAssertFieldName)) {
				if (account.getAssignedTeams() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("code", additionalAssertFieldName)) {
				if (account.getCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contactEmailAddress", additionalAssertFieldName)) {

				if (account.getContactEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contacts", additionalAssertFieldName)) {
				if (account.getContacts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customerContacts", additionalAssertFieldName)) {
				if (account.getCustomerContacts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataRegion", additionalAssertFieldName)) {
				if (account.getDataRegion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (account.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("entitlements", additionalAssertFieldName)) {
				if (account.getEntitlements() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (account.getExternalLinks() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("faxNumber", additionalAssertFieldName)) {
				if (account.getFaxNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("internal", additionalAssertFieldName)) {
				if (account.getInternal() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (account.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("language", additionalAssertFieldName)) {
				if (account.getLanguage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (account.getLogoId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (account.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentAccountKey", additionalAssertFieldName)) {
				if (account.getParentAccountKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (account.getPhoneNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("postalAddresses", additionalAssertFieldName)) {
				if (account.getPostalAddresses() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productPurchases", additionalAssertFieldName)) {
				if (account.getProductPurchases() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"profileEmailAddress", additionalAssertFieldName)) {

				if (account.getProfileEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("properties", additionalAssertFieldName)) {
				if (account.getProperties() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("region", additionalAssertFieldName)) {
				if (account.getRegion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (account.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("tier", additionalAssertFieldName)) {
				if (account.getTier() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("website", additionalAssertFieldName)) {
				if (account.getWebsite() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("workerContacts", additionalAssertFieldName)) {
				if (account.getWorkerContacts() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Account> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Account> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Account> accounts = page.getItems();

		int size = accounts.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account.
						class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Account account1, Account account2) {
		if (account1 == account2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("assignedTeams", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getAssignedTeams(),
						account2.getAssignedTeams())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("code", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getCode(), account2.getCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contactEmailAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getContactEmailAddress(),
						account2.getContactEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contacts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getContacts(), account2.getContacts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customerContacts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getCustomerContacts(),
						account2.getCustomerContacts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataRegion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDataRegion(), account2.getDataRegion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateCreated(), account2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateModified(),
						account2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDescription(), account2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("entitlements", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getEntitlements(),
						account2.getEntitlements())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getExternalLinks(),
						account2.getExternalLinks())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("faxNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getFaxNumber(), account2.getFaxNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("internal", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getInternal(), account2.getInternal())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(account1.getKey(), account2.getKey())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("language", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getLanguage(), account2.getLanguage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getLogoId(), account2.getLogoId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getName(), account2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentAccountKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getParentAccountKey(),
						account2.getParentAccountKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getPhoneNumber(), account2.getPhoneNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalAddresses", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getPostalAddresses(),
						account2.getPostalAddresses())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productPurchases", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getProductPurchases(),
						account2.getProductPurchases())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"profileEmailAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getProfileEmailAddress(),
						account2.getProfileEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("properties", additionalAssertFieldName)) {
				if (!equals(
						(Map)account1.getProperties(),
						(Map)account2.getProperties())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("region", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getRegion(), account2.getRegion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getStatus(), account2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("tier", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getTier(), account2.getTier())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("website", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getWebsite(), account2.getWebsite())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("workerContacts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getWorkerContacts(),
						account2.getWorkerContacts())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_accountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, Account account) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("assignedTeams")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("code")) {
			Object object = account.getCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("contactEmailAddress")) {
			Object object = account.getContactEmailAddress();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("contacts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customerContacts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataRegion")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = account.getDescription();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("entitlements")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalLinks")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("faxNumber")) {
			Object object = account.getFaxNumber();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("internal")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			Object object = account.getKey();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("language")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("logoId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			Object object = account.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("parentAccountKey")) {
			Object object = account.getParentAccountKey();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("phoneNumber")) {
			Object object = account.getPhoneNumber();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("postalAddresses")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productPurchases")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("profileEmailAddress")) {
			Object object = account.getProfileEmailAddress();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("properties")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("region")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("tier")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("website")) {
			Object object = account.getWebsite();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("workerContacts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Account randomAccount() throws Exception {
		return new Account() {
			{
				code = StringUtil.toLowerCase(RandomTestUtil.randomString());
				contactEmailAddress = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				faxNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				internal = RandomTestUtil.randomBoolean();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				logoId = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				parentAccountKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				phoneNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				profileEmailAddress = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				website = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Account randomIrrelevantAccount() throws Exception {
		Account randomIrrelevantAccount = randomAccount();

		return randomIrrelevantAccount;
	}

	protected Account randomPatchAccount() throws Exception {
		return randomAccount();
	}

	protected AccountResource accountResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseAccountResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AccountResource
		_accountResource;

}