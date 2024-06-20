/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.model;

import com.liferay.osb.asah.common.constants.FieldMappingConstants;
import com.liferay.osb.asah.common.date.DateUtil;
import com.liferay.osb.asah.common.entity.BQIndividual;
import com.liferay.osb.asah.common.util.StringUtil;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author Leslie Wong
 */
public class ReportIndividual {

	public ReportIndividual() {
	}

	public ReportIndividual(BQIndividual bqIndividual) {
		List<BQIndividual.Field> bqIndividualFields = bqIndividual.getFields();

		Stream<BQIndividual.Field> stream1 = bqIndividualFields.stream();

		Set<Field> fields = stream1.map(
			bqIndividualField -> {
				Field field = new Field();

				field.setDataSourceId(bqIndividualField.getDataSourceId());
				field.setName(bqIndividualField.getName());
				field.setValue(bqIndividualField.getValue());

				return field;
			}
		).collect(
			Collectors.toSet()
		);

		if (fields != null) {
			Set<String> demographicsFieldNames =
				FieldMappingConstants.demographicsDisplayNames.keySet();

			Stream<Field> stream2 = fields.stream();

			customFields = stream2.filter(
				bqIndividualField -> !demographicsFieldNames.contains(
					bqIndividualField.getName())
			).collect(
				Collectors.toSet()
			);

			fields.removeAll(customFields);

			customFields.forEach(
				customField -> {
					customField.setModifiedDate(bqIndividual.getModifiedDate());
					customField.setSourceName(customField.getName());
				});

			stream2 = fields.stream();

			stream2.forEach(
				field -> {
					field.setModifiedDate(bqIndividual.getModifiedDate());

					String displayName =
						FieldMappingConstants.demographicsDisplayNames.
							getOrDefault(field.getName(), field.getName());

					field.setName(displayName);

					if (displayName.endsWith("Date")) {
						long value = NumberUtils.toLong(
							(String)field.getValue());

						field.setValue(DateUtil.toUTCString(new Date(value)));
					}

					field.setSourceName(field.getName());
				});

			this.fields = fields;
		}

		customDemographics = new Demographics(customFields);
		demographics = new Demographics(this.fields);
		id = StringUtil.get(bqIndividual.getId(), null);
	}

	public ReportIndividual(BQIndividual bqIndividual, Set<Long> segmentIds) {
		this(bqIndividual);

		this.segmentIds = segmentIds;
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

		if (Objects.equals(
				customDemographics, reportIndividual.customDemographics) &&
			Objects.equals(customFields, reportIndividual.customFields) &&
			Objects.equals(demographics, reportIndividual.demographics) &&
			Objects.equals(fields, reportIndividual.fields) &&
			Objects.equals(id, reportIndividual.id) &&
			Objects.equals(segmentIds, reportIndividual.segmentIds)) {

			return true;
		}

		return false;
	}

	public Demographics getCustomDemographics() {
		return customDemographics;
	}

	public Set<Field> getCustomFields() {
		return customFields;
	}

	public Demographics getDemographics() {
		return demographics;
	}

	public Set<Field> getFields() {
		return fields;
	}

	public String getId() {
		return id;
	}

	public Set<Long> getSegmentIds() {
		return segmentIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			customDemographics, customFields, demographics, fields, id,
			segmentIds);
	}

	public void setCustomDemographics(Demographics customDemographics) {
		this.customDemographics = customDemographics;

		customFields = customDemographics._fields;
	}

	public void setCustomFields(Set<Field> customFields) {
		this.customFields = customFields;

		customDemographics = new Demographics(customFields);
	}

	public void setDemographics(Demographics demographics) {
		this.demographics = demographics;

		fields = demographics._fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;

		demographics = new Demographics(fields);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSegmentIds(Set<Long> segmentIds) {
		this.segmentIds = segmentIds;
	}

	public static class Demographics {

		public Demographics() {
		}

		public Demographics(Set<Field> fields) {
			_fields = fields;
		}

		public void addField(String key, List<Field> fields) {
			Field field = fields.get(0);

			field.setName(key);

			_fields.add(field);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Demographics)) {
				return false;
			}

			Demographics demographics = (Demographics)obj;

			if (Objects.equals(_fields, demographics._fields)) {
				return true;
			}

			return false;
		}

		public Map<String, List<Field>> getField() {
			Stream<Field> stream = _fields.stream();

			return stream.collect(
				Collectors.toMap(
					Field::getName, Collections::singletonList,
					(existing, replacement) -> replacement));
		}

		public Set<Field> getFields() {
			return _fields;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_fields);
		}

		private Set<Field> _fields = new HashSet<>();

	}

	protected Demographics customDemographics;
	protected Set<Field> customFields = new HashSet<>();
	protected Demographics demographics;
	protected Set<Field> fields = new HashSet<>();
	protected String id;
	protected Set<Long> segmentIds;

}