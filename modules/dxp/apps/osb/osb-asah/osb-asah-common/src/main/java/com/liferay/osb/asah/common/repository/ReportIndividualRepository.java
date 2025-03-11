/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository;

import com.liferay.osb.asah.common.model.ReportIndividual;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public interface ReportIndividualRepository
	extends Repository<ReportIndividual, String> {

	public long countIndividuals(
		@Nullable Long channelId, @Nullable String query,
		@Nullable Long segmentId);

	public Optional<ReportIndividual> findIndividualById(String id);

	public List<ReportIndividual> searchIndividuals(
		@Nullable Long channelId, Pageable pageable, @Nullable String query,
		@Nullable Long segmentId);

}