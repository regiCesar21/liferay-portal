/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Carolina Barbosa
 */
public class TextHTMLDDMFormFieldUtil {

	public static String getHTML(String value) {
		if (StringUtil.equals(
				_TEXT_HTML_EDITOR_WYSIWYG, "alloyeditor_bbcode")) {

			return BBCodeTranslatorUtil.getHTML(value);
		}

		return value;
	}

	private static final String _TEXT_HTML_EDITOR_WYSIWYG = PropsUtil.get(
		"editor.wysiwyg.portal-impl.portlet.ddm.text_html.ftl");

}