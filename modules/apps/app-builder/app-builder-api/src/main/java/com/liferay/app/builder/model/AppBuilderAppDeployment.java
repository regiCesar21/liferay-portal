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
 * The extended model interface for the AppBuilderAppDeployment service. Represents a row in the &quot;AppBuilderAppDeployment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeploymentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.app.builder.model.impl.AppBuilderAppDeploymentImpl"
)
@ProviderType
public interface AppBuilderAppDeployment
	extends AppBuilderAppDeploymentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.app.builder.model.impl.AppBuilderAppDeploymentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AppBuilderAppDeployment, Long>
		APP_BUILDER_APP_DEPLOYMENT_ID_ACCESSOR =
			new Accessor<AppBuilderAppDeployment, Long>() {

				@Override
				public Long get(
					AppBuilderAppDeployment appBuilderAppDeployment) {

					return appBuilderAppDeployment.
						getAppBuilderAppDeploymentId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AppBuilderAppDeployment> getTypeClass() {
					return AppBuilderAppDeployment.class;
				}

			};

}