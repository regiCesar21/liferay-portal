/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.repository.ReportIndividualRepository;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.postgresql.jdbc.PgArray;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Marcellus Tavares
 */
@Repository("ReportIndividualRepository")
public class ReportIndividualRepositoryImpl
	implements ReportIndividualRepository {

	public ReportIndividualRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public long countReportIndividuals(
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
	public Optional<ReportIndividual> findReportIndividualById(String id) {
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

		return selectConditionStep.fetchOptional(this::_toReportIndividual);
	}

	@Override
	public List<ReportIndividual> searchReportIndividuals(
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

		return selectConditionStep.orderBy(
			DSL.field("id")
		).limit(
			pageable.getPageSize()
		).offset(
			pageable.getOffset()
		).fetch(
			this::_toReportIndividual
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

	private ReportIndividual _toReportIndividual(Record reportIndividual) {
		PgArray pgArray = (PgArray)reportIndividual.get("segmentIds");

		Set<Long> segmentIds = new HashSet<>();

		if (pgArray != null) {
			try {
				segmentIds = new HashSet<>(
					Arrays.asList((Long[])pgArray.getArray()));
			}
			catch (SQLException sqlException) {
				throw new RuntimeException(sqlException);
			}
		}

		return new ReportIndividual(segmentIds, reportIndividual.intoMap());
	}

	private static final String[] _SEARCH_COLUMNS = {
		"emailAddress", "firstName", "jobTitle", "lastName", "middleName"
	};

	private final DSLContext _dslContext;

}