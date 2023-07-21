/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import {OPEN_SIDE_PANEL} from '../../utilities/eventsDefinitions';
import {logError} from '../../utilities/logError';
import {getOpenedSidePanel} from '../../utilities/sidePanels';

function submit({action, data, formId, formRef}) {
	let form = formRef.current;

	if (!form && formId) {
		form = document.getElementById(formId);
	}

	if (form) {
		Liferay.Util.postForm(form, {data, url: action || form.action});
	}
	else {
		logError(`Form not found.`);
	}
}

function getQueryString(key, values = []) {
	return `?${key}=${values.join(',')}`;
}

function getRichPayload(payload, key, values = []) {
	const richPayload = {
		...payload,
		url: payload.baseURL + getQueryString(key, values),
	};

	return richPayload;
}

function BulkActions({
	bulkActions,
	fluid,
	selectAllItems,
	selectedItemsKey,
	selectedItemsValue,
	total,
}) {
	const {actionParameterName} = useContext(DataSetDisplayContext);
	const [
		currentSidePanelActionPayload,
		setCurrentSidePanelActionPayload,
	] = useState(null);

	function handleActionClick(
		actionDefinition,
		formId,
		formRef,
		loadData,
		sidePanelId
	) {
		const {data, href, slug, target} = actionDefinition;

		if (target === 'sidePanel') {
			const sidePanelActionPayload = {
				baseURL: href,
				id: sidePanelId,
				onAfterSubmit: () => loadData(),
				slug: slug ?? null,
			};

			Liferay.fire(
				OPEN_SIDE_PANEL,
				getRichPayload(
					sidePanelActionPayload,
					selectedItemsKey,
					selectedItemsValue
				)
			);

			setCurrentSidePanelActionPayload(sidePanelActionPayload);
		}
		else {
			submit({
				action: href,
				data: {
					...data,
					[`${
						actionParameterName || selectedItemsKey
					}`]: selectedItemsValue.join(','),
				},
				formId,
				formRef,
			});
		}
	}

	useEffect(
		() => {
			if (!currentSidePanelActionPayload) {
				return;
			}

			const currentOpenedSidePanel = getOpenedSidePanel();

			if (
				currentOpenedSidePanel?.id ===
					currentSidePanelActionPayload.id &&
				currentOpenedSidePanel.url.indexOf(
					currentSidePanelActionPayload.baseURL
				) > -1
			) {
				Liferay.fire(
					OPEN_SIDE_PANEL,
					getRichPayload(
						currentSidePanelActionPayload,
						selectedItemsValue
					)
				);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedItemsValue]
	);

	return selectedItemsValue.length ? (
		<DataSetDisplayContext.Consumer>
			{({formId, formRef, loadData, sidePanelId}) => (
				<nav className="management-bar management-bar-primary navbar navbar-expand-md pb-2 pt-2 subnav-tbar">
					<div
						className={classNames(
							'container-fluid container-fluid-max-xl py-1',
							!fluid && 'px-0'
						)}
					>
						<ul className="navbar-nav">
							<li className="nav-item">
								<span className="text-truncate">
									{selectedItemsValue.length}{' '}
									{Liferay.Language.get('of')} {total}{' '}
									{Liferay.Language.get('items-selected')}
								</span>
								<ClayLink
									className="ml-3"
									href="#"
									onClick={(event) => {
										event.preventDefault();
										selectAllItems();
									}}
								>
									{Liferay.Language.get('select-all')}
								</ClayLink>
							</li>
						</ul>
						<div className="bulk-actions">
							{bulkActions.map((actionDefinition, i) => (
								<button
									className={classNames(
										'btn btn-monospaced btn-link',
										i > 0 && 'ml-1'
									)}
									key={actionDefinition.label}
									onClick={() =>
										handleActionClick(
											actionDefinition,
											formId,
											formRef,
											loadData,
											sidePanelId
										)
									}
									type="button"
								>
									<ClayIcon symbol={actionDefinition.icon} />
								</button>
							))}
						</div>
					</div>
				</nav>
			)}
		</DataSetDisplayContext.Consumer>
	) : null;
}

BulkActions.propTypes = {
	bulkActions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			method: PropTypes.string,
			target: PropTypes.oneOf(['sidePanel', 'modal']),
		})
	),
	selectedItemsKey: PropTypes.string.isRequired,
	selectedItemsValue: PropTypes.array.isRequired,
	total: PropTypes.number.isRequired,
};

export default BulkActions;
