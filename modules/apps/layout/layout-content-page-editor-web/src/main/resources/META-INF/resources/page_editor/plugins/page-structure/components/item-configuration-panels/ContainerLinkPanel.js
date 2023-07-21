/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import LinkField, {
	TARGET_OPTIONS,
} from '../../../../app/components/fragment-configuration-fields/LinkField';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateItemConfig from '../../../../app/thunks/updateItemConfig';
import {getLayoutDataItemPropTypes} from '../../../../prop-types/index';

export default function ContainerLinkPanel({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const handleValueSelect = (_, linkConfig) => {
		dispatch(
			updateItemConfig({
				itemConfig: {
					...item.config,
					link: linkConfig,
				},
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<LinkField
			field={{name: 'link'}}
			onValueSelect={handleValueSelect}
			value={item.config.link || {}}
		/>
	);
}

ContainerLinkPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			link: PropTypes.oneOfType([
				PropTypes.shape({
					href: PropTypes.string,
					target: PropTypes.oneOf(
						TARGET_OPTIONS.map((option) => option.value)
					),
				}),
				PropTypes.shape({
					classNameId: PropTypes.string,
					classPK: PropTypes.string,
					fieldId: PropTypes.string,
					target: PropTypes.oneOf(
						TARGET_OPTIONS.map((option) => option.value)
					),
				}),
				PropTypes.shape({
					mappedField: PropTypes.string,
					target: PropTypes.oneOf(
						TARGET_OPTIONS.map((option) => option.value)
					),
				}),
			]),
		}),
	}),
};
