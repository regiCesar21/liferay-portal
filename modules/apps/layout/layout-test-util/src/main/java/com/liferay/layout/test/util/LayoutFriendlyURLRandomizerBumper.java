/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.test.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.model.impl.LayoutImpl;

/**
 * @author Shuyang Zhou
 */
public class LayoutFriendlyURLRandomizerBumper
	implements RandomizerBumper<String> {

	public static final LayoutFriendlyURLRandomizerBumper INSTANCE =
		new LayoutFriendlyURLRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if ((randomValue == null) || randomValue.isEmpty()) {
			return false;
		}

		if (randomValue.charAt(0) != CharPool.SLASH) {
			randomValue = StringPool.SLASH.concat(randomValue);
		}

		if (LayoutImpl.validateFriendlyURL(randomValue) != -1) {
			return false;
		}

		try {
			LayoutImpl.validateFriendlyURLKeyword(randomValue);

			return true;
		}
		catch (LayoutFriendlyURLException layoutFriendlyURLException) {
			return false;
		}
	}

}