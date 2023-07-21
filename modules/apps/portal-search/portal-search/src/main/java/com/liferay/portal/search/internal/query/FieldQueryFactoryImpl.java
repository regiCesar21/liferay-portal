/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;
import com.liferay.portal.search.internal.analysis.DescriptionFieldQueryBuilder;

import java.util.HashSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FieldQueryFactory.class)
public class FieldQueryFactoryImpl implements FieldQueryFactory {

	@Override
	public Query createQuery(
		String fieldName, String keywords, boolean like,
		boolean splitKeywords) {

		FieldQueryBuilder fieldQueryBuilder = getQueryBuilder(fieldName);

		return fieldQueryBuilder.build(fieldName, keywords);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addFieldQueryBuilderFactory(
		FieldQueryBuilderFactory fieldQueryBuilderFactory) {

		_fieldQueryBuilderFactories.add(fieldQueryBuilderFactory);
	}

	protected FieldQueryBuilder getDefaultQueryBuilder() {
		return descriptionFieldQueryBuilder;
	}

	protected FieldQueryBuilder getQueryBuilder(String fieldName) {
		for (FieldQueryBuilderFactory fieldQueryBuilderFactory :
				_fieldQueryBuilderFactories) {

			FieldQueryBuilder fieldQueryBuilder =
				fieldQueryBuilderFactory.getQueryBuilder(fieldName);

			if (fieldQueryBuilder != null) {
				return fieldQueryBuilder;
			}
		}

		return getDefaultQueryBuilder();
	}

	protected void removeFieldQueryBuilderFactory(
		FieldQueryBuilderFactory fieldQueryBuilderFactory) {

		_fieldQueryBuilderFactories.remove(fieldQueryBuilderFactory);
	}

	@Reference
	protected DescriptionFieldQueryBuilder descriptionFieldQueryBuilder;

	private final HashSet<FieldQueryBuilderFactory>
		_fieldQueryBuilderFactories = new HashSet<>();

}