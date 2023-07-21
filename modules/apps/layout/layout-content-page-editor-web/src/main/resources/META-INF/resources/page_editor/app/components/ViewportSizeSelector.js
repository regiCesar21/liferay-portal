/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {config} from '../config/index';

const SelectorButtonList = ({
	availableViewportSizes,
	dropdown,
	onSelect,
	selectedSize,
}) =>
	Object.values(availableViewportSizes).map(({icon, label, sizeId}) =>
		dropdown ? (
			<ClayDropDown.Item
				key={label}
				onClick={() => onSelect(sizeId)}
				symbolLeft={icon}
			>
				{label}
			</ClayDropDown.Item>
		) : (
			<ClayButtonWithIcon
				aria-label={label}
				aria-pressed={selectedSize === sizeId}
				displayType="secondary"
				key={sizeId}
				onClick={() => onSelect(sizeId)}
				small
				symbol={icon}
				title={label}
			/>
		)
	);

export default function ViewportSizeSelector({onSizeSelected, selectedSize}) {
	const {availableViewportSizes} = config;
	const [active, setActive] = useState(false);

	return (
		<>
			<ClayButton.Group className="d-lg-block d-none">
				<SelectorButtonList
					availableViewportSizes={availableViewportSizes}
					onSelect={onSizeSelected}
					selectedSize={selectedSize}
					setActive={setActive}
				/>
			</ClayButton.Group>

			<ClayDropDown
				active={active}
				className="d-lg-none"
				hasLeftSymbols
				hasRightSymbols
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="btn-monospaced"
						displayType="secondary"
						small
					>
						<ClayIcon
							symbol={availableViewportSizes[selectedSize].icon}
						/>
						<span className="sr-only">
							{availableViewportSizes[selectedSize].label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<SelectorButtonList
						availableViewportSizes={availableViewportSizes}
						dropdown
						onSelect={onSizeSelected}
						selectedSize={selectedSize}
					/>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

ViewportSizeSelector.propTypes = {
	onSizeSelected: PropTypes.func,
	selectedSize: PropTypes.string,
};
