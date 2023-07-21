/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import FriendlyURLHistoryModal from './FriendlyURLHistoryModal';

export default function FriendlyURLHistory({portletNamespace, ...restProps}) {
	const [showModal, setShowModal] = useState(false);
	const [selectedLanguageId, setSelectedLanguageId] = useState();

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	return (
		<>
			<ClayButtonWithIcon
				borderless
				className="btn-url-history"
				displayType="secondary"
				onClick={() => {
					setSelectedLanguageId(
						Liferay.component(
							`${portletNamespace}friendlyURL`
						).getSelectedLanguageId()
					);
					setShowModal(true);
				}}
				outline
				small
				symbol="restore"
			/>
			{showModal && (
				<FriendlyURLHistoryModal
					{...restProps}
					initialLanguageId={selectedLanguageId}
					observer={observer}
					onModalClose={onClose}
					portletNamespace={portletNamespace}
				/>
			)}
		</>
	);
}

FriendlyURLHistory.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
};
