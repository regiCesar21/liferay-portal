/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.list.retriever;

import com.liferay.info.pagination.Pagination;

import java.util.Optional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface LayoutListRetrieverContext {

	public Optional<Pagination> getPaginationOptional();

	public Optional<long[]> getSegmentsEntryIdsOptional();

	public Optional<long[]> getSegmentsExperienceIdsOptional();

}