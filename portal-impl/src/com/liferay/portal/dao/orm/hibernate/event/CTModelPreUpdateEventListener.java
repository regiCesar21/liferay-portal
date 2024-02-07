/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate.event;

import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.change.tracking.CTModel;

import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

/**
 * @author David Truong
 */
public class CTModelPreUpdateEventListener implements PreUpdateEventListener {

	public static final CTModelPreUpdateEventListener INSTANCE =
		new CTModelPreUpdateEventListener();

	@Override
	public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
		Object entity = preUpdateEvent.getEntity();

		if (entity instanceof CTModel) {
			CTModel<?> ctModel = (CTModel<?>)entity;

			if (ctModel.getCtCollectionId() !=
					CTCollectionThreadLocal.getCTCollectionId()) {

				return true;
			}
		}

		return false;
	}

}