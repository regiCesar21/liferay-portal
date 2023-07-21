/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = KeywordQueryContributorsHolder.class)
public class KeywordQueryContributorsHolderImpl
	implements KeywordQueryContributorsHolder {

	@Override
	public Stream<KeywordQueryContributor> stream(
		Collection<String> excludes, Collection<String> includes) {

		Stream<KeywordQueryContributor> stream = StreamSupport.stream(
			_serviceTrackerList.spliterator(), false);

		return IncludeExcludeUtil.stream(
			stream, includes, excludes,
			keywordQueryContributor -> getClassName(keywordQueryContributor));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, KeywordQueryContributor.class,
			"(!(indexer.class.name=*))");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected String getClassName(Object object) {
		Class<?> clazz = object.getClass();

		return clazz.getName();
	}

	private ServiceTrackerList<KeywordQueryContributor, KeywordQueryContributor>
		_serviceTrackerList;

}