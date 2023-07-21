/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayColorPicker from '@clayui/color-picker';
import ClayForm, {ClayInput} from '@clayui/form';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {useId} from '../useId';

const debouncedOnValueSelect = debounce(
	(onValueSelect, value) => onValueSelect(value),
	300
);
export default function ColorFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label} = frontendToken;

	const [customColors, setCustomColors] = useState([]);
	const [color, setColor] = useState(value || '');
	const ref = useRef(null);
	const id = useId();

	useEffect(() => {
		if (ref.current) {
			ref.current.style.setProperty(
				'--style-book-color-picker-color',
				color
			);
		}
	}, [color]);

	return (
		<ClayForm.Group
			className="style-book-editor__color-frontend-token"
			ref={ref}
			small
		>
			<label htmlFor={id}>{label}</label>
			<ClayInput.Group small>
				<ClayInput.GroupItem prepend shrink>
					<ClayColorPicker
						colors={customColors}
						onColorsChange={setCustomColors}
						onValueChange={(color) => {
							setColor(`#${color}`);
							debouncedOnValueSelect(onValueSelect, `#${color}`);
						}}
						showHex={false}
						showPalette={false}
						value={color?.replace('#', '') ?? ''}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append>
					<ClayInput
						id={id}
						onChange={(event) => {
							setColor(event.target.value);
							debouncedOnValueSelect(
								onValueSelect,
								event.target.value
							);
						}}
						value={color}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

ColorFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({label: PropTypes.string.isRequired})
		.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
