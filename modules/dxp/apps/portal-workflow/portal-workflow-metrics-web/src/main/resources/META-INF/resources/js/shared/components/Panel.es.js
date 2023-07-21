/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React from 'react';

const Body = ({children, elementClasses}) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) {
		return null;
	}

	return <div className={classes}>{children}</div>;
};

const Footer = ({children, elementClasses, label}) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) {
		return null;
	}

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

const Header = ({children, elementClasses, title}) => {
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes}>
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

const HeaderWithOptions = ({
	children,
	description,
	elementClasses,
	title,
	tooltipPosition = 'right',
}) => {
	return (
		<Header elementClasses={elementClasses}>
			<ClayLayout.ContentRow>
				<ClayLayout.ContentRow className="flex-row" expand>
					<span className="mr-2">{title}</span>

					<ClayTooltipProvider>
						<span>
							<span
								className="workflow-tooltip"
								data-tooltip-align={tooltipPosition}
								title={description}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentRow>

				{children}
			</ClayLayout.ContentRow>
		</Header>
	);
};

const Panel = ({children, elementClasses}) => {
	const classes = getCN('panel', 'panel-secondary', elementClasses);

	return (
		<ClayLayout.ContainerFluid className="mt-4">
			<div className={classes}>{children}</div>
		</ClayLayout.ContainerFluid>
	);
};

Panel.Body = Body;
Panel.Footer = Footer;
Panel.Header = Header;
Panel.HeaderWithOptions = HeaderWithOptions;

export default Panel;
