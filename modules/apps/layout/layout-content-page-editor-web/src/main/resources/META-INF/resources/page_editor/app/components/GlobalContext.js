/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useState} from 'react';
import {createPortal} from 'react-dom';

import RawDOM from '../../common/components/RawDOM';
import {config} from '../config/index';

const GlobalContext = React.createContext({document, window});

export default function GlobalContextProvider({children, useIframe}) {
	const [baseElement, setBaseElement] = useState(null);
	const localContext = useMemo(() => ({document, window}), []);
	const [iframeContext, setIFrameContext] = useState(null);
	const [iframeElement, setIframeElement] = useState(null);
	const isMounted = useIsMounted();

	useEffect(() => {
		let timeoutId = null;

		const handleIframeLoaded = () => {
			if (!isMounted() || !iframeElement) {
				return;
			}

			const pageEditorStylesLinkId = `${config.portletNamespace}pageEditorStylesLink`;

			if (
				!iframeElement.contentDocument.getElementById(
					pageEditorStylesLinkId
				)
			) {
				const pageEditorStylesLink = iframeElement.contentDocument.createElement(
					'link'
				);

				pageEditorStylesLink.id = pageEditorStylesLinkId;
				pageEditorStylesLink.rel = 'stylesheet';
				pageEditorStylesLink.href = config.getIframeContentCssURL;

				iframeElement.contentDocument.head.appendChild(
					pageEditorStylesLink
				);
			}

			const element =
				iframeElement.contentDocument &&
				iframeElement.contentDocument.getElementById('content');

			if (element) {
				element.innerHTML = '';

				iframeElement.contentWindow.requestAnimationFrame(() => {
					setBaseElement(element);

					setIFrameContext({
						document: iframeElement.contentDocument,
						iframe: iframeElement,
						window: iframeElement.contentWindow,
					});
				});
			}
			else {
				timeoutId = setTimeout(handleIframeLoaded, 100);
			}
		};

		if (iframeElement) {
			iframeElement.addEventListener('load', handleIframeLoaded);
			iframeElement.src = config.getIframeContentURL;
			iframeElement.classList.add('page-editor__global-context-iframe');
		}

		return () => {
			clearTimeout(timeoutId);

			if (iframeElement) {
				iframeElement.removeEventListener('load', handleIframeLoaded);
			}

			if (isMounted()) {
				setBaseElement(null);
				setIFrameContext(null);
			}
		};
	}, [iframeElement, isMounted]);

	let context;
	let content;

	if (useIframe && baseElement && iframeContext) {
		content = createPortal(<>{children}</>, baseElement);
		context = iframeContext;

		iframeElement.classList.remove(
			'page-editor__global-context-iframe--hidden',
			'page-editor__global-context-iframe--loading'
		);
	}
	else if (!useIframe) {
		content = <>{children}</>;
		context = localContext;

		if (iframeElement) {
			iframeElement.classList.add(
				'page-editor__global-context-iframe--hidden'
			);
		}
	}
	else {
		iframeElement.classList.add(
			'page-editor__global-context-iframe--loading'
		);
	}

	return (
		<GlobalContext.Provider value={context}>
			{content}
			<RawDOM TagName="iframe" elementRef={setIframeElement} />
		</GlobalContext.Provider>
	);
}

GlobalContextProvider.propTypes = {
	useIframe: PropTypes.bool,
};

export function useGlobalContext() {
	return useContext(GlobalContext);
}
