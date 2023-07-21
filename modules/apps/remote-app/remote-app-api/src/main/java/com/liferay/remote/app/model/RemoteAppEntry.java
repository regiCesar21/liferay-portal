/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.remote.app.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RemoteAppEntry service. Represents a row in the &quot;RemoteAppEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.remote.app.model.impl.RemoteAppEntryImpl")
@ProviderType
public interface RemoteAppEntry extends PersistedModel, RemoteAppEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.remote.app.model.impl.RemoteAppEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RemoteAppEntry, Long>
		REMOTE_APP_ENTRY_ID_ACCESSOR = new Accessor<RemoteAppEntry, Long>() {

			@Override
			public Long get(RemoteAppEntry remoteAppEntry) {
				return remoteAppEntry.getRemoteAppEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RemoteAppEntry> getTypeClass() {
				return RemoteAppEntry.class;
			}

		};

}