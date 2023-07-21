/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import React from 'react';

import '../../../css/AutocompleteDropDown.scss';

export default function AutocompleteDropDown({
	active,
	activeItem,
	emptyMessage = Liferay.Language.get('no-results-were-found'),
	emptyResultMessage = Liferay.Language.get('no-results-were-found'),
	id = '',
	items,
	match,
	onSelect,
	setActiveItem,
}) {
	return (
		<ClayAutocomplete.DropDown active={active}>
			<ClayDropDown.ItemList id={`dropDownList${id}`}>
				{items.length > 0 ? (
					items.map((item, index) => (
						<ClayAutocomplete.Item
							className={index === activeItem ? 'active' : ''}
							key={index}
							match={match}
							onMouseDown={() => onSelect(item)}
							onMouseOver={() => setActiveItem(index)}
							style={{cursor: 'pointer'}}
							value={item.name}
						/>
					))
				) : (
					<ClayDropDown.Item className="disabled">
						{!match ? emptyMessage : emptyResultMessage}
					</ClayDropDown.Item>
				)}
			</ClayDropDown.ItemList>
		</ClayAutocomplete.DropDown>
	);
}
