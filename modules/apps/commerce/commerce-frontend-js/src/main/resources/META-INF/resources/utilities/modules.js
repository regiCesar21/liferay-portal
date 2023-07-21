/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useEffect, useState} from 'react';

export function getLiferayJsModule(moduleUrl) {
	return new Promise((resolve, reject) => {
		Liferay.Loader.require(
			moduleUrl,
			(jsModule) => resolve(jsModule.default || jsModule),
			(err) => reject(err)
		);
	});
}

export function getFakeJsModule() {
	return new Promise((resolve) => {
		setTimeout(
			() =>
				resolve(() => (
					<div className="custom-component">
						fakely fetched component
					</div>
				)),
			3000
		);
	});
}

export const getJsModule = Liferay.Loader?.require
	? getLiferayJsModule
	: getFakeJsModule;

export const fetchedJsModules = [];

export function getComponentByModuleUrl(url) {
	return new Promise((resolve, reject) => {
		const foundModule = fetchedJsModules.find((cr) => cr.url === url);
		if (foundModule) {
			resolve(foundModule.component);
		}

		return getJsModule(url)
			.then((fetchedComponent) => {
				fetchedJsModules.push({
					component: fetchedComponent,
					url,
				});

				return resolve(fetchedComponent);
			})
			.catch(reject);
	});
}

export function useLiferayModule(
	moduleUrl,
	LoadingComponent = ClayLoadingIndicator
) {
	const [Component, updateComponent] = useState(
		moduleUrl ? LoadingComponent : null
	);

	useEffect(() => {
		if (moduleUrl) {
			getComponentByModuleUrl(moduleUrl).then((module) => {
				updateComponent(() => module);
			});
		}
	}, [moduleUrl]);

	return Component;
}
