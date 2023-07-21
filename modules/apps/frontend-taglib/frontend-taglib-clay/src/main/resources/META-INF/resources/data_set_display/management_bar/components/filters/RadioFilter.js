/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function formatValue(itemValue, items, exclude) {
	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		items.find((item) => item.value === itemValue).label
	);
}

function getOdataString(value, key, exclude) {
	return `${key} ${exclude ? 'ne' : 'eq'} ${
		typeof value === 'string' ? `'${value}'` : value
	}`;
}
function RadioFilter({actions, id, items, value: valueProp}) {
	const [itemValue, setItemValue] = useState(
		valueProp && valueProp.itemValue
	);
	const [exclude, setExclude] = useState(!!valueProp?.exclude);

	const actionType = valueProp ? 'edit' : 'add';

	let submitDisabled = true;

	if (
		(!valueProp && itemValue) ||
		(valueProp && valueProp.itemValue !== itemValue) ||
		(valueProp && itemValue && valueProp.exclude !== exclude)
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption className="pb-0">
				<div className="row">
					<div className="col">
						<label htmlFor={`autocomplete-exclude-${id}`}>
							{Liferay.Language.get('exclude')}
						</label>
					</div>
					<div className="col-auto">
						<ClayToggle
							id={`autocomplete-exclude-${id}`}
							onToggle={() => setExclude(!exclude)}
							toggled={exclude}
						/>
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<div className="inline-scroller mb-n2 mx-n2 px-2">
					<ClayRadioGroup
						onSelectedValueChange={setItemValue}
						selectedValue={itemValue || ''}
					>
						{items.map((item) => (
							<ClayRadio
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClayRadioGroup>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() =>
						actionType !== 'delete'
							? actions.updateFilterState(
									id,
									{
										exclude,
										itemValue,
									},
									formatValue(itemValue, items, exclude),
									getOdataString(itemValue, id, exclude)
							  )
							: actions.updateFilterState(id)
					}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}
					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

RadioFilter.propTypes = {
	actions: PropTypes.shape({
		updateFilterState: PropTypes.func.isRequired,
	}),
	id: PropTypes.string.isRequired,
	items: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
		})
	),
	value: PropTypes.shape({
		exclude: PropTypes.bool,
		itemValue: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	}),
};

export default RadioFilter;
