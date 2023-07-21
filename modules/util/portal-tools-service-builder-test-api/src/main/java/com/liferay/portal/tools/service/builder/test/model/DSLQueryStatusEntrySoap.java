/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

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
public class DSLQueryStatusEntrySoap implements Serializable {

	public static DSLQueryStatusEntrySoap toSoapModel(
		DSLQueryStatusEntry model) {

		DSLQueryStatusEntrySoap soapModel = new DSLQueryStatusEntrySoap();

		soapModel.setDslQueryStatusEntryId(model.getDslQueryStatusEntryId());
		soapModel.setDslQueryEntryId(model.getDslQueryEntryId());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static DSLQueryStatusEntrySoap[] toSoapModels(
		DSLQueryStatusEntry[] models) {

		DSLQueryStatusEntrySoap[] soapModels =
			new DSLQueryStatusEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryStatusEntrySoap[][] toSoapModels(
		DSLQueryStatusEntry[][] models) {

		DSLQueryStatusEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DSLQueryStatusEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DSLQueryStatusEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryStatusEntrySoap[] toSoapModels(
		List<DSLQueryStatusEntry> models) {

		List<DSLQueryStatusEntrySoap> soapModels =
			new ArrayList<DSLQueryStatusEntrySoap>(models.size());

		for (DSLQueryStatusEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DSLQueryStatusEntrySoap[soapModels.size()]);
	}

	public DSLQueryStatusEntrySoap() {
	}

	public long getPrimaryKey() {
		return _dslQueryStatusEntryId;
	}

	public void setPrimaryKey(long pk) {
		setDslQueryStatusEntryId(pk);
	}

	public long getDslQueryStatusEntryId() {
		return _dslQueryStatusEntryId;
	}

	public void setDslQueryStatusEntryId(long dslQueryStatusEntryId) {
		_dslQueryStatusEntryId = dslQueryStatusEntryId;
	}

	public long getDslQueryEntryId() {
		return _dslQueryEntryId;
	}

	public void setDslQueryEntryId(long dslQueryEntryId) {
		_dslQueryEntryId = dslQueryEntryId;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private long _dslQueryStatusEntryId;
	private long _dslQueryEntryId;
	private String _status;
	private Date _statusDate;

}