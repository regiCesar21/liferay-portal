/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Link} from 'react-router-dom';

import {toQueryString} from '../../hooks/useQuery.es';
import {FieldValuePreview} from './FieldPreview.es';

export function buildEntries({
	dataDefinition,
	fieldNames = [],
	permissions,
	query,
}) {
	return ({dataRecordValues = {}, ...entry}, index) => {
		const entryIndex = query.pageSize * (query.page - 1) + index + 1;

		const viewURL = `/entries/${entryIndex}?${toQueryString({
			...query,
			backURL: window.location.href,
		})}`;

		const displayedDataRecordValues = {};

		fieldNames.forEach((fieldName, columnIndex) => {
			let fieldValuePreview = (
				<FieldValuePreview
					dataDefinition={dataDefinition}
					dataRecordValues={dataRecordValues}
					displayType="list"
					fieldName={fieldName}
				/>
			);

			if (columnIndex === 0 && permissions.view) {
				fieldValuePreview = (
					<Link to={viewURL}>{fieldValuePreview}</Link>
				);
			}

			displayedDataRecordValues[
				'dataRecordValues/' + fieldName
			] = fieldValuePreview;
		});

		return {
			...displayedDataRecordValues,
			...entry,
			viewURL,
		};
	};
}

export function navigateToEditPage(basePortletURL, params = {}) {
	Liferay.Util.navigate(
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataRecordId: 0,
			mvcPath: '/edit_entry.jsp',
			...params,
		})
	);
}
