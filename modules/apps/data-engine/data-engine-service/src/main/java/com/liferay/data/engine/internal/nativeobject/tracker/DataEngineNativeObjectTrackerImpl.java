/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.nativeobject.tracker;

import com.liferay.data.engine.nativeobject.DataEngineNativeObject;
import com.liferay.data.engine.nativeobject.tracker.DataEngineNativeObjectTracker;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DataEngineNativeObjectTracker.class)
public class DataEngineNativeObjectTrackerImpl
	implements DataEngineNativeObjectTracker {

	@Override
	public DataEngineNativeObject getDataEngineNativeObject(String className) {
		return _dataEngineNativeObjects.get(StringUtil.toUpperCase(className));
	}

	@Override
	public Collection<DataEngineNativeObject> getDataEngineNativeObjects() {
		return _dataEngineNativeObjects.values();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataEngineNativeObject(
			DataEngineNativeObject dataEngineNativeObject)
		throws Exception {

		_dataEngineNativeObjects.put(
			StringUtil.toUpperCase(dataEngineNativeObject.getClassName()),
			dataEngineNativeObject);
	}

	protected void removeDataEngineNativeObject(
		DataEngineNativeObject dataEngineNativeObject) {

		_dataEngineNativeObjects.remove(
			StringUtil.toUpperCase(dataEngineNativeObject.getClassName()));
	}

	private final Map<String, DataEngineNativeObject> _dataEngineNativeObjects =
		new ConcurrentHashMap<>();

}