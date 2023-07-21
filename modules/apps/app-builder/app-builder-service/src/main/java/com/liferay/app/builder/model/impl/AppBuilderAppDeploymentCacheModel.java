/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.model.impl;

import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AppBuilderAppDeployment in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AppBuilderAppDeploymentCacheModel
	implements CacheModel<AppBuilderAppDeployment>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AppBuilderAppDeploymentCacheModel)) {
			return false;
		}

		AppBuilderAppDeploymentCacheModel appBuilderAppDeploymentCacheModel =
			(AppBuilderAppDeploymentCacheModel)object;

		if (appBuilderAppDeploymentId ==
				appBuilderAppDeploymentCacheModel.appBuilderAppDeploymentId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, appBuilderAppDeploymentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{appBuilderAppDeploymentId=");
		sb.append(appBuilderAppDeploymentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", appBuilderAppId=");
		sb.append(appBuilderAppId);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AppBuilderAppDeployment toEntityModel() {
		AppBuilderAppDeploymentImpl appBuilderAppDeploymentImpl =
			new AppBuilderAppDeploymentImpl();

		appBuilderAppDeploymentImpl.setAppBuilderAppDeploymentId(
			appBuilderAppDeploymentId);
		appBuilderAppDeploymentImpl.setCompanyId(companyId);
		appBuilderAppDeploymentImpl.setAppBuilderAppId(appBuilderAppId);

		if (settings == null) {
			appBuilderAppDeploymentImpl.setSettings("");
		}
		else {
			appBuilderAppDeploymentImpl.setSettings(settings);
		}

		if (type == null) {
			appBuilderAppDeploymentImpl.setType("");
		}
		else {
			appBuilderAppDeploymentImpl.setType(type);
		}

		appBuilderAppDeploymentImpl.resetOriginalValues();

		return appBuilderAppDeploymentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		appBuilderAppDeploymentId = objectInput.readLong();

		companyId = objectInput.readLong();

		appBuilderAppId = objectInput.readLong();
		settings = (String)objectInput.readObject();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(appBuilderAppDeploymentId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(appBuilderAppId);

		if (settings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(settings);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long appBuilderAppDeploymentId;
	public long companyId;
	public long appBuilderAppId;
	public String settings;
	public String type;

}