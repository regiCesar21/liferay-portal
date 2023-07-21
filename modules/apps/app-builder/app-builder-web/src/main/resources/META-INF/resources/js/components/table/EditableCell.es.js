/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useState} from 'react';

import {getLocalizedValue} from '../../utils/lang.es';

const getValue = (item, key) => {
	const value = item[key];

	if (typeof value === 'object') {
		return getLocalizedValue(item.defaultLanguageId, value);
	}

	return value;
};

export default ({Cell, columns, editMode, item: {originalItem}}) => {
	const [state, setState] = useState({});
	const [lastColumn] = columns.map(({key}) => key).slice(-1);
	const {key: editableColumn} = columns.find(({editable}) => editable);
	const {onCancel = () => {}, onSave = () => {}} = editMode;
	const value = state[editableColumn]?.trim();

	const onSubmit = (event) => {
		event.preventDefault();

		if (value) {
			onSave(value);
		}
	};

	return columns.map(({editable, key}, index) => {
		if (editable) {
			return (
				<Cell index={index}>
					<ClayForm onSubmit={onSubmit}>
						<ClayInput
							autoFocus
							defaultValue={getValue(originalItem, key)}
							onChange={({target: {value}}) => {
								setState({[key]: value});
							}}
							value={state[key]}
						/>
					</ClayForm>
				</Cell>
			);
		}

		if (lastColumn === key) {
			return (
				<Cell colSpan={columns.length} fieldAlign="right">
					<ClayButton
						disabled={!value}
						displayType="primary"
						onClick={onSubmit}
						small
					>
						{Liferay.Language.get('save')}
					</ClayButton>
					<ClayButton
						className="ml-3"
						displayType="secondary"
						onClick={onCancel}
						small
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</Cell>
			);
		}

		return <></>;
	});
};
