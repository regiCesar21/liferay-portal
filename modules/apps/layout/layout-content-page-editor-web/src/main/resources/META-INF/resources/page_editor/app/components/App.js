/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useEffect, useMemo} from 'react';

import {StyleBookContextProvider} from '../../plugins/page-design-options/hooks/useStyleBook';
import {INIT} from '../actions/types';
import {config} from '../config/index';
import {reducer} from '../reducers/index';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import {StoreContextProvider, useSelector} from '../store/index';
import {DragAndDropContextProvider} from '../utils/dragAndDrop/useDragAndDrop';
import {CollectionActiveItemContextProvider} from './CollectionActiveItemContext';
import {ControlsProvider} from './Controls';
import DragPreview from './DragPreview';
import LayoutViewport from './LayoutViewport';
import ShortcutManager from './ShortcutManager';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';
import URLParser from './URLParser';
import {WidgetsContextProvider} from './WidgetsContext';
import {EditableProcessorContextProvider} from './fragment-content/EditableProcessorContext';

const DEFAULT_SESSION_LENGTH = 60 * 1000;

export default function App({state}) {
	const initialState = reducer(state, {type: INIT});

	useEffect(() => {
		if (Liferay.Session && config.autoExtendSessionEnabled) {
			const sessionLength =
				Liferay.Session.get('sessionLength') || DEFAULT_SESSION_LENGTH;

			const interval = setInterval(() => {
				Liferay.Session.extend();
			}, sessionLength / 2);

			return () => clearInterval(interval);
		}
	}, []);

	return (
		<StoreContextProvider initialState={initialState} reducer={reducer}>
			<BackURL />
			<LanguageDirection />
			<URLParser />
			<ControlsProvider>
				<CollectionActiveItemContextProvider>
					<DragAndDropContextProvider>
						<EditableProcessorContextProvider>
							<WidgetsContextProvider>
								<DragPreview />
								<Toolbar />
								<LayoutViewport />
								<ShortcutManager />

								<StyleBookContextProvider>
									<Sidebar />
								</StyleBookContextProvider>
							</WidgetsContextProvider>
						</EditableProcessorContextProvider>
					</DragAndDropContextProvider>
				</CollectionActiveItemContextProvider>
			</ControlsProvider>
		</StoreContextProvider>
	);
}

App.propTypes = {
	state: PropTypes.object.isRequired,
};

const BackURL = () => {
	const [backLinkElement, backLinkURL] = useMemo(() => {
		const backLinkElement = document.querySelector('.lfr-back-link');

		try {
			return [backLinkElement, new URL(backLinkElement?.href)];
		}
		catch (error) {
			return [];
		}
	}, []);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		if (backLinkElement && backLinkURL && segmentsExperienceId) {
			backLinkURL.searchParams.set(
				'segmentsExperienceId',
				segmentsExperienceId
			);
			backLinkElement.href = backLinkURL.toString();

			const currentURL = new URL(window.location.href);

			if (currentURL.searchParams.has('p_l_back_url')) {
				currentURL.searchParams.set(
					'p_l_back_url',
					backLinkURL.toString()
				);

				window.history.replaceState(
					null,
					document.title,
					currentURL.toString()
				);
			}
		}
	}, [backLinkElement, backLinkURL, segmentsExperienceId]);

	return null;
};

const LanguageDirection = () => {
	const languageId = useSelector((state) => state.languageId);

	useEffect(() => {
		const currentLanguageDirection = config.languageDirection[languageId];
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.dir = currentLanguageDirection;
			wrapper.lang = languageId;
		}
	}, [languageId]);

	return null;
};
