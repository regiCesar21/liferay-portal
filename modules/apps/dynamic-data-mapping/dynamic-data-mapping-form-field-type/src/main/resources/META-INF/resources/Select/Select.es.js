/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import React, {forwardRef, useEffect, useMemo, useRef, useState} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import {useSyncValue} from '../hooks/useSyncValue.es';
import HiddenSelectInput from './HiddenSelectInput.es';
import VisibleSelectInput from './VisibleSelectInput.es';

/**
 * Mapping to be used to match keyCodes
 * returned from keydown events.
 */
const KEYCODES = {
	ARROW_DOWN: 40,
	ARROW_UP: 38,
	ENTER: 13,
	ESCAPE: 27,
	SHIFT: 16,
	SPACE: 32,
	TAB: 9,
};

/**
 * Appends a new value on the current value state
 * @param options {Object}
 * @param options.value {Array|String}
 * @param options.valueToBeAppended {Array|String}
 * @returns {Array}
 */
function appendValue({value, valueToBeAppended}) {
	const currentValue = toArray(value);
	const newValue = [...currentValue];

	if (value) {
		newValue.push(valueToBeAppended);
	}

	return newValue;
}

/**
 * Removes a value from the value array.
 * @param options {Object}
 * @param options.value {Array|String}
 * @param options.valueToBeRemoved {Array|String}
 * @returns {Array}
 */
function removeValue({value, valueToBeRemoved}) {
	const currentValue = toArray(value);

	return currentValue.filter((v) => v !== valueToBeRemoved);
}

/**
 * Wraps the given argument into an array.
 * @param value {Array|String}
 */
function toArray(value = '') {
	let newValue = value;

	if (!Array.isArray(newValue)) {
		newValue = [newValue];
	}

	return newValue;
}

function normalizeValue({
	localizedValueEdited,
	multiple,
	normalizedOptions,
	predefinedValueArray,
	valueArray,
}) {
	const assertValue =
		valueArray.length || (valueArray.length === 0 && localizedValueEdited)
			? valueArray
			: predefinedValueArray;

	const valueWithoutMultiple = assertValue.filter((_, index) => {
		return multiple ? true : index === 0;
	});

	return valueWithoutMultiple.filter((value) =>
		normalizedOptions.some((option) => value === option.value)
	);
}

/**
 * Some parameters on each option
 * needs to be prepared in case of
 * multiple selected values(when the value state is an array).
 */
function assertOptionParameters({multiple, option, valueArray}) {
	const included = valueArray.includes(option.value);

	return {
		...option,
		active: !multiple && included,
		checked: multiple && included,
		type: multiple ? 'checkbox' : 'item',
	};
}

function normalizeOptions({
	fixedOptions,
	multiple,
	options,
	showEmptyOption,
	valueArray,
}) {
	const newOptions = [
		...options.map((option, index) => ({
			...assertOptionParameters({multiple, option, valueArray}),
			separator:
				Array.isArray(fixedOptions) &&
				fixedOptions.length > 0 &&
				index === options.length - 1,
		})),
		...fixedOptions.map((option) =>
			assertOptionParameters({multiple, option, valueArray})
		),
	].filter(({value}) => value !== '');

	if (!multiple && showEmptyOption) {
		const emptyOption = {
			label: Liferay.Language.get('choose-an-option'),
			value: null,
		};

		return [emptyOption, ...newOptions];
	}

	return newOptions;
}

function handleDropdownItemClick({currentValue, multiple, option}) {
	const itemValue = option.value;

	let newValue;

	if (multiple) {
		if (currentValue.includes(itemValue)) {
			newValue = removeValue({
				value: currentValue,
				valueToBeRemoved: itemValue,
			});
		}
		else {
			newValue = appendValue({
				value: currentValue,
				valueToBeAppended: itemValue,
			});
		}
	}
	else if (itemValue === null) {
		newValue = [];
	}
	else {
		newValue = [itemValue];
	}

	return newValue;
}

const DropdownItem = ({
	currentValue,
	expand,
	index,
	multiple,
	onSelect,
	option,
	options,
}) => (
	<>
		<ClayDropDown.Item
			aria-label={option.label}
			aria-selected={expand && currentValue.includes(option.value)}
			data-testid={`dropdownItem-${index}`}
			label={option.label}
			onClick={(event) => {
				event.preventDefault();
				event.stopPropagation();

				onSelect({
					currentValue,
					event,
					multiple,
					option,
				});
			}}
			role="option"
			value={options.value}
		>
			{multiple ? (
				<ClayCheckbox
					aria-checked={currentValue.includes(option.value)}
					aria-label={option.label}
					checked={currentValue.includes(option.value)}
					data-testid={`labelItem-${option.value}`}
					label={option.label}
					onChange={(event) => {
						onSelect({
							currentValue,
							event,
							multiple,
							option,
						});
					}}
				/>
			) : (
				option.label
			)}
		</ClayDropDown.Item>

		{option && option.separator && <ClayDropDown.Divider />}
	</>
);

const Trigger = forwardRef(
	(
		{
			ariaRequired,
			ariaControls,
			onCloseButtonClicked,
			onTriggerClicked,
			onTriggerKeyDown,
			readOnly,
			value,
			...otherProps
		},
		ref
	) => {
		return (
			<>
				{!readOnly && (
					<HiddenSelectInput value={value} {...otherProps} />
				)}
				<VisibleSelectInput
					ariaRequired={ariaRequired}
					ariaControls={ariaControls}
					onClick={onTriggerClicked}
					onCloseButtonClicked={onCloseButtonClicked}
					onKeyDown={onTriggerKeyDown}
					readOnly={readOnly}
					ref={ref}
					value={value}
					{...otherProps}
				/>
			</>
		);
	}
);

const Select = ({
	multiple,
	onCloseButtonClicked,
	onDropdownItemClicked,
	onExpand,
	options,
	predefinedValue,
	readOnly,
	value,
	...otherProps
}) => {
	const menuElementRef = useRef(null);
	const triggerElementRef = useRef(null);

	const timeoutIdRef = useRef();
	const stringRef = useRef('');
	const prevIndexRef = useRef(-1);
	const matchIndexRef = useRef(null);

	const [currentValue, setCurrentValue] = useSyncValue(value, false);
	const [expand, setExpand] = useState(false);

	useEffect(() => {
		if (expand) {
			const element =
				menuElementRef.current.querySelector('[aria-selected=true]') ??
				menuElementRef.current.querySelector('button');

			element?.focus();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [expand]);

	useEffect(() => {
		const onClose = (event) => {
			if (
				expand &&
				menuElementRef.current &&
				!menuElementRef.current.contains(event.target) &&
				!triggerElementRef.current.contains(event.target)
			) {
				setExpand(false);
				onExpand({event, expand: false});
				triggerElementRef.current.firstChild.focus();
			}
		};

		document.addEventListener('click', onClose, true);

		return () => document.removeEventListener('click', onClose, true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [expand]);

	const handleFocus = (event, direction) => {
		const target = event.target;
		const focusabledElements = event.currentTarget.querySelectorAll(
			'button'
		);
		const targetIndex = [...focusabledElements].findIndex(
			(current) => current === target
		);

		let nextElement;

		if (direction) {
			nextElement = focusabledElements[targetIndex - 1];
		}
		else {
			nextElement = focusabledElements[targetIndex + 1];
		}

		if (nextElement) {
			event.preventDefault();
			event.stopPropagation();
			nextElement.focus();
		}
		else if (targetIndex === 0 && direction) {
			event.preventDefault();
			event.stopPropagation();
			menuElementRef.current.focus();
		}
	};

	const handleSelect = ({currentValue, event, multiple, option}) => {
		const newValue = handleDropdownItemClick({
			currentValue,
			multiple,
			option,
		});

		setCurrentValue(newValue);

		onDropdownItemClicked({event, value: newValue});

		if (!multiple) {
			setExpand(false);

			onExpand({event, expand: false});

			triggerElementRef.current.firstChild.focus();
		}
	};

	return (
		<>
			<Trigger
				ariaRequired={otherProps.required}
				ariaControls={`ddm-select-dropdown${otherProps.name}`}
				expand={expand}
				multiple={multiple}
				onCloseButtonClicked={({event, value}) => {
					const newValue = removeValue({
						value: currentValue,
						valueToBeRemoved: value,
					});

					setCurrentValue(newValue);

					onCloseButtonClicked({event, value: newValue});
				}}
				onTriggerClicked={(event) => {
					if (readOnly) {
						return;
					}

					setExpand(!expand);
					onExpand({event, expand: !expand});

					if (expand) {
						triggerElementRef.current.firstChild.focus();
					}
				}}
				onTriggerKeyDown={(event) => {
					if (
						(event.keyCode === KEYCODES.TAB ||
							event.keyCode === KEYCODES.ARROW_DOWN) &&
						!event.shiftKey &&
						expand
					) {
						event.preventDefault();
						event.stopPropagation();

						const firstElement = menuElementRef.current.querySelector(
							'button'
						);

						firstElement.focus();
					}

					if (
						event.keyCode === KEYCODES.ENTER ||
						(event.keyCode === KEYCODES.SPACE && !event.shiftKey)
					) {
						event.preventDefault();
						event.stopPropagation();

						setExpand(!expand);

						onExpand({event, expand: !expand});

						if (expand) {
							triggerElementRef.current.firstChild.focus();
						}
					}
				}}
				options={options}
				predefinedValue={predefinedValue}
				readOnly={readOnly}
				ref={triggerElementRef}
				setExpand={setExpand}
				value={currentValue}
				{...otherProps}
			/>
			<div
				className={classNames(
					'dropdown-menu dropdown-menu-indicator-start dropdown-menu-select ddm-btn-full ddm-select-dropdown',
					{
						show: expand,
					}
				)}
				onKeyDown={(event) => {
					switch (event.keyCode) {
						case KEYCODES.ARROW_DOWN:
							handleFocus(event, false);
							break;
						case KEYCODES.ARROW_UP:
							handleFocus(event, true);
							break;
						case KEYCODES.TAB:
							handleFocus(event, event.shiftKey);
							break;
						case KEYCODES.ESCAPE:
							setExpand(false);
							triggerElementRef.current.firstChild.focus();
							break;
						default: {
							const target = event.target;

							if (
								target.tagName === 'INPUT' ||
								event.key === KEYCODES.TAB
							) {
								return;
							}

							if (
								event.currentTarget &&
								!event.currentTarget.contains(target)
							) {
								return;
							}

							if (
								stringRef.current.length > 0 &&
								stringRef.current[0] !== KEYCODES.SPACE
							) {
								if (event.key === KEYCODES.SPACE) {
									event.preventDefault();
									event.stopPropagation();
								}
							}

							if (
								event.key.length !== 1 ||
								event.ctrlKey ||
								event.metaKey ||
								event.altKey
							) {
								return;
							}

							event.stopPropagation();

							const items = Array.from(
								menuElementRef.current.querySelectorAll(
									'button'
								)
							);

							if (stringRef.current === event.key) {
								stringRef.current = '';
								prevIndexRef.current = matchIndexRef.current;
							}

							stringRef.current += event.key;

							clearTimeout(timeoutIdRef.current);

							timeoutIdRef.current = setTimeout(() => {
								stringRef.current = '';
								prevIndexRef.current = matchIndexRef.current;
							}, 1000);

							const prevIndex = prevIndexRef.current;

							const orderedList = [
								...items.slice((prevIndex ?? 0) + 1),
								...items.slice(0, (prevIndex ?? 0) + 1),
							];

							const item = orderedList.find((item) => {
								const value =
									item.innerText ?? item.textContent;

								return (
									value
										?.toLowerCase()
										.indexOf(
											stringRef.current.toLocaleLowerCase()
										) === 0
								);
							});

							if (item) {
								event.preventDefault();

								matchIndexRef.current = items.indexOf(item);
								item.focus();
							}
							break;
						}
					}
				}}
				ref={menuElementRef}
				role="presentation"
			>
				<ClayDropDown.ItemList
					aria-label={Liferay.Language.get('choose-an-option')}
					id={`ddm-select-dropdown${otherProps.name}`}
					role="listbox"
				>
					{options.map((option, index) => (
						<DropdownItem
							currentValue={currentValue}
							expand={expand}
							index={index}
							key={`${option.value}-${index}`}
							multiple={multiple}
							onSelect={handleSelect}
							option={option}
							options={options}
							role="option"
						/>
					))}
				</ClayDropDown.ItemList>
			</div>
		</>
	);
};

const Main = ({
	fixedOptions = [],
	label,
	localizedValue = {},
	localizedValueEdited,
	multiple,
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	options = [],
	predefinedValue = [],
	readOnly = false,
	showEmptyOption = true,
	value = [],
	...otherProps
}) => {
	const predefinedValueArray = toArray(predefinedValue);
	const valueArray = toArray(value);

	const normalizedOptions = useMemo(
		() =>
			normalizeOptions({
				fixedOptions,
				multiple,
				options,
				showEmptyOption,
				valueArray,
			}),
		[fixedOptions, multiple, options, showEmptyOption, valueArray]
	);

	value = useMemo(
		() =>
			normalizeValue({
				localizedValueEdited,
				multiple,
				normalizedOptions,
				predefinedValueArray,
				valueArray,
			}),
		[
			localizedValueEdited,
			multiple,
			normalizedOptions,
			predefinedValueArray,
			valueArray,
		]
	);

	return (
		<FieldBase
			{...otherProps}
			label={label}
			localizedValue={localizedValue}
			name={name}
			readOnly={readOnly}
			style={null}
		>
			<Select
				multiple={multiple}
				name={`${name}_field`}
				onCloseButtonClicked={({event, value}) =>
					onChange(event, value)
				}
				onDropdownItemClicked={({event, value}) =>
					onChange(event, value)
				}
				onExpand={({event, expand}) => {
					if (expand) {
						onFocus(event);
					}
					else {
						onBlur(event);
					}
				}}
				options={normalizedOptions}
				predefinedValue={predefinedValueArray}
				readOnly={readOnly}
				showEmptyOption={showEmptyOption}
				value={value}
				{...otherProps}
			/>
			<input name={name} type="hidden" value={value} />
		</FieldBase>
	);
};

Main.displayName = 'Select';

export default Main;
