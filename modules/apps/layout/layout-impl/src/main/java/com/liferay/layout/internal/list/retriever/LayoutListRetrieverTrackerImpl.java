/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.list.retriever;

import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.petra.reflect.GenericUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetrieverTracker.class)
public class LayoutListRetrieverTrackerImpl
	implements LayoutListRetrieverTracker {

	@Override
	public LayoutListRetriever<?, ?> getLayoutListRetriever(String type) {
		return _layoutListRetrievers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutListRetrievers(
		LayoutListRetriever<?, ?> layoutListRetriever) {

		_layoutListRetrievers.put(
			GenericUtil.getGenericClassName(layoutListRetriever),
			layoutListRetriever);
	}

	protected void unsetLayoutListRetrievers(
		LayoutListRetriever<?, ?> layoutListRetriever) {

		_layoutListRetrievers.remove(
			GenericUtil.getGenericClassName(layoutListRetriever));
	}

	private final Map<String, LayoutListRetriever<?, ?>> _layoutListRetrievers =
		new ConcurrentHashMap<>();

}