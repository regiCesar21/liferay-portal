/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const MIN_HEIGHT = 100;

export default function PageStructureSidebarSection({
	children,
	resizable = false,
	size = 1,
}) {
	const [handlerElement, setHandlerElement] = useState(null);
	const [panelElement, setPanelElement] = useState(null);
	const [panelHeight, setPanelHeight] = useState();
	const [resizing, setResizing] = useState(false);

	useEffect(() => {
		if (!handlerElement || !panelElement) {
			return;
		}

		let initialHeight = 0;
		let initialY = 0;
		let maxHeight = 0;

		const handleResize = (event) => {
			const delta = event.clientY - initialY;

			setPanelHeight(
				Math.max(Math.min(maxHeight, initialHeight - delta), MIN_HEIGHT)
			);
		};

		const handleResizeEnd = () => {
			document.body.removeEventListener('mousemove', handleResize);
			document.body.removeEventListener('mouseleave', handleResizeEnd);
			document.body.removeEventListener('mouseup', handleResizeEnd);

			setResizing(false);
		};

		const handleResizeStart = (event) => {
			initialHeight = panelElement.getBoundingClientRect().height;
			initialY = event.clientY;

			maxHeight =
				initialHeight +
				(handlerElement?.getBoundingClientRect().height || 0) +
				(panelElement.previousSibling?.previousSibling?.getBoundingClientRect()
					.height || 0) -
				MIN_HEIGHT;

			document.body.addEventListener('mousemove', handleResize);
			document.body.addEventListener('mouseleave', handleResizeEnd);
			document.body.addEventListener('mouseup', handleResizeEnd);

			setResizing(true);
		};

		handlerElement.addEventListener('mousedown', handleResizeStart);

		return () => {
			handlerElement.removeEventListener('mousedown', handleResizeStart);
			handleResizeEnd();
		};
	}, [handlerElement, panelElement]);

	return (
		<>
			{resizable && (
				<div
					className={classNames(
						'page-editor__page-structure__section__resize-handler',
						{
							active: resizing,
						}
					)}
					ref={setHandlerElement}
				/>
			)}

			<div
				className={classNames('page-editor__page-structure__section', {
					resized: !!panelHeight,
				})}
				ref={setPanelElement}
				style={{flexGrow: panelHeight ? 0 : size, height: panelHeight}}
			>
				{children}
			</div>
		</>
	);
}

PageStructureSidebarSection.propTypes = {
	resizable: PropTypes.bool,
	size: PropTypes.number,
};
