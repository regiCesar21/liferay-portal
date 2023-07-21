/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.type;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Jorge Ferrer
 */
public class WebImage {

	public WebImage(String url) {
		_url = url;
	}

	public String getAlt() {
		if (_altInfoLocalizedValue != null) {
			return _altInfoLocalizedValue.getValue(LocaleUtil.getDefault());
		}

		return StringPool.BLANK;
	}

	public Optional<InfoLocalizedValue<String>>
		getAltInfoLocalizedValueOptional() {

		return Optional.ofNullable(_altInfoLocalizedValue);
	}

	public String getUrl() {
		return _url;
	}

	public WebImage setAlt(String alt) {
		_altInfoLocalizedValue = InfoLocalizedValue.singleValue(alt);

		return this;
	}

	public WebImage setAltInfoLocalizedValue(
		InfoLocalizedValue<String> altInfoLocalizedValue) {

		_altInfoLocalizedValue = altInfoLocalizedValue;

		return this;
	}

	public JSONObject toJSONObject() {
		return toJSONObject(LocaleUtil.getDefault());
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONUtil.put("url", _url);

		if (_altInfoLocalizedValue != null) {
			jsonObject = jsonObject.put(
				"alt", _altInfoLocalizedValue.getValue(locale));
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		return getUrl();
	}

	private InfoLocalizedValue<String> _altInfoLocalizedValue;
	private final String _url;

}