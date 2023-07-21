/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {Loading} from '../../components/loading/Loading.es';
import useDataListView from '../../hooks/useDataListView.es';
import useEntriesActions from '../../hooks/useEntriesActions.es';
import usePermissions from '../../hooks/usePermissions.es';
import useQuery from '../../hooks/useQuery.es';
import {getLocalizedUserPreferenceValue} from '../../utils/lang.es';
import NoPermissionEntry from './NoPermissionEntry.es';
import {buildEntries, navigateToEditPage} from './utils.es';

export default function ListEntries({history}) {
	const actions = useEntriesActions();
	const permissions = usePermissions();
	const {
		appId,
		basePortletURL,
		dataDefinitionId,
		dataListViewId,
		showFormView,
		userLanguageId,
	} = useContext(AppContext);

	const {
		columns,
		dataDefinition,
		dataListView: {fieldNames},
		isLoading,
	} = useDataListView(dataListViewId, dataDefinitionId, permissions.view);

	const formColumns = columns.map(({value, ...column}) => ({
		...column,
		value: getLocalizedUserPreferenceValue(
			value,
			userLanguageId,
			dataDefinition.defaultLanguageId
		),
	}));

	const onClickEditPage = () => {
		navigateToEditPage(basePortletURL, {
			backURL: window.location.href,
			languageId: userLanguageId,
		});
	};

	const [query] = useQuery(
		history,
		{
			keywords: '',
			page: 1,
			pageSize: 20,
			sort: '',
		},
		appId
	);

	if (!permissions.view) {
		return <NoPermissionEntry />;
	}

	return (
		<Loading isLoading={isLoading}>
			<ListView
				actions={actions}
				addButton={() =>
					showFormView &&
					permissions.add && (
						<Button
							className="nav-btn nav-btn-monospaced"
							onClick={onClickEditPage}
							symbol="plus"
							tooltip={Liferay.Language.get('new-entry')}
						/>
					)
				}
				columns={formColumns}
				emptyState={{
					button: () =>
						showFormView &&
						permissions.add && (
							<Button
								displayType="secondary"
								onClick={onClickEditPage}
							>
								{Liferay.Language.get('new-entry')}
							</Button>
						),
					title: Liferay.Language.get('there-are-no-entries-yet'),
				}}
				endpoint={`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`}
				noActionsMessage={Liferay.Language.get(
					'you-do-not-have-the-permission-to-manage-this-entry'
				)}
				queryParams={{dataListViewId}}
				scope={appId}
			>
				{buildEntries({
					dataDefinition,
					fieldNames,
					permissions,
					query,
				})}
			</ListView>
		</Loading>
	);
}
