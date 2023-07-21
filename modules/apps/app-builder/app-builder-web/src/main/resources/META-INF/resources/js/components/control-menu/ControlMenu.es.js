/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';
import {Link as InternalLink, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';

const ExternalLink = ({children, to, ...props}) => {
	return (
		<a href={to} {...props}>
			{children}
		</a>
	);
};

const BackButton = ({backURL}) => {
	const [backButtonContainer, setBackButtonContainer] = useState(null);
	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	useEffect(() => {
		if (backButtonContainer !== null) {
			return;
		}

		setBackButtonContainer(
			document.querySelector('.sites-control-group .control-menu-nav')
		);
	}, [backButtonContainer]);

	if (!backButtonContainer) {
		return <></>;
	}

	return createPortal(
		<li className="control-menu-nav-item">
			<Link
				className="control-menu-icon lfr-icon-item"
				tabIndex={1}
				to={backURL}
			>
				<span className="icon-monospaced">
					<ClayIcon symbol="angle-left" />
				</span>
			</Link>
		</li>,
		backButtonContainer
	);
};

const resolveBackURL = (backURL, url) => {
	if (backURL === '../') {
		const paths = url.split('/');

		paths.pop();
		backURL = paths.join('/');
	}

	return backURL;
};

const setDocumentTitle = (title) => {
	if (title) {
		const titles = document.title.split(' - ');
		titles[0] = title;
		document.title = titles.join(' - ');
	}
};

export const InlineControlMenu = ({backURL, title, url}) => {
	const {appDeploymentType, controlMenuElementId} = useContext(AppContext);

	backURL = resolveBackURL(backURL, url);

	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	const [controlMenuContainer, setControlMenuContainer] = useState(null);

	useEffect(() => {
		if (controlMenuContainer !== null) {
			return;
		}

		setControlMenuContainer(document.getElementById(controlMenuElementId));
	}, [controlMenuContainer, controlMenuElementId]);

	const ControlMenu = () => (
		<div
			className={classNames(
				'app-builder-control-menu',
				appDeploymentType
			)}
		>
			{backURL && (
				<Link
					className={classNames(
						'control-menu-back-button',
						appDeploymentType
					)}
					tabIndex={1}
					to={backURL}
				>
					<span className="icon-monospaced">
						<ClayIcon symbol="angle-left" />
					</span>
				</Link>
			)}
			{title && (
				<span
					className={classNames(
						'control-menu-title',
						appDeploymentType
					)}
				>
					{title}
				</span>
			)}
		</div>
	);

	return controlMenuContainer ? (
		createPortal(<ControlMenu />, controlMenuContainer)
	) : (
		<ControlMenu />
	);
};

export const PortalControlMenu = ({backURL, title, url}) => {
	backURL = resolveBackURL(backURL, url);

	useEffect(() => {
		document.querySelector(
			'.tools-control-group .control-menu-level-1-heading'
		).innerHTML = Liferay.Util.escapeHTML(title);
	}, [title]);

	return <>{backURL && <BackButton backURL={backURL} />}</>;
};

export const ControlMenuBase = (props) => {
	useEffect(() => {
		setDocumentTitle(props.title);
	}, [props.title]);

	const {appDeploymentType} = useContext(AppContext);

	if (
		appDeploymentType &&
		(appDeploymentType === 'standalone' || appDeploymentType === 'widget')
	) {
		return <InlineControlMenu {...props} />;
	}
	else {
		return <PortalControlMenu {...props} />;
	}
};

export default withRouter(({match: {url}, ...props}) => {
	return <ControlMenuBase {...props} url={url} />;
});
