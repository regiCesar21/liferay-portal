/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import useClipboardJS from '../hooks/useClipboardJS';
import ErrorBoundary from '../shared/ErrorBoundary';
import ThemeContext from '../shared/ThemeContext';
import {COPY_BUTTON_CSS_CLASS} from '../utils/constants';
import fetchData from '../utils/fetch/fetch_data';
import {openInitialSuccessToast} from '../utils/toasts';
import EditSXPBlueprintForm from './EditSXPBlueprintForm';

export default function ({
	contextPath,
	defaultLocale,
	learnMessages,
	locale,
	namespace,
	redirectURL,
	sxpBlueprintId,
}) {
	const [resource, setResource] = useState(null);

	useClipboardJS('.' + COPY_BUTTON_CSS_CLASS);

	useEffect(() => {
		openInitialSuccessToast();

		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-blueprints/${sxpBlueprintId}`
		)
			.then((responseContent) => setResource(responseContent))
			.catch(() => setResource({}));
	}, []); //eslint-disable-line

	if (!resource) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				availableLanguages: Liferay.Language.available,
				contextPath,
				defaultLocale,
				learnMessages,
				locale,
				namespace,
				redirectURL,
			}}
		>
			<div className="edit-sxp-blueprint-root">
				<ErrorBoundary>
					<EditSXPBlueprintForm
						entityJSON={resource.entityJSON}
						initialConfiguration={resource.configuration}
						initialDescription={{
							[defaultLocale]: resource.description,
						}}
						initialSXPElementInstances={resource.elementInstances}
						initialTitle={{
							[defaultLocale]: resource.title,
						}}
						sxpBlueprintId={sxpBlueprintId}
					/>
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
