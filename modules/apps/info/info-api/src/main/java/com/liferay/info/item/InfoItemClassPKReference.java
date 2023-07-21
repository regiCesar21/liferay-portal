/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

/**
 * @author     Jorge Ferrer
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class InfoItemClassPKReference extends InfoItemReference {

	public InfoItemClassPKReference(String className, long classPK) {
		super(className, classPK);
	}

	@Override
	public long getClassPK() {
		ClassPKInfoItemIdentifier infoItemIdentifier =
			(ClassPKInfoItemIdentifier)getInfoItemIdentifier();

		return infoItemIdentifier.getClassPK();
	}

}