/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate.event;

import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;

/**
 * @author David Truong
 */
public class CTModelPreDeleteEventListener implements PreDeleteEventListener {

	public static final CTModelPreDeleteEventListener INSTANCE =
		new CTModelPreDeleteEventListener();

	@Override
	public boolean onPreDelete(PreDeleteEvent preDeleteEvent) {
		Object entity = preDeleteEvent.getEntity();

		if (entity instanceof CTModel) {
			CTModel<?> ctModel = (CTModel<?>)entity;

			long ctCollectionId = CTCollectionThreadLocal.getCTCollectionId();

			if ((ctCollectionId == 0) &&
				(ctCollectionId != ctModel.getCtCollectionId())) {

				return true;
			}
		}

		return false;
	}

}