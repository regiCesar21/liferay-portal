/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.entity.IndividualInterest;
import com.liferay.osb.asah.common.repository.IndividualInterestRepository;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Rachael Koestartyo
 */
@Repository
public class IndividualInterestRepositoryImpl
	implements IndividualInterestRepository {

	public IndividualInterestRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public long countByChannelIdAndIndividualId(
		@Nullable Long channelId, String individualId) {

		SelectSelectStep<Record1<Integer>> selectSelectStep =
			_dslContext.selectCount();

		return selectSelectStep.from(
			DSL.table("IndividualInterest")
		).join(
			DSL.table("Individual")
		).on(
			DSL.field(
				"IndividualInterest.individualId"
			).eq(
				DSL.field("Individual.id")
			)
		).where(
			_getConditions(channelId, individualId)
		).fetchOptional(
			0, Long.class
		).orElse(
			0L
		);
	}

	@Override
	public List<IndividualInterest> findByChannelIdAndIndividualId(
		@Nullable Long channelId, String individualId, Pageable pageable) {

		return _dslContext.selectDistinct(
			DSL.field(
				"IndividualInterest.channelId"
			).as(
				"channelId"
			),
			DSL.field(
				"IndividualInterest.identityId"
			).as(
				"identityId"
			),
			DSL.field(
				"IndividualInterest.interestScore"
			).as(
				"interestScore"
			),
			DSL.field(
				"IndividualInterest.interested"
			).as(
				"interested"
			),
			_getKeywordField("IndividualInterest.keyword"),
			DSL.field(
				"IndividualInterest.recordedDate"
			).as(
				"recordedDate"
			)
		).from(
			DSL.table("IndividualInterest")
		).join(
			DSL.table("Individual")
		).on(
			DSL.field(
				"IndividualInterest.individualId"
			).eq(
				DSL.field("Individual.id")
			)
		).where(
			_getConditions(channelId, individualId)
		).limit(
			pageable.getPageSize()
		).offset(
			pageable.getOffset()
		).fetch(
			record -> new IndividualInterest(record.intoMap())
		);
	}

	private List<Condition> _getConditions(
		Long channelId, String individualId) {

		List<Condition> conditions = new ArrayList<>();

		if (channelId != null) {
			conditions.add(
				DSL.field(
					"IndividualInterest.channelId", Long.class
				).eq(
					channelId
				));
		}

		conditions.add(
			DSL.and(
				DSL.field(
					"IndividualInterest.individualId"
				).eq(
					individualId
				),
				DSL.or(
					DSL.field(
						"Individual.suppressed"
					).isNull(),
					DSL.field(
						"Individual.suppressed"
					).notEqual(
						Boolean.TRUE
					))));

		conditions.add(
			DSL.field(
				"recordedDate"
			).eq(
				_getMaxRecordedDateSelect(channelId)
			));

		return conditions;
	}

	private Field<String> _getKeywordField(String fieldName) {
		return DSL.lower(
			DSL.trim(DSL.replace(DSL.field(fieldName, String.class), "\n", ""))
		).as(
			"keyword"
		);
	}

	private Select<Record1<Object>> _getMaxRecordedDateSelect(Long channelId) {
		SelectJoinStep<Record1<Object>> selectJoinStep = _dslContext.select(
			DSL.max(DSL.field("recordedDate"))
		).from(
			"IndividualInterest"
		);

		if (channelId == null) {
			return selectJoinStep;
		}

		return selectJoinStep.where(
			DSL.field(
				"channelId"
			).eq(
				channelId
			));
	}

	private final DSLContext _dslContext;

}