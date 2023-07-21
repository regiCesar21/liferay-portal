/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=date",
	service = {
		DateDDMFormFieldValueRenderer.class, DDMFormFieldValueRenderer.class
	}
)
public class DateDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		return render(value.getString(locale), locale);
	}

	protected String render(String valueString, Locale locale) {
		if (Validator.isNotNull(valueString)) {
			try {
				SimpleDateFormat simpleDateFormat =
					(SimpleDateFormat)DateFormat.getDateInstance(
						DateFormat.SHORT,
						Optional.ofNullable(
							LocaleThreadLocal.getThemeDisplayLocale()
						).orElse(
							locale
						));

				String pattern = simpleDateFormat.toPattern();

				if (StringUtils.countMatches(pattern, "d") == 1) {
					pattern = StringUtil.replace(pattern, 'd', "dd");
				}

				if (StringUtils.countMatches(pattern, "M") == 1) {
					pattern = StringUtil.replace(pattern, 'M', "MM");
				}

				if (StringUtils.countMatches(pattern, "y") == 2) {
					pattern = StringUtil.replace(pattern, 'y', "yy");
				}

				Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
					pattern);

				return format.format(
					DateUtil.parseDate("yyyy-MM-dd", valueString, locale));
			}
			catch (ParseException parseException) {
				_log.error("Unable to parse date", parseException);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateDDMFormFieldValueRenderer.class);

}