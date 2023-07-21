/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.internal.list.renderer;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoListRendererTracker.class)
public class InfoListRendererTrackerImpl implements InfoListRendererTracker {

	@Override
	public InfoListRenderer<?> getInfoListRenderer(String key) {
		return _infoItemServiceTracker.getInfoItemService(
			InfoListRenderer.class, key);
	}

	@Override
	public List<InfoListRenderer<?>> getInfoListRenderers() {
		return (List<InfoListRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListRenderer.class);
	}

	@Override
	public List<InfoListRenderer<?>> getInfoListRenderers(
		String itemClassName) {

		return (List<InfoListRenderer<?>>)
			(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
				InfoListRenderer.class, itemClassName);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}