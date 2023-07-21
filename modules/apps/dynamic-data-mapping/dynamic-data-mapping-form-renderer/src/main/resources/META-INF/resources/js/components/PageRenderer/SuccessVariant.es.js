/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayLayout from '@clayui/layout';
import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import {EVENT_TYPES} from '../../actions/eventTypes.es';
import {useForm} from '../../hooks/useForm.es';
import {usePage} from '../../hooks/usePage.es';
import {setValue} from '../../util/i18n.es';

export const Container = ({children, pages, strings = {}}) => {
	const {editingLanguageId} = usePage();
	const dispatch = useForm();

	return (
		<div className="page">
			<ClayLayout.Sheet className="ddm-page-success-layout simple-page">
				<div className="form-builder-layout">
					<h5 className="pagination">
						{strings['success-page'] ??
							Liferay.Language.get('success-page')}
					</h5>

					{children}
				</div>
			</ClayLayout.Sheet>

			<div className="ddm-paginated-builder-dropdown">
				<ClayDropDownWithItems
					className="dropdown-action"
					items={[
						{
							label: Liferay.Language.get('remove-success-page'),
							onClick: () => {
								dispatch({
									payload: {
										body: {[editingLanguageId]: ''},
										enabled: false,
										title: {[editingLanguageId]: ''},
									},
									type: EVENT_TYPES.SUCCESS_CHANGED,
								});
								dispatch({
									payload: pages.length - 1,
									type: EVENT_TYPES.CHANGE_ACTIVE_PAGE,
								});
							},
						},
					]}
					trigger={
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="ellipsis-v"
							title={Liferay.Language.get('page-options')}
						/>
					}
				/>
			</div>
		</div>
	);
};

export const Page = ({page}) => {
	const {editingLanguageId} = usePage();
	const dispatch = useForm();

	const {defaultLanguageId, successPageSettings} = page;

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	const {initialBody, initialTitle} = {
		initialBody:
			(successPageSettings.body &&
				(successPageSettings.body[editingLanguageId] ||
					successPageSettings.body[defaultLanguageId])) ||
			'',
		initialTitle:
			(successPageSettings.title &&
				(successPageSettings.title[editingLanguageId] ||
					successPageSettings.title[defaultLanguageId])) ||
			'',
	};

	const [body, setBody] = useState(initialBody);
	const [title, setTitle] = useState(initialTitle);

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setBody(initialBody);
			setTitle(initialTitle);
		}
	}, [editingLanguageId, initialBody, initialTitle, prevEditingLanguageId]);

	const onChange = (event, setting) => {
		dispatch({
			payload: setValue(
				successPageSettings,
				editingLanguageId,
				setting,
				event.target.value
			),
			type: EVENT_TYPES.SUCCESS_CHANGED,
		});
	};

	return (
		<div className="active ddm-form-page form-builder-success-page lfr-ddm-form-page">
			<input
				className="form-builder-page-header-title form-control p-0"
				maxLength="120"
				onChange={(event) => {
					setTitle(event.target.value);
					onChange(event, 'title');
				}}
				type="text"
				value={title}
			/>

			<input
				className="form-builder-page-header-description form-control p-0"
				maxLength="120"
				onChange={(event) => {
					setBody(event.target.value);
					onChange(event, 'body');
				}}
				type="text"
				value={body}
			/>
		</div>
	);
};
