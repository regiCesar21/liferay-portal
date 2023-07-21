/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {useId} from '../../app/utils/useId';
import {openImageSelector} from '../../core/openImageSelector';

export function ImageSelector({
	imageTitle = Liferay.Language.get('none'),
	label,
	onClearButtonPressed,
	onImageSelected,
}) {
	const imageTitleId = useId();

	return (
		<>
			<ClayForm.Group>
				<label htmlFor={imageTitleId}>{label}</label>
				<ClayInput
					className="page-editor__item-selector__content-input"
					id={imageTitleId}
					onClick={() =>
						openImageSelector((image) => {
							onImageSelected(image);
						})
					}
					placeholder={Liferay.Language.get('none')}
					readOnly
					sizing="sm"
					value={imageTitle}
				/>
			</ClayForm.Group>
			<ClayButton.Group>
				<div className="btn-group-item">
					<ClayButton
						displayType="secondary"
						onClick={() =>
							openImageSelector((image) => {
								onImageSelected(image);
							})
						}
						small
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</div>
				<div className="btn-group-item">
					<ClayButton
						borderless
						displayType="secondary"
						onClick={onClearButtonPressed}
						small
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				</div>
			</ClayButton.Group>
		</>
	);
}

ImageSelector.propTypes = {
	imageTitle: PropTypes.string,
	label: PropTypes.string,
	onClearButtonPressed: PropTypes.func.isRequired,
	onImageSelected: PropTypes.func.isRequired,
};
