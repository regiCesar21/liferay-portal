/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class WorkflowInstanceManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchCountWhenThereAreActiveParallelTasks()
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				FileUtil.getBytes(
					_getResourceInputStream(
						"join-xor-workflow-definition.xml")));

		ServiceRegistration<WorkflowHandler<?>>
			workflowHandlerServiceRegistration = _registryWorkflowHandler(
				workflowDefinition.getName());

		Class<?> clazz = getClass();

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
			clazz.getName(), 1, null, new ServiceContext());

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.getWorkflowInstanceLink(
				TestPropsValues.getCompanyId(), 0, clazz.getName(), 1);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				workflowInstanceLink.getCompanyId(),
				workflowInstanceLink.getWorkflowInstanceId());

		_kaleoInstanceLocalService.completeKaleoInstance(
			workflowInstance.getWorkflowInstanceId());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
			clazz.getName(), 2, null, new ServiceContext());

		Assert.assertEquals(
			1,
			_workflowInstanceManager.searchCount(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, workflowDefinition.getName(), false));

		Assert.assertEquals(
			1,
			_workflowInstanceManager.searchCount(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, workflowDefinition.getName(), true));

		Assert.assertEquals(
			2,
			_workflowInstanceManager.searchCount(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, workflowDefinition.getName(), null));

		workflowHandlerServiceRegistration.unregister();
	}

	private InputStream _getResourceInputStream(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/dependencies/" + name);
	}

	private ServiceRegistration<WorkflowHandler<?>> _registryWorkflowHandler(
		String workflowDefinitionName) {

		Class<?> clazz = getClass();

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		BundleContext bundleContext = bundle.getBundleContext();

		return bundleContext.registerService(
			(Class<WorkflowHandler<?>>)(Class<?>)WorkflowHandler.class,
			(WorkflowHandler)ProxyUtil.newProxyInstance(
				WorkflowHandler.class.getClassLoader(),
				new Class<?>[] {WorkflowHandler.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getClassName")) {
						return clazz.getName();
					}

					if (Objects.equals(method.getName(), "getType")) {
						return StringPool.BLANK;
					}

					if (Objects.equals(method.getName(), "isScopeable")) {
						return false;
					}

					if (Objects.equals(
							method.getName(), "getWorkflowDefinitionLink")) {

						return _workflowDefinitionLinkLocalService.
							updateWorkflowDefinitionLink(
								TestPropsValues.getUserId(),
								TestPropsValues.getCompanyId(), 0,
								clazz.getName(), 0, 0, workflowDefinitionName,
								1);
					}

					if (Objects.equals(
							method.getName(), "startWorkflowInstance")) {

						_workflowInstanceLinkLocalService.startWorkflowInstance(
							TestPropsValues.getCompanyId(), 0,
							TestPropsValues.getUserId(), clazz.getName(), 1,
							(Map<String, Serializable>)args[5]);
					}

					return null;
				}),
			HashMapDictionaryBuilder.put(
				"model.class.name=", clazz.getName()
			).build());
	}

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

}