/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import {Context} from '@clayui/modal';
import React, {useContext} from 'react';

import {DEPLOYMENT_ACTION, DEPLOYMENT_TYPES} from '../pages/apps/constants.es';
import {updateItem} from '../utils/client.es';
import {concatValues} from '../utils/utils.es';

export default () => {
	const [{onClose}, dispatch] = useContext(Context);

	const deployApp = (item, undeploy) => {
		return updateItem({
			endpoint: `/o/app-builder/v1.0/apps/${item.id}/${
				undeploy ? 'undeploy' : 'deploy'
			}`,
		})
			.then(() => true)
			.catch((error) => error);
	};

	const undeployApp = (app) => {
		return new Promise((resolve, reject) => {
			dispatch({
				payload: {
					body: (
						<>
							<p>{Liferay.Language.get('undeploy-warning')}</p>
							<ClayList>
								<ClayList.Header>
									{Liferay.Language.get('app')}
								</ClayList.Header>
								<ClayList.Item flex>
									<ClayList.ItemField expand>
										<span>
											<b>
												{Liferay.Language.get('name')}:
											</b>{' '}
											{app.appName}
										</span>
										<span>
											<b>
												{Liferay.Language.get(
													'deployed-as'
												)}
												:
											</b>{' '}
											{concatValues(
												app.appDeployments.map(
													({type}) =>
														DEPLOYMENT_TYPES[type]
												)
											)}
										</span>
										<span>
											<b>
												{Liferay.Language.get(
													'modified-date'
												)}
												:
											</b>{' '}
											{app.dateModified}
										</span>
									</ClayList.ItemField>
								</ClayList.Item>
							</ClayList>
						</>
					),
					footer: [
						<></>,
						<></>,
						<ClayButton.Group key={0} spaced>
							<ClayButton
								displayType="secondary"
								key={1}
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
							<ClayButton
								key={2}
								onClick={() => {
									deployApp(app, true)
										.then((result) => {
											onClose();
											resolve(result);
										})
										.catch(reject);
								}}
							>
								{DEPLOYMENT_ACTION.undeploy}
							</ClayButton>
						</ClayButton.Group>,
					],
					header: DEPLOYMENT_ACTION.undeploy,
					size: 'md',
					status: 'warning',
				},
				type: 1,
			});
		});
	};

	return {deployApp, undeployApp};
};
