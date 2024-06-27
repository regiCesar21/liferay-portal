/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository;

import com.liferay.osb.asah.common.model.ReportIndividual;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public interface CustomReportIndividualRepository {

	public long countReportIndividuals(
		@Nullable Long channelId, @Nullable String query,
		@Nullable Long segmentId);

	public Optional<ReportIndividual> findReportIndividualById(String id);

	public List<ReportIndividual> searchReportIndividuals(
		@Nullable Long channelId, Pageable pageable, @Nullable String query,
		@Nullable Long segmentId);

}