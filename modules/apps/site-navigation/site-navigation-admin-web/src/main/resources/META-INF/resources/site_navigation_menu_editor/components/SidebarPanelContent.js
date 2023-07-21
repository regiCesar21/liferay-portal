/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import {globalEval} from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {useConstants} from '../contexts/ConstantsContext';
import {useSelectedMenuItemId} from '../contexts/SelectedMenuItemIdContext';
import {useSetSidebarPanelId} from '../contexts/SidebarPanelIdContext';

export const SidebarPanelContent = ({
	contentRequestBody,
	contentUrl,
	title,
}) => {
	const [body, setBody] = useState(null);

	const changedRef = useRef(false);

	const isMounted = useIsMounted();
	const selectedMenuItemId = useSelectedMenuItemId();
	const setSidebarPanelId = useSetSidebarPanelId();

	const {portletId, redirect} = useConstants();

	const namespace = Liferay.Util.getPortletNamespace(portletId);

	useEffect(() => {
		if (changedRef.current) {
			const confirm = confirmUnsavedChanges();

			if (confirm) {
				return;
			}
		}

		setBody(null);

		fetch(contentUrl, {
			body: objectToFormData(
				Liferay.Util.ns(namespace, {redirect, ...contentRequestBody})
			),
			method: 'POST',
		})
			.then((response) => response.text())
			.then((responseContent) => {
				if (isMounted()) {
					setBody(responseContent);
					changedRef.current = false;
				}
			});
	}, [
		contentRequestBody,
		contentUrl,
		isMounted,
		namespace,
		redirect,
		selectedMenuItemId,
		title,
	]);

	return (
		<>
			<div className="sidebar-header">
				<ClayLayout.ContentRow className="sidebar-section">
					<ClayLayout.ContentCol expand>
						<p className="component-title">
							<span className="text-truncate-inline">
								<span className="text-truncate">{title}</span>
							</span>
						</p>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						<ClayButton
							displayType="unstyled"
							monospaced
							onClick={() => {
								if (changedRef.current) {
									confirmUnsavedChanges();
								}

								setSidebarPanelId(null);
							}}
						>
							<ClayIcon symbol="times" />
						</ClayButton>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</div>

			<div className="sidebar-body">
				{!body ? (
					<ClayLoadingIndicator />
				) : (
					<SidebarBody
						body={body}
						onChange={() => {
							changedRef.current = true;
						}}
					/>
				)}
			</div>
		</>
	);
};

SidebarPanelContent.propTypes = {
	contentRequestBody: PropTypes.object,
	contentUrl: PropTypes.string,
	title: PropTypes.string,
};

class SidebarBody extends React.Component {
	constructor(props) {
		super(props);

		this._handleOnChange = this._handleOnChange.bind(this);
		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			globalEval.runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}

	componentWillUnmount() {
		if (this._ref.current) {
			this._ref.current.removeEventListener(
				'change',
				this._handleOnChange
			);
		}
	}

	shouldComponentUpdate() {
		return false;
	}

	_handleOnChange() {
		if (this.props.onChange) {
			this.props.onChange();
		}
	}

	render() {
		return (
			<div
				dangerouslySetInnerHTML={{__html: this.props.body}}
				ref={this._ref}
			/>
		);
	}
}

function confirmUnsavedChanges() {
	const form = document.querySelector(`.sidebar-body form`);

	const error = form ? form.querySelector('[role="alert"]') : null;

	let confirmChanged;

	if (!error) {
		confirmChanged = confirm(
			Liferay.Language.get(
				'you-have-unsaved-changes.-do-you-want-to-save-them'
			)
		);

		if (confirmChanged) {
			if (form) {
				form.submit();
			}
		}
	}

	return confirmChanged;
}
