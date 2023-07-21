/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import ReactDOM from 'react-dom';

let counter = 0;

/**
 * Wrapper for ReactDOM render that automatically:
 *
 * - Provides commonly-needed context (for example, the Clay spritemap).
 * - Unmounts when portlets are destroyed based on the received
 *   `portletId` value inside `renderData`. If none is passed, the
 *   component will be automatically unmounted before the next navigation.
 *
 * @param {Function|React.Element} renderable Component, or function that returns an Element, to be rendered.
 * @param {object} renderData Data to be passed to the component as props.
 * @param {HTMLElement} container DOM node where the component is to be mounted.
 *
 * The React docs advise not to rely on the render return value, so we
 * don't propagate it.
 *
 * @see https://reactjs.org/docs/react-dom.html#render
 */
export default function render(renderable, renderData, container) {
	if (!Liferay.SPA || Liferay.SPA.app) {
		const {portletId} = renderData;
		const spritemap =
			Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

		let {componentId} = renderData;

		const destroyOnNavigate = !portletId;

		if (!componentId) {
			componentId = `__UNNAMED_COMPONENT__${portletId}__${counter++}`;
		}

		Liferay.component(
			componentId,
			{
				destroy: () => {
					ReactDOM.unmountComponentAtNode(container);
				},
			},
			{
				destroyOnNavigate,
				portletId,
			}
		);

		const Component =
			typeof renderable === 'function' ||
			renderable.$$typeof === Symbol.for('react.forward_ref')
				? renderable
				: null;

		// eslint-disable-next-line @liferay/portal/no-react-dom-render
		ReactDOM.render(
			<ClayIconSpriteContext.Provider value={spritemap}>
				{Component ? <Component {...renderData} /> : renderable}
			</ClayIconSpriteContext.Provider>,
			container
		);
	}
	else {
		Liferay.once('SPAReady', () => {
			render(renderable, renderData, container);
		});
	}
}
