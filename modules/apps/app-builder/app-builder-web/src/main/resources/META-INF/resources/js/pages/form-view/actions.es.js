/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataLayoutBuilderActions} from 'data-engine-taglib';

export const EVALUATION_ERROR = 'EVALUATION_ERROR';
export const UPDATE_DATA_DEFINITION = 'UPDATE_DATA_DEFINITION';
export const UPDATE_DATA_LAYOUT = 'UPDATE_DATA_LAYOUT';
export const UPDATE_FIELD_TYPES = 'UPDATE_FIELD_TYPES';
export const UPDATE_IDS = 'UPDATE_IDS';
export const UPDATE_PAGES = 'UPDATE_PAGES';

export const deleteDefinitionField = (fieldName) => ({
	payload: {fieldName},
	type: DataLayoutBuilderActions.DELETE_DATA_DEFINITION_FIELD,
});
