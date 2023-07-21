/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

let nextInputId = 0;

export default function SearchForm({onChange}) {
	const id = `pageEditorSearchFormInput${nextInputId++}`;
	const onChangeDebounce = useRef(debounce((value) => onChange(value), 100));
	const [searchValue, setSearchValue] = useState('');

	return (
		<ClayForm.Group className="mb-3" role="search">
			<label className="sr-only" htmlFor={id}>
				{Liferay.Language.get('search-form')}
			</label>
			<ClayInput
				id={id}
				onChange={(event) => {
					setSearchValue(event.target.value);
					onChangeDebounce.current(event.target.value);
				}}
				placeholder={`${Liferay.Language.get('search')}...`}
				sizing="sm"
				type="search"
				value={searchValue}
			/>
		</ClayForm.Group>
	);
}

SearchForm.propTypes = {
	onChange: PropTypes.func.isRequired,
};
