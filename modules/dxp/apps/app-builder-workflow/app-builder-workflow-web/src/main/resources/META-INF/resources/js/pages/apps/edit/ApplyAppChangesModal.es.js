/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

export default function ApplyAppChangesModal({onSave}) {
	const {isAppChangesModalVisible, setAppChangesModalVisible} = useContext(
		EditAppContext
	);

	const {observer, onClose} = useModal({
		onClose: () => setAppChangesModalVisible(false),
	});

	if (!isAppChangesModalVisible) {
		return <></>;
	}

	return (
		<ClayModal center className="save-app-modal" observer={observer}>
			<ClayModal.Header className="border-0">
				<ClayIcon
					className="circle-icon info mr-3"
					fontSize="26px"
					symbol="exclamation-full"
				/>
				{Liferay.Language.get('applying-app-updates')}
			</ClayModal.Header>

			<ClayModal.Body>
				<span className="text-secondary">
					{Liferay.Language.get(
						'some-of-the-updates-cannot-be-applied-to-existing-app-data'
					)}
				</span>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<>
						<ClayButton
							className="mr-3"
							displayType="secondary"
							onClick={onClose}
							small
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							onClick={() => onSave(setAppChangesModalVisible)}
							small
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</>
				}
			/>
		</ClayModal>
	);
}
