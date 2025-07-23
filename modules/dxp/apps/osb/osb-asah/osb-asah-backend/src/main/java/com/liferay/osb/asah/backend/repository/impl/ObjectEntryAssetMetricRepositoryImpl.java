/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.backend.repository.impl;

import com.liferay.osb.asah.backend.model.AssetType;
import com.liferay.osb.asah.backend.model.Metric;
import com.liferay.osb.asah.backend.model.ObjectEntryMetric;
import com.liferay.osb.asah.backend.model.ObjectEntryMetricType;
import com.liferay.osb.asah.common.model.MetricType;
import com.liferay.osb.asah.common.model.TimeRange;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.impl.DSL;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * @author Rachael Koestartyo
 */
@Repository("ObjectEntryAssetMetricRepository")
public class ObjectEntryAssetMetricRepositoryImpl
	extends BaseAssetMetricRepository<ObjectEntryMetric> {

	@Override
	public AssetType getAssetType() {
		return AssetType.OBJECT_ENTRY;
	}

	@Override
	public ObjectEntryMetric getObjectEntryMetric(
		Long dataSourceId, String externalReferenceCode, Set<Long> groupIds,
		Set<String> selectedMetrics, TimeRange timeRange) {

		Field<Boolean> previousField = DSL.when(
			DSL.field(
				"eventDate"
			).ge(
				dslHelper.getDateParam(
					timeRange.getStartLocalDateTime(),
					timeZoneDog.getTimeZoneId())
			),
			false
		).otherwise(
			true
		).as(
			"previous"
		);

		SelectSelectStep<Record> selectSelectStep = dslContext.select(
			getMetricFields(selectedMetrics, timeRange)
		).select(
			previousField
		);

		SelectJoinStep<Record> selectJoinStep = getAssetMetricSelectJoinStep(
			selectSelectStep, timeRange);

		SelectConditionStep<Record> selectConditionStep = selectJoinStep.where(
			DSL.and(
				DSL.noCondition(),
				_createWhereClauseCondition(
					dataSourceId, externalReferenceCode, groupIds,
					timeRange.getIncludePreviousTimeRange())));

		List<Map<String, Object>> recordMaps = queryExecutor.queryForList(
			Function.identity(), selectConditionStep.groupBy(previousField));

		return _toObjectEntryMetric(
			dataSourceId, externalReferenceCode, recordMaps, selectedMetrics);
	}

	@Override
	protected ObjectEntryMetric createAssetMetric() {
		return new ObjectEntryMetric();
	}

	@Override
	protected String getAssetIdFieldName() {
		return "externalReferenceCode";
	}

	@Override
	protected Map<String, BiConsumer<ObjectEntryMetric, Metric>>
		getAssetMetricSetters() {

		return new HashMap<>() {
			{
				put(
					ObjectEntryMetricType.DOWNLOADS.getName(),
					ObjectEntryMetric::setDownloadsMetric);
				put(
					ObjectEntryMetricType.IMPRESSIONS.getName(),
					ObjectEntryMetric::setImpressionsMetric);
				put(
					ObjectEntryMetricType.VIEWS.getName(),
					ObjectEntryMetric::setViewsMetric);
			}
		};
	}

	@Override
	protected Field<BigDecimal> getMetricField(
		MetricType metricType, TimeRange timeRange) {

		Field<Long> longField = DSL.field(
			metricType.getFieldName(), Long.class);

		return DSL.sum(longField);
	}

	@Override
	protected MetricType getMetricType(String metricTypeName) {
		return ObjectEntryMetricType.of(metricTypeName);
	}

	@Override
	protected MetricType[] getMetricTypes() {
		return ObjectEntryMetricType.values();
	}

	@Override
	protected String getTableName(TimeRange timeRange) {
		if ((timeRange == TimeRange.LAST_24_HOURS) ||
			(timeRange == TimeRange.YESTERDAY)) {

			return "ObjectEntryHourly";
		}

		return "ObjectEntryDaily";
	}

	private Condition _createWhereClauseCondition(
		Long dataSourceId, String externalReferenceCode,
		@Nullable Set<Long> groupIds, TimeRange timeRange) {

		String timeZoneId = timeZoneDog.getTimeZoneId();

		List<Condition> conditions = new ArrayList<>();

		conditions.add(
			DSL.field(
				getAssetIdFieldName()
			).eq(
				externalReferenceCode
			));
		conditions.add(
			DSL.field(
				"metric.dataSourceId"
			).eq(
				dataSourceId
			));

		if ((groupIds != null) && !groupIds.isEmpty()) {
			conditions.add(
				DSL.field(
					"metric.groupId"
				).in(
					groupIds
				));
		}

		conditions.add(
			DSL.field(
				"eventDate"
			).between(
				dslHelper.getDateParam(
					timeRange.getStartLocalDateTime(), timeZoneId),
				dslHelper.getDateParam(
					timeRange.getEndLocalDateTime(), timeZoneId)
			));

		return DSL.and(conditions);
	}

	private ObjectEntryMetric _toObjectEntryMetric(
		Long dataSourceId, String externalReferenceCode,
		List<Map<String, Object>> recordMaps, Set<String> selectedMetrics) {

		ObjectEntryMetric objectEntryMetric = createAssetMetric();

		objectEntryMetric.setDataSourceId(String.valueOf(dataSourceId));
		objectEntryMetric.setExternalReferenceCode(externalReferenceCode);

		Map<String, BiConsumer<ObjectEntryMetric, Metric>> assetMetricSetters =
			getAssetMetricSetters();

		Map<String, Metric> metrics = new HashMap<>();

		for (String selectedMetric : selectedMetrics) {
			for (Map<String, Object> recordMap : recordMaps) {
				Metric metric = metrics.computeIfAbsent(
					selectedMetric,
					metricTypeName -> {
						MetricType metricType = getMetricType(metricTypeName);

						return new Metric(metricType);
					});

				MetricType metricType = metric.getMetricType();

				BigDecimal metricValueBigDecimal = (BigDecimal)recordMap.get(
					metricType.getName());

				if (metricValueBigDecimal != null) {
					boolean previous = (boolean)recordMap.get("previous");

					if (previous) {
						metric.setPreviousValue(
							metricValueBigDecimal.doubleValue());
					}
					else {
						metric.setValue(metricValueBigDecimal.doubleValue());
					}
				}
			}
		}

		for (Map.Entry<String, Metric> entry : metrics.entrySet()) {
			BiConsumer<ObjectEntryMetric, Metric> metricSetterBiConsumer =
				assetMetricSetters.get(entry.getKey());

			metricSetterBiConsumer.accept(objectEntryMetric, entry.getValue());
		}

		return objectEntryMetric;
	}

}