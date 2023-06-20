/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.analytics.message.storage.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AnalyticsAssociationSoap implements Serializable {

	public static AnalyticsAssociationSoap toSoapModel(
		AnalyticsAssociation model) {

		AnalyticsAssociationSoap soapModel = new AnalyticsAssociationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAnalyticsAssociationId(model.getAnalyticsAssociationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setUserId(model.getUserId());
		soapModel.setAssociationClassName(model.getAssociationClassName());
		soapModel.setAssociationClassPK(model.getAssociationClassPK());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static AnalyticsAssociationSoap[] toSoapModels(
		AnalyticsAssociation[] models) {

		AnalyticsAssociationSoap[] soapModels =
			new AnalyticsAssociationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsAssociationSoap[][] toSoapModels(
		AnalyticsAssociation[][] models) {

		AnalyticsAssociationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AnalyticsAssociationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnalyticsAssociationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsAssociationSoap[] toSoapModels(
		List<AnalyticsAssociation> models) {

		List<AnalyticsAssociationSoap> soapModels =
			new ArrayList<AnalyticsAssociationSoap>(models.size());

		for (AnalyticsAssociation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AnalyticsAssociationSoap[soapModels.size()]);
	}

	public AnalyticsAssociationSoap() {
	}

	public long getPrimaryKey() {
		return _analyticsAssociationId;
	}

	public void setPrimaryKey(long pk) {
		setAnalyticsAssociationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAnalyticsAssociationId() {
		return _analyticsAssociationId;
	}

	public void setAnalyticsAssociationId(long analyticsAssociationId) {
		_analyticsAssociationId = analyticsAssociationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getAssociationClassName() {
		return _associationClassName;
	}

	public void setAssociationClassName(String associationClassName) {
		_associationClassName = associationClassName;
	}

	public long getAssociationClassPK() {
		return _associationClassPK;
	}

	public void setAssociationClassPK(long associationClassPK) {
		_associationClassPK = associationClassPK;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _mvccVersion;
	private long _analyticsAssociationId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _userId;
	private String _associationClassName;
	private long _associationClassPK;
	private String _className;
	private long _classPK;

}