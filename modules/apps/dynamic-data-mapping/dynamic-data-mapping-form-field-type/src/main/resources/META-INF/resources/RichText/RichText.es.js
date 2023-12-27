/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';

const RichText = ({
	editorConfig,
	id,
	name,
	onBlur,
	onChange,
	onFocus,
	predefinedValue,
	readOnly,
	value,
	visible,
	...otherProps
}) => {
	const [currentValue, setCurrentValue] = useSyncValue(
		value ? value : predefinedValue
	);

	return (
		<FieldBase
			{...otherProps}
			id={id}
			name={name}
			readOnly={readOnly}
			style={readOnly ? {pointerEvents: 'none'} : null}
			visible={visible}
		>
			<ClassicEditor
				ariaRequired={otherProps.required}
				contents={currentValue}
				data={currentValue}
				editorConfig={editorConfig}
				name={name}
				onBlur={onBlur}
				onChange={(data) => {
					if (currentValue !== data) {
						setCurrentValue(data);

						onChange({}, data);
					}
				}}
				onFocus={onFocus}
				readOnly={readOnly}
			/>

			<input
				defaultValue={currentValue}
				id={id || name}
				name={name}
				type="hidden"
			/>
		</FieldBase>
	);
};

export default RichText;
