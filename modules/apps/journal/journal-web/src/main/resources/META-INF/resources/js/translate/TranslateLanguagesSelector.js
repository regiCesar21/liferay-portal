/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import LanguageSelector from './LanguageSelector';

const TranslateLanguagesSelector = ({
	currentUrl,
	portletNamespace,
	sourceAvailableLanguages,
	sourceLanguageId,
	targetAvailableLanguages,
	targetLanguageId,
}) => {
	const namespace = `${portletNamespace}`;
	const [formHaschanges, setFormHasChanges] = useState(false);

	const bridgeComponentId = `${namespace}TranslateLanguagesSelector`;

	if (!Liferay.component(bridgeComponentId)) {
		Liferay.component(
			bridgeComponentId,
			{
				onFormChange: () => {
					if (!formHaschanges) {
						setFormHasChanges(true);
					}
				},
			},
			{
				destroyOnNavigate: true,
			}
		);
	}

	const refreshPage = (sourceId, targetId) => {
		const url = new URL(currentUrl);
		const search_params = url.searchParams;

		search_params.set(namespace + 'sourceLanguageId', sourceId);
		search_params.set(namespace + 'targetLanguageId', targetId);

		url.search = search_params.toString();

		location.href = url.toString();
	};

	const changePage = (sourceId, targetId) => {
		if (!formHaschanges) {
			refreshPage(sourceId, targetId);
		}
		else if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-leave-the-page-you-may-lose-your-changes'
				)
			)
		) {
			refreshPage(sourceId, targetId);
		}
	};

	return (
		<div className="autofit-row autofit-row-center languages-selector">
			<span className="autofit-col">
				{Liferay.Language.get('translate-from')}
			</span>

			<span className="autofit-col">
				<LanguageSelector
					languageIds={sourceAvailableLanguages}
					onChange={(value) => {
						changePage(value, targetLanguageId);
					}}
					selectedLanguageId={sourceLanguageId}
				/>
			</span>

			<span className="autofit-col">
				{Liferay.Language.get('to').toLowerCase()}
			</span>

			<span className="autofit-col">
				<LanguageSelector
					languageIds={targetAvailableLanguages}
					onChange={(value) => {
						changePage(sourceLanguageId, value);
					}}
					selectedLanguageId={targetLanguageId}
				/>
			</span>
		</div>
	);
};

export default TranslateLanguagesSelector;
