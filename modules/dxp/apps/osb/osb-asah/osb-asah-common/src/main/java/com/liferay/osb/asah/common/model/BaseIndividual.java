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

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Leslie Wong
 */
public abstract class BaseIndividual {

	public BaseIndividual() {
	}

	public BaseIndividual(BQIndividual bqIndividual) {
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

					field.setSourceName(field.getName());

					String displayName =
						FieldMappingConstants.demographicsDisplayNames.
							getOrDefault(field.getName(), field.getName());

					field.setName(displayName);

					if (displayName.endsWith("Date")) {
						long value = NumberUtils.toLong(
							(String)field.getValue());

						field.setValue(DateUtil.toUTCString(new Date(value)));
					}
				});

			this.fields = fields;
		}

		customDemographics = new Demographics(customFields);
		demographics = new Demographics(this.fields);
		id = StringUtil.get(bqIndividual.getId(), null);
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

}