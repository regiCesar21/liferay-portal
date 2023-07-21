/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useEffect, useState} from 'react';

import isClickOutside from '../../hooks/useOnClickOutside.es';

const SidebarContext = createContext({});

const SidebarContextProvider = ({
	children,
	formReportRecordsFieldValuesURL,
	portletNamespace,
}) => {
	const [sidebarState, setSidebarState] = useState({
		field: null,
		isOpen: false,
		totalEntries: 0,
		type: null,
	});

	useEffect(() => {
		const eventHandler = ({target}) => {
			if (
				isClickOutside(
					target,
					'#' + portletNamespace + '-sidebar-reports',
					'#' + portletNamespace + '-see-more'
				)
			) {
				setSidebarState(() => ({
					isOpen: false,
				}));
			}
		};

		window.addEventListener('click', eventHandler);

		return () => window.removeEventListener('click', eventHandler);
	});

	const toggleSidebar = (field, summary, totalEntries, type) => {
		const isOpen = field !== undefined;

		if (isOpen) {
			setSidebarState(() => ({
				field,
				isOpen,
				summary,
				totalEntries,
				type,
			}));
		}
		else {
			setSidebarState(() => ({
				...sidebarState,
				isOpen,
			}));
		}
	};

	return (
		<SidebarContext.Provider
			value={{
				...sidebarState,
				formReportRecordsFieldValuesURL,
				portletNamespace,
				toggleSidebar,
			}}
		>
			{children}
		</SidebarContext.Provider>
	);
};

export {SidebarContext, SidebarContextProvider};
