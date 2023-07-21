/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.app.builder.workflow.rest.internal.graphql.servlet.v1_0;

import com.liferay.app.builder.workflow.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.app.builder.workflow.rest.internal.graphql.query.v1_0.Query;
import com.liferay.app.builder.workflow.rest.internal.resource.v1_0.AppWorkflowDataRecordLinkResourceImpl;
import com.liferay.app.builder.workflow.rest.internal.resource.v1_0.AppWorkflowResourceImpl;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowDataRecordLinkResource;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAppWorkflowResourceComponentServiceObjects(
			_appWorkflowResourceComponentServiceObjects);
		Mutation.setAppWorkflowDataRecordLinkResourceComponentServiceObjects(
			_appWorkflowDataRecordLinkResourceComponentServiceObjects);

		Query.setAppWorkflowResourceComponentServiceObjects(
			_appWorkflowResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.App.Builder.Workflow.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/app-builder-workflow-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#deleteAppWorkflow",
						new ObjectValuePair<>(
							AppWorkflowResourceImpl.class,
							"deleteAppWorkflow"));
					put(
						"mutation#createAppWorkflow",
						new ObjectValuePair<>(
							AppWorkflowResourceImpl.class, "postAppWorkflow"));
					put(
						"mutation#updateAppWorkflow",
						new ObjectValuePair<>(
							AppWorkflowResourceImpl.class, "putAppWorkflow"));
					put(
						"mutation#createAppAppWorkflowDataRecordLinksPage",
						new ObjectValuePair<>(
							AppWorkflowDataRecordLinkResourceImpl.class,
							"postAppAppWorkflowDataRecordLinksPage"));

					put(
						"query#appWorkflow",
						new ObjectValuePair<>(
							AppWorkflowResourceImpl.class, "getAppWorkflow"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AppWorkflowResource>
		_appWorkflowResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AppWorkflowDataRecordLinkResource>
		_appWorkflowDataRecordLinkResourceComponentServiceObjects;

}