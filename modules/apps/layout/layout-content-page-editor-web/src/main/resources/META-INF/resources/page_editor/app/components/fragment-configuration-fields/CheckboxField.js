/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const CheckboxField = ({
	disabled,
	field,
	onValueSelect,
	title,
	value,
}) => {
	const [nextValue, setNextValue] = useState(!!value);

	return (
		<ClayForm.Group>
			<div
				className="align-items-center d-flex justify-content-between"
				data-tooltip-align="bottom"
				title={title}
			>
				<ClayCheckbox
					aria-label={field.label}
					checked={nextValue}
					disabled={disabled}
					label={field.label}
					onChange={(event) => {
						setNextValue(event.target.checked);
						onValueSelect(field.name, event.target.checked);
					}}
				/>
			</div>
		</ClayForm.Group>
	);
};

CheckboxField.propTypes = {
	disabled: PropTypes.bool,
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
