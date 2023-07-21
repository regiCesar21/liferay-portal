/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useRef, useState} from 'react';

import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import WidgetService from '../services/WidgetService';
import {useSelector} from '../store/index';

const WidgetsContext = React.createContext([]);

export const useWidgets = () => useContext(WidgetsContext);

function normalizePortlets(portlets, fragmentEntryLinks) {
	return portlets.map((portlet) => {
		const normalizedPortlet = {
			...portlet,
			used: Object.values(fragmentEntryLinks).some(
				({portletId}) => portlet.portletId === portletId
			),
		};

		if (portlet.portletItems?.length) {
			normalizedPortlet.portletItems = normalizePortlets(
				portlet.portletItems,
				fragmentEntryLinks
			);
		}

		return normalizedPortlet;
	});
}

function normalizeCategories(categories, fragmentEntryLinks) {
	return categories.map((category) => {
		const normalizedCategory = {
			...category,
			portlets: normalizePortlets(category.portlets, fragmentEntryLinks),
		};

		if (category.categories?.length) {
			normalizedCategory.categories = normalizeCategories(
				category.categories,
				fragmentEntryLinks
			);
		}

		return normalizedCategory;
	});
}

export function WidgetsContextProvider({children}) {
	const [widgets, setWidgets] = useState([]);

	const fragmentEntryLinksRef = useRef();

	const fragmentEntryLinksIds = useSelector((state) => {
		const nextSegmentsExperienceId = selectSegmentsExperienceId(state);

		return Object.values(state.fragmentEntryLinks)
			.filter(
				({portletId, removed, ...fragmentEntryLink}) =>
					portletId &&
					!removed &&
					fragmentEntryLink.segmentsExperienceId ===
						nextSegmentsExperienceId
			)
			.map(({fragmentEntryLinkId}) => fragmentEntryLinkId)
			.join(',');
	});

	useSelector((state) => {
		const nextSegmentsExperienceId = selectSegmentsExperienceId(state);

		fragmentEntryLinksRef.current = Object.values(
			state.fragmentEntryLinks
		).filter(
			({portletId, removed, ...fragmentEntryLink}) =>
				portletId &&
				!removed &&
				fragmentEntryLink.segmentsExperienceId ===
					nextSegmentsExperienceId
		);

		return null;
	});

	useEffect(() => {
		WidgetService.getWidgets().then((categories) =>
			setWidgets(
				normalizeCategories(categories, fragmentEntryLinksRef.current)
			)
		);
	}, []);

	useEffect(() => {
		setWidgets((currentWidgets) =>
			normalizeCategories(currentWidgets, fragmentEntryLinksRef.current)
		);
	}, [fragmentEntryLinksIds]);

	return (
		<WidgetsContext.Provider value={widgets}>
			{children}
		</WidgetsContext.Provider>
	);
}
