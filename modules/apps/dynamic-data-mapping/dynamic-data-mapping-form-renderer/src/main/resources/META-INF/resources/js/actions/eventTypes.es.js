/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const PUBLIC_EVENTS = {
	CHANGE_ACTIVE_PAGE: 'activePageUpdated',
	FIELD_BLUR: 'fieldBlurred',
	FIELD_CHANGE: 'fieldEdited',
	FIELD_DROP: 'fieldDrop',
	FIELD_EVALUATED: 'evaluated',
	FIELD_EVALUATION_ERROR: 'evaluationError',
	FIELD_FOCUS: 'fieldFocused',
	FIELD_REMOVED: 'fieldRemoved',
	FIELD_REPEATED: 'fieldRepeated',
	PAGE_ADDED: 'pageAdded',
	PAGE_DELETED: 'pageDeleted',
	PAGE_RESET: 'pageReset',
	PAGE_SWAPPED: 'pagesSwapped',
	PAGE_UPDATED: 'pagesUpdated',
	PAGE_VALIDATION_FAILED: 'pageValidationFailed',
	SUCCESS_CHANGED: 'successPageChanged',
};

const PRIVATE_EVENTS = {
	ALL: 'all',
	UPDATE_DATA_RECORD_VALUES: 'update_data_record_values',
	UPDATE_PAGES: 'update_pages',
};

export const EVENT_TYPES = {
	...PUBLIC_EVENTS,
	...PRIVATE_EVENTS,
};
