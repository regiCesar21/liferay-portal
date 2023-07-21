/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const TextField = ({field, onValueSelect, value}) => {
	const [errorMessage, setErrorMessage] = useState('');

	const initialValue =
		value === undefined
			? field.defaultValue === undefined
				? ''
				: field.defaultValue
			: value;

	const [nextValue, setNextValue] = useState(initialValue);

	const {additionalProps = {}, type = 'text'} = parseTypeOptions(
		field.typeOptions
	);

	return (
		<ClayForm.Group className={errorMessage ? 'has-error' : ''}>
			<label htmlFor={field.name}>{field.label}</label>

			<ClayInput
				id={field.name}
				onBlur={(event) => {
					if (event.target.checkValidity()) {
						setErrorMessage('');

						if (nextValue !== initialValue) {
							onValueSelect(field.name, nextValue);
						}
					}
				}}
				onChange={(event) => {
					if (event.target.validity.valid) {
						setErrorMessage('');
					}
					else {
						const validationErrorMessage =
							(field.typeOptions &&
								field.typeOptions.validation &&
								field.typeOptions.validation.errorMessage) ||
							Liferay.Language.get(
								'you-have-entered-invalid-data'
							);

						setErrorMessage(validationErrorMessage);
					}

					setNextValue(event.target.value);
				}}
				placeholder={
					field.typeOptions ? field.typeOptions.placeholder : ''
				}
				sizing="sm"
				type={type}
				value={nextValue}
				{...additionalProps}
			/>

			{errorMessage && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />
						{errorMessage}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
};

function parseTypeOptions(typeOptions = {}) {
	if (!typeOptions.validation) {
		return {type: 'text'};
	}

	const {type: validationType, ...properties} = typeOptions.validation;

	const result = {
		additionalProps: {},
		type: 'text',
	};

	if (!validationType || validationType === 'text') {
		result.type = 'text';

		if (Number.isInteger(properties.minLength)) {
			result.additionalProps.minLength = properties.minLength;
		}

		if (Number.isInteger(properties.maxLength)) {
			result.additionalProps.maxLength = properties.maxLength;
		}
	}

	if (validationType === 'pattern') {
		result.additionalProps = {pattern: typeOptions.validation.regexp};
	}

	if (validationType === 'url' || validationType === 'email') {
		result.type = validationType;

		if (Number.isInteger(properties.minLength)) {
			result.additionalProps.minLength = properties.minLength;
		}

		if (Number.isInteger(properties.maxLength)) {
			result.additionalProps.maxLength = properties.maxLength;
		}
	}

	if (validationType === 'number') {
		result.type = validationType;

		if (Number.isInteger(properties.min)) {
			result.additionalProps.min = properties.min;
		}

		if (Number.isInteger(properties.max)) {
			result.additionalProps.max = properties.max;
		}
	}

	return result;
}

TextField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
