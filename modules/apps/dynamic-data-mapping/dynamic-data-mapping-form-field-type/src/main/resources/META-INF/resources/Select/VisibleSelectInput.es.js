/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {forwardRef} from 'react';

/**
 * Mapping to be used to match keyCodes
 * returned from keydown events.
 */
const KEYCODES = {
	ARROW_DOWN: 40,
	ARROW_UP: 38,
	ENTER: 13,
	SHIFT: 16,
	SPACE: 32,
	TAB: 9,
};

const LabelOptionListItem = ({
	onCloseButtonClicked,
	option,
	readOnly,
	setExpand,
}) => (
	<li>
		<ClayLabel
			className="ddm-select-option-label"
			closeButtonProps={{
				'aria-label': Liferay.Util.sub(
					Liferay.Language.get('remove-x'),
					option.label
				),
				'data-testid': `closeButton${option.value}`,
				onClick: (event) => {
					event.preventDefault();
					event.stopPropagation();

					onCloseButtonClicked({event, value: option.value});
				},
				onKeyDown: (event) => {
					if (
						event.keyCode === KEYCODES.ENTER ||
						(event.keyCode === KEYCODES.SPACE && !event.shiftKey)
					) {
						event.preventDefault();
						event.stopPropagation();

						onCloseButtonClicked({event, value: option.value});
					}

					if (
						event.keyCode === KEYCODES.ARROW_DOWN &&
						!event.shiftKey
					) {
						setExpand(true);
					}
				},
			}}
			value={option.value}
			withClose={!readOnly}
		>
			{option.label}
		</ClayLabel>
	</li>
);

const OptionSelected = ({isPlaceholder, label}) => (
	<div
		className={classNames('option-selected', {
			'option-selected-placeholder': isPlaceholder,
		})}
	>
		{label}
	</div>
);

const VisibleSelectInput = forwardRef(
	(
		{
			className,
			expand,
			id,
			multiple,
			onClick,
			onCloseButtonClicked,
			onKeyDown,
			options,
			readOnly,
			setExpand,
			value,
		},
		ref
	) => {
		const triggerPlaceholder = multiple
			? Liferay.Language.get('choose-options')
			: Liferay.Language.get('choose-an-option');

		const isValueEmpty = value.length === 0;

		const selectedLabel = () => {
			if (isValueEmpty) {
				return triggerPlaceholder;
			}

			const selectedOption = options.find(
				(option) => option.value === value[0]
			);

			return selectedOption ? selectedOption.label : triggerPlaceholder;
		};

		return (
			<div
				className={classNames(
					className,
					'form-builder-select-field input-group-container'
				)}
				onClick={onClick}
				onKeyDown={onKeyDown}
				ref={ref}
			>
				<div
					aria-expanded={expand}
					aria-haspopup="listbox"
					aria-label={selectedLabel()}
					className={classNames(
						'form-control results-chosen select-field-trigger',
						{
							disabled: readOnly,
							'multiple-label-list': multiple,
						}
					)}
					disabled={readOnly}
					id={id}
					role="combobox"
					tabIndex="0"
				>
					{isValueEmpty || (value.length === 1 && !multiple) ? (
						<OptionSelected
							isPlaceholder={isValueEmpty}
							label={selectedLabel()}
						/>
					) : (
						value.map((item) => {
							const option = options.find(
								(option) => option.value === item
							);

							return (
								<LabelOptionListItem
									key={`${option.value}-${option.label}`}
									onCloseButtonClicked={onCloseButtonClicked}
									option={option}
									readOnly={readOnly}
									setExpand={setExpand}
								/>
							);
						})
					)}

					<a className="select-arrow-down-container">
						<ClayIcon symbol="caret-double" />
					</a>
				</div>
			</div>
		);
	}
);

export default VisibleSelectInput;
