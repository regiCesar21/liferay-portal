/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio} from '@clayui/form';
import React, {useMemo} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import {setJSONArrayValue} from '../util/setters.es';

const Radio = ({
	options = [
		{
			label: 'Option 1',
			value: 'option1',
		},
		{
			label: 'Option 2',
			value: 'option2',
		},
	],
	inline,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly: disabled,
	value: initialValue,
	...otherProps
}) => {
	const predefinedValueMemo = useMemo(() => {
		if (typeof predefinedValue === 'string') {
			return predefinedValue;
		}

		const predefinedValueJSONArray =
			setJSONArrayValue(predefinedValue) || [];

		return predefinedValueJSONArray[0];
	}, [predefinedValue]);

	const [currentValue, setCurrentValue] = useSyncValue(
		initialValue ? initialValue : predefinedValueMemo
	);

	return (
		<FieldBase {...otherProps} name={name} readOnly={disabled} style={null}>
			<div className="ddm-radio" onBlur={onBlur} onFocus={onFocus}>
				{options.map((option, index) => (
					<ClayRadio
						aria-required={otherProps.required}
						checked={currentValue === option.value}
						disabled={disabled}
						inline={inline}
						key={option.value}
						label={option.label}
						name={`${name}_${index}`}
						onChange={(event) => {
							setCurrentValue(option.value);

							onChange(event);
						}}
						value={option.value}
					/>
				))}
			</div>
			<input name={name} type="hidden" value={currentValue} />
		</FieldBase>
	);
};

export default Radio;
