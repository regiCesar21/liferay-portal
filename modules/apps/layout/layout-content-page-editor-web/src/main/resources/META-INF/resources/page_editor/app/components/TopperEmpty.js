/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useRef} from 'react';

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import selectCanUpdatePageStructure from '../selectors/selectCanUpdatePageStructure';
import {useSelector} from '../store/index';
import {TARGET_POSITION} from '../utils/dragAndDrop/constants/targetPosition';
import {useDropTarget} from '../utils/dragAndDrop/useDragAndDrop';
import getLayoutDataItemLabel from '../utils/getLayoutDataItemLabel';

export default function ({children, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);

	return canUpdatePageStructure ? (
		<TopperEmpty {...props}>{children}</TopperEmpty>
	) : (
		children
	);
}

function TopperEmpty({children, item}) {
	const containerRef = useRef(null);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const {
		canDropOverTarget,
		isOverTarget,
		sourceItem,
		targetPosition,
		targetRef,
	} = useDropTarget(item);

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const notDroppableMessage =
		isOverTarget && !canDropOverTarget
			? Liferay.Util.sub(
					Liferay.Language.get('a-x-cannot-be-dropped-inside-a-x'),
					[
						getLayoutDataItemLabel(sourceItem, fragmentEntryLinks),
						getLayoutDataItemLabel(item, fragmentEntryLinks),
					]
			  )
			: null;

	return React.Children.map(realChildren, (child) => {
		if (!child) {
			return child;
		}

		return (
			<>
				{React.cloneElement(child, {
					...child.props,
					className: classNames(child.props.className, {
						'drag-over-bottom':
							isOverTarget &&
							targetPosition === TARGET_POSITION.BOTTOM,
						'drag-over-middle':
							isOverTarget &&
							targetPosition === TARGET_POSITION.MIDDLE,
						'drag-over-top':
							isOverTarget &&
							targetPosition === TARGET_POSITION.TOP,
						'not-droppable': !!notDroppableMessage,
						'page-editor__topper': true,
					}),
					'data-not-droppable-message': notDroppableMessage,
					ref: (node) => {
						containerRef.current = node;
						targetRef(node);

						// Call the original ref, if any.

						if (typeof child.ref === 'function') {
							child.ref(node);
						}
						else if (child.ref && 'current' in child.ref) {
							child.ref.current = node;
						}
					},
				})}
			</>
		);
	});
}

TopperEmpty.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
