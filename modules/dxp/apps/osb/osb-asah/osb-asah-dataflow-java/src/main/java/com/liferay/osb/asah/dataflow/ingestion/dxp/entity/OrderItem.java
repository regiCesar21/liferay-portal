/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.dxp.entity;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

/**
 * @author Riccardo Ferrari
 */
@DefaultSchema(JavaFieldSchema.class)
public class OrderItem implements Serializable {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof OrderItem)) {
			return false;
		}

		OrderItem orderItem = (OrderItem)o;

		if ((cpDefinitionId == orderItem.cpDefinitionId) &&
			(id == orderItem.id) &&
			(parentOrderItemId == orderItem.parentOrderItemId) &&
			(quantity == orderItem.quantity) && (userId == orderItem.userId) &&
			Objects.equals(createDate, orderItem.createDate) &&
			Objects.equals(customFields, orderItem.customFields) &&
			Objects.equals(
				externalReferenceCode, orderItem.externalReferenceCode) &&
			Objects.equals(finalPrice, orderItem.finalPrice) &&
			Objects.equals(modifiedDate, orderItem.modifiedDate) &&
			Objects.equals(name, orderItem.name) &&
			Objects.equals(options, orderItem.options) &&
			Objects.equals(sku, orderItem.sku) &&
			Objects.equals(subscription, orderItem.subscription) &&
			Objects.equals(unitOfMeasure, orderItem.unitOfMeasure) &&
			Objects.equals(unitPrice, orderItem.unitPrice)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			cpDefinitionId, createDate, customFields, externalReferenceCode,
			finalPrice, id, modifiedDate, name, options, parentOrderItemId,
			quantity, sku, subscription, unitOfMeasure, unitPrice, userId);
	}

	public long cpDefinitionId;
	public String createDate;

	@Nullable
	public Map<String, String> customFields;

	@Nullable
	public String externalReferenceCode;

	@Nullable
	public String finalPrice;

	public long id;

	@Nullable
	public String modifiedDate;

	@Nullable
	public Map<String, String> name;

	@Nullable
	public String options;

	public long parentOrderItemId;
	public long quantity;
	public String sku;

	@Nullable
	public Boolean subscription;

	@Nullable
	public String unitOfMeasure;

	@Nullable
	public String unitPrice;

	public long userId;

}