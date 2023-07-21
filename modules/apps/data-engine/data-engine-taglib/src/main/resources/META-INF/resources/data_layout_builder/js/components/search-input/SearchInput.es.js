/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useEffect, useRef, useState} from 'react';

const SearchInput = React.forwardRef(
	(
		{
			clearButton = true,
			onChange = () => {},
			onSubmit = () => {},
			searchText = '',
			...restProps
		},
		ref
	) => {
		const [value, setValue] = useState(searchText);
		const fallbackRef = useRef(null);
		const searchInputRef = ref ? ref : fallbackRef;

		useEffect(() => {
			setValue(searchText);
		}, [searchText]);

		const onClear = () => {
			setValue('');
			onChange('');
			searchInputRef.current.focus();
		};

		let SearchButton = (
			<ClayButtonWithIcon
				displayType="unstyled"
				key="searcgButton"
				onClick={(_) => onSubmit(value)}
				symbol="search"
				{...restProps}
			/>
		);

		if (clearButton && value) {
			SearchButton = (
				<ClayButtonWithIcon
					displayType="unstyled"
					key="clearButton"
					onClick={onClear}
					symbol="times"
				/>
			);
		}

		return (
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search')}
						className="input-group-inset input-group-inset-after"
						onChange={({target: {value}}) => {
							setValue(value);
							onChange(value);
						}}
						placeholder={`${Liferay.Language.get('search')}...`}
						ref={searchInputRef}
						type="text"
						value={value}
						{...restProps}
					/>

					<ClayInput.GroupInsetItem after>
						{SearchButton}
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		);
	}
);

const SearchInputWithForm = ({onSubmit = () => {}, ...restProps}) => {
	const [searchText, setSearchText] = useState('');

	const handleSubmit = (value) => {
		onSubmit(value.trim());
	};

	return (
		<ClayForm
			onSubmit={(event) => {
				event.preventDefault();
				handleSubmit(searchText);
			}}
		>
			<SearchInput
				clearButton={false}
				onChange={(searchText) => setSearchText(searchText)}
				onSubmit={handleSubmit}
				{...restProps}
			/>
		</ClayForm>
	);
};

export default SearchInput;

export {SearchInputWithForm};
