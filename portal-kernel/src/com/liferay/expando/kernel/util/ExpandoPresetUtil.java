/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author     Raymond Augé
 * @author     Drew Brokke
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class ExpandoPresetUtil {

	public static int addPresetExpando(
			ExpandoBridge expandoBridge, String preset, String name)
		throws Exception {

		int type = 0;

		UnicodeProperties unicodeProperties = null;

		try {
			unicodeProperties = expandoBridge.getAttributeProperties(name);
		}
		catch (Exception exception) {
			unicodeProperties = new UnicodeProperties();
		}

		if (preset.equals("PresetSelectionIntegerArray()")) {
			type = ExpandoColumnConstants.INTEGER_ARRAY;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionDoubleArray()")) {
			type = ExpandoColumnConstants.DOUBLE_ARRAY;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionStringArray()")) {
			type = ExpandoColumnConstants.STRING_ARRAY;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetTextBox()")) {
			type = ExpandoColumnConstants.STRING;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
		}
		else if (preset.equals("PresetTextBoxIndexed()")) {
			type = ExpandoColumnConstants.STRING;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}
		else if (preset.equals("PresetTextFieldSecret()")) {
			type = ExpandoColumnConstants.STRING;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.PROPERTY_SECRET,
				Boolean.TRUE.toString());
		}
		else {
			type = ExpandoColumnConstants.STRING;

			unicodeProperties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}

		expandoBridge.addAttribute(name, type);

		expandoBridge.setAttributeProperties(name, unicodeProperties);

		return type;
	}

}