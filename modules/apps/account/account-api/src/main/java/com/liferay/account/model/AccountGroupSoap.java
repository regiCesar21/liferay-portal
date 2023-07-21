/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.account.service.http.AccountGroupServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountGroupSoap implements Serializable {

	public static AccountGroupSoap toSoapModel(AccountGroup model) {
		AccountGroupSoap soapModel = new AccountGroupSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setAccountGroupId(model.getAccountGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static AccountGroupSoap[] toSoapModels(AccountGroup[] models) {
		AccountGroupSoap[] soapModels = new AccountGroupSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AccountGroupSoap[][] toSoapModels(AccountGroup[][] models) {
		AccountGroupSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AccountGroupSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AccountGroupSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AccountGroupSoap[] toSoapModels(List<AccountGroup> models) {
		List<AccountGroupSoap> soapModels = new ArrayList<AccountGroupSoap>(
			models.size());

		for (AccountGroup model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AccountGroupSoap[soapModels.size()]);
	}

	public AccountGroupSoap() {
	}

	public long getPrimaryKey() {
		return _accountGroupId;
	}

	public void setPrimaryKey(long pk) {
		setAccountGroupId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getAccountGroupId() {
		return _accountGroupId;
	}

	public void setAccountGroupId(long accountGroupId) {
		_accountGroupId = accountGroupId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private long _mvccVersion;
	private String _externalReferenceCode;
	private long _accountGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;

}