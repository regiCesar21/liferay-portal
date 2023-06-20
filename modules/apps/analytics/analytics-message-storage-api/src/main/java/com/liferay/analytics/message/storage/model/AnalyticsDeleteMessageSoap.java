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
public class AnalyticsDeleteMessageSoap implements Serializable {

	public static AnalyticsDeleteMessageSoap toSoapModel(
		AnalyticsDeleteMessage model) {

		AnalyticsDeleteMessageSoap soapModel = new AnalyticsDeleteMessageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAnalyticsDeleteMessageId(
			model.getAnalyticsDeleteMessageId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static AnalyticsDeleteMessageSoap[] toSoapModels(
		AnalyticsDeleteMessage[] models) {

		AnalyticsDeleteMessageSoap[] soapModels =
			new AnalyticsDeleteMessageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsDeleteMessageSoap[][] toSoapModels(
		AnalyticsDeleteMessage[][] models) {

		AnalyticsDeleteMessageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AnalyticsDeleteMessageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnalyticsDeleteMessageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnalyticsDeleteMessageSoap[] toSoapModels(
		List<AnalyticsDeleteMessage> models) {

		List<AnalyticsDeleteMessageSoap> soapModels =
			new ArrayList<AnalyticsDeleteMessageSoap>(models.size());

		for (AnalyticsDeleteMessage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AnalyticsDeleteMessageSoap[soapModels.size()]);
	}

	public AnalyticsDeleteMessageSoap() {
	}

	public long getPrimaryKey() {
		return _analyticsDeleteMessageId;
	}

	public void setPrimaryKey(long pk) {
		setAnalyticsDeleteMessageId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAnalyticsDeleteMessageId() {
		return _analyticsDeleteMessageId;
	}

	public void setAnalyticsDeleteMessageId(long analyticsDeleteMessageId) {
		_analyticsDeleteMessageId = analyticsDeleteMessageId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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
	private long _analyticsDeleteMessageId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _className;
	private long _classPK;

}