/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.repository.impl;

import com.liferay.osb.asah.common.entity.IndividualActivity;
import com.liferay.osb.asah.common.repository.EventDefinitionRepository;
import com.liferay.osb.asah.common.repository.IndividualActivityRepository;
import com.liferay.osb.asah.common.repository.helper.DSLHelper;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Rachael Koestartyo
 */
@Repository
public class IndividualActivityRepositoryImpl
	extends BaseRepository implements IndividualActivityRepository {

	public IndividualActivityRepositoryImpl(DSLContext dslContext) {
		_dslContext = dslContext;
	}

	@Override
	public long countIndividualActivities(
		@Nullable Long channelId, String individualId,
		LocalDateTime rangeEndLocalDateTime,
		LocalDateTime rangeStartLocalDateTime, String timeZoneId) {

		return _dslContext.selectCount(
		).from(
			DSL.table("IndividualActivity")
		).where(
			_getConditions(
				channelId, individualId, rangeEndLocalDateTime,
				rangeStartLocalDateTime, timeZoneId)
		).fetchOptional(
			0, Long.class
		).orElse(
			0L
		);
	}

	@Override
	public List<IndividualActivity> searchIndividualActivities(
		@Nullable Long channelId, String individualId, Pageable pageable,
		LocalDateTime rangeEndLocalDateTime,
		LocalDateTime rangeStartLocalDateTime, String timeZoneId) {

		Table<Record> individualActivityTable = DSL.table("IndividualActivity");

		return _dslContext.select(
			DSL.asterisk()
		).from(
			individualActivityTable
		).where(
			_getConditions(
				channelId, individualId, rangeEndLocalDateTime,
				rangeStartLocalDateTime, timeZoneId)
		).orderBy(
			getSortFields(pageable.getSort(), individualActivityTable)
		).limit(
			pageable.getPageSize()
		).offset(
			pageable.getOffset()
		).fetch(
			record -> new IndividualActivity(record.intoMap())
		);
	}

	private List<Condition> _getConditions(
		@Nullable Long channelId, String individualId,
		LocalDateTime rangeEndLocalDateTime,
		LocalDateTime rangeStartLocalDateTime, String timeZoneId) {

		List<Condition> conditions = new ArrayList<Condition>() {
			{
				add(
					DSL.field(
						"applicationId"
					).in(
						_eventDefinitionRepository.
							getEventDefinitionApplicationIds(false)
					));
				add(
					DSL.field(
						"eventDate"
					).between(
						_dslHelper.getDateParam(
							rangeStartLocalDateTime, timeZoneId),
						_dslHelper.getDateParam(
							rangeEndLocalDateTime, timeZoneId)
					));
				add(
					DSL.field(
						"eventId"
					).in(
						_eventDefinitionRepository.getEventDefinitionNames(
							false)
					));
			}
		};

		if (channelId != null) {
			conditions.add(
				DSL.field(
					"channelId"
				).eq(
					channelId
				));
		}

		conditions.add(
			DSL.field(
				"individualId"
			).eq(
				individualId
			));

		return conditions;
	}

	private final DSLContext _dslContext;

	@Autowired
	private DSLHelper _dslHelper;

	@Autowired
	private EventDefinitionRepository _eventDefinitionRepository;

}