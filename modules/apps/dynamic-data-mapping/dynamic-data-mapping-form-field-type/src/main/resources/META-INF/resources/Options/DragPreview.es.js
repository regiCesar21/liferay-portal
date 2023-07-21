/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';
import ReactDOM from 'react-dom';

import {OPTIONS_TYPES} from './DnD.es';

const layerStyles = {
	height: '100%',
	left: 0,
	pointerEvents: 'none',
	position: 'fixed',
	top: 0,
	width: '100%',
	zIndex: 100,
};

const getItemStyles = (currentOffset, ref) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const {x, y} = currentOffset;
	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview({children, component: Component}) {
	const ref = useRef();

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging || (isDragging && item.type !== OPTIONS_TYPES.OPTION)) {
		return null;
	}

	return ReactDOM.createPortal(
		<div style={layerStyles}>
			<Component
				{...item.option}
				className="dragging"
				ref={ref}
				style={getItemStyles(currentOffset, ref)}
			>
				{children({index: item.position, option: item.option})}
			</Component>
		</div>,
		document.body
	);
}
