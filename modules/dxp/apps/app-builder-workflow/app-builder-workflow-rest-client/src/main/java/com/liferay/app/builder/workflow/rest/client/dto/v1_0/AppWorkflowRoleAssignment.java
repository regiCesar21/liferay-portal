/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.client.dto.v1_0;

import com.liferay.app.builder.workflow.rest.client.function.UnsafeSupplier;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowRoleAssignmentSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AppWorkflowRoleAssignment implements Cloneable, Serializable {

	public static AppWorkflowRoleAssignment toDTO(String json) {
		return AppWorkflowRoleAssignmentSerDes.toDTO(json);
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleId(
		UnsafeSupplier<Long, Exception> roleIdUnsafeSupplier) {

		try {
			roleId = roleIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long roleId;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleName(
		UnsafeSupplier<String, Exception> roleNameUnsafeSupplier) {

		try {
			roleName = roleNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String roleName;

	@Override
	public AppWorkflowRoleAssignment clone() throws CloneNotSupportedException {
		return (AppWorkflowRoleAssignment)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppWorkflowRoleAssignment)) {
			return false;
		}

		AppWorkflowRoleAssignment appWorkflowRoleAssignment =
			(AppWorkflowRoleAssignment)object;

		return Objects.equals(toString(), appWorkflowRoleAssignment.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AppWorkflowRoleAssignmentSerDes.toJSON(this);
	}

}