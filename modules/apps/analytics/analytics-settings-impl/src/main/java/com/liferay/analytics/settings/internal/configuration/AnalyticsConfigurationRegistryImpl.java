/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.configuration;

import com.liferay.analytics.batch.exportimport.AnalyticsDXPEntityBatchExporter;
import com.liferay.analytics.batch.exportimport.constants.AnalyticsDXPEntityBatchExporterConstants;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	configurationPid = "com.liferay.analytics.settings.configuration.AnalyticsConfiguration",
	immediate = true,
	property = Constants.SERVICE_PID + "=com.liferay.analytics.settings.configuration.AnalyticsConfiguration.scoped",
	service = {
		AnalyticsConfigurationRegistry.class, ManagedServiceFactory.class
	}
)
public class AnalyticsConfigurationRegistryImpl
	implements AnalyticsConfigurationRegistry, ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		long companyId = getCompanyId(pid);

		_unmapPid(pid);

		_disable(companyId);
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(long companyId) {
		return _analyticsConfigurations.getOrDefault(
			companyId, _systemAnalyticsConfiguration);
	}

	@Override
	public AnalyticsConfiguration getAnalyticsConfiguration(String pid) {
		Long companyId = _pidCompanyIdMapping.get(pid);

		if (companyId == null) {
			return _systemAnalyticsConfiguration;
		}

		return getAnalyticsConfiguration(companyId);
	}

	@Override
	public Dictionary<String, Object> getAnalyticsConfigurationProperties(
		long companyId) {

		if (!isActive()) {
			return null;
		}

		Set<Map.Entry<String, Long>> entries = _pidCompanyIdMapping.entrySet();

		Stream<Map.Entry<String, Long>> stream = entries.stream();

		String pid = stream.filter(
			entry -> Objects.equals(entry.getValue(), companyId)
		).map(
			Map.Entry::getKey
		).findFirst(
		).orElse(
			null
		);

		if (pid == null) {
			return null;
		}

		try {
			Configuration configuration = _configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);

			return configuration.getProperties();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get configuration for company " + companyId,
					exception);
			}
		}

		return null;
	}

	@Override
	public Map<Long, AnalyticsConfiguration> getAnalyticsConfigurations() {
		return _analyticsConfigurations;
	}

	@Override
	public long getCompanyId(String pid) {
		return _pidCompanyIdMapping.getOrDefault(pid, CompanyConstants.SYSTEM);
	}

	@Override
	public String getName() {
		return "com.liferay.analytics.settings.configuration." +
			"AnalyticsConfiguration.scoped";
	}

	@Override
	public boolean isActive() {
		if (!_active && _hasConfiguration()) {
			_active = true;
		}
		else if (_active && !_hasConfiguration()) {
			_active = false;
		}

		return _active;
	}

	public void updateCompanyConfiguration(
			long companyId, Map<String, Object> properties)
		throws Exception {

		Map<String, Object> configurationProperties = _toMap(
			getAnalyticsConfigurationProperties(companyId));

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (configurationProperties.containsKey(entry.getKey())) {
				configurationProperties.put(entry.getKey(), entry.getValue());
			}
		}

		for (String multiValuedKey :
				new String[] {
					"previousSyncedUserFieldNames", "syncedUserFieldNames"
				}) {

			String[] value = (String[])configurationProperties.get(
				multiValuedKey);

			if ((value != null) && (value.length == 0)) {
				configurationProperties.remove(multiValuedKey);
			}

			configurationProperties.computeIfAbsent(
				multiValuedKey,
				key -> _defaults.getOrDefault(key, new String[0]));
		}

		_configurationProvider.saveCompanyConfiguration(
			AnalyticsConfiguration.class, companyId,
			new HashMapDictionary<>(configurationProperties));
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary) {
		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_pidCompanyIdMapping.put(pid, companyId);

			_analyticsConfigurations.put(
				companyId,
				ConfigurableUtil.createConfigurable(
					AnalyticsConfiguration.class, dictionary));
		}

		if (!_initializedCompanyIds.contains(companyId)) {
			_initializedCompanyIds.add(companyId);

			if (Validator.isNotNull(dictionary.get("previousToken"))) {
				return;
			}
		}

		if (Validator.isNull(dictionary.get("token"))) {
			if (Validator.isNotNull(dictionary.get("previousToken"))) {
				_disable((Long)dictionary.get("companyId"));
			}
		}
		else {
			if (Validator.isNull(dictionary.get("previousToken"))) {
				_enable((Long)dictionary.get("companyId"));
			}

			_sync(dictionary);
		}
	}

	@Activate
	@Modified
	protected void activate(
		ComponentContext componentContext, Map<String, Object> properties) {

		_componentContext = componentContext;

		_systemAnalyticsConfiguration = ConfigurableUtil.createConfigurable(
			AnalyticsConfiguration.class, properties);
	}

	private void _addAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			return;
		}

		Company company = _companyLocalService.getCompany(companyId);

		Role role = _roleLocalService.getRole(
			companyId, "Analytics Administrator");

		user = _userLocalService.addUser(
			0, companyId, true, null, null, false,
			AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN,
			"analytics.administrator@" + company.getMx(),
			LocaleUtil.getDefault(), "Analytics", "", "Administrator", 0, 0,
			true, 0, 1, 1970, "", null, null, new long[] {role.getRoleId()},
			null, false, new ServiceContext());

		_userLocalService.updateUser(user);
	}

	private void _addSAPEntry(long companyId) throws Exception {
		String sapEntryName = _SAP_ENTRY_OBJECT[0];

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, sapEntryName);

		if (sapEntry != null) {
			return;
		}

		_sapEntryLocalService.addSAPEntry(
			_userLocalService.getDefaultUserId(companyId), _SAP_ENTRY_OBJECT[1],
			false, true, sapEntryName,
			Collections.singletonMap(LocaleUtil.getDefault(), sapEntryName),
			new ServiceContext());
	}

	private void _deleteAnalyticsAdmin(long companyId) throws Exception {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		if (user != null) {
			_userLocalService.deleteUser(user);
		}
	}

	private void _deleteSAPEntry(long companyId) throws Exception {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME);

		if (sapEntry != null) {
			_sapEntryLocalService.deleteSAPEntry(sapEntry);
		}
	}

	private void _disable(long companyId) {
		try {
			if (companyId != CompanyConstants.SYSTEM) {
				_analyticsDXPEntityBatchExporter.unscheduleExportTriggers(
					companyId,
					new String[] {
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAME_DXP_ENTITIES
					});

				_deleteAnalyticsAdmin(companyId);
				_deleteSAPEntry(companyId);
			}

			if (_active && !_hasConfiguration()) {
				_active = false;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private void _enable(long companyId) {
		try {
			_active = true;

			_addAnalyticsAdmin(companyId);
			_addSAPEntry(companyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private boolean _hasConfiguration() {
		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				"(service.pid=" + AnalyticsConfiguration.class.getName() +
					"*)");
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to list analytics configurations", exception);
			}
		}

		if (configurations == null) {
			return false;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if (Validator.isNotNull(properties.get("token"))) {
				return true;
			}
		}

		return false;
	}

	private void _sync(Dictionary<String, ?> dictionary) {
		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		try {
			if (Validator.isNotNull(dictionary.get("previousToken"))) {
				Set<String> refreshDispatchTriggerNames = new HashSet<>();
				Set<String> unscheduleDispatchTriggerNames = new HashSet<>();

				if (_syncedContactSettingsChanged(companyId)) {
					if (_syncedContactSettingsEnabled(companyId)) {
						refreshDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_DXP_ENTITIES);
					}
					else {
						unscheduleDispatchTriggerNames.add(
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_DXP_ENTITIES);
					}
				}

				if (_syncedContactSettingsEnabled(companyId) &&
					_syncedUserFieldsChanged(companyId)) {

					refreshDispatchTriggerNames.add(
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAME_DXP_ENTITIES);
				}

				if (!refreshDispatchTriggerNames.isEmpty()) {
					_analyticsDXPEntityBatchExporter.refreshExportTriggers(
						companyId,
						refreshDispatchTriggerNames.toArray(new String[0]));

					_analyticsDXPEntityBatchExporter.export(
						companyId,
						new String[] {
							AnalyticsDXPEntityBatchExporterConstants.
								DISPATCH_TRIGGER_NAME_DXP_ENTITIES
						});
				}

				if (!unscheduleDispatchTriggerNames.isEmpty()) {
					_analyticsDXPEntityBatchExporter.unscheduleExportTriggers(
						companyId,
						unscheduleDispatchTriggerNames.toArray(new String[0]));
				}
			}
			else {
				try {
					Set<String> dispatchTriggerNames = new HashSet<>();

					Collections.addAll(
						dispatchTriggerNames,
						AnalyticsDXPEntityBatchExporterConstants.
							DISPATCH_TRIGGER_NAME_DXP_ENTITIES);

					_analyticsDXPEntityBatchExporter.scheduleExportTriggers(
						companyId, dispatchTriggerNames.toArray(new String[0]));

					_analyticsDXPEntityBatchExporter.export(
						companyId, dispatchTriggerNames.toArray(new String[0]));
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private boolean _syncedContactSettingsChanged(long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.previousSyncAllContacts() !=
				analyticsConfiguration.syncAllContacts()) {

			return true;
		}

		String[] previousSyncedOrganizationIds =
			analyticsConfiguration.previousSyncedOrganizationIds();

		Arrays.sort(previousSyncedOrganizationIds);

		String[] previousSyncedUserGroupIds =
			analyticsConfiguration.previousSyncedUserGroupIds();

		Arrays.sort(previousSyncedUserGroupIds);

		String[] syncedOrganizationIds =
			analyticsConfiguration.syncedOrganizationIds();

		Arrays.sort(syncedOrganizationIds);

		String[] syncedUserGroupIds =
			analyticsConfiguration.syncedUserGroupIds();

		Arrays.sort(syncedUserGroupIds);

		if (!analyticsConfiguration.syncAllContacts() &&
			(!Arrays.equals(
				previousSyncedOrganizationIds, syncedOrganizationIds) ||
			 !Arrays.equals(previousSyncedUserGroupIds, syncedUserGroupIds))) {

			return true;
		}

		return false;
	}

	private boolean _syncedContactSettingsEnabled(long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		String[] syncedOrganizationIds =
			analyticsConfiguration.syncedOrganizationIds();
		String[] syncedUserGroupIds =
			analyticsConfiguration.syncedUserGroupIds();

		if (analyticsConfiguration.syncAllContacts() ||
			(syncedOrganizationIds.length != 0) ||
			(syncedUserGroupIds.length != 0)) {

			return true;
		}

		return false;
	}

	private boolean _syncedUserFieldsChanged(long companyId) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			getAnalyticsConfiguration(companyId);

		String[] previousSyncedContactFieldNames =
			analyticsConfiguration.previousSyncedContactFieldNames();

		Arrays.sort(previousSyncedContactFieldNames);

		String[] previousSyncedUserFieldNames =
			analyticsConfiguration.previousSyncedUserFieldNames();

		Arrays.sort(previousSyncedUserFieldNames);

		String[] syncedContactFieldNames =
			analyticsConfiguration.syncedContactFieldNames();

		Arrays.sort(syncedContactFieldNames);

		String[] syncedUserFieldNames =
			analyticsConfiguration.syncedUserFieldNames();

		Arrays.sort(syncedUserFieldNames);

		if ((previousSyncedContactFieldNames.length != 0) &&
			(previousSyncedUserFieldNames.length != 0) &&
			(!Arrays.equals(
				previousSyncedUserFieldNames, syncedUserFieldNames) ||
			 !Arrays.equals(
				 previousSyncedContactFieldNames, syncedContactFieldNames))) {

			return true;
		}

		return false;
	}

	private Map<String, Object> _toMap(Dictionary<String, Object> dictionary) {
		if (dictionary == null) {
			return Collections.emptyMap();
		}

		Map<String, Object> map = new HashMap<>();

		for (String key : Collections.list(dictionary.keys())) {
			map.put(key, dictionary.get(key));
		}

		return map;
	}

	private void _unmapPid(String pid) {
		Long companyId = _pidCompanyIdMapping.remove(pid);

		if (companyId != null) {
			_analyticsConfigurations.remove(companyId);
		}
	}

	private static final String[] _SAP_ENTRY_OBJECT = {
		AnalyticsSecurityConstants.SERVICE_ACCESS_POLICY_NAME,
		StringBundler.concat(
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"ExperimentResourceImpl#deleteExperiment\n",
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"ExperimentRunResourceImpl#postExperimentRun\n",
			"com.liferay.segments.asah.rest.internal.resource.v1_0.",
			"StatusResourceImpl#postExperimentStatus")
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsConfigurationRegistryImpl.class);

	private static final Map<String, String[]> _defaults = HashMapBuilder.put(
		"syncedUserFieldNames",
		new String[] {
			"createDate", "emailAddress", "firstName", "jobTitle", "lastName",
			"modifiedDate", "timeZoneId", "userId", "uuid"
		}
	).build();

	private boolean _active;
	private final Map<Long, AnalyticsConfiguration> _analyticsConfigurations =
		new ConcurrentHashMap<>();

	@Reference
	private AnalyticsDXPEntityBatchExporter _analyticsDXPEntityBatchExporter;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ComponentContext _componentContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Set<Long> _initializedCompanyIds = new HashSet<>();
	private final Map<String, Long> _pidCompanyIdMapping =
		new ConcurrentHashMap<>();

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

	private volatile AnalyticsConfiguration _systemAnalyticsConfiguration;

	@Reference
	private UserLocalService _userLocalService;

}