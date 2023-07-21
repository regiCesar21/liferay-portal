/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

function PageTypeSelector(props) {
	const [active, setActive] = useState(false);

	const handleOnChange = useCallback(
		(event) => {
			const pageType = event.target.value;

			Liferay.Util.Session.set(
				`${props.namespace}PRIVATE_LAYOUT`,
				pageType === 'private-pages'
			).then(() => Liferay.Util.navigate(window.location.href));
		},
		[props.namespace]
	);

	const handleOnAddCollectionPageClick = useCallback(() => {
		setActive(false);
		Liferay.Util.navigate(props.addCollectionLayoutURL);
	}, [props.addCollectionLayoutURL]);

	const handleOnAddPageClick = useCallback(() => {
		setActive(false);
		Liferay.Util.navigate(props.addLayoutURL);
	}, [props.addLayoutURL]);

	return (
		<div className="align-items-center d-flex page-type-selector">
			<div>
				<select
					className="form-control form-control-sm"
					defaultValue={
						props.privateLayout ? 'private-pages' : 'public-pages'
					}
					onChange={handleOnChange}
				>
					<option value="public-pages">
						{Liferay.Language.get('public-pages')}
					</option>
					<option value="private-pages">
						{Liferay.Language.get('private-pages')}
					</option>
				</select>
			</div>

			<div className="flex-fill flex-grow-1 text-right">
				{props.showAddIcon && (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								aria-haspopup="true"
								className="dropdown-toggle"
								displayType="unstyled"
							>
								<ClayIcon symbol="plus" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{props.addLayoutURL && (
								<ClayDropDown.Item
									data-value={Liferay.Language.get(
										'add-page'
									)}
									key={Liferay.Language.get('add-page')}
									onClick={handleOnAddPageClick}
									title={Liferay.Language.get('add-page')}
								>
									{Liferay.Language.get('add-page')}
								</ClayDropDown.Item>
							)}
							{props.addCollectionLayoutURL && (
								<ClayDropDown.Item
									data-value={Liferay.Language.get(
										'add-collection-page'
									)}
									key={Liferay.Language.get(
										'add-collection-page'
									)}
									onClick={handleOnAddCollectionPageClick}
									title={Liferay.Language.get(
										'add-collection-page'
									)}
								>
									{Liferay.Language.get(
										'add-collection-page'
									)}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</div>
			<div className="autofit-col ml-2">
				{props.configureLayoutSetURL && (
					<ClayLink
						borderless
						className="configure-link"
						displayType="unstyled"
						href={props.configureLayoutSetURL}
						monospaced
						outline
					>
						<ClayIcon symbol="cog" />
					</ClayLink>
				)}
			</div>
		</div>
	);
}

PageTypeSelector.propTypes = {
	addLayoutURL: PropTypes.string,
	configureLayoutSetURL: PropTypes.string,
	namespace: PropTypes.string,
	privateLayout: PropTypes.bool,
	showAddIcon: PropTypes.bool,
};

export default PageTypeSelector;
