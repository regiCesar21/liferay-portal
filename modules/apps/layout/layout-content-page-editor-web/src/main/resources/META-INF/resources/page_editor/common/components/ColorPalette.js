/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../app/config/index';
import {useId} from '../../app/utils/useId';

export default function ColorPalette({
	label,
	onClear,
	onColorSelect,
	selectedColor,
}) {
	const colorPaletteId = useId();

	return (
		<div className="page-editor__color-palette">
			{label && <label htmlFor={colorPaletteId}>{label}</label>}

			<div className="palette-container" id={colorPaletteId}>
				<ul className="list-unstyled palette-items-container">
					{config.themeColorsCssClasses.map((color) => (
						<li
							className={classNames('palette-item', {
								'palette-item-selected':
									color === selectedColor,
							})}
							key={color}
						>
							<ClayButton
								block
								className={classNames(
									`bg-${color}`,
									'palette-item-inner',
									'p-1',
									'rounded-circle'
								)}
								displayType="unstyled"
								onClick={(event) => onColorSelect(color, event)}
								small
								title={color}
							/>
						</li>
					))}
				</ul>
			</div>

			{onClear && (
				<ClayButton displayType="secondary" onClick={onClear} small>
					{Liferay.Language.get('clear')}
				</ClayButton>
			)}
		</div>
	);
}

ColorPalette.propTypes = {
	label: PropTypes.string,
	onClear: PropTypes.func,
	onColorSelect: PropTypes.func.isRequired,
	selectedColor: PropTypes.string,
};
