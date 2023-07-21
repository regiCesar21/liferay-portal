/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowDataRecordLinkSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowDataRecordLink implements Cloneable, Serializable {

	public static AppWorkflowDataRecordLink toDTO(String json) {
		return AppWorkflowDataRecordLinkSerDes.toDTO(json);
	}

	public AppWorkflow getAppWorkflow() {
		return appWorkflow;
	}

	public void setAppWorkflow(AppWorkflow appWorkflow) {
		this.appWorkflow = appWorkflow;
	}

	public void setAppWorkflow(
		UnsafeSupplier<AppWorkflow, Exception> appWorkflowUnsafeSupplier) {

		try {
			appWorkflow = appWorkflowUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflow appWorkflow;

	public Long getDataRecordId() {
		return dataRecordId;
	}

	public void setDataRecordId(Long dataRecordId) {
		this.dataRecordId = dataRecordId;
	}

	public void setDataRecordId(
		UnsafeSupplier<Long, Exception> dataRecordIdUnsafeSupplier) {

		try {
			dataRecordId = dataRecordIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long dataRecordId;

	@Override
	public AppWorkflowDataRecordLink clone() throws CloneNotSupportedException {
		return (AppWorkflowDataRecordLink)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowDataRecordLink)) {
			return false;
		}

		AppWorkflowDataRecordLink appWorkflowDataRecordLink =
			(AppWorkflowDataRecordLink)object;

		return Objects.equals(toString(), appWorkflowDataRecordLink.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowDataRecordLinkSerDes.toJSON(this);
	}

}