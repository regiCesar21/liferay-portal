/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {
	convertObjectDateToIsoString,
	formatDateObject,
	formatDateRangeObject,
	getDateFromDateString,
} from '../../../utilities/dates';

function getOdataString(value, key) {
	if (value.from && value.to) {
		return `${key} ge ${convertObjectDateToIsoString(
			value.from,
			'from'
		)}) and (${key} le ${convertObjectDateToIsoString(value.to, 'to')}`;
	}
	if (value.from) {
		return `${key} ge ${convertObjectDateToIsoString(value.from, 'from')}`;
	}
	if (value.to) {
		return `${key} le ${convertObjectDateToIsoString(value.to, 'to')}`;
	}
}
function DateRangeFilter({
	actions,
	id,
	max,
	min,
	placeholder,
	value: valueProp,
}) {
	const [fromValue, setFromValue] = useState(
		valueProp && valueProp.from && formatDateObject(valueProp.from)
	);
	const [toValue, setToValue] = useState(
		valueProp && valueProp.to && formatDateObject(valueProp.to)
	);

	let actionType = 'edit';

	if (valueProp && !fromValue && !toValue) {
		actionType = 'delete';
	}

	if (!valueProp) {
		actionType = 'add';
	}

	let submitDisabled = true;

	if (
		actionType === 'delete' ||
		((!valueProp || !valueProp.from) && fromValue) ||
		((!valueProp || !valueProp.to) && toValue) ||
		(valueProp &&
			valueProp.from &&
			fromValue !== formatDateObject(valueProp.from)) ||
		(valueProp &&
			valueProp.to &&
			toValue !== formatDateObject(valueProp.to))
	) {
		submitDisabled = false;
	}

	return (
		<>
			<ClayDropDown.Caption>
				<div className="form-group">
					<ClayForm.Group className="form-group-sm">
						<label htmlFor={`from-${id}`}>
							{Liferay.Language.get('from')}
						</label>
						<input
							className="form-control"
							id={`from-${id}`}
							max={toValue || (max && formatDateObject(max))}
							min={min && formatDateObject(min)}
							onChange={(e) => setFromValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={placeholder || 'yyyy-mm-dd'}
							type="date"
							value={fromValue || ''}
						/>
					</ClayForm.Group>
					<ClayForm.Group className="form-group-sm mt-2">
						<label htmlFor={`to-${id}`}>
							{Liferay.Language.get('to')}
						</label>
						<input
							className="form-control"
							id={`to-${id}`}
							max={max && formatDateObject(max)}
							min={fromValue || (min && formatDateObject(min))}
							onChange={(e) => setToValue(e.target.value)}
							pattern="\d{4}-\d{2}-\d{2}"
							placeholder={placeholder || 'yyyy-mm-dd'}
							type="date"
							value={toValue || ''}
						/>
					</ClayForm.Group>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={submitDisabled}
					onClick={() => {
						if (actionType === 'delete') {
							actions.updateFilterState(id);
						}
						else {
							const newValue = {
								from: fromValue
									? getDateFromDateString(fromValue)
									: null,
								to: toValue
									? getDateFromDateString(toValue)
									: null,
							};
							actions.updateFilterState(
								id,
								newValue,
								formatDateRangeObject(newValue),
								getOdataString(newValue, id)
							);
						}
					}}
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

const dateShape = PropTypes.shape({
	day: PropTypes.number,
	month: PropTypes.number,
	year: PropTypes.number,
});

DateRangeFilter.propTypes = {
	actions: PropTypes.shape({
		updateFilterState: PropTypes.func.isRequired,
	}),
	id: PropTypes.string.isRequired,
	max: dateShape,
	min: dateShape,
	placeholder: PropTypes.string,
	value: PropTypes.shape({
		from: dateShape,
		to: dateShape,
	}),
};

export default DateRangeFilter;
