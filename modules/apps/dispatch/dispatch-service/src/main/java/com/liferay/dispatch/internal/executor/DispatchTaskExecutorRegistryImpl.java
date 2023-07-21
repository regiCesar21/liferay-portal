/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.executor;

import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Matija Petanjek
 */
@Component(service = DispatchTaskExecutorRegistry.class)
public class DispatchTaskExecutorRegistryImpl
	implements DispatchTaskExecutorRegistry {

	@Override
	public DispatchTaskExecutor fetchDispatchTaskExecutor(
		String dispatchTaskExecutorType) {

		return _dispatchTaskExecutors.get(dispatchTaskExecutorType);
	}

	@Override
	public String fetchDispatchTaskExecutorName(
		String dispatchTaskExecutorType) {

		return _dispatchTaskExecutorNames.get(dispatchTaskExecutorType);
	}

	@Override
	public Set<String> getDispatchTaskExecutorTypes() {
		return _dispatchTaskExecutorNames.keySet();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDispatchTaskExecutor(
		DispatchTaskExecutor dispatchTaskExecutor,
		Map<String, Object> properties) {

		String dispatchTaskExecutorType = (String)properties.get(
			_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

		_validateDispatchTaskExecutorProperties(
			dispatchTaskExecutor, dispatchTaskExecutorType);

		_dispatchTaskExecutorNames.put(
			dispatchTaskExecutorType,
			(String)properties.get(_KEY_DISPATCH_TASK_EXECUTOR_NAME));
		_dispatchTaskExecutors.put(
			dispatchTaskExecutorType, dispatchTaskExecutor);
	}

	protected void removeDispatchTaskExecutor(
		DispatchTaskExecutor dispatchTaskExecutor,
		Map<String, Object> properties) {

		String dispatchTaskExecutorType = (String)properties.get(
			_KEY_DISPATCH_TASK_EXECUTOR_TYPE);

		_dispatchTaskExecutorNames.remove(dispatchTaskExecutorType);
		_dispatchTaskExecutors.remove(dispatchTaskExecutorType);
	}

	private void _validateDispatchTaskExecutorProperties(
		DispatchTaskExecutor dispatchTaskExecutor,
		String dispatchTaskExecutorType) {

		if (!_dispatchTaskExecutors.containsKey(dispatchTaskExecutorType)) {
			return;
		}

		DispatchTaskExecutor curDispatchTaskExecutor =
			_dispatchTaskExecutors.get(dispatchTaskExecutorType);

		Class<?> clazz1 = curDispatchTaskExecutor.getClass();

		Class<?> clazz2 = dispatchTaskExecutor.getClass();

		_log.error(
			StringBundler.concat(
				_KEY_DISPATCH_TASK_EXECUTOR_TYPE, " property must have unique ",
				"value. The same value is found in ", clazz1.getName(), " and ",
				clazz2.getName(), StringPool.PERIOD));
	}

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_NAME =
		"dispatch.task.executor.name";

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_TYPE =
		"dispatch.task.executor.type";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTaskExecutorRegistryImpl.class);

	private final Map<String, String> _dispatchTaskExecutorNames =
		new HashMap<>();
	private final Map<String, DispatchTaskExecutor> _dispatchTaskExecutors =
		new HashMap<>();

}