/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import {ControlMenuBase} from '../../components/control-menu/ControlMenu.es';
import useDataDefinition from '../../hooks/useDataDefinition.es';
import withDDMForm, {
	useDDMFormSubmit,
	useDDMFormValidation,
} from '../../hooks/withDDMForm.es';
import {addItem, updateItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';

export const EditEntry = ({
	dataDefinitionId,
	dataRecordId,
	ddmForm,
	redirect,
	userLanguageId,
}) => {
	const {basePortletURL, portletId, showFormView, showTableView} = useContext(
		AppContext
	);
	const {availableLanguageIds, defaultLanguageId} = useDataDefinition(
		dataDefinitionId
	);
	const [submitting, setSubmitting] = useState(false);

	const isFormViewOnly = showFormView && !showTableView;
	const urlParams = new URLSearchParams(window.location.href);
	const backURL = urlParams.get(`_${portletId}_backURL`) || basePortletURL;

	const onCancel = useCallback(() => {
		if (redirect) {
			Liferay.Util.navigate(redirect);
		}
		else {
			Liferay.Util.navigate(backURL);
		}
	}, [redirect, backURL]);

	const onError = () => {
		errorToast();
		setSubmitting(false);
	};

	const validateForm = useDDMFormValidation(
		ddmForm,
		defaultLanguageId,
		availableLanguageIds
	);

	const onSubmit = useCallback(
		(event) => {
			event.preventDefault();
			setSubmitting(true);

			validateForm(event)
				.then((dataRecord) => {
					if (dataRecordId !== '0') {
						updateItem({
							endpoint: `/o/data-engine/v2.0/data-records/${dataRecordId}`,
							item: dataRecord,
							method: 'PATCH',
						})
							.then(() => {
								successToast(
									Liferay.Language.get('an-entry-was-updated')
								);
								onCancel();
							})
							.catch(onError);
					}
					else {
						addItem(
							`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-records`,
							dataRecord
						)
							.then(() => {
								successToast(
									Liferay.Language.get('an-entry-was-added')
								);
								onCancel();
							})
							.catch(onError);
					}
				})
				.catch(() => {
					setSubmitting(false);
				});
		},
		[dataDefinitionId, dataRecordId, onCancel, validateForm]
	);

	useDDMFormSubmit(ddmForm, onSubmit);

	useEffect(() => {
		const ddmReactForm = ddmForm.reactComponentRef.current;

		ddmReactForm.updateEditingLanguageId({
			editingLanguageId: userLanguageId,
			preserveValue: true,
		});
	}, [ddmForm, userLanguageId]);

	return (
		<>
			<ControlMenuBase
				backURL={isFormViewOnly ? null : redirect || backURL}
				title={
					dataRecordId !== '0'
						? Liferay.Language.get('edit-entry')
						: Liferay.Language.get('add-entry')
				}
				url={location.href}
			/>

			<ClayButton.Group className="app-builder-form-buttons" spaced>
				<Button disabled={submitting} onClick={onSubmit}>
					{Liferay.Language.get('save')}
				</Button>

				{!isFormViewOnly && (
					<Button displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</Button>
				)}
			</ClayButton.Group>
		</>
	);
};

export default withDDMForm(EditEntry);
