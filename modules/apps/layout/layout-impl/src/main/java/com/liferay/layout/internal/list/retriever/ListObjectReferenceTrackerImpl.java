/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.list.retriever;

import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
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
@Component(immediate = true, service = ListObjectReferenceFactoryTracker.class)
public class ListObjectReferenceTrackerImpl
	implements ListObjectReferenceFactoryTracker {

	@Override
	public ListObjectReferenceFactory<?> getListObjectReference(String type) {
		return _listObjectReferenceFactories.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setListObjectReferenceFactories(
		ListObjectReferenceFactory<?> listObjectReferenceFactory) {

		_listObjectReferenceFactories.put(
			GenericUtil.getGenericClassName(listObjectReferenceFactory),
			listObjectReferenceFactory);
	}

	protected void unsetListObjectReferenceFactories(
		ListObjectReferenceFactory<?> listObjectReferenceFactory) {

		_listObjectReferenceFactories.remove(
			GenericUtil.getGenericClassName(listObjectReferenceFactory));
	}

	private final Map<String, ListObjectReferenceFactory<?>>
		_listObjectReferenceFactories = new ConcurrentHashMap<>();

}