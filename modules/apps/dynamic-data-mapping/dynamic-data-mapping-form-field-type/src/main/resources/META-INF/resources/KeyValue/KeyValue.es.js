/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import React, {useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import Text from '../Text/Text.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const KeyValue = ({className, disabled, onChange, value, ...otherProps}) => (
	<div className="active form-text key-value-editor">
		<label className="control-label key-value-label">
			{className === 'key-value-reference-input'
				? Liferay.Language.get('field-reference')
				: Liferay.Language.get('field-name')}
			:
		</label>

		<input
			{...otherProps}
			className={`${disabled ? 'disabled ' : ''}${className}`}
			onChange={(event) => {
				const value = normalizeFieldName(event.target.value);
				onChange({target: {value}});
			}}
			readOnly={disabled}
			tabIndex={disabled ? '-1' : '0'}
			type="text"
			value={value}
		/>
	</div>
);

const Main = ({
	generateKeyword,
	keyword: initialKeyword,
	keywordReadOnly,
	name,
	onBlur,
	onChange,
	onFocus,
	onKeywordBlur,
	onKeywordChange,
	onReferenceBlur,
	onReferenceChange,
	placeholder,
	readOnly,
	reference,
	required,
	showKeyword = false,
	showLabel,
	spritemap,
	value,
	visible,
	...otherProps
}) => {
	const [keyword, setKeyword] = useSyncValue(initialKeyword);

	const generateKeywordRef = useRef(generateKeyword);

	return (
		<FieldBase
			{...otherProps}
			name={name}
			readOnly={readOnly}
			required={required}
			showLabel={showLabel}
			spritemap={spritemap}
			visible={visible}
		>
			<Text
				name={`keyValueLabel${name}`}
				onBlur={onBlur}
				onChange={(event) => {
					const {value} = event.target;

					onChange(event);

					if (generateKeywordRef.current) {
						const newKeyword = normalizeFieldName(value);
						onKeywordChange(event, newKeyword, true);
					}
				}}
				onFocus={onFocus}
				placeholder={placeholder}
				readOnly={readOnly}
				required={required}
				showLabel={showLabel}
				spritemap={spritemap}
				syncDelay={false}
				value={value}
				visible={visible}
			/>
			{showKeyword && (
				<KeyValue
					className="key-value-input"
					disabled={keywordReadOnly}
					onBlur={onKeywordBlur}
					onChange={(event) => {
						const {value} = event.target;

						generateKeywordRef.current = false;
						onKeywordChange(event, value, false);
						setKeyword(value);
					}}
					value={keyword}
				/>
			)}
			<KeyValue
				className="key-value-reference-input"
				onBlur={onReferenceBlur}
				onChange={(event) => {
					onReferenceChange(event);
				}}
				value={reference}
			/>
		</FieldBase>
	);
};

Main.displayName = 'KeyValue';

export default Main;
