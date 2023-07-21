/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import {AppContext} from '../AppContext.es';
import Link from './Link.es';

export default ({
	hasDropdown = false,
	isEllipsis = false,
	isFirstNode = false,
	section = {},
	ui,
}) => {
	const context = useContext(AppContext);
	const [active, setActive] = useState(false);

	return (
		<>
			<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
				{hasDropdown &&
				section.subSections &&
				section.subSections.length > 0 ? (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="c-p-0 questions-breadcrumb-unstyled text-truncate"
								displayType="unstyled"
							>
								{isEllipsis ? (
									<ClayIcon symbol="ellipsis-h" />
								) : (
									<Link
										className="breadcrumb-item breadcrumb-text-truncate questions-breadcrumb-item"
										onClick={() => {
											setActive(false);
										}}
										to={`/questions/${
											context.useTopicNamesInURL
												? section.title
												: section.id
										}`}
									>
										{ui || section.title}
									</Link>
								)}
								<ClayIcon symbol="caret-bottom-l" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Group>
								{section.subSections.map((section, i) => (
									<Link
										className="text-decoration-none"
										key={i}
										onClick={() => {
											setActive(false);
										}}
										to={`/questions/${
											context.useTopicNamesInURL
												? section.title
												: section.id
										}`}
									>
										<ClayDropDown.Item key={i}>
											{section.title}
										</ClayDropDown.Item>
									</Link>
								))}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : context.showCardsForTopicNavigation && isFirstNode ? (
					<Link
						className="breadcrumb-item questions-breadcrumb-unstyled"
						to={'/'}
					>
						{ui || section.title}
					</Link>
				) : (
					ui || section.title
				)}
			</li>
		</>
	);
};
