/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.admin.web.internal;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.app.admin.web.internal.portlet.RemoteAppPortlet;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = RemoteAppPortletRegistrar.class)
public class RemoteAppPortletRegistrar {

	public void registerPortlet(RemoteAppEntry remoteAppEntry) {
		_registerPortlet(remoteAppEntry);
	}

	public void unregisterPortlet(RemoteAppEntry remoteAppEntry) {
		_unregisterPortlet(remoteAppEntry.getRemoteAppEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote app entries");
		}

		for (RemoteAppEntry remoteAppEntry :
				remoteAppEntryLocalService.getRemoteAppEntries(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(remoteAppEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote app entries");
		}

		for (long remoteAppEntryId : _remoteAppPortlets.keySet()) {
			_unregisterPortlet(remoteAppEntryId);
		}
	}

	@Reference
	protected RemoteAppEntryLocalService remoteAppEntryLocalService;

	private void _registerPortlet(RemoteAppEntry remoteAppEntry) {
		RemoteAppPortlet remoteAppPortlet = new RemoteAppPortlet(
			remoteAppEntry);

		long remoteAppEntryId = remoteAppEntry.getRemoteAppEntryId();

		RemoteAppPortlet existingRemoteAppPortlet =
			_remoteAppPortlets.putIfAbsent(remoteAppEntryId, remoteAppPortlet);

		if (existingRemoteAppPortlet != null) {
			throw new IllegalStateException(
				"Remote app entry " + remoteAppEntryId +
					" is already registered");
		}

		remoteAppPortlet.register(_bundleContext);

		if (_log.isInfoEnabled()) {
			_log.info("Started remote app entry " + remoteAppPortlet.getName());
		}
	}

	private void _unregisterPortlet(long remoteAppEntryId) {
		RemoteAppPortlet remoteAppPortlet = _remoteAppPortlets.remove(
			remoteAppEntryId);

		if (remoteAppPortlet != null) {
			remoteAppPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Stopped remote app entry " + remoteAppPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, RemoteAppPortlet> _remoteAppPortlets =
		new ConcurrentHashMap<>();

}