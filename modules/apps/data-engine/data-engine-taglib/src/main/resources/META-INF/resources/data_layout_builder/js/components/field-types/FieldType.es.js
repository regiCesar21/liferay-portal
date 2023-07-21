/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React, {useEffect, useState} from 'react';
import {useDrag} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

import {DRAG_FIELD_TYPE} from '../../drag-and-drop/dragTypes.es';
import Button from '../button/Button.es';
import DropDown from '../drop-down/DropDown.es';
import FieldTypeDragPreview from './FieldTypeDragPreview.es';

const ICONS = {
	checkbox_multiple: 'select-from-list',
	document_library: 'upload',
	numeric: 'caret-double',
	radio: 'radio-button',
	select: 'list',
};

export default (props) => {
	const {
		actions,
		active,
		className,
		deleteLabel = Liferay.Language.get('delete'),
		description,
		disabled,
		dragAlignment = 'left',
		draggable = true,
		dragType = DRAG_FIELD_TYPE,
		icon,
		label,
		name,
		onClick,
		onDelete,
		onDoubleClick,
	} = props;

	const [{dragging}, drag, preview] = useDrag({
		canDrag: (_) => !disabled && draggable,
		collect: (monitor) => ({
			dragging: monitor.isDragging(),
		}),
		item: {
			data: {...props},
			preview: () => <FieldTypeDragPreview {...props} />,
			type: dragType,
		},
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	const handleOnClick = () => {
		onClick({...props});
	};

	const handleOnDoubleClick = () => {
		if (disabled) {
			return;
		}

		onDoubleClick({...props});
	};

	const [loading, setLoading] = useState(false);

	const fieldIcon = ICONS[icon] ? ICONS[icon] : icon;

	return (
		<ClayLayout.ContentRow
			className={classnames(className, 'field-type', {
				active,
				disabled,
				dragging,
				loading,
			})}
			data-field-type-name={name}
			onClick={onClick && handleOnClick}
			onDoubleClick={onDoubleClick && handleOnDoubleClick}
			ref={drag}
			verticalAlign="center"
		>
			{draggable && dragAlignment === 'left' && (
				<ClayLayout.ContentCol className="pl-2 pr-2">
					<ClayIcon symbol="drag" />
				</ClayLayout.ContentCol>
			)}

			<ClayLayout.ContentCol
				className={classnames('pr-2', {
					'pl-2': dragAlignment === 'right',
				})}
			>
				<ClaySticker
					className="data-layout-builder-field-sticker"
					displayType="light"
					size="md"
				>
					<ClayIcon symbol={fieldIcon} />
				</ClaySticker>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol className="pr-2" expand>
				<h4 className="list-group-title text-truncate">
					<span>{label}</span>
				</h4>

				{description && (
					<p className="list-group-subtitle text-truncate">
						<small>{description}</small>
					</p>
				)}
			</ClayLayout.ContentCol>

			<div className="autofit-col pr-2">
				{actions && <DropDown actions={actions} />}
			</div>

			{draggable && dragAlignment === 'right' && (
				<ClayLayout.ContentCol className="pr-2">
					<ClayIcon symbol="drag" />
				</ClayLayout.ContentCol>
			)}

			{onDelete && (
				<div className="field-type-remove-icon">
					{loading ? (
						<ClayLoadingIndicator />
					) : (
						<ClayTooltipProvider>
							<Button
								borderless
								data-tooltip-align="right"
								data-tooltip-delay="200"
								displayType="secondary"
								onClick={(event) => {
									event.stopPropagation();

									setLoading(true);

									onDelete(name)
										.then(() => setLoading(false))
										.catch((error) => {
											setLoading(false);

											throw error;
										});
								}}
								symbol="times-circle"
								title={deleteLabel}
							/>
						</ClayTooltipProvider>
					)}
				</div>
			)}
		</ClayLayout.ContentRow>
	);
};
