/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.content.transformer.backwards.compatibility.internal;

import com.liferay.adaptive.media.content.transformer.BaseRegexStringContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, property = "content.transformer.content.type=html",
	service = ContentTransformer.class
)
public class AMBackwardsCompatibilityHtmlContentTransformer
	extends BaseRegexStringContentTransformer {

	@Override
	public ContentTransformerContentType<String>
		getContentTransformerContentType() {

		return ContentTransformerContentTypes.HTML;
	}

	@Override
	public String transform(String html) throws PortalException {
		if (html == null) {
			return null;
		}

		if (!html.contains("<img") || !html.contains("/documents/")) {
			return html;
		}

		Document document = _parseDocument(html);

		for (Element imgElement : document.select("img:not(picture > img)")) {
			String imgElementString = imgElement.toString();

			String replacement = _transform(
				imgElementString, imgElement.attr("src"));

			imgElement.replaceWith(_parseNode(replacement));
		}

		if (html.contains("<html>") || html.contains("<head>")) {
			return document.html();
		}

		Element body = document.body();

		return body.html();
	}

	@Override
	protected FileEntry getFileEntry(Matcher matcher) throws PortalException {
		if (matcher.group(4) != null) {
			long groupId = Long.valueOf(matcher.group(1));

			String uuid = matcher.group(4);

			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}

		long groupId = Long.valueOf(matcher.group(1));
		long folderId = Long.valueOf(matcher.group(2));
		String title = matcher.group(3);

		return _dlAppLocalService.getFileEntry(groupId, folderId, title);
	}

	@Override
	protected Pattern getPattern() {
		return _pattern;
	}

	@Override
	protected String getReplacement(String originalImgTag, FileEntry fileEntry)
		throws PortalException {

		if (fileEntry == null) {
			return originalImgTag;
		}

		return _amImageHTMLTagFactory.create(originalImgTag, fileEntry);
	}

	private Document _parseDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		document.outputSettings(outputSettings);

		return document;
	}

	private Node _parseNode(String tag) {
		Document document = _parseDocument(tag);

		Node bodyNode = document.body();

		return bodyNode.childNode(0);
	}

	private String _transform(String imgElementString, String src)
		throws PortalException {

		// Check if the src starts with "data:image/" first because "data:image"
		// indicates a Base64 URL which can potentially be millions of
		// characters. So it is faster to run startsWith first to return early
		// on these Strings first so that we don't have to do a 'contains' over
		// a very long string.

		if (src.startsWith("data:image/")) {
			return imgElementString;
		}

		// If we got past the above check, we have a URL. Now we can do a quick
		// check if the URL contains "/documents" as a crude way of bypassing
		// most non-Liferay URLs before we have to get into the less performant
		// regex logic.

		if (!src.contains("/documents")) {
			return imgElementString;
		}

		String replacement = imgElementString;

		StringBuffer sb = null;

		Pattern pattern = getPattern();

		Matcher matcher = pattern.matcher(src);

		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer(imgElementString.length());
			}

			FileEntry fileEntry = null;

			if (!imgElementString.contains(
					AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID)) {

				fileEntry = getFileEntry(matcher);
			}

			replacement = getReplacement(imgElementString, fileEntry);

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(replacement));
		}

		if (sb != null) {
			matcher.appendTail(sb);

			replacement = sb.toString();
		}

		return replacement;
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?:/?[^\\s]*)/documents/(\\d+)/(\\d+)/([^/?]+)(?:/([-0-9a-fA-F]+))?" +
			"(?:\\?t=\\d+)?");

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}