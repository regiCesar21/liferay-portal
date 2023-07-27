/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayCardTag;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class UserCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayUserCard");

		if (_userCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setImageAlt(String imageAlt) {
		putValue("imageAlt", imageAlt);
	}

	public void setImageSrc(String imageSrc) {
		putValue("imageSrc", imageSrc);
	}

	public void setName(String name) {
		if (_userCard.getName() != null) {
			putValue("name", HtmlUtil.escapeAttribute(name));
		}
		else {
			putValue("name", name);
		}
	}

	public void setSubtitle(String subtitle) {
		putValue("subtitle", subtitle);
	}

	public void setUserCard(UserCard userCard) {
		_userCard = userCard;

		super.setBaseClayCard(userCard);
	}

	public void setUserColorClass(String userColorClass) {
		putValue("userColorClass", userColorClass);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setUserColorClass(String)}
	 */
	@Deprecated
	public void setUserColorCssClass(String userColorCssClass) {
		setUserColorClass(userColorCssClass);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("imageAlt") == null) {
			setImageAlt(_userCard.getImageAlt());
		}

		if (context.get("imageSrc") == null) {
			setImageSrc(_userCard.getImageSrc());
		}

		if (context.get("name") == null) {
			setName(_userCard.getName());
		}

		if (context.get("subtitle") == null) {
			setSubtitle(_userCard.getSubtitle());
		}

		if (context.get("userColorClass") == null) {
			setUserColorClass(_userCard.getUserColorClass());
		}
	}

	private UserCard _userCard;

}