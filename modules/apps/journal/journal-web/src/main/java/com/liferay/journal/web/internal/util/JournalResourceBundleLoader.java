/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.util;

import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;

/**
 * @author Adolfo Pérez
 */
public class JournalResourceBundleLoader extends ClassResourceBundleLoader {

	public static final ResourceBundleLoader INSTANCE =
		new AggregateResourceBundleLoader(
			new JournalResourceBundleLoader(),
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());

	protected JournalResourceBundleLoader() {
		super("content.Language", JournalResourceBundleLoader.class);
	}

}