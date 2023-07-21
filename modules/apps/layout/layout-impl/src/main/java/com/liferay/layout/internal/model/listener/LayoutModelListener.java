/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.model.listener;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterRemove(Layout layout) {
		if (layout == null) {
			return;
		}

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			layout.getGroupId(),
			_layoutFriendlyURLEntryHelper.getClassNameId(
				layout.isPrivateLayout()),
			layout.getPlid());

		if (layout.isTypeAssetDisplay()) {
			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				layout.getGroupId(),
				_layoutFriendlyURLEntryHelper.getClassNameId(
					!layout.isPrivateLayout()),
				layout.getPlid());
		}
	}

	@Override
	public void onBeforeRemove(Layout layout) throws ModelListenerException {
		_layoutClassedModelUsageLocalService.
			deleteLayoutClassedModelUsagesByPlid(layout.getPlid());
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}