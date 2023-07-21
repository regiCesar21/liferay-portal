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
public class GroupUrlTitleInfoItemIdentifier extends BaseInfoItemIdentifier {

	public GroupUrlTitleInfoItemIdentifier(long groupId, String urlTitle) {
		_groupId = groupId;
		_urlTitle = urlTitle;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GroupUrlTitleInfoItemIdentifier)) {
			return false;
		}

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
			(GroupUrlTitleInfoItemIdentifier)object;

		if (Objects.equals(
				_groupId, groupUrlTitleInfoItemIdentifier._groupId) &&
			Objects.equals(
				_urlTitle, groupUrlTitleInfoItemIdentifier._urlTitle)) {

			return true;
		}

		return false;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_groupId, _urlTitle);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{className=");
		sb.append(GroupKeyInfoItemIdentifier.class.getName());
		sb.append(", _groupId=");
		sb.append(_groupId);
		sb.append(", _urlTitle=");
		sb.append(_urlTitle);
		sb.append("}");

		return sb.toString();
	}

	private final long _groupId;
	private final String _urlTitle;

}