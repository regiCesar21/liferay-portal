/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {usePrevious} from 'frontend-js-react-web';
import {ItemSelectorDialog} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import Lang from '../utils/lang.es';

function AssetVocabulariesCategoriesSelector({
	eventName,
	id,
	isValid = true,
	groupIds = [],
	inputName,
	label,
	onSelectedItemsChange = () => {},
	portletURL,
	required,
	selectedItems = [],
	singleSelect,
	sourceItemsVocabularyIds = [],
	useFallbackInput,
}) {
	const [inputValue, setInputValue] = useState('');

	const [invalidItems, setInvalidItems] = useState([]);

	const {refetch, resource} = useResource({
		fetchOptions: {
			body: new URLSearchParams({
				cmd: JSON.stringify({
					'/assetcategory/search': {
						'-obc': null,
						end: 20,
						groupIds,
						name: `%${inputValue.toLowerCase()}%`,
						start: 0,
						vocabularyIds: sourceItemsVocabularyIds,
					},
				}),
				p_auth: Liferay.authToken,
			}),
			credentials: 'include',
			method: 'POST',
			'x-csrf-token': Liferay.authToken,
		},
		link: `${window.location.origin}${themeDisplay.getPathContext()}
				/api/jsonws/invoke`,
	});

	const previousInputValue = usePrevious(inputValue);

	useEffect(() => {
		if (inputValue && inputValue !== previousInputValue) {
			refetch();
		}
	}, [inputValue, previousInputValue, refetch]);

	const getUnique = (arr, property) => {
		return arr
			.map((element) => element[property])
			.map(
				(element, index, array) =>
					array.indexOf(element) === index && index
			)
			.filter((element) => arr[element])
			.map((element) => arr[element]);
	};

	const handleItemsChange = (items) => {
		const addedItems = getUnique(
			items.filter(
				(item) =>
					!selectedItems.find(
						(selectedItem) => selectedItem.value === item.value
					)
			),
			'label'
		);

		const invalidAddedItems = [];

		const validAddedItems = [];

		addedItems.map((item) => {
			if (
				resource.find(
					(sourceItem) => sourceItem.titleCurrentValue === item.label
				)
			) {
				validAddedItems.push(item);
			}
			else {
				invalidAddedItems.push(item);
			}
		});

		const removedItems = selectedItems.filter(
			(selectedItem) =>
				!items.find((item) => item.value === selectedItem.value)
		);

		const current = [...selectedItems, ...validAddedItems].filter(
			(item) =>
				!removedItems.find(
					(removedItem) => removedItem.value === item.value
				)
		);

		setInvalidItems(invalidAddedItems);

		onSelectedItemsChange(current);
	};

	const handleSelectButtonClick = () => {
		const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

		const randomIntValue = Math.ceil(Math.random() * new Date().getTime());
		const selectedCategoriesCookieName =
			'SELECTED_CATEGORIES_COOKIE_' + randomIntValue;

		const url = sub(decodeURIComponent(portletURL), {
			selectedCategories: selectedCategoriesCookieName,
			singleSelect,
			vocabularyIds: sourceItemsVocabularyIds.concat(),
		});

		const expires = new Date(Date.now() + 20000).toUTCString();

		document.cookie =
			selectedCategoriesCookieName +
			'=' +
			selectedItems.map((item) => item.value).join() +
			'; expires=' +
			expires +
			';path=/;';

		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			dialogClasses: 'modal-lg',
			eventName,
			onClose: () => {
				document.cookie =
					selectedCategoriesCookieName +
					'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
			},
			title: label
				? Liferay.Util.sub(Liferay.Language.get('select-x'), label)
				: Liferay.Language.get('select-categories'),
			url,
		});

		itemSelectorDialog.open();

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const dialogSelectedItems = event.selectedItem;

			if (dialogSelectedItems) {
				const newValues = Object.keys(dialogSelectedItems).reduce(
					(acc, itemKey) => {
						const item = dialogSelectedItems[itemKey];
						if (!item.unchecked) {
							acc.push({
								label: item.value,
								value: item.categoryId,
							});
						}

						return acc;
					},
					[]
				);

				onSelectedItemsChange(newValues);
			}
		});
	};

	return (
		<div className="field-content">
			<ClayForm.Group
				className={classNames({
					'has-error':
						(invalidItems && invalidItems.length > 0) || !isValid,
				})}
				id={id}
			>
				{useFallbackInput && (
					<input
						name={inputName}
						type="hidden"
						value={selectedItems.map((item) => item.value).join()}
					/>
				)}

				{label && (
					<label htmlFor={inputName + '_MultiSelect'}>
						{label}

						{required && (
							<span className="inline-item inline-item-after reference-mark">
								<ClayIcon symbol={'asterisk'} />

								<span className="hide-accessible">
									{Liferay.Language.get('required')}
								</span>
							</span>
						)}
					</label>
				)}

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							id={inputName + '_MultiSelect'}
							inputName={inputName}
							items={selectedItems}
							onChange={setInputValue}
							onItemsChange={handleItemsChange}
							sourceItems={
								resource
									? resource.map((category) => {
											return {
												label:
													category.titleCurrentValue,
												value: category.categoryId,
											};
									  })
									: []
							}
							value={inputValue}
						/>

						{invalidItems && invalidItems.length > 0 && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem aria-live="polite">
									<ClayForm.FeedbackIndicator symbol="info-circle" />

									{Lang.sub(
										Liferay.Language.get(
											`category-x-does-not-exist`
										),
										[
											invalidItems
												.map((item) => item.label)
												.join(','),
										]
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}

						{!isValid && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="info-circle" />
									<span className="ml-2">
										{Liferay.Language.get(
											'this-field-is-required'
										)}
									</span>
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							onClick={handleSelectButtonClick}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

AssetVocabulariesCategoriesSelector.propTypes = {
	eventName: PropTypes.string.isRequired,
	groupIds: PropTypes.array.isRequired,
	id: PropTypes.string,
	inputName: PropTypes.string.isRequired,
	label: PropTypes.string,
	onSelectedItemsChange: PropTypes.func,
	portletURL: PropTypes.string.isRequired,
	required: PropTypes.bool,
	selectedItems: PropTypes.array,
	singleSelect: PropTypes.bool,
	sourceItemsVocabularyIds: PropTypes.array,
	useFallbackInput: PropTypes.bool,
};

export default AssetVocabulariesCategoriesSelector;
