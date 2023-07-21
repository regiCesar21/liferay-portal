/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.util.comparator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class AMPropertyDistanceComparator
	implements AMDistanceComparator<AdaptiveMedia<AMImageProcessor>> {

	public AMPropertyDistanceComparator(
		Map<AMAttribute<AMImageProcessor, ?>, ?> amAttributes) {

		_amAttributes = amAttributes;
	}

	@Override
	public long compare(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AMImageProcessor> adaptiveMedia2) {

		for (Map.Entry<AMAttribute<AMImageProcessor, ?>, ?> entry :
				_amAttributes.entrySet()) {

			AMAttribute<AMImageProcessor, Object> amAttribute =
				(AMAttribute<AMImageProcessor, Object>)entry.getKey();

			Object requestedValue = entry.getValue();

			Optional<?> valueOptional1 = adaptiveMedia1.getValueOptional(
				amAttribute);

			Optional<Long> valueDistanceOptional1 = valueOptional1.map(
				value1 -> amAttribute.distance(value1, requestedValue));

			Optional<?> valueOptional2 = adaptiveMedia2.getValueOptional(
				amAttribute);

			Optional<Long> valueDistanceOptional2 = valueOptional2.map(
				value2 -> amAttribute.distance(value2, requestedValue));

			Optional<Long> resultOptional = valueDistanceOptional1.flatMap(
				value1 -> valueDistanceOptional2.map(
					value2 -> value1 - value2));

			long result = resultOptional.orElse(0L);

			if (result != 0) {
				return result;
			}
		}

		return 0L;
	}

	private final Map<AMAttribute<AMImageProcessor, ?>, ?> _amAttributes;

}