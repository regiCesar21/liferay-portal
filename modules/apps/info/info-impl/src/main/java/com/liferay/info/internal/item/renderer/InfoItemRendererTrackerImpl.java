/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.item.renderer;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemRendererTracker.class)
public class InfoItemRendererTrackerImpl implements InfoItemRendererTracker {

	@Override
	public InfoItemRenderer<?> getInfoItemRenderer(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoItemRenderer.class, key);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers() {
		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemRenderer.class);
	}

	@Override
	public List<InfoItemRenderer<?>> getInfoItemRenderers(
		String itemClassName) {

		return (List<InfoItemRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoItemRenderer.class, itemClassName);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}