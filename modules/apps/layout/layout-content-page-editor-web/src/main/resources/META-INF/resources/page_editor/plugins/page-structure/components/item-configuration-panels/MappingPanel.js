/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useCollectionConfig} from '../../../../app/components/CollectionItemContext';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../app/config/constants/editableTypes';
import selectEditableValue from '../../../../app/selectors/selectEditableValue';
import {useDispatch, useSelector} from '../../../../app/store/index';
import updateEditableValues from '../../../../app/thunks/updateEditableValues';
import isMapped from '../../../../app/utils/isMapped';
import MappingSelector from '../../../../common/components/MappingSelector';
import {getEditableItemPropTypes} from '../../../../prop-types/index';

export function MappingPanel({item}) {
	const collectionConfig = useCollectionConfig();

	const {editableId, fragmentEntryLinkId, type} = item;

	const dispatch = useDispatch();
	const state = useSelector((state) => state);

	const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

	const processoryKey =
		type === EDITABLE_TYPES.backgroundImage
			? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
			: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

	const editableValue = selectEditableValue(
		state,
		fragmentEntryLinkId,
		editableId,
		processoryKey
	);

	const updateEditableValue = (newEditableValue) => {
		const nextEditableValues = {
			...fragmentEntryLink.editableValues,
			[processoryKey]: {
				...fragmentEntryLink.editableValues[processoryKey],
				[editableId]: {
					config: isMapped(newEditableValue)
						? {...editableValue.config, alt: '', imageTitle: ''}
						: editableValue.config,
					defaultValue: editableValue.defaultValue,
					...newEditableValue,
				},
			},
		};

		dispatch(
			updateEditableValues({
				editableValues: nextEditableValues,
				fragmentEntryLinkId,
				segmentsExperienceId: state.segmentsExperienceId,
			})
		);
	};

	return (
		<>
			{collectionConfig && (
				<p className="page-editor__mapping-panel__helper text-secondary">
					{Liferay.Language.get('collection-mapping-help')}
				</p>
			)}

			<MappingSelector
				fieldType={type}
				mappedItem={editableValue}
				onMappingSelect={updateEditableValue}
			/>
		</>
	);
}

MappingPanel.propTypes = {
	item: getEditableItemPropTypes(),
};
