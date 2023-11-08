/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.marketplace.rest.internal.graphql.query.v1_0;

import com.liferay.osb.provisioning.marketplace.rest.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.resource.v1_0.AppLicenseKeyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public class Query {

	public static void setAppLicenseKeyResourceComponentServiceObjects(
		ComponentServiceObjects<AppLicenseKeyResource>
			appLicenseKeyResourceComponentServiceObjects) {

		_appLicenseKeyResourceComponentServiceObjects =
			appLicenseKeyResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {appLicenseKeys(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the app license keys. Results can be paginated, filtered, searched, and sorted."
	)
	public AppLicenseKeyPage appLicenseKeys(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_appLicenseKeyResourceComponentServiceObjects,
			this::_populateResourceContext,
			appLicenseKeyResource -> new AppLicenseKeyPage(
				appLicenseKeyResource.getAppLicenseKeysPage(
					search,
					_filterBiFunction.apply(
						appLicenseKeyResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						appLicenseKeyResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {appLicenseKey(appLicenseKeyId: ___){accountKey, active, complimentary, createDate, description, expirationDate, hostName, id, ipAddresses, key, licenseType, macAddresses, modifiedDate, modifiedUserName, modifiedUserUuid, orderId, owner, productId, productKey, productName, productPurchaseKey, productVersion, startDate, userName, userUuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the app license key.")
	public AppLicenseKey appLicenseKey(
			@GraphQLName("appLicenseKeyId") Long appLicenseKeyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_appLicenseKeyResourceComponentServiceObjects,
			this::_populateResourceContext,
			appLicenseKeyResource -> appLicenseKeyResource.getAppLicenseKey(
				appLicenseKeyId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {appLicenseKeyDownload(appLicenseKeyId: ___){}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Downloads an XML of the app license key.")
	public Response appLicenseKeyDownload(
			@GraphQLName("appLicenseKeyId") Long appLicenseKeyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_appLicenseKeyResourceComponentServiceObjects,
			this::_populateResourceContext,
			appLicenseKeyResource ->
				appLicenseKeyResource.getAppLicenseKeyDownload(
					appLicenseKeyId));
	}

	@GraphQLTypeExtension(AppLicenseKey.class)
	public class GetAppLicenseKeyDownloadTypeExtension {

		public GetAppLicenseKeyDownloadTypeExtension(
			AppLicenseKey appLicenseKey) {

			_appLicenseKey = appLicenseKey;
		}

		@GraphQLField(description = "Downloads an XML of the app license key.")
		public Response download() throws Exception {
			return _applyComponentServiceObjects(
				_appLicenseKeyResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				appLicenseKeyResource ->
					appLicenseKeyResource.getAppLicenseKeyDownload(
						_appLicenseKey.getId()));
		}

		private AppLicenseKey _appLicenseKey;

	}

	@GraphQLName("AppLicenseKeyPage")
	public class AppLicenseKeyPage {

		public AppLicenseKeyPage(Page appLicenseKeyPage) {
			actions = appLicenseKeyPage.getActions();

			items = appLicenseKeyPage.getItems();
			lastPage = appLicenseKeyPage.getLastPage();
			page = appLicenseKeyPage.getPage();
			pageSize = appLicenseKeyPage.getPageSize();
			totalCount = appLicenseKeyPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AppLicenseKey> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			AppLicenseKeyResource appLicenseKeyResource)
		throws Exception {

		appLicenseKeyResource.setContextAcceptLanguage(_acceptLanguage);
		appLicenseKeyResource.setContextCompany(_company);
		appLicenseKeyResource.setContextHttpServletRequest(_httpServletRequest);
		appLicenseKeyResource.setContextHttpServletResponse(
			_httpServletResponse);
		appLicenseKeyResource.setContextUriInfo(_uriInfo);
		appLicenseKeyResource.setContextUser(_user);
		appLicenseKeyResource.setGroupLocalService(_groupLocalService);
		appLicenseKeyResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AppLicenseKeyResource>
		_appLicenseKeyResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}