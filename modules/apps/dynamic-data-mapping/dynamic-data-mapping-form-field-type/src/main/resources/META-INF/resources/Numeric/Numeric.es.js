/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useRef, useState} from 'react';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import vanillaTextMask from 'vanilla-text-mask';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const getMaskConfig = (dataType, symbols) => {
	let config = {
		allowLeadingZeroes: true,
		includeThousandsSeparator: false,
		prefix: '',
	};

	if (dataType === 'double') {
		config = {
			...config,
			allowDecimal: true,
			decimalLimit: null,
			decimalSymbol: symbols.decimalSymbol,
		};
	}

	return config;
};

const getValue = (dataType, symbols, value) => {
	let newValue = value;

	let decimalSymbol = symbols.decimalSymbol;

	if (newValue && !newValue.includes('.') && symbols.decimalSymbol != ',') {
		decimalSymbol = ',';
	}

	if (
		dataType === 'integer' &&
		newValue &&
		newValue.includes(decimalSymbol)
	) {
		newValue = String(Math.round(newValue.replace(decimalSymbol, '.')));
	}

	return newValue;
};

const Numeric = ({
	dataType = 'integer',
	defaultLanguageId,
	disabled,
	editingLanguageId,
	htmlAutocompleteAttribute,
	localizable,
	localizedValue,
	onChange,
	symbols = {
		decimalSymbol: '.',
		thousandsSeparator: ',',
	},
	value,
	...otherProps
}) => {
	const [currentValue, setCurrentValue] = useState(value);
	const inputRef = useRef(null);

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId && localizable) {
			let newValue =
				localizedValue[editingLanguageId] !== undefined
					? localizedValue[editingLanguageId]
					: localizedValue[defaultLanguageId];

			newValue = getValue(dataType, symbols, newValue);

			setCurrentValue(newValue);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		defaultLanguageId,
		editingLanguageId,
		localizable,
		localizedValue,
		prevEditingLanguageId,
		setCurrentValue,
	]);

	useEffect(() => {
		let maskInstance = null;

		if (inputRef.current) {
			const newValue = getValue(dataType, symbols, value);

			const mask = createNumberMask(getMaskConfig(dataType, symbols));

			maskInstance = vanillaTextMask({
				inputElement: inputRef.current,
				mask,
			});

			if (newValue !== inputRef.current.value) {
				setCurrentValue(newValue);
			}
		}

		return () => {
			if (maskInstance) {
				maskInstance.destroy();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataType, inputRef, setCurrentValue, value]);

	return (
		<ClayInput
			{...otherProps}
			{...(htmlAutocompleteAttribute && {
				autoComplete: htmlAutocompleteAttribute,
			})}
			disabled={disabled}
			onChange={(event) => {
				const {value: newValue} = event.target;

				if (
					dataType === 'integer' &&
					newValue.substr(-1) === symbols.decimalSymbol
				) {
					return;
				}

				setCurrentValue(newValue);
				onChange(event);
			}}
			ref={inputRef}
			type="text"
			value={currentValue}
		/>
	);
};

const Main = ({
	dataType,
	defaultLanguageId,
	editingLanguageId,
	htmlAutocompleteAttribute,
	id,
	localizable,
	localizedValue = {},
	name,
	onBlur,
	onChange,
	onFocus,
	placeholder,
	predefinedValue = '',
	readOnly,
	symbols,
	value,
	...otherProps
}) => (
	<FieldBase
		{...otherProps}
		id={id}
		localizedValue={localizedValue}
		name={name}
		readOnly={readOnly}
		style={null}
	>
		<Numeric
			{...{
				...(otherProps.tip && {
					'aria-describedby': `${id ?? name}_fieldHelp`,
				}),
				...(otherProps.errorMessage && {
					'aria-errormessage': `${id ?? name}_fieldError`,
				}),
				'aria-invalid': !otherProps.valid,
				'aria-required': otherProps.required,
			}}
			dataType={dataType}
			defaultLanguageId={defaultLanguageId}
			disabled={readOnly}
			editingLanguageId={editingLanguageId}
			htmlAutocompleteAttribute={htmlAutocompleteAttribute}
			id={id ?? name}
			localizable={localizable}
			localizedValue={localizedValue}
			name={name}
			onBlur={onBlur}
			onChange={onChange}
			onFocus={onFocus}
			placeholder={placeholder}
			symbols={symbols}
			value={value ? value : predefinedValue}
		/>
	</FieldBase>
);

Main.displayName = 'Numeric';

export {Main};
export default Main;
