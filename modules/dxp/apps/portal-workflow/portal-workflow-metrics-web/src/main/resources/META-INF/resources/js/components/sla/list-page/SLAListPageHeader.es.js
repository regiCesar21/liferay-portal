/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import ChildLink from '../../../shared/components/router/ChildLink.es';

const Header = ({processId}) => {
	return (
		<ClayManagementToolbar>
			<ClayManagementToolbar.ItemList expand>
				<ClayManagementToolbar.Item className="autofit-col-expand autofit-float-end">
					<ClayTooltipProvider>
						<span>
							<span
								className="workflow-tooltip"
								data-tooltip-align={'bottom'}
								title={Liferay.Language.get('new-sla')}
							>
								<ChildLink
									className="btn btn-primary nav-btn nav-btn-monospaced"
									to={`/sla/${processId}/new`}
								>
									<ClayIcon symbol="plus" />
								</ChildLink>
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
};

export {Header};
