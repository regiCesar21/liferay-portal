/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository;

import com.liferay.osb.asah.common.entity.ProjectFeature;
import com.liferay.osb.asah.common.model.Feature;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author Marcellus Tavares
 */
public interface ProjectFeatureRepository
	extends Repository<ProjectFeature, Long> {

	@Cacheable
	public List<ProjectFeature> findByEnabledAndProjectId(
		Boolean enabled, String projectId);

	@Cacheable
	public Optional<ProjectFeature> findByFeatureAndProjectId(
		Feature feature, String projectId);

}