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
 * The extended model interface for the AppBuilderAppDataRecordLink service. Represents a row in the &quot;AppBuilderAppDataRecordLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkImpl"
)
@ProviderType
public interface AppBuilderAppDataRecordLink
	extends AppBuilderAppDataRecordLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AppBuilderAppDataRecordLink, Long>
		APP_BUILDER_APP_DATA_RECORD_LINK_ID_ACCESSOR =
			new Accessor<AppBuilderAppDataRecordLink, Long>() {

				@Override
				public Long get(
					AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

					return appBuilderAppDataRecordLink.
						getAppBuilderAppDataRecordLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AppBuilderAppDataRecordLink> getTypeClass() {
					return AppBuilderAppDataRecordLink.class;
				}

			};

}