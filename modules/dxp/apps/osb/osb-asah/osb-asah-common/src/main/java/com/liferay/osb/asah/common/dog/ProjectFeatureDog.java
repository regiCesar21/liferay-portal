/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.dog;

import com.liferay.osb.asah.common.entity.ProjectFeature;
import com.liferay.osb.asah.common.model.Feature;
import com.liferay.osb.asah.common.repository.ProjectFeatureRepository;
import com.liferay.osb.asah.common.util.ProjectIdThreadLocal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Marcellus Tavares
 */
@Component
public class ProjectFeatureDog {

	public List<Feature> getEnabledFeatures(String projectId) {
		try {
			ProjectIdThreadLocal.setGlobalContext(true);

			List<ProjectFeature> projectFeatures =
				_projectFeatureRepository.findByEnabledAndProjectId(
					Boolean.TRUE, projectId);

			Stream<ProjectFeature> stream = projectFeatures.stream();

			return stream.map(
				ProjectFeature::getFeature
			).collect(
				Collectors.toUnmodifiableList()
			);
		}
		finally {
			ProjectIdThreadLocal.setGlobalContext(false);
		}
	}

	public boolean isFeatureEnabled(Feature feature, String projectId) {
		try {
			ProjectIdThreadLocal.setGlobalContext(true);

			Optional<ProjectFeature> projectFeatureOptional =
				_projectFeatureRepository.findByFeatureAndProjectId(
					feature, projectId);

			return projectFeatureOptional.map(
				ProjectFeature::getEnabled
			).orElse(
				Boolean.FALSE
			);
		}
		finally {
			ProjectIdThreadLocal.setGlobalContext(false);
		}
	}

	@Autowired
	private ProjectFeatureRepository _projectFeatureRepository;

}