/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util.comparator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergio González
 */
public class AMAttributeDistanceComparator
	implements AMDistanceComparator<AdaptiveMedia<AMImageProcessor>> {

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute) {

		this(
			Collections.singletonMap(
				amAttribute, AMImageQueryBuilder.SortOrder.ASC));
	}

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute,
		AMImageQueryBuilder.SortOrder sortOrder) {

		this(Collections.singletonMap(amAttribute, sortOrder));
	}

	public AMAttributeDistanceComparator(
		Map<AMAttribute<AMImageProcessor, ?>, AMImageQueryBuilder.SortOrder>
			sortCriteria) {

		_sortCriteria = (Map)sortCriteria;
	}

	@Override
	public long compare(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AMImageProcessor> adaptiveMedia2) {

		for (Map.Entry
				<AMAttribute<AMImageProcessor, Object>,
				 AMImageQueryBuilder.SortOrder> sortCriterion :
					_sortCriteria.entrySet()) {

			AMAttribute<AMImageProcessor, Object> amAttribute =
				sortCriterion.getKey();

			Optional<?> valueOptional1 = adaptiveMedia1.getValueOptional(
				amAttribute);
			Optional<?> valueOptional2 = adaptiveMedia2.getValueOptional(
				amAttribute);

			Optional<Long> valueOptional3 = valueOptional1.flatMap(
				value1 -> valueOptional2.map(
					value2 -> amAttribute.compare(value1, value2)));

			AMImageQueryBuilder.SortOrder sortOrder = sortCriterion.getValue();

			long result = valueOptional3.map(
				sortOrder::getSortValue
			).orElse(
				0L
			);

			if (result != 0) {
				return result;
			}
		}

		return 0L;
	}

	private final Map
		<AMAttribute<AMImageProcessor, Object>, AMImageQueryBuilder.SortOrder>
			_sortCriteria;

}