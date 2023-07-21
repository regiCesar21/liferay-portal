/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useTimeout} from 'frontend-js-react-web';
import React, {useContext, useEffect, useState} from 'react';

const SidebarContext = React.createContext();

const noop = () => {};

const SidebarBody = ({children, className}) => {
	return (
		<div className={classNames('sidebar-body', className)}>{children}</div>
	);
};

const SidebarHeader = ({title}) => {
	const {onClose} = useContext(SidebarContext);

	return (
		<div className="sidebar-header">
			<ClayLayout.ContentRow
				className="sidebar-section"
				verticalAlign="center"
			>
				<ClayLayout.ContentCol expand>
					<div className="font-weight-bold text-truncate-inline">
						<span className="text-truncate">{title}</span>
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol>
					<ClayButtonWithIcon
						className="component-action"
						displayType="unstyled"
						onClick={onClose}
						symbol="times-small"
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</div>
	);
};

const Sidebar = ({children, onClose = noop, open = true}) => {
	const [isOpen, setIsOpen] = useState(false);

	const delay = useTimeout();

	// Wait until the component is rendered to show it so animation happens

	useEffect(() => {
		if (open !== false) {
			delay(() => setIsOpen(true), 100);
		}
		else {
			setIsOpen(false);
		}
	}, [delay, open]);

	useEffect(() => {
		if (isOpen) {
			document.body.classList.add('sidebar-open');
		}
		else {
			document.body.classList.remove('sidebar-open');
		}
	}, [isOpen]);

	return (
		<div className="content-dashboard sidebar sidebar-light sidebar-sm">
			<SidebarContext.Provider value={{onClose}}>
				{children}
			</SidebarContext.Provider>
		</div>
	);
};

Sidebar.Body = SidebarBody;
Sidebar.Header = SidebarHeader;

export {SidebarContext};
export default Sidebar;
