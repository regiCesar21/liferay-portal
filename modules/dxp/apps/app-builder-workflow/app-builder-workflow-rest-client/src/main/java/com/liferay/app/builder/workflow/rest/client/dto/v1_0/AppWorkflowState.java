/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowStateSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowState implements Cloneable, Serializable {

	public static AppWorkflowState toDTO(String json) {
		return AppWorkflowStateSerDes.toDTO(json);
	}

	public AppWorkflowTransition[] getAppWorkflowTransitions() {
		return appWorkflowTransitions;
	}

	public void setAppWorkflowTransitions(
		AppWorkflowTransition[] appWorkflowTransitions) {

		this.appWorkflowTransitions = appWorkflowTransitions;
	}

	public void setAppWorkflowTransitions(
		UnsafeSupplier<AppWorkflowTransition[], Exception>
			appWorkflowTransitionsUnsafeSupplier) {

		try {
			appWorkflowTransitions = appWorkflowTransitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowTransition[] appWorkflowTransitions;

	public Boolean getInitial() {
		return initial;
	}

	public void setInitial(Boolean initial) {
		this.initial = initial;
	}

	public void setInitial(
		UnsafeSupplier<Boolean, Exception> initialUnsafeSupplier) {

		try {
			initial = initialUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean initial;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	@Override
	public AppWorkflowState clone() throws CloneNotSupportedException {
		return (AppWorkflowState)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowState)) {
			return false;
		}

		AppWorkflowState appWorkflowState = (AppWorkflowState)object;

		return Objects.equals(toString(), appWorkflowState.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowStateSerDes.toJSON(this);
	}

}