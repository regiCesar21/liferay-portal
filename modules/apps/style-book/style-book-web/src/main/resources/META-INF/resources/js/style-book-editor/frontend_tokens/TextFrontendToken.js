/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {useId} from '../useId';

const debouncedOnValueSelect = debounce(
	(onValueSelect, value) => onValueSelect(value),
	300
);

export default function TextFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label} = frontendToken;

	const id = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{label}</label>
			<ClayInput
				defaultValue={value}
				id={id}
				onChange={(event) =>
					debouncedOnValueSelect(onValueSelect, event.target.value)
				}
				type="text"
			/>
		</ClayForm.Group>
	);
}

TextFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({
		label: PropTypes.string.isRequired,
	}).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.any,
};
