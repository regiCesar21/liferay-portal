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
	return `${key} eq '${value}'`;
}

function TextFilter({actions, id, inputText, value: valueProp}) {
	const [value, setValue] = useState(valueProp);

	let actionType = 'edit';

	if (valueProp && !value) {
		actionType = 'delete';
	}

	if (!valueProp && value) {
		actionType = 'add';
	}

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
								onChange={(e) => setValue(e.target.value)}
								type="text"
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
					disabled={(!valueProp && value) || valueProp !== value}
					onClick={() =>
						actions.updateFilterState(
							id,
							value,
							value,
							getOdataString(value, id)
						)
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

TextFilter.propTypes = {
	actions: PropTypes.shape({
		updateFilterState: PropTypes.func.isRequired,
	}),
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	value: PropTypes.string,
};

export default TextFilter;
