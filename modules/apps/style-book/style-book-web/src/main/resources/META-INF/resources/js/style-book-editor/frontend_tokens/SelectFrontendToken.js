/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {useId} from '../useId';

export default function SelectFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label, validValues} = frontendToken;

	const id = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{label}</label>

			<ClaySelectWithOption
				defaultValue={value}
				id={id}
				onChange={(event) => {
					const value =
						event.target.options[event.target.selectedIndex].value;

					onValueSelect(value);
				}}
				options={validValues}
			/>
		</ClayForm.Group>
	);
}

SelectFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({
		label: PropTypes.string.isRequired,
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.any.isRequired,
			})
		),
	}).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.any,
};
