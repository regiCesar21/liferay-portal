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
public class GroupKeyInfoItemIdentifier extends BaseInfoItemIdentifier {

	public GroupKeyInfoItemIdentifier(long groupId, String key) {
		_groupId = groupId;
		_key = key;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GroupKeyInfoItemIdentifier)) {
			return false;
		}

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier =
			(GroupKeyInfoItemIdentifier)object;

		if (Objects.equals(_groupId, groupKeyInfoItemIdentifier._groupId) &&
			Objects.equals(_key, groupKeyInfoItemIdentifier._key)) {

			return true;
		}

		return false;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKey() {
		return _key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_groupId, _key);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");
		sb.append(GroupKeyInfoItemIdentifier.class.getName());
		sb.append(", _groupId=");
		sb.append(_groupId);
		sb.append(", _key=");
		sb.append(_key);
		sb.append("}");

		return sb.toString();
	}

	private final long _groupId;
	private final String _key;

}