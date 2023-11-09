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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.AuditEntry;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.AuditEntryResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.AuditEntrySerDes;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
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
public abstract class BaseAuditEntryResourceTestCase {

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

		_auditEntryResource.setContextCompany(testCompany);

		AuditEntryResource.Builder builder = AuditEntryResource.builder();

		auditEntryResource = builder.authentication(
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

		AuditEntry auditEntry1 = randomAuditEntry();

		String json = objectMapper.writeValueAsString(auditEntry1);

		AuditEntry auditEntry2 = AuditEntrySerDes.toDTO(json);

		Assert.assertTrue(equals(auditEntry1, auditEntry2));
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

		AuditEntry auditEntry = randomAuditEntry();

		String json1 = objectMapper.writeValueAsString(auditEntry);
		String json2 = AuditEntrySerDes.toJSON(auditEntry);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AuditEntry auditEntry = randomAuditEntry();

		auditEntry.setAgentName(regex);
		auditEntry.setAgentUID(regex);
		auditEntry.setDescription(regex);
		auditEntry.setField(regex);
		auditEntry.setFieldClassLabel(regex);
		auditEntry.setKey(regex);
		auditEntry.setNewValue(regex);
		auditEntry.setOldValue(regex);
		auditEntry.setSummary(regex);

		String json = AuditEntrySerDes.toJSON(auditEntry);

		Assert.assertFalse(json.contains(regex));

		auditEntry = AuditEntrySerDes.toDTO(json);

		Assert.assertEquals(regex, auditEntry.getAgentName());
		Assert.assertEquals(regex, auditEntry.getAgentUID());
		Assert.assertEquals(regex, auditEntry.getDescription());
		Assert.assertEquals(regex, auditEntry.getField());
		Assert.assertEquals(regex, auditEntry.getFieldClassLabel());
		Assert.assertEquals(regex, auditEntry.getKey());
		Assert.assertEquals(regex, auditEntry.getNewValue());
		Assert.assertEquals(regex, auditEntry.getOldValue());
		Assert.assertEquals(regex, auditEntry.getSummary());
	}

	@Test
	public void testGetAccountAccountKeyAuditEntriesPage() throws Exception {
		String accountKey =
			testGetAccountAccountKeyAuditEntriesPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountAccountKeyAuditEntriesPage_getIrrelevantAccountKey();

		Page<AuditEntry> page =
			auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				accountKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantAccountKey != null) {
			AuditEntry irrelevantAuditEntry =
				testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
					irrelevantAccountKey, randomIrrelevantAuditEntry());

			page = auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				irrelevantAccountKey, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantAuditEntry, (List<AuditEntry>)page.getItems());
			assertValid(
				page,
				testGetAccountAccountKeyAuditEntriesPage_getExpectedActions(
					irrelevantAccountKey));
		}

		AuditEntry auditEntry1 =
			testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
				accountKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
				accountKey, randomAuditEntry());

		page = auditEntryResource.getAccountAccountKeyAuditEntriesPage(
			accountKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(auditEntry1, (List<AuditEntry>)page.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page.getItems());
		assertValid(
			page,
			testGetAccountAccountKeyAuditEntriesPage_getExpectedActions(
				accountKey));
	}

	protected Map<String, Map<String, String>>
			testGetAccountAccountKeyAuditEntriesPage_getExpectedActions(
				String accountKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountAccountKeyAuditEntriesPageWithPagination()
		throws Exception {

		String accountKey =
			testGetAccountAccountKeyAuditEntriesPage_getAccountKey();

		Page<AuditEntry> auditEntryPage =
			auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				accountKey, null);

		int totalCount = GetterUtil.getInteger(auditEntryPage.getTotalCount());

		AuditEntry auditEntry1 =
			testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
				accountKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
				accountKey, randomAuditEntry());

		AuditEntry auditEntry3 =
			testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
				accountKey, randomAuditEntry());

		Page<AuditEntry> page1 =
			auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				accountKey, Pagination.of(1, totalCount + 2));

		List<AuditEntry> auditEntries1 = (List<AuditEntry>)page1.getItems();

		Assert.assertEquals(
			auditEntries1.toString(), totalCount + 2, auditEntries1.size());

		Page<AuditEntry> page2 =
			auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				accountKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AuditEntry> auditEntries2 = (List<AuditEntry>)page2.getItems();

		Assert.assertEquals(auditEntries2.toString(), 1, auditEntries2.size());

		Page<AuditEntry> page3 =
			auditEntryResource.getAccountAccountKeyAuditEntriesPage(
				accountKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(auditEntry1, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry3, (List<AuditEntry>)page3.getItems());
	}

	protected AuditEntry testGetAccountAccountKeyAuditEntriesPage_addAuditEntry(
			String accountKey, AuditEntry auditEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountAccountKeyAuditEntriesPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountAccountKeyAuditEntriesPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountKeyAuditEntriesPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetAuditEntry() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetAuditEntry() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGraphQLGetAuditEntryNotFound() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetContactRoleContactRoleKeyAuditEntriesPage()
		throws Exception {

		String contactRoleKey =
			testGetContactRoleContactRoleKeyAuditEntriesPage_getContactRoleKey();
		String irrelevantContactRoleKey =
			testGetContactRoleContactRoleKeyAuditEntriesPage_getIrrelevantContactRoleKey();

		Page<AuditEntry> page =
			auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
				contactRoleKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantContactRoleKey != null) {
			AuditEntry irrelevantAuditEntry =
				testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
					irrelevantContactRoleKey, randomIrrelevantAuditEntry());

			page =
				auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
					irrelevantContactRoleKey,
					Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantAuditEntry, (List<AuditEntry>)page.getItems());
			assertValid(
				page,
				testGetContactRoleContactRoleKeyAuditEntriesPage_getExpectedActions(
					irrelevantContactRoleKey));
		}

		AuditEntry auditEntry1 =
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				contactRoleKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				contactRoleKey, randomAuditEntry());

		page = auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
			contactRoleKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(auditEntry1, (List<AuditEntry>)page.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page.getItems());
		assertValid(
			page,
			testGetContactRoleContactRoleKeyAuditEntriesPage_getExpectedActions(
				contactRoleKey));
	}

	protected Map<String, Map<String, String>>
			testGetContactRoleContactRoleKeyAuditEntriesPage_getExpectedActions(
				String contactRoleKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetContactRoleContactRoleKeyAuditEntriesPageWithPagination()
		throws Exception {

		String contactRoleKey =
			testGetContactRoleContactRoleKeyAuditEntriesPage_getContactRoleKey();

		Page<AuditEntry> auditEntryPage =
			auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
				contactRoleKey, null);

		int totalCount = GetterUtil.getInteger(auditEntryPage.getTotalCount());

		AuditEntry auditEntry1 =
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				contactRoleKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				contactRoleKey, randomAuditEntry());

		AuditEntry auditEntry3 =
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				contactRoleKey, randomAuditEntry());

		Page<AuditEntry> page1 =
			auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
				contactRoleKey, Pagination.of(1, totalCount + 2));

		List<AuditEntry> auditEntries1 = (List<AuditEntry>)page1.getItems();

		Assert.assertEquals(
			auditEntries1.toString(), totalCount + 2, auditEntries1.size());

		Page<AuditEntry> page2 =
			auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
				contactRoleKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AuditEntry> auditEntries2 = (List<AuditEntry>)page2.getItems();

		Assert.assertEquals(auditEntries2.toString(), 1, auditEntries2.size());

		Page<AuditEntry> page3 =
			auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
				contactRoleKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(auditEntry1, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry3, (List<AuditEntry>)page3.getItems());
	}

	protected AuditEntry
			testGetContactRoleContactRoleKeyAuditEntriesPage_addAuditEntry(
				String contactRoleKey, AuditEntry auditEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactRoleContactRoleKeyAuditEntriesPage_getContactRoleKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactRoleContactRoleKeyAuditEntriesPage_getIrrelevantContactRoleKey()
		throws Exception {

		return null;
	}

	@Test
	public void testGetContactByUuidContactUuidAuditEntriesPage()
		throws Exception {

		String contactUuid =
			testGetContactByUuidContactUuidAuditEntriesPage_getContactUuid();
		String irrelevantContactUuid =
			testGetContactByUuidContactUuidAuditEntriesPage_getIrrelevantContactUuid();

		Page<AuditEntry> page =
			auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
				contactUuid, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantContactUuid != null) {
			AuditEntry irrelevantAuditEntry =
				testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
					irrelevantContactUuid, randomIrrelevantAuditEntry());

			page =
				auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
					irrelevantContactUuid,
					Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantAuditEntry, (List<AuditEntry>)page.getItems());
			assertValid(
				page,
				testGetContactByUuidContactUuidAuditEntriesPage_getExpectedActions(
					irrelevantContactUuid));
		}

		AuditEntry auditEntry1 =
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				contactUuid, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				contactUuid, randomAuditEntry());

		page = auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
			contactUuid, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(auditEntry1, (List<AuditEntry>)page.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page.getItems());
		assertValid(
			page,
			testGetContactByUuidContactUuidAuditEntriesPage_getExpectedActions(
				contactUuid));
	}

	protected Map<String, Map<String, String>>
			testGetContactByUuidContactUuidAuditEntriesPage_getExpectedActions(
				String contactUuid)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetContactByUuidContactUuidAuditEntriesPageWithPagination()
		throws Exception {

		String contactUuid =
			testGetContactByUuidContactUuidAuditEntriesPage_getContactUuid();

		Page<AuditEntry> auditEntryPage =
			auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
				contactUuid, null);

		int totalCount = GetterUtil.getInteger(auditEntryPage.getTotalCount());

		AuditEntry auditEntry1 =
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				contactUuid, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				contactUuid, randomAuditEntry());

		AuditEntry auditEntry3 =
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				contactUuid, randomAuditEntry());

		Page<AuditEntry> page1 =
			auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
				contactUuid, Pagination.of(1, totalCount + 2));

		List<AuditEntry> auditEntries1 = (List<AuditEntry>)page1.getItems();

		Assert.assertEquals(
			auditEntries1.toString(), totalCount + 2, auditEntries1.size());

		Page<AuditEntry> page2 =
			auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
				contactUuid, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AuditEntry> auditEntries2 = (List<AuditEntry>)page2.getItems();

		Assert.assertEquals(auditEntries2.toString(), 1, auditEntries2.size());

		Page<AuditEntry> page3 =
			auditEntryResource.getContactByUuidContactUuidAuditEntriesPage(
				contactUuid, Pagination.of(1, (int)totalCount + 3));

		assertContains(auditEntry1, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry3, (List<AuditEntry>)page3.getItems());
	}

	protected AuditEntry
			testGetContactByUuidContactUuidAuditEntriesPage_addAuditEntry(
				String contactUuid, AuditEntry auditEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactByUuidContactUuidAuditEntriesPage_getContactUuid()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetContactByUuidContactUuidAuditEntriesPage_getIrrelevantContactUuid()
		throws Exception {

		return null;
	}

	@Test
	public void testPostContactByUuidContactUuidAuditEntriesPage()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testGetTeamRoleTeamRoleKeyAuditEntriesPage() throws Exception {
		String teamRoleKey =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getTeamRoleKey();
		String irrelevantTeamRoleKey =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getIrrelevantTeamRoleKey();

		Page<AuditEntry> page =
			auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				teamRoleKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantTeamRoleKey != null) {
			AuditEntry irrelevantAuditEntry =
				testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
					irrelevantTeamRoleKey, randomIrrelevantAuditEntry());

			page = auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				irrelevantTeamRoleKey, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantAuditEntry, (List<AuditEntry>)page.getItems());
			assertValid(
				page,
				testGetTeamRoleTeamRoleKeyAuditEntriesPage_getExpectedActions(
					irrelevantTeamRoleKey));
		}

		AuditEntry auditEntry1 =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				teamRoleKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				teamRoleKey, randomAuditEntry());

		page = auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
			teamRoleKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(auditEntry1, (List<AuditEntry>)page.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page.getItems());
		assertValid(
			page,
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getExpectedActions(
				teamRoleKey));
	}

	protected Map<String, Map<String, String>>
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getExpectedActions(
				String teamRoleKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetTeamRoleTeamRoleKeyAuditEntriesPageWithPagination()
		throws Exception {

		String teamRoleKey =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getTeamRoleKey();

		Page<AuditEntry> auditEntryPage =
			auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				teamRoleKey, null);

		int totalCount = GetterUtil.getInteger(auditEntryPage.getTotalCount());

		AuditEntry auditEntry1 =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				teamRoleKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				teamRoleKey, randomAuditEntry());

		AuditEntry auditEntry3 =
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				teamRoleKey, randomAuditEntry());

		Page<AuditEntry> page1 =
			auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				teamRoleKey, Pagination.of(1, totalCount + 2));

		List<AuditEntry> auditEntries1 = (List<AuditEntry>)page1.getItems();

		Assert.assertEquals(
			auditEntries1.toString(), totalCount + 2, auditEntries1.size());

		Page<AuditEntry> page2 =
			auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				teamRoleKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AuditEntry> auditEntries2 = (List<AuditEntry>)page2.getItems();

		Assert.assertEquals(auditEntries2.toString(), 1, auditEntries2.size());

		Page<AuditEntry> page3 =
			auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
				teamRoleKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(auditEntry1, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry3, (List<AuditEntry>)page3.getItems());
	}

	protected AuditEntry
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_addAuditEntry(
				String teamRoleKey, AuditEntry auditEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamRoleTeamRoleKeyAuditEntriesPage_getTeamRoleKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTeamRoleTeamRoleKeyAuditEntriesPage_getIrrelevantTeamRoleKey()
		throws Exception {

		return null;
	}

	@Test
	public void testGetTeamTeamKeyAuditEntriesPage() throws Exception {
		String teamKey = testGetTeamTeamKeyAuditEntriesPage_getTeamKey();
		String irrelevantTeamKey =
			testGetTeamTeamKeyAuditEntriesPage_getIrrelevantTeamKey();

		Page<AuditEntry> page =
			auditEntryResource.getTeamTeamKeyAuditEntriesPage(
				teamKey, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		if (irrelevantTeamKey != null) {
			AuditEntry irrelevantAuditEntry =
				testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
					irrelevantTeamKey, randomIrrelevantAuditEntry());

			page = auditEntryResource.getTeamTeamKeyAuditEntriesPage(
				irrelevantTeamKey, Pagination.of(1, (int)totalCount + 1));

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantAuditEntry, (List<AuditEntry>)page.getItems());
			assertValid(
				page,
				testGetTeamTeamKeyAuditEntriesPage_getExpectedActions(
					irrelevantTeamKey));
		}

		AuditEntry auditEntry1 =
			testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
				teamKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
				teamKey, randomAuditEntry());

		page = auditEntryResource.getTeamTeamKeyAuditEntriesPage(
			teamKey, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(auditEntry1, (List<AuditEntry>)page.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page.getItems());
		assertValid(
			page,
			testGetTeamTeamKeyAuditEntriesPage_getExpectedActions(teamKey));
	}

	protected Map<String, Map<String, String>>
			testGetTeamTeamKeyAuditEntriesPage_getExpectedActions(
				String teamKey)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetTeamTeamKeyAuditEntriesPageWithPagination()
		throws Exception {

		String teamKey = testGetTeamTeamKeyAuditEntriesPage_getTeamKey();

		Page<AuditEntry> auditEntryPage =
			auditEntryResource.getTeamTeamKeyAuditEntriesPage(teamKey, null);

		int totalCount = GetterUtil.getInteger(auditEntryPage.getTotalCount());

		AuditEntry auditEntry1 =
			testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
				teamKey, randomAuditEntry());

		AuditEntry auditEntry2 =
			testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
				teamKey, randomAuditEntry());

		AuditEntry auditEntry3 =
			testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
				teamKey, randomAuditEntry());

		Page<AuditEntry> page1 =
			auditEntryResource.getTeamTeamKeyAuditEntriesPage(
				teamKey, Pagination.of(1, totalCount + 2));

		List<AuditEntry> auditEntries1 = (List<AuditEntry>)page1.getItems();

		Assert.assertEquals(
			auditEntries1.toString(), totalCount + 2, auditEntries1.size());

		Page<AuditEntry> page2 =
			auditEntryResource.getTeamTeamKeyAuditEntriesPage(
				teamKey, Pagination.of(2, totalCount + 2));

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<AuditEntry> auditEntries2 = (List<AuditEntry>)page2.getItems();

		Assert.assertEquals(auditEntries2.toString(), 1, auditEntries2.size());

		Page<AuditEntry> page3 =
			auditEntryResource.getTeamTeamKeyAuditEntriesPage(
				teamKey, Pagination.of(1, (int)totalCount + 3));

		assertContains(auditEntry1, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry2, (List<AuditEntry>)page3.getItems());
		assertContains(auditEntry3, (List<AuditEntry>)page3.getItems());
	}

	protected AuditEntry testGetTeamTeamKeyAuditEntriesPage_addAuditEntry(
			String teamKey, AuditEntry auditEntry)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamTeamKeyAuditEntriesPage_getTeamKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamTeamKeyAuditEntriesPage_getIrrelevantTeamKey()
		throws Exception {

		return null;
	}

	protected void assertContains(
		AuditEntry auditEntry, List<AuditEntry> auditEntries) {

		boolean contains = false;

		for (AuditEntry item : auditEntries) {
			if (equals(auditEntry, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			auditEntries + " does not contain " + auditEntry, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AuditEntry auditEntry1, AuditEntry auditEntry2) {

		Assert.assertTrue(
			auditEntry1 + " does not equal " + auditEntry2,
			equals(auditEntry1, auditEntry2));
	}

	protected void assertEquals(
		List<AuditEntry> auditEntries1, List<AuditEntry> auditEntries2) {

		Assert.assertEquals(auditEntries1.size(), auditEntries2.size());

		for (int i = 0; i < auditEntries1.size(); i++) {
			AuditEntry auditEntry1 = auditEntries1.get(i);
			AuditEntry auditEntry2 = auditEntries2.get(i);

			assertEquals(auditEntry1, auditEntry2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AuditEntry> auditEntries1, List<AuditEntry> auditEntries2) {

		Assert.assertEquals(auditEntries1.size(), auditEntries2.size());

		for (AuditEntry auditEntry1 : auditEntries1) {
			boolean contains = false;

			for (AuditEntry auditEntry2 : auditEntries2) {
				if (equals(auditEntry1, auditEntry2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				auditEntries2 + " does not contain " + auditEntry1, contains);
		}
	}

	protected void assertValid(AuditEntry auditEntry) throws Exception {
		boolean valid = true;

		if (auditEntry.getDateCreated() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("action", additionalAssertFieldName)) {
				if (auditEntry.getAction() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("agentName", additionalAssertFieldName)) {
				if (auditEntry.getAgentName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("agentUID", additionalAssertFieldName)) {
				if (auditEntry.getAgentUID() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("auditSetId", additionalAssertFieldName)) {
				if (auditEntry.getAuditSetId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (auditEntry.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("field", additionalAssertFieldName)) {
				if (auditEntry.getField() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fieldClassLabel", additionalAssertFieldName)) {
				if (auditEntry.getFieldClassLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fieldClassPK", additionalAssertFieldName)) {
				if (auditEntry.getFieldClassPK() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (auditEntry.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("newValue", additionalAssertFieldName)) {
				if (auditEntry.getNewValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("oldValue", additionalAssertFieldName)) {
				if (auditEntry.getOldValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (auditEntry.getSummary() == null) {
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

	protected void assertValid(Page<AuditEntry> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<AuditEntry> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<AuditEntry> auditEntries = page.getItems();

		int size = auditEntries.size();

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
					com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry.
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

	protected boolean equals(AuditEntry auditEntry1, AuditEntry auditEntry2) {
		if (auditEntry1 == auditEntry2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("action", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getAction(), auditEntry2.getAction())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("agentName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getAgentName(),
						auditEntry2.getAgentName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("agentUID", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getAgentUID(), auditEntry2.getAgentUID())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("auditSetId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getAuditSetId(),
						auditEntry2.getAuditSetId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getDateCreated(),
						auditEntry2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getDescription(),
						auditEntry2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("field", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getField(), auditEntry2.getField())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fieldClassLabel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getFieldClassLabel(),
						auditEntry2.getFieldClassLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fieldClassPK", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getFieldClassPK(),
						auditEntry2.getFieldClassPK())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getKey(), auditEntry2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("newValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getNewValue(), auditEntry2.getNewValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("oldValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getOldValue(), auditEntry2.getOldValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						auditEntry1.getSummary(), auditEntry2.getSummary())) {

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

		if (!(_auditEntryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_auditEntryResource;

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
		EntityField entityField, String operator, AuditEntry auditEntry) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("action")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("agentName")) {
			Object object = auditEntry.getAgentName();

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

		if (entityFieldName.equals("agentUID")) {
			Object object = auditEntry.getAgentUID();

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

		if (entityFieldName.equals("auditSetId")) {
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
						DateUtils.addSeconds(auditEntry.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(auditEntry.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(auditEntry.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = auditEntry.getDescription();

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

		if (entityFieldName.equals("field")) {
			Object object = auditEntry.getField();

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

		if (entityFieldName.equals("fieldClassLabel")) {
			Object object = auditEntry.getFieldClassLabel();

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

		if (entityFieldName.equals("fieldClassPK")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			Object object = auditEntry.getKey();

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

		if (entityFieldName.equals("newValue")) {
			Object object = auditEntry.getNewValue();

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

		if (entityFieldName.equals("oldValue")) {
			Object object = auditEntry.getOldValue();

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

		if (entityFieldName.equals("summary")) {
			Object object = auditEntry.getSummary();

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

	protected AuditEntry randomAuditEntry() throws Exception {
		return new AuditEntry() {
			{
				agentName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				agentUID = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				auditSetId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				field = StringUtil.toLowerCase(RandomTestUtil.randomString());
				fieldClassLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				fieldClassPK = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				newValue = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				oldValue = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				summary = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AuditEntry randomIrrelevantAuditEntry() throws Exception {
		AuditEntry randomIrrelevantAuditEntry = randomAuditEntry();

		return randomIrrelevantAuditEntry;
	}

	protected AuditEntry randomPatchAuditEntry() throws Exception {
		return randomAuditEntry();
	}

	protected AuditEntryResource auditEntryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

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
		LogFactoryUtil.getLog(BaseAuditEntryResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private
		com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AuditEntryResource
			_auditEntryResource;

}