/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.repository.impl;

import com.liferay.osb.asah.backend.model.AdjacentPageViewsMetric;
import com.liferay.osb.asah.backend.repository.PagePathRepository;
import com.liferay.osb.asah.common.model.TimeRange;
import com.liferay.osb.asah.common.repository.executor.QueryExecutor;
import com.liferay.osb.asah.common.repository.helper.DSLHelper;

import java.math.BigDecimal;

import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.SelectHavingStep;
import org.jooq.SelectOrderByStep;
import org.jooq.WithStep;
import org.jooq.impl.DSL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Marcellus Tavares
 */
@Repository
public class PagePathRepositoryImpl implements PagePathRepository {

	@Override
	public Set<AdjacentPageViewsMetric> getAdjacentPagesViewsMetric(
		String canonicalUrl, @Nullable Long channelId, @Nullable Long segmentId,
		TimeRange timeRange, @Nullable String title, ZoneId zoneId) {

		return _queryExecutor.queryForSet(
			AdjacentPageViewsMetric::new,
			_dslContext.with(
				_getPagePathCTE(channelId, segmentId, timeRange, zoneId, "")
			).with(
				_getFollowingPagesCTE(canonicalUrl, title)
			).with(
				_getTopFollowingPagesCTE()
			).with(
				_getPreviousPagesCTE(canonicalUrl, title, "")
			).with(
				_getTopPreviousPagesCTE("")
			).select(
				_canonicalUrlField, _eventDateField, _externalField,
				_previousField, _titleField, _viewsField
			).from(
				"TopFollowingPages"
			).unionAll(
				_dslContext.select(
					_canonicalUrlField, _eventDateField, _externalField,
					_previousField, _titleField, _viewsField
				).from(
					"TopPreviousPages"
				)
			).unionAll(
				_dslContext.select(
					_canonicalUrlField,
					DSL.max(
						_eventDateField
					).as(
						_eventDateField
					),
					DSL.val(
						Boolean.TRUE
					).as(
						"external"
					),
					DSL.val(
						Boolean.TRUE
					).as(
						"previous"
					),
					_titleField,
					DSL.sum(
						_viewsField
					).as(
						"views"
					)
				).from(
					"PreviousPages"
				).where(
					_canonicalUrlField.eq("direct")
				).groupBy(
					_canonicalUrlField, _previousField, _titleField
				)
			));
	}

	@Override
	public Set<AdjacentPageViewsMetric> getPreviousAdjacentPagesViewsMetric(
		String canonicalUrl, @Nullable Long channelId, List<Long> segmentIds,
		TimeRange timeRange, @Nullable String title, ZoneId zoneId) {

		if (segmentIds.isEmpty()) {
			return Collections.emptySet();
		}

		WithStep withStep = _dslContext.with();

		for (Long segmentId : segmentIds) {
			withStep = withStep.with(
				_getPagePathCTE(
					channelId, segmentId, timeRange, zoneId,
					String.valueOf(segmentId))
			).with(
				_getPreviousPagesCTE(
					canonicalUrl, title, String.valueOf(segmentId))
			).with(
				_getTopPreviousPagesCTE(String.valueOf(segmentId))
			);
		}

		SelectOrderByStep
			<Record6<String, Boolean, Boolean, String, BigDecimal, Long>>
				selectOrderByStep = null;

		for (int i = 0; i < segmentIds.size(); i++) {
			Long segmentId = segmentIds.get(i);

			if (i == 0) {
				selectOrderByStep = withStep.select(
					_canonicalUrlField, _externalField, _previousField,
					_titleField, _viewsField,
					DSL.val(
						segmentId
					).as(
						"segmentId"
					)
				).from(
					"TopPreviousPages" + segmentId
				).unionAll(
					_getPreviousPagesSelect(segmentId)
				);
			}
			else {
				selectOrderByStep = selectOrderByStep.unionAll(
					_dslContext.select(
						_canonicalUrlField, _externalField, _previousField,
						_titleField, _viewsField,
						DSL.val(
							segmentId
						).as(
							"segmentId"
						)
					).from(
						"TopPreviousPages" + segmentId
					)
				).unionAll(
					_getPreviousPagesSelect(segmentId)
				);
			}
		}

		return _queryExecutor.queryForSet(
			AdjacentPageViewsMetric::new, selectOrderByStep);
	}

	private CommonTableExpression<?> _getFollowingPagesCTE(
		String canonicalUrl, @Nullable String title) {

		Condition condition = DSL.field(
			"previousCanonicalUrl"
		).eq(
			canonicalUrl
		);

		if (StringUtils.isNotBlank(title)) {
			condition = condition.and(
				DSL.field(
					"previousTitle"
				).eq(
					title
				));
		}

		return DSL.name(
			"FollowingPages"
		).as(
			_dslContext.select(
				_canonicalUrlField, _eventDateField, _titleField,
				DSL.val(
					1
				).as(
					"views"
				)
			).from(
				"PagePath"
			).where(
				condition
			)
		);
	}

	private CommonTableExpression<?> _getPagePathCTE(
		Long channelId, @Nullable Long segmentId, TimeRange timeRange,
		ZoneId zoneId, String nameSuffix) {

		List<Condition> conditions = new ArrayList<>();

		conditions.add(
			DSL.field(
				"applicationId"
			).eq(
				"Page"
			));
		conditions.add(
			DSL.field(
				"channelId"
			).eq(
				channelId
			));
		conditions.add(
			DSL.field(
				"eventId"
			).eq(
				"pageViewed"
			));
		conditions.add(
			DSL.field(
				"eventDate"
			).between(
				_dslHelper.getDateParam(
					timeRange.getStartLocalDateTime(), zoneId.toString()),
				_dslHelper.getDateParam(
					timeRange.getEndLocalDateTime(), zoneId.toString())
			));

		if (segmentId != null) {
			conditions.add(
				DSL.field(
					"userId"
				).in(
					_dslContext.select(
						DSL.field("identityId")
					).from(
						"BQMembership"
					).where(
						DSL.field(
							"segmentId"
						).eq(
							segmentId
						)
					)
				));
		}

		return DSL.name(
			"PagePath" + nameSuffix
		).as(
			_dslContext.select(
				DSL.field("canonicalUrl"), DSL.field("channelId"),
				_eventDateField,
				DSL.coalesce(
					DSL.lag(
						DSL.field("canonicalUrl")
					).over(
						DSL.partitionBy(
							DSL.field("channelId"), DSL.field("sessionId"),
							DSL.field("userId")
						).orderBy(
							DSL.field("eventDate")
						)
					),
					DSL.nullif(DSL.field("referrer"), "")
				).as(
					"previousCanonicalUrl"
				),
				DSL.coalesce(
					DSL.lag(
						DSL.field("title")
					).over(
						DSL.partitionBy(
							DSL.field("channelId"), DSL.field("sessionId"),
							DSL.field("userId")
						).orderBy(
							DSL.field("eventDate")
						)
					),
					DSL.nullif(DSL.field("referrer"), "")
				).as(
					"previousTitle"
				),
				DSL.field("title"), DSL.field("userId")
			).from(
				"BQEvent"
			).where(
				conditions
			)
		);
	}

	private CommonTableExpression<?> _getPreviousPagesCTE(
		String canonicalUrl, @Nullable String title, String nameSuffix) {

		Condition condition = DSL.field(
			"canonicalUrl"
		).eq(
			canonicalUrl
		);

		if (StringUtils.isNotBlank(title)) {
			condition = condition.and(
				DSL.field(
					"title"
				).eq(
					title
				));
		}

		return DSL.name(
			"PreviousPages" + nameSuffix
		).as(
			_dslContext.select(
				DSL.coalesce(
					DSL.field("previousCanonicalUrl"), "direct"
				).as(
					"canonicalUrl"
				),
				_eventDateField,
				DSL.coalesce(
					DSL.field("previousTitle"), "direct"
				).as(
					"title"
				),
				DSL.val(
					1
				).as(
					"views"
				)
			).from(
				"PagePath" + nameSuffix
			).where(
				condition
			)
		);
	}

	private SelectHavingStep
		<Record6<String, Boolean, Boolean, String, BigDecimal, Long>>
			_getPreviousPagesSelect(Long segmentId) {

		return _dslContext.select(
			_canonicalUrlField,
			DSL.val(
				Boolean.TRUE
			).as(
				"external"
			),
			DSL.val(
				Boolean.TRUE
			).as(
				"previous"
			),
			_titleField,
			DSL.sum(
				_viewsField
			).as(
				"views"
			),
			DSL.val(
				segmentId
			).as(
				"segmentId"
			)
		).from(
			"PreviousPages" + segmentId
		).where(
			_canonicalUrlField.eq("direct")
		).groupBy(
			_canonicalUrlField, _previousField, _titleField
		);
	}

	private CommonTableExpression<?> _getTopFollowingPagesCTE() {
		return DSL.name(
			"TopFollowingPages"
		).as(
			_dslContext.select(
				DSL.when(
					DSL.field(
						"rowNumber", Long.class
					).greaterThan(
						3L
					),
					DSL.val("others")
				).otherwise(
					_canonicalUrlField
				).as(
					"canonicalUrl"
				),
				DSL.max(
					_eventDateField
				).as(
					_eventDateField
				),
				DSL.max(
					DSL.when(
						DSL.field(
							"rowNumber", Long.class
						).greaterThan(
							3L
						),
						Boolean.TRUE
					).otherwise(
						Boolean.FALSE
					)
				).as(
					"external"
				),
				DSL.when(
					DSL.field(
						"rowNumber", Long.class
					).greaterThan(
						3L
					),
					DSL.val("others")
				).otherwise(
					_titleField
				).as(
					"title"
				),
				DSL.sum(
					_viewsField
				).as(
					"views"
				),
				DSL.val(
					Boolean.FALSE
				).as(
					"previous"
				)
			).from(
				_dslContext.select(
					_canonicalUrlField,
					DSL.max(
						_eventDateField
					).as(
						_eventDateField
					),
					_titleField,
					DSL.sum(
						_viewsField
					).as(
						"views"
					),
					DSL.rowNumber(
					).over(
						DSL.orderBy(
							DSL.sum(
								_viewsField
							).desc(),
							DSL.max(
								_eventDateField
							).desc())
					).as(
						"rowNumber"
					)
				).from(
					"FollowingPages"
				).groupBy(
					_canonicalUrlField, _titleField
				)
			).groupBy(
				_canonicalUrlField, _titleField
			)
		);
	}

	private CommonTableExpression<?> _getTopPreviousPagesCTE(
		String nameSuffix) {

		return DSL.name(
			"TopPreviousPages" + nameSuffix
		).as(
			_dslContext.select(
				DSL.when(
					DSL.field(
						"rowNumber", Long.class
					).greaterThan(
						3L
					),
					DSL.val("others")
				).otherwise(
					_canonicalUrlField
				).as(
					"canonicalUrl"
				),
				DSL.max(
					_eventDateField
				).as(
					_eventDateField
				),
				DSL.max(
					DSL.when(
						DSL.or(
							DSL.field(
								"trackedCanonicalUrl"
							).isNull(),
							DSL.field(
								"rowNumber", Long.class
							).greaterThan(
								3L
							)),
						DSL.value(true)
					).otherwise(
						DSL.value(false)
					)
				).as(
					"external"
				),
				DSL.when(
					DSL.field(
						"rowNumber", Long.class
					).greaterThan(
						3L
					),
					DSL.val("others")
				).otherwise(
					_titleField
				).as(
					"title"
				),
				DSL.sum(
					_viewsField
				).as(
					"views"
				),
				DSL.val(
					Boolean.TRUE
				).as(
					"previous"
				)
			).from(
				_dslContext.select(
					_canonicalUrlField,
					DSL.max(
						_eventDateField
					).as(
						_eventDateField
					),
					_titleField,
					DSL.sum(
						_viewsField
					).as(
						"views"
					),
					DSL.rowNumber(
					).over(
						DSL.orderBy(
							DSL.sum(
								_viewsField
							).desc(),
							DSL.max(
								_eventDateField
							).desc())
					).as(
						"rowNumber"
					)
				).from(
					"PreviousPages" + nameSuffix
				).where(
					_canonicalUrlField.notEqual("direct")
				).groupBy(
					_canonicalUrlField, _titleField
				)
			).leftJoin(
				DSL.selectDistinct(
					DSL.field(
						"canonicalUrl"
					).as(
						"trackedCanonicalUrl"
					)
				).from(
					"PagePath" + nameSuffix
				)
			).on(
				DSL.field(
					"trackedCanonicalUrl"
				).eq(
					DSL.field("canonicalUrl")
				)
			).groupBy(
				_canonicalUrlField, _titleField
			)
		);
	}

	private final Field<String> _canonicalUrlField = DSL.field(
		"canonicalUrl", String.class);

	@Autowired
	private DSLContext _dslContext;

	@Autowired
	private DSLHelper _dslHelper;

	private final Field<Date> _eventDateField = DSL.field(
		"eventDate", Date.class);
	private final Field<Boolean> _externalField = DSL.field(
		"external", Boolean.class);
	private final Field<Boolean> _previousField = DSL.field(
		"previous", Boolean.class);

	@Autowired
	private QueryExecutor _queryExecutor;

	private final Field<String> _titleField = DSL.field("title", String.class);
	private final Field<BigDecimal> _viewsField = DSL.field(
		"views", BigDecimal.class);

}