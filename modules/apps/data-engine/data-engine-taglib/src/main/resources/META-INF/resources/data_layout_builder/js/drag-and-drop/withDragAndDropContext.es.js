/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

export default function withDragAndDropContext(Component) {
	return (props) => (
		<DndProvider backend={HTML5Backend}>
			<Component {...props} />
		</DndProvider>
	);
}
