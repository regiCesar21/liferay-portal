/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import {DeploySettings} from 'app-builder-web/js/pages/apps/edit/DeployApp.es';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext, useState} from 'react';

export default function DeployAppModal({onSave}) {
	const {
		isDeployModalVisible,
		setDeployModalVisible,
		state: {app},
	} = useContext(EditAppContext);

	const [isDeploying, setDeploying] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setDeploying(false);
			setDeployModalVisible(false);
		},
	});

	if (!isDeployModalVisible) {
		return <></>;
	}

	const onDone = () => {
		setDeploying(true);

		if (!app.active) {
			onSave(onClose, true);
		}
		else {
			onClose();
		}
	};

	return (
		<ClayModal center observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('deploy')}
			</ClayModal.Header>

			<ClayModal.Body>
				<DeploySettings />
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							disabled={isDeploying}
							displayType="secondary"
							onClick={onClose}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={
								isDeploying || app.appDeployments.length === 0
							}
							onClick={onDone}
							small
						>
							{Liferay.Language.get('done')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
