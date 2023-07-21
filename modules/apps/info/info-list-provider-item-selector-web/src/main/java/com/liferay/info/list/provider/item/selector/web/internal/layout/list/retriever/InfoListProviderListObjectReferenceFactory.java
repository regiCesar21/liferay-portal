/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.list.provider.item.selector.web.internal.layout.list.retriever;

import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ListObjectReferenceFactory.class)
public class InfoListProviderListObjectReferenceFactory
	implements ListObjectReferenceFactory
		<InfoListProviderItemSelectorReturnType> {

	@Override
	public ListObjectReference getListObjectReference(JSONObject jsonObject) {
		return new KeyListObjectReference(jsonObject);
	}

}