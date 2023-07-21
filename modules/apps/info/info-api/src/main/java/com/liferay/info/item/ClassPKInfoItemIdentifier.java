/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class ClassPKInfoItemIdentifier extends BaseInfoItemIdentifier {

	public ClassPKInfoItemIdentifier(long classPK) {
		_classPK = classPK;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ClassPKInfoItemIdentifier)) {
			return false;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)object;

		return Objects.equals(_classPK, classPKInfoItemIdentifier._classPK);
	}

	public long getClassPK() {
		return _classPK;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_classPK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");
		sb.append(ClassPKInfoItemIdentifier.class.getName());
		sb.append(", classPK=");
		sb.append(_classPK);
		sb.append("}");

		return sb.toString();
	}

	private final long _classPK;

}