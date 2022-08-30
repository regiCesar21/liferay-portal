/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.contributor;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistration;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, service = FragmentCollectionContributorTracker.class
)
public class FragmentCollectionContributorTrackerImpl
	implements FragmentCollectionContributorTracker {

	@Override
	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey) {

		return _serviceTrackerMap.getService(fragmentCollectionKey);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by #getFragmentEntries
	 */
	@Deprecated
	@Override
	public Map<String, FragmentEntry>
		getFragmentCollectionContributorEntries() {

		return getFragmentEntries();
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			new ArrayList<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			if (MapUtil.isNotEmpty(fragmentCollectionContributor.getNames())) {
				fragmentCollectionContributors.add(
					fragmentCollectionContributor);
			}
		}

		return fragmentCollectionContributors;
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		return new HashMap<>(_fragmentEntries);
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(
						_SUPPORTED_FRAGMENT_TYPES, locale)) {

				fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}
		}

		return fragmentEntries;
	}

	@Override
	public FragmentEntry getFragmentEntry(String fragmentEntryKey) {
		return _fragmentEntries.get(fragmentEntryKey);
	}

	@Override
	public ResourceBundleLoader getResourceBundleLoader() {
		List<ResourceBundleLoader> resourceBundleLoaders = new ArrayList<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			ResourceBundleLoader resourceBundleLoader =
				fragmentCollectionContributor.getResourceBundleLoader();

			if (resourceBundleLoader != null) {
				resourceBundleLoaders.add(resourceBundleLoader);
			}
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoaders.toArray(new ResourceBundleLoader[0]));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentCollectionContributor.class,
			"fragment.collection.key",
			new FragmentCollectionContributorTrackerServiceTrackerCustomizer(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	protected FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry;

	@Reference
	protected FragmentEntryValidator fragmentEntryValidator;

	private Configuration _getFragmentServiceCompanyConfiguration(
			long companyId)
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, StringPool.EQUAL,
				FragmentServiceConfiguration.class.getName(), ".scoped",
				")(companyId=", companyId, "))");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	private boolean _isPropagateContributedFragmentChanges(long companyId)
		throws ConfigurationException {

		if (_getFragmentServiceCompanyConfiguration(companyId) != null) {
			FragmentServiceConfiguration companyFragmentServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					FragmentServiceConfiguration.class, companyId);

			return companyFragmentServiceConfiguration.
				propagateContributedFragmentChanges();
		}

		FragmentServiceConfiguration systemFragmentServiceConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				FragmentServiceConfiguration.class);

		return systemFragmentServiceConfiguration.
			propagateContributedFragmentChanges();
	}

	private void _updateFragmentEntryLinks(
		Map<String, FragmentEntry> fragmentEntries) {

		_companyLocalService.forEachCompany(
			company -> {
				try {
					if (!_isPropagateContributedFragmentChanges(
							company.getCompanyId())) {

						PortletPreferences portletPreferences =
							_portalPreferencesLocalService.getPreferences(
								company.getCompanyId(),
								PortletKeys.PREFS_OWNER_TYPE_COMPANY);

						portletPreferences.setValue(
							"alreadyPropagateContributedFragmentChanges",
							Boolean.FALSE.toString());

						portletPreferences.store();

						return;
					}
				}
				catch (Exception exception) {
					_log.error(exception);

					return;
				}

				Set<String> fragmentEntriesSet = fragmentEntries.keySet();

				List<FragmentEntryLink> fragmentEntryLinks =
					_fragmentEntryLinkLocalService.getFragmentEntryLinks(
						company.getCompanyId(),
						fragmentEntriesSet.toArray(new String[0]));

				for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
					FragmentEntry fragmentEntry = fragmentEntries.get(
						fragmentEntryLink.getRendererKey());

					if (fragmentEntry == null) {
						continue;
					}

					try {
						_fragmentEntryLinkLocalService.updateLatestChanges(
							fragmentEntry, fragmentEntryLink);
					}
					catch (PortalException portalException) {
						_log.error(portalException);
					}
				}
			});
	}

	private boolean _validateFragmentEntry(FragmentEntry fragmentEntry) {
		try {
			fragmentEntryValidator.validateConfiguration(
				fragmentEntry.getConfiguration());

			fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration());

			return true;
		}
		catch (PortalException portalException) {
			_log.error("Unable to validate fragment entry", portalException);
		}

		return false;
	}

	private static final int[] _SUPPORTED_FRAGMENT_TYPES = {
		FragmentConstants.TYPE_COMPONENT, FragmentConstants.TYPE_SECTION
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionContributorTrackerImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private final Map<String, FragmentEntry> _fragmentEntries =
		new ConcurrentHashMap<>();

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	private final Map<FragmentCollectionContributor, ServiceRegistration<?>>
		_serviceRegistrations = new HashMap<>();
	private ServiceTrackerMap<String, FragmentCollectionContributor>
		_serviceTrackerMap;

	private class FragmentCollectionContributorTrackerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentCollectionContributor, FragmentCollectionContributor> {

		@Override
		public FragmentCollectionContributor addingService(
			ServiceReference<FragmentCollectionContributor> serviceReference) {

			FragmentCollectionContributor fragmentCollectionContributor =
				_bundleContext.getService(serviceReference);

			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(
						_SUPPORTED_FRAGMENT_TYPES)) {

				if (!_validateFragmentEntry(fragmentEntry)) {
					continue;
				}

				_fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}

			_updateFragmentEntryLinks(_fragmentEntries);

			_serviceRegistrations.put(
				fragmentCollectionContributor,
				_bundleContext.registerService(
					FragmentCollectionContributorRegistration.class,
					new FragmentCollectionContributorRegistration() {
					},
					MapUtil.singletonDictionary(
						"fragment.collection.key",
						serviceReference.getProperty(
							"fragment.collection.key"))));

			return fragmentCollectionContributor;
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionContributor fragmentCollectionContributor) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionContributor fragmentCollectionContributor) {

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(fragmentCollectionContributor);

			serviceRegistration.unregister();

			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(
						_SUPPORTED_FRAGMENT_TYPES)) {

				_fragmentEntries.remove(fragmentEntry.getFragmentEntryKey());
			}

			_bundleContext.ungetService(serviceReference);
		}

		private FragmentCollectionContributorTrackerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}