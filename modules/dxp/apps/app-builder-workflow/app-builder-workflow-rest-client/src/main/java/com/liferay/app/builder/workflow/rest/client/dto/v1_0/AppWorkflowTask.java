/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowTask implements Cloneable, Serializable {

	public static AppWorkflowTask toDTO(String json) {
		return AppWorkflowTaskSerDes.toDTO(json);
	}

	public AppWorkflowDataLayoutLink[] getAppWorkflowDataLayoutLinks() {
		return appWorkflowDataLayoutLinks;
	}

	public void setAppWorkflowDataLayoutLinks(
		AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks) {

		this.appWorkflowDataLayoutLinks = appWorkflowDataLayoutLinks;
	}

	public void setAppWorkflowDataLayoutLinks(
		UnsafeSupplier<AppWorkflowDataLayoutLink[], Exception>
			appWorkflowDataLayoutLinksUnsafeSupplier) {

		try {
			appWorkflowDataLayoutLinks =
				appWorkflowDataLayoutLinksUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowDataLayoutLink[] appWorkflowDataLayoutLinks;

	public AppWorkflowRoleAssignment[] getAppWorkflowRoleAssignments() {
		return appWorkflowRoleAssignments;
	}

	public void setAppWorkflowRoleAssignments(
		AppWorkflowRoleAssignment[] appWorkflowRoleAssignments) {

		this.appWorkflowRoleAssignments = appWorkflowRoleAssignments;
	}

	public void setAppWorkflowRoleAssignments(
		UnsafeSupplier<AppWorkflowRoleAssignment[], Exception>
			appWorkflowRoleAssignmentsUnsafeSupplier) {

		try {
			appWorkflowRoleAssignments =
				appWorkflowRoleAssignmentsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AppWorkflowRoleAssignment[] appWorkflowRoleAssignments;

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
	public AppWorkflowTask clone() throws CloneNotSupportedException {
		return (AppWorkflowTask)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowTask)) {
			return false;
		}

		AppWorkflowTask appWorkflowTask = (AppWorkflowTask)object;

		return Objects.equals(toString(), appWorkflowTask.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowTaskSerDes.toJSON(this);
	}

}