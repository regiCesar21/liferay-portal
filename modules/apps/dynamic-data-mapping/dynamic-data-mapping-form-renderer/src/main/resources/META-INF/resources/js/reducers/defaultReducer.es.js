/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {PagesVisitor} from '../util/visitors.es';

export default (state, action) => {
	switch (action.type) {
		case EVENT_TYPES.ALL: {
			const {defaultLanguageId} = state;
			const {editingLanguageId, pages} = action.payload;

			if (
				editingLanguageId &&
				state.editingLanguageId !== editingLanguageId
			) {
				const visitor = new PagesVisitor(pages ?? state.pages);

				return {
					...action.payload,
					pages: visitor.mapFields(
						({localizedValue}) => {
							let value;

							if (localizedValue) {
								if (
									localizedValue[editingLanguageId] !==
									undefined
								) {
									value = localizedValue[editingLanguageId];
								}
								else if (localizedValue[defaultLanguageId]) {
									value = localizedValue[defaultLanguageId];
								}
							}

							return {
								value,
							};
						},
						true,
						true,
						true
					),
				};
			}

			return action.payload;
		}
		case EVENT_TYPES.UPDATE_DATA_RECORD_VALUES:
			return {
				dataRecordValues: action.payload,
			};
		default:
			return state;
	}
};
