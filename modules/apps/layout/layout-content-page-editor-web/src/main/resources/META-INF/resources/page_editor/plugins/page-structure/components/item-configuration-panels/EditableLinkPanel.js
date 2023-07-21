/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import LinkField, {
	TARGET_OPTIONS,
} from '../../../../app/components/fragment-configuration-fields/LinkField';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import selectEditableValue from '../../../../app/selectors/selectEditableValue';
import selectEditableValues from '../../../../app/selectors/selectEditableValues';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../app/store/index';
import updateEditableValues from '../../../../app/thunks/updateEditableValues';
import {deepEqual} from '../../../../app/utils/checkDeepEqual';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

export default function EditableLinkPanel({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const editableValues = useSelectorCallback(
		(state) => selectEditableValues(state, item.fragmentEntryLinkId),
		[item.fragmentEntryLinkId]
	);

	const editableValue = useSelectorCallback(
		(state) => {
			const editableValue =
				selectEditableValue(
					state,
					item.fragmentEntryLinkId,
					item.editableId,
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				) || {};

			if (!editableValue.config) {
				editableValue.config = {};
			}

			return editableValue;
		},
		[item.fragmentEntryLinkId, item.editableId],
		deepEqual
	);

	const handleValueSelect = (_, nextConfig) => {
		const config = {...nextConfig};

		if (
			Object.keys(nextConfig).length > 0 &&
			item.type !== EDITABLE_TYPES.link
		) {
			config.mapperType = 'link';
		}

		dispatch(
			updateEditableValues({
				editableValues: {
					...editableValues,
					[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
						...editableValues[EDITABLE_FRAGMENT_ENTRY_PROCESSOR],
						[item.editableId]: {...editableValue, config},
					},
				},

				fragmentEntryLinkId: item.fragmentEntryLinkId,
				segmentsExperienceId,
			})
		);
	};

	return (
		<LinkField
			field={{name: 'link'}}
			onValueSelect={handleValueSelect}
			value={editableValue.config}
		/>
	);
}

EditableLinkPanel.propTypes = {
	item: getEditableItemPropTypes({
		config: PropTypes.oneOfType([
			PropTypes.shape({
				href: PropTypes.string,
				target: PropTypes.oneOf(TARGET_OPTIONS),
			}),
			PropTypes.shape({
				classNameId: PropTypes.string,
				classPK: PropTypes.string,
				fieldId: PropTypes.string,
				target: PropTypes.oneOf(TARGET_OPTIONS),
			}),
			PropTypes.shape({
				mappedField: PropTypes.string,
				target: PropTypes.oneOf(TARGET_OPTIONS),
			}),
		]),
	}),
};
