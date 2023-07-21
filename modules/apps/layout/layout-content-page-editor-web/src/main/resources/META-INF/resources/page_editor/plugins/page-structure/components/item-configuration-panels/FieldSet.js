/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {FRAGMENT_CONFIGURATION_FIELDS} from '../../../../app/components/fragment-configuration-fields/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {useSelector} from '../../../../app/store/index';
import {ConfigurationFieldPropTypes} from '../../../../prop-types/index';

const DISPLAY_SIZES = {
	small: 'small',
};

const fieldIsDisabled = (item, field) =>
	item.type === LAYOUT_DATA_ITEM_TYPES.container &&
	item.config?.widthType === 'fixed' &&
	(field.name === 'marginRight' || field.name === 'marginLeft');

export const FieldSet = ({fields, item = {}, label, onValueSelect, values}) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const availableFields =
		selectedViewportSize === VIEWPORT_SIZES.desktop
			? fields
			: fields.filter((field) => field.responsive);

	return (
		availableFields.length > 0 && (
			<>
				{label && (
					<div className="align-items-center d-flex justify-content-between page-editor__sidebar__fieldset-label pt-2">
						<p className="mb-2 text-uppercase">{label}</p>
					</div>
				)}

				<div className="page-editor__sidebar__fieldset">
					{availableFields.map((field, index) => {
						const FieldComponent =
							field.type &&
							FRAGMENT_CONFIGURATION_FIELDS[field.type];

						const fieldValue = Object.keys(values).includes(
							field.name
						)
							? values[field.name]
							: field.defaultValue;

						const visible =
							!field.dependencies ||
							field.dependencies.every(
								(dependency) =>
									values[dependency.styleName] ===
									dependency.value
							);

						return (
							visible && (
								<div
									className={classNames(
										'page-editor__sidebar__fieldset__field',
										{
											'page-editor__sidebar__fieldset__field-small':
												field.displaySize ===
												DISPLAY_SIZES.small,
										}
									)}
									key={index}
								>
									<FieldComponent
										disabled={fieldIsDisabled(item, field)}
										field={field}
										onValueSelect={onValueSelect}
										value={fieldValue}
									/>
								</div>
							)
						);
					})}
				</div>
			</>
		)
	);
};

FieldSet.propTypes = {
	fields: PropTypes.arrayOf(PropTypes.shape(ConfigurationFieldPropTypes)),
	label: PropTypes.string,
	onValueSelect: PropTypes.func.isRequired,
	values: PropTypes.object,
};
