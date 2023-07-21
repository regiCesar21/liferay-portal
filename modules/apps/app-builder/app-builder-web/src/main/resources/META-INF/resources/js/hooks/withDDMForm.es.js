/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import React, {useCallback, useEffect, useState} from 'react';

export function useDDMFormSubmit(ddmForm, onSubmit) {
	useEffect(() => {
		const formNode = ddmForm.reactComponentRef.current.getFormNode();

		formNode.addEventListener('submit', onSubmit);

		return () => formNode.removeEventListener('submit', onSubmit);
	}, [ddmForm, onSubmit]);
}

export function useDDMFormValidation(
	ddmForm,
	languageId,
	availableLanguageIds
) {
	return useCallback(
		(event) => {
			return new Promise((resolve, reject) => {
				if (typeof event.stopImmediatePropagation === 'function') {
					event.stopImmediatePropagation();
				}

				const ddmReactForm = ddmForm.reactComponentRef.current;

				ddmReactForm
					.validate()
					.then((isValidForm) => {
						if (!isValidForm) {
							return reject();
						}

						const dataRecordValues = {};

						const visitor = new PagesVisitor(
							ddmReactForm.get('pages')
						);

						const setDataRecord = ({
							fieldName,
							repeatable,
							type,
							value,
							visible,
						}) => {
							if (type === 'fieldset') {
								return;
							}

							if (!visible) {
								value = '';
							}

							if (!dataRecordValues[fieldName]) {
								dataRecordValues[fieldName] = {
									[languageId]: [],
								};
							}

							if (repeatable) {
								dataRecordValues[fieldName][languageId].push(
									value
								);
							}
							else {
								dataRecordValues[fieldName] = {
									[languageId]: value,
								};
							}

							availableLanguageIds.forEach((key) => {
								dataRecordValues[fieldName][key] =
									dataRecordValues[fieldName][languageId];
							});
						};

						visitor.mapFields(setDataRecord, true, true);

						resolve({dataRecordValues});
					})
					.catch(reject);
			});
		},
		[availableLanguageIds, ddmForm.reactComponentRef, languageId]
	);
}

export default function withDDMForm(Component) {
	return ({containerElementId, ...props}) => {
		const [ddmForm, setDDMForm] = useState();

		if (!ddmForm) {
			Liferay.componentReady(containerElementId).then(setDDMForm);
		}

		return ddmForm ? <Component ddmForm={ddmForm} {...props} /> : null;
	};
}
