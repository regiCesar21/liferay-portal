/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.internal.item.provider;

import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.type.categorization.Category;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoCollectionTextFormatter.class)
public class CategoryCommaSeparatedLabelsInfoTextFormatter
	implements InfoCollectionTextFormatter<Category> {

	@Override
	public String format(Collection<Category> categories, Locale locale) {
		Stream<Category> stream = categories.stream();

		return stream.map(
			assetCategory -> {
				String title = assetCategory.getLabel(locale);

				if (Validator.isNull(title)) {
					return assetCategory.getKey();
				}

				return title;
			}
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

}