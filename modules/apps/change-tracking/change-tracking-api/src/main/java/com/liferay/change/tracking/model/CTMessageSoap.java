/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

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
public class CTMessageSoap implements Serializable {

	public static CTMessageSoap toSoapModel(CTMessage model) {
		CTMessageSoap soapModel = new CTMessageSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtMessageId(model.getCtMessageId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setMessageContent(model.getMessageContent());

		return soapModel;
	}

	public static CTMessageSoap[] toSoapModels(CTMessage[] models) {
		CTMessageSoap[] soapModels = new CTMessageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTMessageSoap[][] toSoapModels(CTMessage[][] models) {
		CTMessageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTMessageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTMessageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTMessageSoap[] toSoapModels(List<CTMessage> models) {
		List<CTMessageSoap> soapModels = new ArrayList<CTMessageSoap>(
			models.size());

		for (CTMessage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTMessageSoap[soapModels.size()]);
	}

	public CTMessageSoap() {
	}

	public long getPrimaryKey() {
		return _ctMessageId;
	}

	public void setPrimaryKey(long pk) {
		setCtMessageId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtMessageId() {
		return _ctMessageId;
	}

	public void setCtMessageId(long ctMessageId) {
		_ctMessageId = ctMessageId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getMessageContent() {
		return _messageContent;
	}

	public void setMessageContent(String messageContent) {
		_messageContent = messageContent;
	}

	private long _mvccVersion;
	private long _ctMessageId;
	private long _companyId;
	private long _ctCollectionId;
	private String _messageContent;

}