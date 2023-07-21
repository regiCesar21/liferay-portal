/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

function APIService({
	endpoints: {
		analyticsReportsHistoricalReadsURL,
		analyticsReportsHistoricalViewsURL,
		analyticsReportsTotalReadsURL,
		analyticsReportsTotalViewsURL,
		analyticsReportsTrafficSourcesURL,
	},
	namespace,
	page: {plid},
}) {
	function getHistoricalReads({timeSpanKey, timeSpanOffset}) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	}

	function getHistoricalViews({timeSpanKey, timeSpanOffset}) {
		const body = {plid, timeSpanKey, timeSpanOffset};

		return _fetchWithError(analyticsReportsHistoricalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	}

	function getTotalReads() {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalReadsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	}

	function getTotalViews() {
		const body = {plid};

		return _fetchWithError(analyticsReportsTotalViewsURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	}

	function getTrafficSources() {
		const body = {plid};

		return _fetchWithError(analyticsReportsTrafficSourcesURL, {
			body: _getFormDataRequest(body, namespace),
			method: 'POST',
		});
	}

	return {
		getHistoricalReads,
		getHistoricalViews,
		getTotalReads,
		getTotalViews,
		getTrafficSources,
	};
}

export default APIService;

/**
 *
 *
 * @export
 * @param {Object} body
 * @param {string} prefix
 * @param {FormData} [formData=new FormData()]
 * @returns {FormData}
 */
export function _getFormDataRequest(body, prefix, formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${prefix}${key}`, value);
	});

	return formData;
}

/**
 * Wrapper to `fetch` function throwing an error when `error` is present in the response
 */
function _fetchWithError(url, options = {}) {
	return fetch(url, options)
		.then((response) => response.json())
		.then((objectResponse) => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
