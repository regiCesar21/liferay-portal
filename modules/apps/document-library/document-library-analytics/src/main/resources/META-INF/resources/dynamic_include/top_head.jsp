<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<script data-senna-track="temporary" type="text/javascript">
	if (window.Analytics) {
		window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry = false;
	}
</script>

<aui:script>
	var pathnameRegexp = /\/documents\/(\d+)\/(\d+)\/(.+?)\/([^&]+)/;

	function sendAnalyticsEvent(anchor) {
		var fileEntryId =
			anchor.dataset.analyticsFileEntryId ||
			(anchor.parentElement &&
				anchor.parentElement.dataset.analyticsFileEntryId);

		var title =
			anchor.dataset.analyticsFileEntryTitle ||
			(anchor.parentElement &&
				anchor.parentElement.dataset.analyticsFileEntryTitle);

		var getParameterValue = (parameterName) => {
			var result = null;

			anchor.search
				.substr(1)
				.split('&')
				.forEach((item) => {
					var tmp = item.split('=');

					if (tmp[0] === parameterName) {
						result = decodeURIComponent(tmp[1]);
					}
				});

			return result;
		};

		var match = pathnameRegexp.exec(anchor.pathname);

		if (fileEntryId && match) {
			Analytics.send('documentDownloaded', 'Document', {
				groupId: match[1],
				fileEntryId,
				preview: !!window.<%= DocumentLibraryAnalyticsConstants.JS_PREFIX %>isViewFileEntry,
				title: title || decodeURIComponent(match[3].replace(/\+/gi, ' ')),
				version: getParameterValue('version'),
			});
		}
	}

	function handleDownloadClick(event) {
		if (window.Analytics) {
			if (event.target.nodeName.toLowerCase() === 'a') {
				sendAnalyticsEvent(event.target);
			}
			else if (
				event.target.parentNode &&
				event.target.parentNode.nodeName.toLowerCase() === 'a'
			) {
				sendAnalyticsEvent(event.target.parentNode);
			}
			else if (
				event.target.querySelector('.lexicon-icon-download') ||
				event.target.classList.contains('lexicon-icon-download') ||
				(event.target.parentNode &&
					(event.target.parentNode.classList.contains(
						'lexicon-icon-download'
					) ||
						event.target.parentNode.dataset.action === 'download'))
			) {
				var selectedFiles = document.querySelectorAll(
					'.portlet-document-library .entry-selector:checked'
				);

				selectedFiles.forEach(({value}) => {
					var selectedFile = document.querySelector(
						'[data-analytics-file-entry-id="' + value + '"]'
					);

					sendAnalyticsEvent(selectedFile);
				});
			}
		}
	}

	Liferay.once('destroyPortlet', () => {
		document.body.removeEventListener('click', handleDownloadClick);
	});

	Liferay.once('portletReady', () => {
		document.body.addEventListener('click', handleDownloadClick);
	});
</aui:script>