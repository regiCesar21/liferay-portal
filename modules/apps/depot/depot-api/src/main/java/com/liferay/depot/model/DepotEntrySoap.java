/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.depot.service.http.DepotEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DepotEntrySoap implements Serializable {

	public static DepotEntrySoap toSoapModel(DepotEntry model) {
		DepotEntrySoap soapModel = new DepotEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setDepotEntryId(model.getDepotEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static DepotEntrySoap[] toSoapModels(DepotEntry[] models) {
		DepotEntrySoap[] soapModels = new DepotEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DepotEntrySoap[][] toSoapModels(DepotEntry[][] models) {
		DepotEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DepotEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DepotEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DepotEntrySoap[] toSoapModels(List<DepotEntry> models) {
		List<DepotEntrySoap> soapModels = new ArrayList<DepotEntrySoap>(
			models.size());

		for (DepotEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DepotEntrySoap[soapModels.size()]);
	}

	public DepotEntrySoap() {
	}

	public long getPrimaryKey() {
		return _depotEntryId;
	}

	public void setPrimaryKey(long pk) {
		setDepotEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getDepotEntryId() {
		return _depotEntryId;
	}

	public void setDepotEntryId(long depotEntryId) {
		_depotEntryId = depotEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
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

	private long _mvccVersion;
	private String _uuid;
	private long _depotEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;

}