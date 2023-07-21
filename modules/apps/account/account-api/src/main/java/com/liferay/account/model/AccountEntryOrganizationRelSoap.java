/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.account.service.http.AccountEntryOrganizationRelServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountEntryOrganizationRelSoap implements Serializable {

	public static AccountEntryOrganizationRelSoap toSoapModel(
		AccountEntryOrganizationRel model) {

		AccountEntryOrganizationRelSoap soapModel =
			new AccountEntryOrganizationRelSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setAccountEntryOrganizationRelId(
			model.getAccountEntryOrganizationRelId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setAccountEntryId(model.getAccountEntryId());
		soapModel.setOrganizationId(model.getOrganizationId());

		return soapModel;
	}

	public static AccountEntryOrganizationRelSoap[] toSoapModels(
		AccountEntryOrganizationRel[] models) {

		AccountEntryOrganizationRelSoap[] soapModels =
			new AccountEntryOrganizationRelSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AccountEntryOrganizationRelSoap[][] toSoapModels(
		AccountEntryOrganizationRel[][] models) {

		AccountEntryOrganizationRelSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AccountEntryOrganizationRelSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new AccountEntryOrganizationRelSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AccountEntryOrganizationRelSoap[] toSoapModels(
		List<AccountEntryOrganizationRel> models) {

		List<AccountEntryOrganizationRelSoap> soapModels =
			new ArrayList<AccountEntryOrganizationRelSoap>(models.size());

		for (AccountEntryOrganizationRel model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AccountEntryOrganizationRelSoap[soapModels.size()]);
	}

	public AccountEntryOrganizationRelSoap() {
	}

	public long getPrimaryKey() {
		return _accountEntryOrganizationRelId;
	}

	public void setPrimaryKey(long pk) {
		setAccountEntryOrganizationRelId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getAccountEntryOrganizationRelId() {
		return _accountEntryOrganizationRelId;
	}

	public void setAccountEntryOrganizationRelId(
		long accountEntryOrganizationRelId) {

		_accountEntryOrganizationRelId = accountEntryOrganizationRelId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	private long _mvccVersion;
	private long _accountEntryOrganizationRelId;
	private long _companyId;
	private long _accountEntryId;
	private long _organizationId;

}