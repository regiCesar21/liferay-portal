/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.marketplace.rest.internal.graphql.servlet.v1_0;

import com.liferay.osb.provisioning.marketplace.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.osb.provisioning.marketplace.rest.internal.graphql.query.v1_0.Query;
import com.liferay.osb.provisioning.marketplace.rest.internal.resource.v1_0.AppLicenseKeyResourceImpl;
import com.liferay.osb.provisioning.marketplace.rest.resource.v1_0.AppLicenseKeyResource;
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
 * @author Amos Fong
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setAppLicenseKeyResourceComponentServiceObjects(
			_appLicenseKeyResourceComponentServiceObjects);

		Query.setAppLicenseKeyResourceComponentServiceObjects(
			_appLicenseKeyResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Provisioning.Marketplace.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/provisioning-marketplace-rest-graphql/v1_0";
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
						"mutation#createAppLicenseKey",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"postAppLicenseKey"));
					put(
						"mutation#updateAppLicenseKeyActivate",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"putAppLicenseKeyActivate"));
					put(
						"mutation#updateAppLicenseKeyDeactivate",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"putAppLicenseKeyDeactivate"));

					put(
						"query#appLicenseKeys",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"getAppLicenseKeysPage"));
					put(
						"query#appLicenseKey",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"getAppLicenseKey"));
					put(
						"query#appLicenseKeyDownload",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"getAppLicenseKeyDownload"));

					put(
						"query#AppLicenseKey.download",
						new ObjectValuePair<>(
							AppLicenseKeyResourceImpl.class,
							"getAppLicenseKeyDownload"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AppLicenseKeyResource>
		_appLicenseKeyResourceComponentServiceObjects;

}