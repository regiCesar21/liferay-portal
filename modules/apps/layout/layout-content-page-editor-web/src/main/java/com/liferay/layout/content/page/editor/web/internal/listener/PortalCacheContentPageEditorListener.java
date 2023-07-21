/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.listener;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = ContentPageEditorListener.class)
public class PortalCacheContentPageEditorListener
	implements ContentPageEditorListener {

	@Override
	public void onAddFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onDeleteFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLinkConfigurationValues(
		FragmentEntryLink fragmentEntryLink) {

		_clearCache(fragmentEntryLink);
	}

	@Activate
	protected void activate() {
		_portalCache = (PortalCache<String, String>)_multiVMPool.getPortalCache(
			FragmentEntryLink.class.getName());
	}

	private void _clearCache(FragmentEntryLink fragmentEntryLink) {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			fragmentEntryLink.getGroupId());

		for (Locale locale : availableLocales) {
			StringBundler cacheKeySB = new StringBundler(5);

			cacheKeySB.append(fragmentEntryLink.getFragmentEntryLinkId());
			cacheKeySB.append(StringPool.DASH);
			cacheKeySB.append(locale);
			cacheKeySB.append(StringPool.DASH);
			cacheKeySB.append(fragmentEntryLink.getSegmentsExperienceId());

			_portalCache.remove(cacheKeySB.toString());
		}
	}

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, String> _portalCache;

}