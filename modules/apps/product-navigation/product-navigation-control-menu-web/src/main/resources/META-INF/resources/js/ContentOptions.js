/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {AddPanelContext} from './AddPanel';

const OPTIONS = [
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 4),
		value: 4,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 8),
		value: 8,
	},
	{
		label: Liferay.Util.sub(Liferay.Language.get('x-items'), 10),
		value: 10,
	},
];

const ContentOptions = ({onChangeSelect}) => {
	const {
		addContentsURLs,
		displayGrid,
		portletNamespace,
		setDisplayGrid,
	} = useContext(AddPanelContext);

	const [active, setActive] = useState(false);

	return (
		<div className="sidebar-body__add-panel__content-options">
			<ClayForm.Group small>
				<ClaySelectWithOption
					aria-label="Select Label"
					className="btn-monospaced sidebar-body__add-panel__content-options-select"
					defaultValue={10}
					id={`${portletNamespace}_contentDropdown`}
					onChange={(event) => onChangeSelect(event.target.value)}
					options={OPTIONS}
					sizing="sm"
				/>
			</ClayForm.Group>
			<ClayButton
				className="btn-monospaced sidebar-body__add-panel__content-options-list"
				displayType="unstyled"
				onClick={() => setDisplayGrid(!displayGrid)}
				small
				title={Liferay.Language.get('display-style')}
			>
				<ClayIcon symbol={displayGrid ? 'cards2' : 'list'} />
				<span className="sr-only">
					{Liferay.Language.get('display-style')}
				</span>
			</ClayButton>

			{!!addContentsURLs.length && (
				<ClayDropDown
					active={active}
					menuElementAttrs={{
						containerProps: {
							className: 'cadmin',
						},
					}}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="btn-monospaced sidebar-body__add-panel__content-options-add"
							displayType="unstyled"
							small
							title={Liferay.Language.get('add-new')}
						>
							<ClayIcon symbol="plus" />

							<span className="sr-only">
								{Liferay.Language.get('add-new')}
							</span>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{addContentsURLs.map((content, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={() => {
									setActive(false);
									Liferay.Util.navigate(content.url);
								}}
							>
								{content.label}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			)}
		</div>
	);
};

ContentOptions.propTypes = {
	onChangeSelect: PropTypes.func,
};

export default ContentOptions;
