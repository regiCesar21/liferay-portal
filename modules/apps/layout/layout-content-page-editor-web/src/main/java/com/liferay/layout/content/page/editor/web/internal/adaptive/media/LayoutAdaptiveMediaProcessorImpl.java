/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.adaptive.media;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration",
	immediate = true, service = LayoutAdaptiveMediaProcessor.class
)
public class LayoutAdaptiveMediaProcessorImpl
	implements LayoutAdaptiveMediaProcessor {

	@Override
	public String processAdaptiveMediaContent(String content) {
		if (_ffLayoutContentPageEditorConfiguration.adaptiveMediaEnabled()) {
			content = _contentTransformerHandler.transform(
				ContentTransformerContentTypes.HTML, content);
		}

		Document document = Jsoup.parse(content);

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffLayoutContentPageEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFLayoutContentPageEditorConfiguration.class, properties);
	}

	@Reference
	private ContentTransformerHandler _contentTransformerHandler;

	private volatile FFLayoutContentPageEditorConfiguration
		_ffLayoutContentPageEditorConfiguration;

}