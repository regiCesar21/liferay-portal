/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';

import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {config} from '../config/index';
import {useSelector} from '../store/index';

const getItemStyles = (currentOffset, ref, rtl) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + rect.width * 0.5 - window.innerWidth
		: currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview() {
	const ref = useRef();

	const languageId = useSelector((state) => state.languageId);

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging) {
		return null;
	}

	return (
		<div className="page-editor__drag-preview">
			<div
				className={classNames('page-editor__drag-preview__content', {
					'page-editor__drag-preview__content__treeview':
						item?.origin === ITEM_ACTIVATION_ORIGINS.structureTree,
				})}
				ref={ref}
				style={getItemStyles(
					currentOffset,
					ref,
					config.languageDirection[languageId] === 'rtl'
				)}
			>
				{item?.icon && <ClayIcon symbol={item.icon} />}
				{item?.name ? item.name : Liferay.Language.get('element')}
			</div>
		</div>
	);
}
