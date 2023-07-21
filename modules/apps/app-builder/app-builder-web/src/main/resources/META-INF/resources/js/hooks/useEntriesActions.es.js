/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {__RouterContext as RouterContext} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {navigateToEditPage} from '../pages/entry/utils.es';
import {confirmDelete} from '../utils/client.es';
import usePermissions from './usePermissions.es';

export default function useEntriesActions(showOptions) {
	const actions = [];
	const {basePortletURL, showFormView, userLanguageId} = useContext(
		AppContext
	);
	const {history} = useContext(RouterContext);
	const permissions = usePermissions();

	if (showFormView) {
		if (permissions.view) {
			actions.push({
				action: ({viewURL}) => Promise.resolve(history.push(viewURL)),
				name: Liferay.Language.get('view'),
				show: showOptions?.view,
			});
		}

		if (permissions.update) {
			actions.push({
				action: ({id}) =>
					Promise.resolve(
						navigateToEditPage(basePortletURL, {
							backURL: window.location.href,
							dataRecordId: id,
							languageId: userLanguageId,
						})
					),
				name: Liferay.Language.get('edit'),
				show: showOptions?.update,
			});
		}

		if (permissions.delete) {
			actions.push({
				action: confirmDelete('/o/data-engine/v2.0/data-records/', {
					successMessage: Liferay.Language.get(
						'an-entry-was-deleted'
					),
				}),
				name: Liferay.Language.get('delete'),
				show: showOptions?.delete,
			});
		}
	}

	return actions;
}
