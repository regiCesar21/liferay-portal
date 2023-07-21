/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.list.provider.item.selector.web.internal.layout.list.retriever;

import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class InfoListProviderLayoutListRetriever
	implements LayoutListRetriever
		<InfoListProviderItemSelectorReturnType, KeyListObjectReference> {

	@Override
	public List<Object> getList(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoListProvider<Object> infoListProvider =
			(InfoListProvider<Object>)
				_infoListProviderTracker.getInfoListProvider(
					keyListObjectReference.getKey());

		if (infoListProvider == null) {
			return Collections.emptyList();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Group group = _groupLocalService.fetchGroup(
			serviceContext.getScopeGroupId());

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		InfoListProviderContext infoListProviderContext =
			new DefaultInfoListProviderContext(group, user);

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		Pagination pagination = paginationOptional.orElse(
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return infoListProvider.getInfoList(
			infoListProviderContext, pagination, null);
	}

	@Override
	public int getListCount(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoListProvider<?> infoListProvider =
			_infoListProviderTracker.getInfoListProvider(
				keyListObjectReference.getKey());

		if (infoListProvider == null) {
			return 0;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Group group = _groupLocalService.fetchGroup(
			serviceContext.getScopeGroupId());

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		InfoListProviderContext infoListProviderContext =
			new DefaultInfoListProviderContext(group, user);

		return infoListProvider.getInfoListCount(infoListProviderContext);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoListProviderTracker _infoListProviderTracker;

	@Reference
	private UserLocalService _userLocalService;

}