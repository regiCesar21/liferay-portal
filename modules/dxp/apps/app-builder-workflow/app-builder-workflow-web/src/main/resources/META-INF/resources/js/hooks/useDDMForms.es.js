/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import {useCallback, useEffect, useMemo, useState} from 'react';

export function useDDMFormsSubmit(ddmForms, onSubmit) {
	useEffect(() => {
		const forms = ddmForms.map((ddmForm) =>
			ddmForm.reactComponentRef.current.getFormNode()
		);

		forms.forEach((formNode) =>
			formNode.addEventListener('submit', onSubmit)
		);

		return () => {
			forms.forEach((form) =>
				form.removeEventListener('submit', onSubmit)
			);
		};
	}, [ddmForms, onSubmit]);
}

export function useDDMFormsValidation(
	ddmForms,
	languageId,
	availableLanguageIds
) {
	const getFormsValues = useCallback(
		(ddmReactForms) => {
			const dataRecordValues = {};

			ddmReactForms.forEach((ddmReactForm) => {
				const visitor = new PagesVisitor(ddmReactForm.get('pages'));

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
						dataRecordValues[fieldName][languageId].push(value);
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
			});

			return {dataRecordValues};
		},
		[availableLanguageIds, languageId]
	);

	const validateForms = (ddmReactForms) => {
		return ddmReactForms
			.map(({validate}) => validate)
			.reduce((promises, validate) => {
				return promises.then((result) =>
					validate().then(Array.prototype.concat.bind(result))
				);
			}, Promise.resolve([]))
			.then((validations) => validations.every((isValid) => isValid));
	};

	return useCallback(
		(event) => {
			return new Promise((resolve, reject) => {
				if (typeof event.stopImmediatePropagation === 'function') {
					event.stopImmediatePropagation();
				}

				const ddmReactForms = ddmForms.map(
					(ddmForm) => ddmForm.reactComponentRef.current
				);

				validateForms(ddmReactForms)
					.then((isValid) => {
						if (isValid) {
							resolve(getFormsValues(ddmReactForms));
						}
						else {
							reject();
						}
					})
					.catch(reject);
			});
		},
		[ddmForms, getFormsValues]
	);
}

export default function useDDMForms(containerElementIds) {
	const [ddmForms, setDdmForms] = useState({});

	containerElementIds.forEach((containerElementId) => {
		if (!ddmForms[containerElementId]) {
			Liferay.componentReady(containerElementId).then((ddmForm) => {
				setDdmForms((prevForms) => ({
					...prevForms,
					[containerElementId]: ddmForm,
				}));
			});
		}
	});

	return useMemo(() => Object.values(ddmForms), [ddmForms]);
}
