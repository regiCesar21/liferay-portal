/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.entity.BQIndividual;
import com.liferay.osb.asah.common.model.ReportIndividual;
import com.liferay.osb.asah.common.repository.CustomReportIndividualRepository;
import com.liferay.osb.asah.common.repository.executor.QueryExecutor;
import com.liferay.osb.asah.common.util.SetUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

/**
 * @author Marcellus Tavares
 */
public class ReportIndividualRepositoryImpl
	extends BaseRepository implements CustomReportIndividualRepository {

	public ReportIndividualRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public long countReportIndividuals(
		@Nullable Long channelId, @Nullable String query,
		@Nullable Long segmentId) {

		return _queryExecutor.queryForLong(
			_getReportIndividualsSelectConditionStep(
				channelId, null, query, segmentId, _dslContext.selectCount()));
	}

	@Override
	public Optional<ReportIndividual> findReportIndividualById(String id) {
		SelectConditionStep selectConditionStep =
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

		return _queryExecutor.queryForObject(
			record -> {
				Object object = record.get("segmentIds");

				Set<Long> segmentIds = new HashSet<>();

				if (object instanceof List) {
					segmentIds = SetUtil.map(
						(List<BigDecimal>)object, BigDecimal::longValue);
				}

				return new ReportIndividual(
					new BQIndividual(record), segmentIds);
			},
			selectConditionStep);
	}

	@Override
	public List<ReportIndividual> searchReportIndividuals(
		@Nullable Long channelId, Pageable pageable, @Nullable String query,
		@Nullable Long segmentId) {

		SelectConditionStep selectConditionStep =
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

		return _queryExecutor.queryForList(
			record -> {
				Object object = record.get("segmentIds");

				Set<Long> segmentIds = new HashSet<>();

				if (object instanceof List) {
					segmentIds = SetUtil.map(
						(List<BigDecimal>)object, BigDecimal::longValue);
				}

				return new ReportIndividual(
					new BQIndividual(record), segmentIds);
			},
			selectConditionStep.limit(
				pageable.getPageSize()
			).offset(
				pageable.getOffset()
			));
	}

	private Condition _getChannelIdCondition(Long channelId) {
		return DSL.exists(
			DSL.selectOne(
			).from(
				DSL.table(
					"BQIdentityActivity"
				).as(
					"IdentityActivity"
				)
			).where(
				DSL.and(
					DSL.field(
						"IdentityActivity.channelId"
					).eq(
						channelId
					),
					DSL.field(
						"IdentityActivity.individualId"
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
				DSL.table(
					"BQMembership"
				).as(
					"Membership"
				)
			).where(
				DSL.and(
					DSL.field(
						"Membership.segmentId"
					).eq(
						individualSegmentId
					),
					DSL.field(
						"Membership.individualId"
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
					DSL.lower(
						DSL.field(column, String.class)
					).like(
						DSL.lower(StringUtils.wrap(word, "%"))
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
			DSL.table(
				"BQIndividual"
			).as(
				"Individual"
			)
		).leftJoin(
			_dslContext.select(
				DSL.field("individualId"),
				DSL.field(
					"ARRAY_AGG(DISTINCT segmentId IGNORE NULLS)"
				).as(
					"segmentIds"
				)
			).from(
				DSL.table("BQMembership")
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

	@Autowired
	private QueryExecutor _queryExecutor;

}