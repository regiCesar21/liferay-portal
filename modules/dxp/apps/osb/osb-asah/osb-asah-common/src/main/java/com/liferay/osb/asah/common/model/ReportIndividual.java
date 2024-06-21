/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import com.liferay.osb.asah.common.entity.BQIndividual;

import java.util.Objects;
import java.util.Set;

/**
 * @author Leslie Wong
 */
public class ReportIndividual extends BaseIndividual {

	public ReportIndividual() {
	}

	public ReportIndividual(BQIndividual bqIndividual, Set<Long> segmentIds) {
		super(bqIndividual);

		_segmentIds = segmentIds;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReportIndividual)) {
			return false;
		}

		ReportIndividual reportIndividual = (ReportIndividual)obj;

		if (Objects.equals(id, reportIndividual.id) &&
			Objects.equals(_segmentIds, reportIndividual._segmentIds)) {

			return true;
		}

		return false;
	}

	public Set<Long> getSegmentIds() {
		return _segmentIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, _segmentIds);
	}

	public void setSegmentIds(Set<Long> segmentIds) {
		_segmentIds = segmentIds;
	}

	private Set<Long> _segmentIds;

}