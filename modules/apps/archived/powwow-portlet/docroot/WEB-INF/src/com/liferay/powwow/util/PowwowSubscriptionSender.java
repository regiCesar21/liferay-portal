/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.powwow.util;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.mail.internet.InternetAddress;

/**
 * @author Evan Thibodeau
 */
public class PowwowSubscriptionSender extends SubscriptionSender {

	public MailMessage getMailMessage(Locale locale) throws Exception {
		MailMessage mailMessage = new MailMessage(
			new InternetAddress(), new InternetAddress(),
			_getEmailNotificationSubject(locale),
			_getEmailNotificationBody(locale), true);

		processMailMessage(mailMessage, locale);

		return mailMessage;
	}

	private String _getEmailNotificationBody(Locale locale) {
		String processedBody = null;

		if (localizedBodyMap != null) {
			String localizedBody = localizedBodyMap.get(locale);

			if (Validator.isNull(localizedBody)) {
				Locale defaultLocale = LocaleUtil.getDefault();

				processedBody = localizedBodyMap.get(defaultLocale);
			}
			else {
				processedBody = localizedBody;
			}
		}
		else {
			processedBody = body;
		}

		return processedBody;
	}

	private String _getEmailNotificationSubject(Locale locale) {
		String processedSubject = null;

		if (localizedSubjectMap != null) {
			String localizedSubject = localizedSubjectMap.get(locale);

			if (Validator.isNull(localizedSubject)) {
				Locale defaultLocale = LocaleUtil.getDefault();

				processedSubject = localizedSubjectMap.get(defaultLocale);
			}
			else {
				processedSubject = localizedSubject;
			}
		}
		else {
			processedSubject = subject;
		}

		return processedSubject;
	}

}