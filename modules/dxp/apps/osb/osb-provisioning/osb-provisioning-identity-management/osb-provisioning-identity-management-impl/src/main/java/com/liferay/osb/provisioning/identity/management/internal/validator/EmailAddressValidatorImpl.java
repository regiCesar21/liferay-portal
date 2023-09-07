/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.identity.management.internal.validator;

import com.liferay.osb.provisioning.identity.management.validator.EmailAddressValidator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Will Newbury
 */
@Component(immediate = true, service = EmailAddressValidator.class)
public class EmailAddressValidatorImpl implements EmailAddressValidator {

	public boolean isLiferayDomain(String emailAddress) throws Exception {
		String domain = emailAddress.substring(
			emailAddress.indexOf(StringPool.AT) + 1);

		if (_liferayDomains.contains(domain)) {
			return true;
		}

		return false;
	}

	public void validateDomain(String emailAddress) throws Exception {
		if (isLiferayDomain(emailAddress)) {
			throw new EmailAddressException(
				"Email address uses a reserved Liferay domain");
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		try {
			StringUtil.readLines(
				EmailAddressValidatorImpl.class.getResourceAsStream(
					"/dependencies/liferay_domains.txt"),
				_liferayDomains);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmailAddressValidatorImpl.class);

	private final Set<String> _liferayDomains = new HashSet<>();

}