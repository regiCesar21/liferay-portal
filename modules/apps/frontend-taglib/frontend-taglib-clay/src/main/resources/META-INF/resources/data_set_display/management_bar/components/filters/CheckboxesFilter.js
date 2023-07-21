/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayToggle} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {isValuesArrayChanged} from '../../../utilities/index';

export const formatValue = (values, items, exclude) => {
	const formattedValue = values
		? values
				.map((value) => {
					return items.reduce(
						(found, item) =>
							found || (item.value === value ? item.label : null),
						null
					);
				})
				.join(', ')
		: '';

	return (
		(exclude ? `(${Liferay.Language.get('exclude')}) ` : '') +
		formattedValue
	);
};

function getOdataString(values, key, exclude = false) {
	if (values?.length) {
		return `${key}/any(x:${values
			.map(
				(value) =>
					`(x ${exclude ? 'ne' : 'eq'} ${
						typeof value === 'string' ? `'${value}'` : value
					})`
			)
			.join(exclude ? ' and ' : ' or ')})`;
	}

	return null;
}
function CheckboxesFilter({actions, id, items, value: valueProp}) {
	const [itemsValues, setItemsValues] = useState(
		(valueProp && valueProp.itemsValues) || []
	);
	const [exclude, setExclude] = useState(
		valueProp ? valueProp.exclude : false
	);

	function selectCheckbox(selected) {
		if (itemsValues.includes(selected)) {
			return setItemsValues(
				itemsValues.filter((value) => value !== selected)
			);
		}

		return setItemsValues(itemsValues.concat(selected));
	}

	useEffect(() => {
		setItemsValues(valueProp ? valueProp.itemsValues : []);
		setExclude(valueProp ? valueProp.exclude : false);
	}, [valueProp]);

	let actionType = 'edit';

	if (valueProp?.itemsValues && !itemsValues.length) {
		actionType = 'delete';
	}

	if (!valueProp) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		(!valueProp && itemsValues.length) ||
		(valueProp &&
			isValuesArrayChanged(valueProp.itemsValues, itemsValues)) ||
		(valueProp && itemsValues.length && valueProp.exclude !== exclude)
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
					{items.map((item, i) => {
						let checked = false;

						if (itemsValues) {
							checked = itemsValues.reduce(
								(acc, el) => acc || el === item.value,
								false
							);
						}

						return (
							<ClayCheckbox
								aria-label={item.label}
								checked={checked}
								key={i}
								label={item.label}
								onChange={() => selectCheckbox(item.value)}
							/>
						);
					})}
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
										itemsValues,
									},
									formatValue(itemsValues, items, exclude),
									getOdataString(itemsValues, id, exclude)
							  )
							: actions.updateFilterState(id)
					}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}
					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}
					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

CheckboxesFilter.propTypes = {
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
		itemsValues: PropTypes.arrayOf(
			PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		),
	}),
};

export default CheckboxesFilter;
