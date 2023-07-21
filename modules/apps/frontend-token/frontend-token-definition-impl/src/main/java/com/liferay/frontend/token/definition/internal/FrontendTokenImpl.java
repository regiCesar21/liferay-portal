/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.frontend.token.definition.FrontendTokenSet;
import com.liferay.frontend.token.definition.internal.json.JSONLocalizer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Iván Zaera
 */
public class FrontendTokenImpl implements FrontendToken {

	public FrontendTokenImpl(
		FrontendTokenSetImpl frontendTokenSetImpl, JSONObject jsonObject) {

		_frontendTokenSetImpl = frontendTokenSetImpl;

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			frontendTokenSetImpl.getFrontendTokenDefinition();

		_jsonLocalizer = frontendTokenDefinitionImpl.createJSONLocalizer(
			jsonObject);

		_type = Type.parse(jsonObject.getString("type"));

		if (_type == Type.BOOLEAN) {
			_defaultValue = jsonObject.getBoolean("defaultValue");
		}
		else if (_type == Type.INT) {
			_defaultValue = jsonObject.getInt("defaultValue");
		}
		else if (_type == Type.DOUBLE) {
			_defaultValue = jsonObject.getDouble("defaultValue");
		}
		else if (_type == Type.STRING) {
			_defaultValue = jsonObject.getString("defaultValue");
		}
		else {
			throw new RuntimeException(
				"Unsupported frontend token type " + _type.toString());
		}

		JSONArray mappingsJSONArray = jsonObject.getJSONArray("mappings");

		if (mappingsJSONArray == null) {
			return;
		}

		for (int i = 0; i < mappingsJSONArray.length(); i++) {
			_frontendTokenMappings.add(
				new FrontendTokenMappingImpl(
					this, mappingsJSONArray.getJSONObject(i)));
		}
	}

	@Override
	public <T> T getDefaultValue() {
		return (T)_defaultValue;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings() {
		return _frontendTokenMappings;
	}

	@Override
	public Collection<FrontendTokenMapping> getFrontendTokenMappings(
		String type) {

		Stream<FrontendTokenMapping> stream = _frontendTokenMappings.stream();

		return stream.filter(
			frontendTokenMapping -> type.equals(frontendTokenMapping.getType())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public FrontendTokenSet getFrontendTokenSet() {
		return _frontendTokenSetImpl;
	}

	@Override
	public String getJSON(Locale locale) {
		return _jsonLocalizer.getJSON(locale);
	}

	@Override
	public Type getType() {
		return _type;
	}

	protected FrontendTokenDefinitionImpl getFrontendTokenDefinition() {
		return _frontendTokenSetImpl.getFrontendTokenDefinition();
	}

	private final Object _defaultValue;
	private final Collection<FrontendTokenMapping> _frontendTokenMappings =
		new ArrayList<>();
	private final FrontendTokenSetImpl _frontendTokenSetImpl;
	private final JSONLocalizer _jsonLocalizer;
	private final Type _type;

}