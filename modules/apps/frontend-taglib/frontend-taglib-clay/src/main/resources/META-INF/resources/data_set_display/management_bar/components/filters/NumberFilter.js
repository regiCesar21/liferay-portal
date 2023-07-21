/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString(value, key) {
	return `${key} eq ${value}`;
}

function NumberFilter({actions, id, inputText, max, min, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	return (
		<>
			<ClayDropDown.Caption>
				<div className="form-group">
					<div className="input-group">
						<div
							className={classNames('input-group-item', {
								'input-group-prepend': inputText,
							})}
						>
							<input
								className="form-control"
								max={max}
								min={min}
								onChange={(e) => setValue(e.target.value)}
								type="number"
								value={value || ''}
							/>
						</div>
						{inputText && (
							<div className="input-group-append input-group-item input-group-item-shrink">
								<span className="input-group-text">
									{inputText}
								</span>
							</div>
						)}
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={Number(value) === valueProp}
					onClick={() =>
						actions.updateFilterState(
							id,
							Number(value),
							value,
							getOdataString(Number(value, id))
						)
					}
					small
				>
					{valueProp
						? Liferay.Language.get('edit-filter')
						: Liferay.Language.get('add-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

NumberFilter.propTypes = {
	actions: PropTypes.shape({
		updateFilterState: PropTypes.func.isRequired,
	}),
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	max: PropTypes.number,
	min: PropTypes.number,
	value: PropTypes.number,
};

export default NumberFilter;
