/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.entity.Individual;
import com.liferay.osb.asah.common.repository.IndividualRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Marcellus Tavares
 */
@Repository
public class IndividualRepositoryImpl implements IndividualRepository {

	public IndividualRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public long countIndividuals(
		@Nullable Long channelId, @Nullable String query,
		@Nullable Long segmentId) {

		SelectConditionStep<Record1<Integer>> selectConditionStep =
			_getReportIndividualsSelectConditionStep(
				channelId, null, query, segmentId, _dslContext.selectCount());

		return selectConditionStep.fetchOptional(
			0, Long.class
		).orElse(
			0L
		);
	}

	@Override
	public Optional<Individual> findIndividualById(String id) {
		SelectConditionStep<Record> selectConditionStep =
			_getReportIndividualsSelectConditionStep(
				null, id, null, null,
				_dslContext.select(
					DSL.field(
						"Individual.fields"
					).as(
						"fields"
					),
					DSL.field(
						"Individual.id"
					).as(
						"id"
					),
					DSL.field(
						"Membership.segmentIds"
					).as(
						"segmentIds"
					)));

		return selectConditionStep.fetchOptional(
			record -> new Individual(record.intoMap()));
	}

	@Override
	public List<Individual> searchIndividuals(
		@Nullable Long channelId, Pageable pageable, @Nullable String query,
		@Nullable Long segmentId) {

		SelectConditionStep<Record> selectConditionStep =
			_getReportIndividualsSelectConditionStep(
				channelId, null, query, segmentId,
				_dslContext.select(
					DSL.field(
						"Individual.fields"
					).as(
						"fields"
					),
					DSL.field(
						"Individual.id"
					).as(
						"id"
					),
					DSL.field(
						"Membership.segmentIds"
					).as(
						"segmentIds"
					)));

		return selectConditionStep.limit(
			pageable.getPageSize()
		).offset(
			pageable.getOffset()
		).fetch(
			record -> new Individual(record.intoMap())
		);
	}

	private Condition _getChannelIdCondition(Long channelId) {
		return DSL.exists(
			DSL.selectOne(
			).from(
				DSL.table("IndividualActivity")
			).where(
				DSL.and(
					DSL.field(
						"IndividualActivity.channelId"
					).eq(
						channelId
					),
					DSL.field(
						"IndividualActivity.individualId"
					).eq(
						DSL.field("Individual.id")
					))
			));
	}

	private Condition _getIndividualSegmentIdCondition(
		Long individualSegmentId) {

		return DSL.exists(
			DSL.selectOne(
			).from(
				DSL.table("IndividualSegment")
			).where(
				DSL.and(
					DSL.field(
						"IndividualSegment.segmentId"
					).eq(
						individualSegmentId
					),
					DSL.field(
						"IndividualSegment.individualId"
					).eq(
						DSL.field("Individual.id")
					))
			));
	}

	private Condition _getQueryCondition(String query) {
		return _getQueryCondition(query, _SEARCH_COLUMNS);
	}

	private Condition _getQueryCondition(String query, String[] searchColumns) {
		if (StringUtils.isEmpty(query)) {
			return DSL.noCondition();
		}

		List<Condition> conditions = new ArrayList<>();

		for (String word : StringUtils.split(query)) {
			List<Condition> wordConditions = new ArrayList<>();

			for (String column : searchColumns) {
				wordConditions.add(
					DSL.field(
						"fields->>'" + column + "'", String.class
					).contains(
						word
					));
			}

			conditions.add(DSL.or(wordConditions));
		}

		return DSL.and(conditions);
	}

	private SelectConditionStep _getReportIndividualsSelectConditionStep(
		@Nullable Long channelId, @Nullable String id, @Nullable String query,
		@Nullable Long segmentId, SelectSelectStep selectSelectStep) {

		List<Condition> conditions = new ArrayList<>();
		Condition membershipCondition = DSL.noCondition();

		conditions.add(_getQueryCondition(query));
		conditions.add(
			DSL.or(
				DSL.field(
					"Individual.suppressed"
				).isNull(),
				DSL.field(
					"Individual.suppressed"
				).notEqual(
					Boolean.TRUE
				)));

		if (channelId != null) {
			conditions.add(_getChannelIdCondition(channelId));

			membershipCondition = DSL.field(
				"channelId"
			).eq(
				channelId
			);
		}

		if (StringUtils.isNotBlank(id)) {
			conditions.add(
				DSL.field(
					"Individual.id"
				).eq(
					id
				));
		}

		if (segmentId != null) {
			conditions.add(_getIndividualSegmentIdCondition(segmentId));
		}

		return selectSelectStep.from(
			DSL.table("Individual")
		).leftJoin(
			_dslContext.select(
				DSL.field("individualId"),
				DSL.field(
					"ARRAY_AGG(DISTINCT segmentId) FILTER (WHERE segmentId " +
						"IS NOT NULL)"
				).as(
					"segmentIds"
				)
			).from(
				DSL.table("IndividualSegment")
			).where(
				membershipCondition
			).groupBy(
				DSL.field("individualId")
			).asTable(
				"Membership"
			)
		).on(
			DSL.field(
				"Individual.id"
			).eq(
				DSL.field("Membership.individualId")
			)
		).where(
			conditions
		);
	}

	private static final String[] _SEARCH_COLUMNS = {
		"emailAddress", "firstName", "jobTitle", "lastName", "middleName"
	};

	private final DSLContext _dslContext;

}