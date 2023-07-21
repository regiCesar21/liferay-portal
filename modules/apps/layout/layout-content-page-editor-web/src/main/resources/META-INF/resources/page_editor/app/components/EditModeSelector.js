/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import togglePermission from '../actions/togglePermission';
import selectCanSwitchEditMode from '../selectors/selectCanSwitchEditMode';
import {useDispatch, useSelector} from '../store/index';

const EDIT_MODES = {
	contentEditing: Liferay.Language.get('content-editing'),
	pageDesign: Liferay.Language.get('page-design'),
};

export default function EditModeSelector() {
	const canSwitchEditMode = useSelector(selectCanSwitchEditMode);
	const dispatch = useDispatch();

	const [active, setActive] = useState(false);
	const [editMode, setEditMode] = useState(
		canSwitchEditMode ? EDIT_MODES.pageDesign : EDIT_MODES.contentEditing
	);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			className="mr-3"
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className="form-control-select page-editor__edit-mode-selector text-left"
					disabled={!canSwitchEditMode}
					displayType="secondary"
					small
					type="button"
				>
					<span>{editMode}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item
					onClick={() => {
						setActive(false);
						setEditMode(EDIT_MODES.pageDesign);

						dispatch(togglePermission('UPDATE', true));
					}}
				>
					{EDIT_MODES.pageDesign}
				</ClayDropDown.Item>
				<ClayDropDown.Item
					onClick={() => {
						setActive(false);
						setEditMode(EDIT_MODES.contentEditing);

						dispatch(togglePermission('UPDATE', false));
					}}
				>
					{EDIT_MODES.contentEditing}
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
