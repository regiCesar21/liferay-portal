/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.style.book.web.internal.portlet.zip.StyleBookEntryZipProcessor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class ImportStyleBookDisplayContext {

	public ImportStyleBookDisplayContext(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public List<String> getStyleBookEntryZipProcessorImportResultEntryNames(
		StyleBookEntryZipProcessor.ImportResultEntry.Status status) {

		List<StyleBookEntryZipProcessor.ImportResultEntry>
			styleBookEntryZipProcessorImportResultEntries =
				_getStyleBookEntryZipProcessorImportResultEntryNames();

		if (ListUtil.isEmpty(styleBookEntryZipProcessorImportResultEntries)) {
			return null;
		}

		Stream<StyleBookEntryZipProcessor.ImportResultEntry> stream =
			styleBookEntryZipProcessorImportResultEntries.stream();

		return stream.filter(
			styleBookEntryZipProcessorImportResultEntry ->
				styleBookEntryZipProcessorImportResultEntry.getStatus() ==
					status
		).map(
			StyleBookEntryZipProcessor.ImportResultEntry::getName
		).collect(
			Collectors.toList()
		);
	}

	private List<StyleBookEntryZipProcessor.ImportResultEntry>
		_getStyleBookEntryZipProcessorImportResultEntryNames() {

		if (_styleBookEntryZipProcessorImportResultEntries != null) {
			return _styleBookEntryZipProcessorImportResultEntries;
		}

		_styleBookEntryZipProcessorImportResultEntries =
			(List<StyleBookEntryZipProcessor.ImportResultEntry>)
				SessionMessages.get(
					_renderRequest,
					"styleBookEntryZipProcessorImportResultEntries");

		return _styleBookEntryZipProcessorImportResultEntries;
	}

	private final RenderRequest _renderRequest;
	private List<StyleBookEntryZipProcessor.ImportResultEntry>
		_styleBookEntryZipProcessorImportResultEntries;

}