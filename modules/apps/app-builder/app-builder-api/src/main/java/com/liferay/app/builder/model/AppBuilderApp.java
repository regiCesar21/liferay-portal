/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AppBuilderApp service. Represents a row in the &quot;AppBuilderApp&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppModel
 * @generated
 */
@ImplementationClassName("com.liferay.app.builder.model.impl.AppBuilderAppImpl")
@ProviderType
public interface AppBuilderApp extends AppBuilderAppModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.app.builder.model.impl.AppBuilderAppImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AppBuilderApp, Long>
		APP_BUILDER_APP_ID_ACCESSOR = new Accessor<AppBuilderApp, Long>() {

			@Override
			public Long get(AppBuilderApp appBuilderApp) {
				return appBuilderApp.getAppBuilderAppId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AppBuilderApp> getTypeClass() {
				return AppBuilderApp.class;
			}

		};

}