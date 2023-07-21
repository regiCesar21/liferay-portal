/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {useId} from '../../utils/useId';

export const SelectField = ({disabled, field, onValueSelect, value}) => {
	const inputId = useId();
	const {tokenValues} = useStyleBook();

	const validValues = field.typeOptions
		? field.typeOptions.validValues
		: field.validValues;

	const [firstOption = {}] = validValues;

	const [nextValue, setNextValue] = useState(
		value || field.defaultValue || firstOption.value
	);

	const getFrontendTokenOption = (option) => {
		const token = tokenValues[option.frontendTokenName];

		if (!token) {
			return option;
		}

		return {
			label: token.label,
			value: option.frontendTokenName,
		};
	};

	return (
		<ClayForm.Group small>
			<label htmlFor={inputId}>{field.label}</label>

			<ClaySelectWithOption
				aria-label={field.label}
				disabled={!!disabled}
				id={inputId}
				onChange={(event) => {
					const nextValue =
						event.target.options[event.target.selectedIndex].value;

					setNextValue(nextValue);
					onValueSelect(field.name, nextValue);
				}}
				options={validValues.map((validValue) =>
					validValue.frontendTokenName
						? getFrontendTokenOption(validValue)
						: validValue
				)}
				value={nextValue}
			/>
		</ClayForm.Group>
	);
};

SelectField.propTypes = {
	disabled: PropTypes.bool,

	field: PropTypes.shape({
		...ConfigurationFieldPropTypes,
		typeOptions: PropTypes.shape({
			validValues: PropTypes.arrayOf(
				PropTypes.shape({
					label: PropTypes.string.isRequired,
					value: PropTypes.string.isRequired,
				})
			).isRequired,
		}),
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.string.isRequired,
			})
		),
	}),

	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
};
