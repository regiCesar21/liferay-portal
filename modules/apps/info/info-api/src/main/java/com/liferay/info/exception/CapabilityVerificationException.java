/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jorge Ferrer
 */
public class CapabilityVerificationException extends RuntimeException {

	public CapabilityVerificationException(
		InfoItemCapability infoItemCapability, String infoItemClassName,
		List<Class<?>> missingServiceClasses) {

		_infoItemCapability = infoItemCapability;
		_infoItemClassName = infoItemClassName;
		_missingServiceClasses = missingServiceClasses;
	}

	public InfoItemCapability getInfoItemCapability() {
		return _infoItemCapability;
	}

	public String getInfoItemClassName() {
		return _infoItemClassName;
	}

	@Override
	public String getMessage() {
		StringBundler sb = new StringBundler(8);

		sb.append("Failed validation of capability ");
		sb.append(_infoItemCapability.getKey());
		sb.append(" for item class name ");
		sb.append(_infoItemClassName);
		sb.append(". An implementation for the following services is ");
		sb.append("required: ");
		sb.append(_getMissingServiceClassNames());
		sb.append(".");

		return sb.toString();
	}

	public List<Class<?>> getMissingServiceClasses() {
		return _missingServiceClasses;
	}

	private String _getMissingServiceClassNames() {
		Stream<Class<?>> stream = _missingServiceClasses.stream();

		return stream.map(
			clazz -> clazz.getName()
		).collect(
			Collectors.joining(", ")
		);
	}

	private final InfoItemCapability _infoItemCapability;
	private final String _infoItemClassName;
	private final List<Class<?>> _missingServiceClasses;

}