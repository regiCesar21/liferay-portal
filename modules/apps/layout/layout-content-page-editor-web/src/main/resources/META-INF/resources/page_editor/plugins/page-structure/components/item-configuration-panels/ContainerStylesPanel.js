/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {SelectField} from '../../../../app/components/fragment-configuration-fields/SelectField';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import {getResponsiveConfig} from '../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../prop-types/index';
import {CommonStyles} from './CommonStyles';

export const ContainerStylesPanel = ({item}) => {
	const dispatch = useDispatch();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const {availableViewportSizes} = config;

	const containerConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const viewportSize = availableViewportSizes[selectedViewportSize];

	const onCustomStyleValueSelect = (name, value) => {
		const itemConfig = {[name]: value};

		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<>
			<p className="page-editor__row-styles-panel__viewport-label">
				<ClayIcon className="mr-2" symbol={viewportSize.icon} />
				{viewportSize.label}
			</p>

			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<div className="page-editor__page-structure__section__custom-styles">
					<h1 className="sr-only">
						{Liferay.Language.get('custom-styles')}
					</h1>

					<SelectField
						field={{
							label: Liferay.Language.get('container-width'),
							name: 'widthType',
							typeOptions: {
								validValues: [
									{
										label: Liferay.Language.get('fluid'),
										value: 'fluid',
									},
									{
										label: Liferay.Language.get(
											'fixed-width'
										),
										value: 'fixed',
									},
								],
							},
						}}
						onValueSelect={onCustomStyleValueSelect}
						value={item.config.widthType}
					/>
				</div>
			)}

			<CommonStyles
				commonStylesValues={containerConfig.styles}
				item={item}
			/>
		</>
	);
};

ContainerStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			styles: PropTypes.object,
		}),
	}),
};
