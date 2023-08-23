/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useEvaluate} from '../../hooks/useEvaluate.es';
import {useForm} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import fieldBlur from '../../thunks/fieldBlur.es';
import fieldChange from '../../thunks/fieldChange.es';
import fieldFocus from '../../thunks/fieldFocus.es';
import {getFormId, getFormNode, getFormTitle} from '../../util/formId.es';
import {Field} from '../Field/Field.es';
import * as DefaultVariant from './DefaultVariant.es';

export const Layout = ({
	components: Components = DefaultVariant,
	editable,
	rows,
}) => {
	const {
		activePage,
		allowNestedFields,
		containerElement,
		pageIndex,
		pages,
		spritemap,
	} = usePage();
	const createFieldChange = useEvaluate(fieldChange);
	const dispatch = useForm();
	const title = getFormTitle(getFormNode(containerElement.current));

	return (
		<Components.Rows
			activePage={activePage}
			editable={editable}
			pageIndex={pageIndex}
			rows={rows}
		>
			{({index: rowIndex, row}) => (
				<Components.Row key={rowIndex} row={row}>
					{({column, index}) => (
						<Components.Column
							activePage={activePage}
							allowNestedFields={allowNestedFields}
							column={column}
							editable={editable}
							index={index}
							key={index}
							pageIndex={pageIndex}
							rowIndex={rowIndex}
						>
							{(fieldProps) => (
								<Field
									{...fieldProps}
									activePage={activePage}
									editable={editable}
									key={
										fieldProps.field?.instanceId ??
										fieldProps.field.name
									}
									onBlur={(event, focusDuration) =>
										dispatch(
											fieldBlur({
												activePage,
												focusDuration,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												formPageTitle: Liferay.Util.escape(
													pages[activePage].title
												),
												properties: event,
												title,
											})
										)
									}
									onChange={(event) =>
										dispatch(
											createFieldChange({
												properties: event,
											})
										)
									}
									onFocus={(event) =>
										dispatch(
											fieldFocus({
												activePage,
												formId: getFormId(
													getFormNode(
														containerElement.current
													)
												),
												formPageTitle: Liferay.Util.escape(
													pages[activePage].title
												),
												properties: event,
												title,
											})
										)
									}
									pageIndex={pageIndex}
									spritemap={spritemap}
								/>
							)}
						</Components.Column>
					)}
				</Components.Row>
			)}
		</Components.Rows>
	);
};
