/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository;

import com.liferay.osb.asah.common.model.ReportIndividual;

/**
 * @author Marcellus Tavares
 */
public interface ReportIndividualRepository
	extends BigQueryRepository<ReportIndividual, String>,
			CustomReportIndividualRepository {
}