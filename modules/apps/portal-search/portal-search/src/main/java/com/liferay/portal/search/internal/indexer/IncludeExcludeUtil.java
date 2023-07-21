/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class IncludeExcludeUtil {

	public static <T> Stream<T> stream(
		Stream<T> stream, Collection<String> includeIds,
		Collection<String> excludeIds, Function<T, String> function) {

		return exclude(
			include(stream, includeIds, function), excludeIds, function);
	}

	protected static <T> Stream<T> exclude(
		Stream<T> stream, Collection<String> ids,
		Function<T, String> function) {

		return filter(stream, ids, t -> !isPresent(t, ids, function));
	}

	protected static <T> Stream<T> filter(
		Stream<T> stream, Collection<String> ids,
		Predicate<? super T> predicate) {

		if ((ids == null) || ids.isEmpty()) {
			return stream;
		}

		return stream.filter(predicate);
	}

	protected static <T> Stream<T> include(
		Stream<T> stream, Collection<String> ids,
		Function<T, String> function) {

		return filter(stream, ids, t -> isPresent(t, ids, function));
	}

	protected static <T> boolean isPresent(
		T t, Collection<String> ids, Function<T, String> function) {

		return ids.contains(function.apply(t));
	}

}