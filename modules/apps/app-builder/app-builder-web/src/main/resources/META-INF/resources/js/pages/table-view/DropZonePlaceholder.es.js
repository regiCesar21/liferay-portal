/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {DragTypes} from 'data-engine-taglib';
import React, {useLayoutEffect, useState} from 'react';
import {useDrop} from 'react-dnd';

import {getColumnNode, getColumns} from './utils.es';

const getLeft = (container, index) => {
	switch (index) {
		case 0: {
			return getColumnNode(container, index).offsetLeft;
		}
		case 1: {
			const columnNode = getColumnNode(container, index);
			const previousColumnNode = getColumnNode(container, index - 1);

			return columnNode.offsetLeft - previousColumnNode.offsetWidth / 2;
		}
		default: {
			return getColumnNode(container, index - 1).offsetLeft;
		}
	}
};

const getWidth = (container, index, total) => {
	switch (index) {
		case 0: {
			return getColumnNode(container, index).offsetWidth / 2;
		}
		case 1: {
			return getColumnNode(container, index - 1).offsetWidth / 2;
		}
		case total - 1: {
			return getColumnNode(container, index - 1).offsetWidth;
		}
		default: {
			return getColumnNode(container, index).offsetWidth;
		}
	}
};

const getStyle = (container, index, total) => {
	return {
		height: container.offsetHeight,
		left: getLeft(container, index),
		position: 'absolute',
		top: container.offsetTop,
		width: getWidth(container, index, total),
	};
};

const Placeholder = ({container, index, onAddFieldName, total}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: DragTypes.DRAG_FIELD_TYPE,
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver(),
		}),
		drop: ({data: {name}}) => {
			onAddFieldName(name, index);
		},
	});

	return (
		<div
			className={classNames({
				'column-drop-zone-left': overTarget && index === 0,
				'column-drop-zone-right': overTarget && index > 0,
				hide: !canDrop,
				'target-over': overTarget,
			})}
			ref={drop}
			style={getStyle(container, index, total)}
		></div>
	);
};

export default ({container, fields, onAddFieldName}) => {
	const [empty, setEmpty] = useState(true);

	useLayoutEffect(() => {
		const columns = getColumns(container);

		setEmpty(columns.length === 0);
	}, [container, fields]);

	if (empty) {
		return null;
	}

	const columnPlaceholders = [];

	for (let i = 0; i < fields.length + 1; i++) {
		columnPlaceholders.push(fields[i] ? fields[i].name : 'last');
	}

	return columnPlaceholders.map((key, index) => (
		<Placeholder
			container={container}
			index={index}
			key={key}
			onAddFieldName={onAddFieldName}
			total={columnPlaceholders.length}
		/>
	));
};
