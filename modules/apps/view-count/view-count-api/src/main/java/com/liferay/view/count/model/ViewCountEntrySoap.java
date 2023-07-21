/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.model;

import com.liferay.view.count.service.persistence.ViewCountEntryPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Preston Crary
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class ViewCountEntrySoap implements Serializable {

	public static ViewCountEntrySoap toSoapModel(ViewCountEntry model) {
		ViewCountEntrySoap soapModel = new ViewCountEntrySoap();

		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setViewCount(model.getViewCount());

		return soapModel;
	}

	public static ViewCountEntrySoap[] toSoapModels(ViewCountEntry[] models) {
		ViewCountEntrySoap[] soapModels = new ViewCountEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ViewCountEntrySoap[][] toSoapModels(
		ViewCountEntry[][] models) {

		ViewCountEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ViewCountEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ViewCountEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ViewCountEntrySoap[] toSoapModels(
		List<ViewCountEntry> models) {

		List<ViewCountEntrySoap> soapModels = new ArrayList<ViewCountEntrySoap>(
			models.size());

		for (ViewCountEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ViewCountEntrySoap[soapModels.size()]);
	}

	public ViewCountEntrySoap() {
	}

	public ViewCountEntryPK getPrimaryKey() {
		return new ViewCountEntryPK(_companyId, _classNameId, _classPK);
	}

	public void setPrimaryKey(ViewCountEntryPK pk) {
		setCompanyId(pk.companyId);
		setClassNameId(pk.classNameId);
		setClassPK(pk.classPK);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getViewCount() {
		return _viewCount;
	}

	public void setViewCount(long viewCount) {
		_viewCount = viewCount;
	}

	private long _companyId;
	private long _classNameId;
	private long _classPK;
	private long _viewCount;

}