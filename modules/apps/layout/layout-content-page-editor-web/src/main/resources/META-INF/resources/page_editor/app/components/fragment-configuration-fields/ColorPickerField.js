/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import ColorPicker from '../../../common/components/ColorPicker';
import {useStyleBook} from '../../../plugins/page-design-options/hooks/useStyleBook';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {ColorPaletteField} from './ColorPaletteField';

const COLOR_PICKER_TYPE = 'ColorPicker';

export const ColorPickerField = ({field, onValueSelect, value}) => {
	const {tokenValues} = useStyleBook();
	const [color, setColor] = useState(tokenValues[value]?.value);

	const colors = Object.values(tokenValues)
		.filter((token) => token.editorType === COLOR_PICKER_TYPE)
		.map((token) => ({
			label: token.label,
			name: token.name,
			value: token.value,
		}));

	if (!colors.length) {
		return (
			<ColorPaletteField
				field={field}
				onValueSelect={(name, value) =>
					onValueSelect(name, value?.rgbValue ?? '')
				}
				value={value}
			/>
		);
	}

	return (
		<ClayForm.Group small>
			<label>{field.label}</label>
			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ColorPicker
						colors={colors}
						onValueChange={({name, value}) => {
							setColor(value);

							onValueSelect(field.name, name);
						}}
						showHex={false}
						value={color}
					/>
				</ClayInput.GroupItem>
				<ClayInput.GroupItem append>
					<ClayInput
						readOnly
						value={
							tokenValues[value]
								? tokenValues[value].label
								: Liferay.Language.get('default')
						}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<ClayButton
				className="mt-2"
				displayType="secondary"
				onClick={() => {
					setColor('');

					onValueSelect(field.name, '');
				}}
			>
				{Liferay.Language.get('clear')}
			</ClayButton>
		</ClayForm.Group>
	);
};

ColorPickerField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.string,
};
