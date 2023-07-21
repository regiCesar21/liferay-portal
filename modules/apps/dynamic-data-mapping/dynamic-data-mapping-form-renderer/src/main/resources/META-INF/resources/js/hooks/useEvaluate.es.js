/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import {usePage} from './usePage.es';

/**
 * This hook is a partial function that removes the need to pass the same
 * properties every time they are called, this is only for thunks that use
 * the `evaluate` function.
 */
export const useEvaluate = (thunk) => {
	const {
		defaultLanguageId,
		editingLanguageId,
		groupId,
		pages,
		portletNamespace,
		rules,
	} = usePage();

	return useCallback(
		(args) =>
			thunk({
				defaultLanguageId,
				editingLanguageId,
				groupId,
				pages,
				portletNamespace,
				rules,
				...args,
			}),
		[
			defaultLanguageId,
			editingLanguageId,
			groupId,
			pages,
			portletNamespace,
			rules,
			thunk,
		]
	);
};
