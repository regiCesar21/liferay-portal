/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from 'frontend-js-react-web';

import SidebarPanel from '../SidebarPanel';
import SidebarPanelInfoView from '../components/SidebarPanelInfoView';
import SidebarPanelMetricsView from '../components/SidebarPanelMetricsView';

const deselectAllRows = (portletNamespace) => {
	const activeRows = document.querySelectorAll(
		`[data-searchcontainerid="${portletNamespace}content"] tr.active`
	);

	activeRows.forEach((row) => row.classList.remove('active'));
};

const getRow = (portletNamespace, rowId) =>
	document.querySelector(
		`[data-searchcontainerid="${portletNamespace}content"] [data-rowid="${rowId}"]`
	);

const selectRow = (portletNamespace, rowId) => {
	deselectAllRows(portletNamespace);

	const currentRow = getRow(portletNamespace, rowId);
	currentRow.classList.add('active');
};

const showSidebar = ({View, fetchURL, portletNamespace}) => {
	const id = `${portletNamespace}sidebar`;

	const sidebarPanel = Liferay.component(id);

	if (!sidebarPanel) {
		const container = document.body.appendChild(
			document.createElement('div')
		);

		render(
			SidebarPanel,
			{
				fetchURL,
				onClose: () => {
					Liferay.component(id).close();

					deselectAllRows(portletNamespace);
				},
				ref: (element) => {
					Liferay.component(id, element);
				},
				viewComponent: View,
			},
			container
		);
	}
	else {
		sidebarPanel.open(fetchURL, View);
	}
};

const actions = {
	showInfo({fetchURL, portletNamespace, rowId}) {
		selectRow(portletNamespace, rowId);
		showSidebar({
			View: SidebarPanelInfoView,
			fetchURL,
			portletNamespace,
		});
	},
	showMetrics({fetchURL, portletNamespace, rowId}) {
		selectRow(portletNamespace, rowId);
		showSidebar({
			View: SidebarPanelMetricsView,
			fetchURL,
			portletNamespace,
		});
	},
};

export default function propsTransformer({
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						actions[action]({
							fetchURL: item.data.fetchURL,
							portletNamespace,
							rowId: item.data.classPK,
						});
					}
				},
			};
		}),
	};
}
