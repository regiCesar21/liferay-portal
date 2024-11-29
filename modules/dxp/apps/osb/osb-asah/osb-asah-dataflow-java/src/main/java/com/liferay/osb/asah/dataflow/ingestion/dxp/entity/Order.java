/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.dataflow.ingestion.dxp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

/**
 * @author Riccardo Ferrari
 */
@DefaultSchema(JavaFieldSchema.class)
public class Order extends BaseDXPEntity {

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Order)) {
			return false;
		}

		Order order = (Order)o;

		if ((accountId == order.accountId) && (id == order.id) &&
			(orderStatus == order.orderStatus) &&
			Objects.equals(commerceChannelId, order.commerceChannelId) &&
			Objects.equals(createDate, order.createDate) &&
			Objects.equals(currencyCode, order.currencyCode) &&
			Objects.equals(
				externalReferenceCode, order.externalReferenceCode) &&
			Objects.equals(modifiedDate, order.modifiedDate) &&
			Objects.equals(orderDate, order.orderDate) &&
			Objects.equals(
				orderTypeExternalReferenceCode,
				order.orderTypeExternalReferenceCode) &&
			Objects.equals(orderTypeId, order.orderTypeId) &&
			Objects.equals(paymentMethod, order.paymentMethod) &&
			Objects.equals(paymentStatus, order.paymentStatus) &&
			Objects.equals(status, order.status) &&
			Objects.equals(total, order.total) &&
			Objects.equals(userId, order.userId)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			accountId, commerceChannelId, createDate, currencyCode,
			externalReferenceCode, id, modifiedDate, orderDate, orderItems,
			orderStatus, orderTypeExternalReferenceCode, orderTypeId,
			paymentMethod, paymentStatus, status, total, userId);
	}

	public long accountId;

	@JsonProperty("channelId")
	@Nullable
	public Long commerceChannelId;

	public String createDate;

	@Nullable
	public String currencyCode;

	@Nullable
	public String externalReferenceCode;

	public long id;
	public String modifiedDate;
	public String orderDate;
	public List<OrderItem> orderItems;
	public long orderStatus;

	@Nullable
	public String orderTypeExternalReferenceCode;

	@Nullable
	public Long orderTypeId;

	@Nullable
	public String paymentMethod;

	@Nullable
	public Long paymentStatus;

	public Long status;

	@Nullable
	public String total;

	@Nullable
	public Long userId;

}