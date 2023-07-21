/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import ExportTranslationContext from './ExportTranslationContext.es';
import ExportTranslationModal from './ExportTranslationModal.es';

function ExportTranslation(props) {
	const [articleIds, setArticleIds] = useState();
	const [showModal, setShowModal] = useState();
	const {namespace} = useContext(ExportTranslationContext);
	const bridgeComponentId = `${namespace}ExportForTranslationComponent`;
	const [availableSourceLocales, setAvailableSourceLocales] = useState([]);
	const [defaultSourceLanguageId, setDefaultSourceLanguageId] = useState(
		null
	);

	const handleOnClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnClose,
	});

	if (!Liferay.component(bridgeComponentId)) {
		Liferay.component(
			bridgeComponentId,
			{
				open: (articleIds) => {
					const getExportTranslationAvailableLocalesURL = Liferay.Util.PortletURL.createPortletURL(
						props.getExportTranslationAvailableLocalesURL,
						{
							articleId: articleIds[0],
						}
					);

					fetch(getExportTranslationAvailableLocalesURL.toString())
						.then((res) => res.json())
						.then(({availableLocales, defaultLanguageId}) => {
							setAvailableSourceLocales(availableLocales);
							setArticleIds(articleIds);
							setDefaultSourceLanguageId(defaultLanguageId);
							setShowModal(true);
						});
				},
			},
			{
				destroyOnNavigate: true,
			}
		);
	}

	return (
		<>
			{showModal && (
				<ExportTranslationModal
					{...props}
					articleIds={articleIds}
					availableSourceLocales={availableSourceLocales}
					defaultSourceLanguageId={defaultSourceLanguageId}
					observer={observer}
					onModalClose={onClose}
				/>
			)}
		</>
	);
}

export default function ({context, props}) {
	return (
		<ExportTranslationContext.Provider value={context}>
			<ExportTranslation {...props} />
		</ExportTranslationContext.Provider>
	);
}
