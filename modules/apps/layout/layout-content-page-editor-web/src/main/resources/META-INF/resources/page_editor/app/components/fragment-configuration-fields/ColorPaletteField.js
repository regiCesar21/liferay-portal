/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ColorPalette from '../../../common/components/ColorPalette';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';

export const ColorPaletteField = ({field, onValueSelect, value}) => {
	const [nextValue, setNextValue] = useState(value && value.cssClass);

	return (
		<ClayForm.Group>
			<ColorPalette
				label={field.label}
				onClear={() => {
					setNextValue('');

					onValueSelect(field.name, '');
				}}
				onColorSelect={(color) => {
					setNextValue(color);

					onValueSelect(field.name, {
						color,
						cssClass: color,
						rgbValue: getRgbValue(color),
					});
				}}
				selectedColor={nextValue}
			/>
		</ClayForm.Group>
	);
};

function getRgbValue(className) {
	const node = document.createElement('div');

	node.classList.add(`bg-${className}`);
	node.style.display = 'none';

	document.body.append(node);

	const rgbValue = getComputedStyle(node).backgroundColor;

	document.body.removeChild(node);

	return rgbValue;
}

ColorPaletteField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.shape({
			color: PropTypes.string,
			cssClass: PropTypes.string,
			rgbValue: PropTypes.string,
		}),
		PropTypes.string,
	]),
};
