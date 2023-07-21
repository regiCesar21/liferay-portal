/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function onLocaleChange(layoutRendererInstance, event) {
	const selectedLanguageId = event.item.getAttribute('data-value');

	const {
		reactComponentRef: {current},
	} = layoutRendererInstance;

	if (current) {
		current.updateEditingLanguageId({
			editingLanguageId: selectedLanguageId,
		});
	}
}

export default function dataEngineLayoutRendererLanguageProxy(props) {
	let localeChangedHandler = null;

	Liferay.componentReady(props.namespace + 'dataEngineLayoutRenderer').then(
		(event) => {
			localeChangedHandler = Liferay.after(
				'inputLocalized:localeChanged',
				onLocaleChange.bind(this, event)
			);
		}
	);

	function destroyInstance(_event) {
		if (localeChangedHandler) {
			localeChangedHandler.detach();
		}

		Liferay.detach('destroyPortlet', destroyInstance);
	}

	Liferay.on('destroyPortlet', destroyInstance);
}
