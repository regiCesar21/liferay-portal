/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React, {useState} from 'react';

import SearchInput from '../search-input/SearchInput.es';

const Sidebar = React.forwardRef(({children, className}, ref) => {
	return (
		<div
			className={classNames(className, 'data-layout-builder-sidebar')}
			ref={ref}
		>
			<div className="sidebar sidebar-light">{children}</div>
		</div>
	);
});

const SidebarBody = ({children, className}) => {
	return (
		<div className={classNames(className, 'sidebar-body')}>{children}</div>
	);
};

const SidebarFooter = ({children}) => {
	return <div className="sidebar-footer">{children}</div>;
};

const SidebarHeader = ({children, className}) => {
	return (
		<div className={classNames(className, 'sidebar-header')}>
			{children}
		</div>
	);
};

const SidebarSearchInput = ({children, onSearch, searchText}) => (
	<ClayLayout.ContentRow className="sidebar-section">
		<ClayLayout.ContentCol expand>
			{onSearch && (
				<SearchInput
					onChange={(searchText) => onSearch(searchText)}
					searchText={searchText}
				/>
			)}
		</ClayLayout.ContentCol>

		{children}
	</ClayLayout.ContentRow>
);

const SidebarTabs = ({
	initialSelectedTab = 0,
	setKeywords = () => {},
	tabs,
}) => {
	const [selectedTab, setSelectedTab] = useState(initialSelectedTab);

	return (
		<>
			<SidebarTab
				onTabClick={(value) => {
					setSelectedTab(value);
					setKeywords('');
				}}
				selectedTab={selectedTab}
				tabs={tabs}
			/>

			<SidebarTabContent>{tabs[selectedTab].render()}</SidebarTabContent>
		</>
	);
};

const SidebarTab = ({onTabClick, selectedTab, tabs}) => {
	return (
		<nav className="component-tbar tbar">
			<ClayLayout.ContainerFluid>
				<ul className="nav nav-underline" role="tablist">
					{tabs.map(({label}, index) => (
						<li className="nav-item" key={index}>
							<button
								className={classNames(
									'btn btn-unstyled nav-link',
									{
										active: selectedTab === index,
									}
								)}
								data-senna-off
								onClick={(event) => {
									event.preventDefault();
									onTabClick(index);
								}}
								role="tab"
							>
								{label}
							</button>
						</li>
					))}
				</ul>
			</ClayLayout.ContainerFluid>
		</nav>
	);
};

const SidebarTabContent = ({children}) => {
	return (
		<div className="tab-content">
			<div className="active fade mt-3 show tab-pane" role="tabpanel">
				{children}
			</div>
		</div>
	);
};

const SidebarTitle = ({title}) => (
	<ClayLayout.ContentRow className="mb-3 sidebar-section">
		<ClayLayout.ContentCol expand>
			<div className="component-title">
				<span className="text-truncate-inline">{title}</span>
			</div>
		</ClayLayout.ContentCol>
	</ClayLayout.ContentRow>
);

Sidebar.Body = SidebarBody;
Sidebar.Footer = SidebarFooter;
Sidebar.Header = SidebarHeader;
Sidebar.SearchInput = SidebarSearchInput;
Sidebar.Tab = SidebarTab;
Sidebar.Tabs = SidebarTabs;
Sidebar.TabContent = SidebarTabContent;
Sidebar.Title = SidebarTitle;

export {
	SidebarBody,
	SidebarFooter,
	SidebarHeader,
	SidebarSearchInput,
	SidebarTab,
	SidebarTabs,
	SidebarTabContent,
};

export default Sidebar;
