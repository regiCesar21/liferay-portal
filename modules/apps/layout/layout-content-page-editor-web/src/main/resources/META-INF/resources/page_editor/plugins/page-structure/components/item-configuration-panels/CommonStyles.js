/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import {FieldSet} from './FieldSet';

export const CommonStyles = ({commonStylesValues, item}) => {
	const {commonStyles} = config;
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const onCommonStylesValueSelect = (name, value) => {
		let itemConfig = {
			styles: {
				[name]: value,
			},
		};

		if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			itemConfig = {
				[selectedViewportSize]: {
					styles: {
						[name]: value,
					},
				},
			};
		}

		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<div className="page-editor__row-styles-panel__common-styles">
			<h1 className="sr-only">{Liferay.Language.get('common-styles')}</h1>

			{commonStyles.map((fieldSet, index) => {
				return (
					<FieldSet
						fields={fieldSet.styles}
						item={item}
						key={index}
						label={fieldSet.label}
						onValueSelect={onCommonStylesValueSelect}
						values={commonStylesValues}
					/>
				);
			})}
		</div>
	);
};

CommonStyles.propTypes = {
	commonStylesValues: PropTypes.object.isRequired,
	item: PropTypes.object.isRequired,
};
