/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CacheMissEntrySoap implements Serializable {

	public static CacheMissEntrySoap toSoapModel(CacheMissEntry model) {
		CacheMissEntrySoap soapModel = new CacheMissEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setCacheMissEntryId(model.getCacheMissEntryId());

		return soapModel;
	}

	public static CacheMissEntrySoap[] toSoapModels(CacheMissEntry[] models) {
		CacheMissEntrySoap[] soapModels = new CacheMissEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CacheMissEntrySoap[][] toSoapModels(
		CacheMissEntry[][] models) {

		CacheMissEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CacheMissEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CacheMissEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CacheMissEntrySoap[] toSoapModels(
		List<CacheMissEntry> models) {

		List<CacheMissEntrySoap> soapModels = new ArrayList<CacheMissEntrySoap>(
			models.size());

		for (CacheMissEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CacheMissEntrySoap[soapModels.size()]);
	}

	public CacheMissEntrySoap() {
	}

	public long getPrimaryKey() {
		return _cacheMissEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCacheMissEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getCacheMissEntryId() {
		return _cacheMissEntryId;
	}

	public void setCacheMissEntryId(long cacheMissEntryId) {
		_cacheMissEntryId = cacheMissEntryId;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _cacheMissEntryId;

}