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
public class DSLQueryEntrySoap implements Serializable {

	public static DSLQueryEntrySoap toSoapModel(DSLQueryEntry model) {
		DSLQueryEntrySoap soapModel = new DSLQueryEntrySoap();

		soapModel.setDslQueryEntryId(model.getDslQueryEntryId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static DSLQueryEntrySoap[] toSoapModels(DSLQueryEntry[] models) {
		DSLQueryEntrySoap[] soapModels = new DSLQueryEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryEntrySoap[][] toSoapModels(DSLQueryEntry[][] models) {
		DSLQueryEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DSLQueryEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new DSLQueryEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DSLQueryEntrySoap[] toSoapModels(List<DSLQueryEntry> models) {
		List<DSLQueryEntrySoap> soapModels = new ArrayList<DSLQueryEntrySoap>(
			models.size());

		for (DSLQueryEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DSLQueryEntrySoap[soapModels.size()]);
	}

	public DSLQueryEntrySoap() {
	}

	public long getPrimaryKey() {
		return _dslQueryEntryId;
	}

	public void setPrimaryKey(long pk) {
		setDslQueryEntryId(pk);
	}

	public long getDslQueryEntryId() {
		return _dslQueryEntryId;
	}

	public void setDslQueryEntryId(long dslQueryEntryId) {
		_dslQueryEntryId = dslQueryEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _dslQueryEntryId;
	private String _name;

}