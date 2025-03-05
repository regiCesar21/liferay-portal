/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.asah.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.liferay.osb.asah.common.model.Feature;

import java.util.Objects;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Marcellus Tavares
 */
@Table
public class ProjectFeature implements Persistable<Long> {

	public ProjectFeature() {
	}

	public ProjectFeature(Boolean enabled, Feature feature, String projectId) {
		_enabled = enabled;
		_feature = feature;
		_projectId = projectId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || !(obj instanceof ProjectFeature)) {
			return false;
		}

		ProjectFeature projectFeature = (ProjectFeature)obj;

		if (Objects.equals(_enabled, projectFeature._enabled) &&
			Objects.equals(_feature, projectFeature._feature) &&
			Objects.equals(_id, projectFeature._id) &&
			Objects.equals(_projectId, projectFeature._projectId)) {

			return true;
		}

		return false;
	}

	@AccessType(AccessType.Type.PROPERTY)
	public Boolean getEnabled() {
		return _enabled;
	}

	@AccessType(AccessType.Type.PROPERTY)
	public Feature getFeature() {
		return _feature;
	}

	@AccessType(AccessType.Type.PROPERTY)
	@Id
	@Override
	public Long getId() {
		return _id;
	}

	@AccessType(AccessType.Type.PROPERTY)
	public String getProjectId() {
		return _projectId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_enabled, _feature, _id, _projectId);
	}

	@JsonIgnore
	@Override
	public boolean isNew() {
		if ((_id == null) || ((_isNew != null) && _isNew)) {
			return true;
		}

		return false;
	}

	public void setEnabled(Boolean enabled) {
		_enabled = enabled;
	}

	public void setFeature(Feature feature) {
		_feature = feature;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setIsNew(Boolean isNew) {
		_isNew = isNew;
	}

	public void setProjectId(String projectId) {
		_projectId = projectId;
	}

	@Transient
	private Boolean _enabled;

	@Transient
	private Feature _feature;

	@Transient
	private Long _id;

	@Transient
	private Boolean _isNew;

	@Transient
	private String _projectId;

}