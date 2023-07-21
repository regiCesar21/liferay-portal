/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflow implements Cloneable, Serializable {

	public static AppWorkflow toDTO(String json) {
		return AppWorkflowSerDes.toDTO(json);
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public void setAppId(UnsafeSupplier<Long, Exception> appIdUnsafeSupplier) {
		try {
			appId = appIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long appId;

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public void setAppVersion(
		UnsafeSupplier<String, Exception> appVersionUnsafeSupplier) {

		try {
			appVersion = appVersionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String appVersion;

	public Long getAppWorkflowDefinitionId() {
		return appWorkflowDefinitionId;
	}

	public void setAppWorkflowDefinitionId(Long appWorkflowDefinitionId) {
		this.appWorkflowDefinitionId = appWorkflowDefinitionId;
	}

	public void setAppWorkflowDefinitionId(
		UnsafeSupplier<Long, Exception> appWorkflowDefinitionIdUnsafeSupplier) {

		try {
			appWorkflowDefinitionId =
				appWorkflowDefinitionIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long appWorkflowDefinitionId;

	public AppWorkflowState[] getAppWorkflowStates() {
		return appWorkflowStates;
	}

	public void setAppWorkflowStates(AppWorkflowState[] appWorkflowStates) {
		this.appWorkflowStates = appWorkflowStates;
	}

	public void setAppWorkflowStates(
		UnsafeSupplier<AppWorkflowState[], Exception>
			appWorkflowStatesUnsafeSupplier) {

		try {
			appWorkflowStates = appWorkflowStatesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowState[] appWorkflowStates;

	public AppWorkflowTask[] getAppWorkflowTasks() {
		return appWorkflowTasks;
	}

	public void setAppWorkflowTasks(AppWorkflowTask[] appWorkflowTasks) {
		this.appWorkflowTasks = appWorkflowTasks;
	}

	public void setAppWorkflowTasks(
		UnsafeSupplier<AppWorkflowTask[], Exception>
			appWorkflowTasksUnsafeSupplier) {

		try {
			appWorkflowTasks = appWorkflowTasksUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowTask[] appWorkflowTasks;

	@Override
	public AppWorkflow clone() throws CloneNotSupportedException {
		return (AppWorkflow)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflow)) {
			return false;
		}

		AppWorkflow appWorkflow = (AppWorkflow)object;

		return Objects.equals(toString(), appWorkflow.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowSerDes.toJSON(this);
	}

}