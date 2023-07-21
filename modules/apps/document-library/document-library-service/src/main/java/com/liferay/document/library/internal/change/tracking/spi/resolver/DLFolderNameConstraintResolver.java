/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class DLFolderNameConstraintResolver
	implements ConstraintResolver<DLFolder> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-folder-name";
	}

	@Override
	public Class<DLFolder> getModelClass() {
		return DLFolder.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "rename-the-folder-in-the-publication";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, "com.liferay.document.library.service");
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"groupId", "parentFolderId", "name"};
	}

	@Override
	public void resolveConflict(
		ConstraintResolverContext<DLFolder> constraintResolverContext) {
	}

}