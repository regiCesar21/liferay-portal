/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.google.docs.internal.util;

import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileEntryTypeHelper {

	public GoogleDocsDLFileEntryTypeHelper(
		Company company, DefaultDDMStructureHelper defaultDDMStructureHelper,
		long dlFileEntryMetadataClassNameId,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_dlFileEntryMetadataClassNameId = dlFileEntryMetadataClassNameId;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;
	}

	public void addGoogleDocsDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS);

		if (ddmStructure == null) {
			ddmStructure = _addGoogleDocsDDMStructure();
		}

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(
				ddmStructure.getStructureId());

		if (dlFileEntryTypes.isEmpty()) {
			_addGoogleDocsDLFileEntryType(ddmStructure.getStructureId());
		}
	}

	private DDMStructure _addGoogleDocsDDMStructure() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, _company.getGroupId(),
			_dlFileEntryMetadataClassNameId, clazz.getClassLoader(),
			"com/liferay/document/library/google/docs/internal/util" +
				"/dependencies/google-docs-metadata-structure.xml",
			serviceContext);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);

		ddmStructure.setNameMap(_updateNameMap(ddmStructure.getNameMap()));

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);

		return ddmStructure;
	}

	private void _addGoogleDocsDLFileEntryType(long ddmStructureId)
		throws Exception {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), GoogleDocsConstants.DL_FILE_ENTRY_TYPE_NAME
		).build();

		Map<Locale, String> descriptionMap = new HashMap<>();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		_dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(),
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY, nameMap, descriptionMap,
			new long[] {ddmStructureId}, serviceContext);
	}

	private Map<Locale, String> _updateNameMap(Map<Locale, String> nameMap) {
		Map<Locale, String> updatedNameMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			updatedNameMap.put(
				entry.getKey(),
				LanguageUtil.get(entry.getKey(), "google-docs-metadata"));
		}

		return updatedNameMap;
	}

	private final Company _company;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final long _dlFileEntryMetadataClassNameId;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final UserLocalService _userLocalService;

}