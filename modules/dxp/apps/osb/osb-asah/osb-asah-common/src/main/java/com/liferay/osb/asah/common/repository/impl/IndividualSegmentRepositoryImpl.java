/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.repository.IndividualSegmentRepository;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import org.springframework.stereotype.Repository;

/**
 * @author Marcellus Tavares
 */
@Repository
public class IndividualSegmentRepositoryImpl
	implements IndividualSegmentRepository {

	public IndividualSegmentRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public List<Long> findSegmentIdByIndividualId(String individualId) {
		return _dslContext.select(
			DSL.field("segmentId")
		).from(
			"IndividualSegment"
		).where(
			DSL.field(
				"individualId"
			).eq(
				individualId
			)
		).fetchInto(
			Long.class
		);
	}

	private final DSLContext _dslContext;

}