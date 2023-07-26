/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.provisioning.identity.management.internal.validator;

import com.liferay.osb.provisioning.identity.management.validator.EmailAddressValidator;
import com.liferay.petra.string.StringPool;
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
@Component(
	immediate = true, property = "validation=reserved",
	service = EmailAddressValidator.class
)
public class ReservedDomainEmailAddressValidator
	implements EmailAddressValidator {

	public boolean validateEmailAddress(String emailAddress) throws Exception {
		String domain = emailAddress.substring(
			emailAddress.indexOf(StringPool.AT) + 1);

		if (_reservedDomains.contains(domain)) {
			return false;
		}

		return true;
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		try {
			StringUtil.readLines(
				ReservedDomainEmailAddressValidator.class.getResourceAsStream(
					"/dependencies/reserved_domains.txt"),
				_reservedDomains);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReservedDomainEmailAddressValidator.class);

	private final Set<String> _reservedDomains = new HashSet<>();

}