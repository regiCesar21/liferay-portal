/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef, useState} from 'react';
import {useDragLayer} from 'react-dnd';

import {useConstants} from '../contexts/ConstantsContext';
import {useItems} from '../contexts/ItemsContext';
import getDescendantsCount from '../utils/getDescendantsCount';

const HANDLER_OFFSET = 10;

const getItemStyles = (currentOffset, ref, rtl) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + HANDLER_OFFSET - window.innerWidth
		: currentOffset.x - HANDLER_OFFSET;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview() {
	const ref = useRef();

	const {languageDirection, languageId} = useConstants();
	const items = useItems();
	const rtl = languageDirection[languageId] === 'rtl';

	const {currentOffset, isDragging, itemId} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		itemId: monitor.getItem()?.id,
	}));

	const [label, setLabel] = useState();

	useEffect(() => {
		const item = items.find(
			(item) => item.siteNavigationMenuItemId === itemId
		);

		if (item) {
			const descendantsCount = getDescendantsCount(items, itemId);

			setLabel(
				descendantsCount
					? Liferay.Util.sub(
							Liferay.Language.get('x-elements'),
							descendantsCount + 1
					  )
					: item.title
			);
		}
	}, [itemId, items]);

	return !isDragging ? null : (
		<div className="site-navigation__drag-preview">
			<div
				className="site-navigation__drag-preview__content"
				ref={ref}
				style={getItemStyles(currentOffset, ref, rtl)}
			>
				{label}
			</div>
		</div>
	);
}
