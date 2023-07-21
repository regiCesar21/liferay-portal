/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

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
public class CTAutoResolutionInfoSoap implements Serializable {

	public static CTAutoResolutionInfoSoap toSoapModel(
		CTAutoResolutionInfo model) {

		CTAutoResolutionInfoSoap soapModel = new CTAutoResolutionInfoSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtAutoResolutionInfoId(model.getCtAutoResolutionInfoId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setModelClassNameId(model.getModelClassNameId());
		soapModel.setSourceModelClassPK(model.getSourceModelClassPK());
		soapModel.setTargetModelClassPK(model.getTargetModelClassPK());
		soapModel.setConflictIdentifier(model.getConflictIdentifier());

		return soapModel;
	}

	public static CTAutoResolutionInfoSoap[] toSoapModels(
		CTAutoResolutionInfo[] models) {

		CTAutoResolutionInfoSoap[] soapModels =
			new CTAutoResolutionInfoSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTAutoResolutionInfoSoap[][] toSoapModels(
		CTAutoResolutionInfo[][] models) {

		CTAutoResolutionInfoSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CTAutoResolutionInfoSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTAutoResolutionInfoSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTAutoResolutionInfoSoap[] toSoapModels(
		List<CTAutoResolutionInfo> models) {

		List<CTAutoResolutionInfoSoap> soapModels =
			new ArrayList<CTAutoResolutionInfoSoap>(models.size());

		for (CTAutoResolutionInfo model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CTAutoResolutionInfoSoap[soapModels.size()]);
	}

	public CTAutoResolutionInfoSoap() {
	}

	public long getPrimaryKey() {
		return _ctAutoResolutionInfoId;
	}

	public void setPrimaryKey(long pk) {
		setCtAutoResolutionInfoId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtAutoResolutionInfoId() {
		return _ctAutoResolutionInfoId;
	}

	public void setCtAutoResolutionInfoId(long ctAutoResolutionInfoId) {
		_ctAutoResolutionInfoId = ctAutoResolutionInfoId;
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

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getModelClassNameId() {
		return _modelClassNameId;
	}

	public void setModelClassNameId(long modelClassNameId) {
		_modelClassNameId = modelClassNameId;
	}

	public long getSourceModelClassPK() {
		return _sourceModelClassPK;
	}

	public void setSourceModelClassPK(long sourceModelClassPK) {
		_sourceModelClassPK = sourceModelClassPK;
	}

	public long getTargetModelClassPK() {
		return _targetModelClassPK;
	}

	public void setTargetModelClassPK(long targetModelClassPK) {
		_targetModelClassPK = targetModelClassPK;
	}

	public String getConflictIdentifier() {
		return _conflictIdentifier;
	}

	public void setConflictIdentifier(String conflictIdentifier) {
		_conflictIdentifier = conflictIdentifier;
	}

	private long _mvccVersion;
	private long _ctAutoResolutionInfoId;
	private long _companyId;
	private Date _createDate;
	private long _ctCollectionId;
	private long _modelClassNameId;
	private long _sourceModelClassPK;
	private long _targetModelClassPK;
	private String _conflictIdentifier;

}