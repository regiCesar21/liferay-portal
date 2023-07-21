/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

import {useSelector} from '../../store/index';
import UndoHistory from './UndoHistory';

export default function Undo({onRedo = () => {}, onUndo = () => {}}) {
	const undoHistory = useSelector((state) => state.undoHistory);
	const redoHistory = useSelector((state) => state.redoHistory);

	return (
		<>
			<ClayButton.Group className="d-block d-none mr-2">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('undo')}
					className="btn-monospaced"
					disabled={!undoHistory || !undoHistory.length}
					displayType="secondary"
					onClick={onUndo}
					small
					symbol="undo"
					title={Liferay.Language.get('undo')}
				/>
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('redo')}
					className="btn-monospaced"
					disabled={!redoHistory || !redoHistory.length}
					displayType="secondary"
					onClick={onRedo}
					small
					symbol="redo"
					title={Liferay.Language.get('redo')}
				/>
			</ClayButton.Group>
			<UndoHistory />
		</>
	);
}

Undo.propTypes = {
	onRedo: PropTypes.func,
	onUndo: PropTypes.func,
};
