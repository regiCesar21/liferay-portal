/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import ProductMenuSettings from './settings/ProductMenuSettings.es';
import Settings from './settings/Settings.es';
import WidgetTip from './settings/WidgetTip.es';

const Divider = () => {
	return (
		<div className="mb-4 pl-4 pr-4">
			<div className="d-flex flex-column">
				<h4 className="card-divider"></h4>
			</div>
		</div>
	);
};

export const DeploySettings = () => (
	<>
		<Settings
			deploymentType="widget"
			subtitle={Liferay.Language.get('deploy-a-widget')}
			tip={WidgetTip}
			title={Liferay.Language.get('widget')}
		/>

		<Divider />

		<Settings
			deploymentType="standalone"
			subtitle={Liferay.Language.get(
				'deploy-a-standalone-app-with-a-direct-link'
			)}
			title={Liferay.Language.get('standalone')}
		/>

		<Divider />

		<Settings
			deploymentType="productMenu"
			settings={ProductMenuSettings}
			subtitle={Liferay.Language.get(
				'deploy-into-applications-or-a-site-menu'
			)}
			title={Liferay.Language.get('product-menu')}
		/>
	</>
);

export default () => {
	return (
		<>
			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol>
					<h2>{`${Liferay.Language.get('deploy-as')}...`}</h2>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<DeploySettings />

			<Divider />
		</>
	);
};
