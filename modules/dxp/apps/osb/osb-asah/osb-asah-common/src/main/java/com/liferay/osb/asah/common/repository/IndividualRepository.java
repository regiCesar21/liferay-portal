/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository;

import com.liferay.osb.asah.common.entity.Individual;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public interface IndividualRepository extends Repository<Individual, String> {

	public long countIndividuals(
		@Nullable Long channelId, @Nullable String query,
		@Nullable Long segmentId);

	public Optional<Individual> findIndividualById(String id);

	public List<Individual> searchIndividuals(
		@Nullable Long channelId, Pageable pageable, @Nullable String query,
		@Nullable Long segmentId);

}