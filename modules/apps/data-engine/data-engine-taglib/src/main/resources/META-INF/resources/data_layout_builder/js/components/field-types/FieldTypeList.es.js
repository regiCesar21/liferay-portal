/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import CollapsablePanel from '../collapsable-panel/CollapsablePanel.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from './FieldType.es';

const FieldTypeWrapper = ({expanded, fieldType, showArrows, ...otherProps}) => {
	const getIcon = () => {
		if (showArrows) {
			return expanded ? 'angle-down' : 'angle-right';
		}

		return fieldType.icon;
	};

	return <FieldType {...otherProps} {...fieldType} icon={getIcon()} />;
};

export default ({
	deleteLabel,
	emptyState,
	fieldTypes,
	keywords,
	onClick,
	onDelete,
	onDoubleClick,
	showEmptyState = true,
}) => {
	const regex = new RegExp(keywords, 'ig');

	const filteredFieldTypes = fieldTypes
		.filter(({system}) => !system)
		.filter(({description, label}) => {
			if (!keywords) {
				return true;
			}

			return regex.test(description) || regex.test(label);
		});

	if (showEmptyState && !filteredFieldTypes.length) {
		return <EmptyState emptyState={emptyState} keywords={keywords} small />;
	}

	return filteredFieldTypes.map((fieldType, index) => {
		const {isFieldSet, nestedDataDefinitionFields = []} = fieldType;

		const handleOnClick = (props) => {
			if (fieldType.disabled || !onClick) {
				return;
			}

			onClick(props);
		};

		if (nestedDataDefinitionFields.length) {
			const Header = ({expanded, setExpanded}) => (
				<FieldTypeWrapper
					deleteLabel={deleteLabel}
					expanded={expanded}
					fieldType={{
						...fieldType,
						className: `${fieldType.className} field-type-header`,
					}}
					onClick={(props) => {
						setExpanded(!expanded);

						handleOnClick(props);
					}}
					onDelete={onDelete}
					onDoubleClick={onDoubleClick}
					setExpanded={setExpanded}
					showArrows
				/>
			);

			return (
				<div className="field-type-list" key={index}>
					<CollapsablePanel
						Header={Header}
						className={classNames({
							'field-type-fieldgroup': !isFieldSet,
							'field-type-fieldset': isFieldSet,
						})}
					>
						<div className="field-type-item position-relative">
							{nestedDataDefinitionFields.map(
								(nestedFieldType) => (
									<FieldTypeWrapper
										draggable={false}
										fieldType={{
											...nestedFieldType,
											disabled: fieldType.disabled,
										}}
										key={`${nestedFieldType.name}_${index}`}
									/>
								)
							)}
						</div>
					</CollapsablePanel>
				</div>
			);
		}

		return (
			<FieldTypeWrapper
				deleteLabel={deleteLabel}
				fieldType={fieldType}
				key={index}
				onClick={handleOnClick}
				onDelete={onDelete}
				onDoubleClick={onDoubleClick}
			/>
		);
	});
};
